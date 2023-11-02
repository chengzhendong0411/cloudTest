package cn.itcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *  eureka 作用注册中心
 *   消费者如何获取服务提供者具体信息？
 *      服务提供者启动时向eureka注册自己信息
 *      eureka保存这些信息
 *      消费者根据服务名称向eureka拉取提供者信息
 *   消费者如何感知提供者健康状态？
 *      提供者每个30s向eureka发送心跳，报告健康状态。不正常会被删除
 *   多个服务提供者如何选择？
 *      消费者利用负载均衡算法，选择一个
 *
 *    EurekeServer ： 服务端，注册中心
 *      记录服务信息
 *       心跳监控
 *     EurekeClient: 客户端
 *          provider：服务提供者。
 *              注册自己信息到 EurekeServer
 *              每隔30秒发送心跳到 EurekeServer
 *          consumer:服务消费者
 *              根据名称从 EurekeServer 拉取服务列表
 *              基于服务负载均衡，选择一个服务发起远程调用
 *
 *
 * Ribbon
 * 、    负载均衡原理：
 *
 *   order-service -> Ribbon ->  Eureka-server(拉去服务列表)  -》 user-service:8081
 *
 *   Ribbon 底层是 LoadBalanceInterceptor
 *      负载均衡有：
 *            randomRule  随机
 *            RoundRobinRule 轮询
 *            ZoneAvoidanceRule 区域性敏感
 *                  以区域就近原则选择可用服务器进行选择，区域内没有可用机器则轮询
 *            WeightedResponseTimeRule 带权重的轮询
 *            BestAvailableRule 最少连接数 根据服务实例当前的连接数来选择最空闲实例的负载均衡策略
 *            AvailabillityFilteringRule ： 可用性敏感策略
 *
 *       原理： 请求进入ribbon后，被 LoadBalanceInterceptor 拦截器拦截。
 *             服务名称交给 RibbonLoadBalanceClient ，然后从Eureka获取服务列表，
 *             然后根据负载均衡策略 选择某个服务。然后请求这个服务地址。
 *
 *     ribbon 默认是懒加载。第一次访问时候才回创建 LoadBalanceClient 。请求时间长，
 *      而饥饿加载则是项目启动时创建，降低第一次访问耗时。
 *          启动方式： yml 配置 eager-load: enable: true  clients:userservice
 *
 *      ribbon 负载均衡配置两种方式：
 *          1、（局部生效）使用yml配置文件方式 (无需打包、无法全局配置)
 *              消费者中配置 服务名称（）userservice
 *                          ribbon:
 *                              NFloadBalancerRule ： random ....
 *          2、（全局生效）在消费者中Application中定义 randomRule()  RoundRobinRule （修改需要打包）通过定义IRele
 *
 *
 *  nacos
 *      启动方式：1、 bin 下面 startup.cmd
 *              2、cmd 命令行：  startup.cmd -m(模式) standalone(单机)
 *              startup.cmd -m standalone (单机版本启动、需要通过cmd 到该bin目录下)
 *
 *  nacos 服务分级存储模型 （按照地域进行了一个分级 服务-集群-实例）
 *      一个服务多个实例部署到多个地方
 *      1、服务跨集群调用问题：
 *          服务尽可能调用本地集群服务、跨集群调用延迟高
 *          本地集群不可用时，在访问其他服务并且报警告。
 *      2、  方法：namespace隔离 修改集群配置：
 *          discovery:
 *              cluster-name： H6  #集群名称
 *       3、修改负载均衡配置： NacosRule 规则，优先选择同集群的机器，本地集群找不到提供者，才会去找其他集群并且警告
 *              1、使用yml配置文件方式 (无需打包、无法全局配置)
 *  *              消费者中配置 服务名称（）userservice
 *  *                          ribbon:
 *  *                              NFloadBalancerRule ： random ....
 *         4、修改权重。
 *              1、通过客户端修改
 *              通过修改权重（0），进行停机版本升级。重启后到0.1，测试 慢慢增加！平滑升级
 *         5、环境隔离 namespace
 *                nacos中服务存储和数据存储的最外层都是一个namespace的东西，用来做最外层隔离
 *                现在客户端页面命名空间创建新的空间，得到空间id
 *                  不同namespace下环境服务环境不可见
 *              方式yml配置：  namespace: 空间id
 *          6、nacos 与 eureka区别
 *            不同
 *              nacos 对于服务提供者默认为临时实例、采用心跳检测，不正常会剔除
 *                                  非临时实例，nacos主动询问，不会剔除
 *                                      配置方式 myl中配置：ephemeral: false (临时实例)
 *                     对于消费者，服务变更nacos主动推送变更消息，如果有服务挂了会主动推送变更消息push
 *                     nacos默认采用ap方式，当集群存在非临时实例时，采用cp模式；eureaka采用ap模式
 *              相同：
 *                  都支持服务注册和服务拉取
 *                  都支持服务提供者心跳做健康检测
 *          7、nacos配置管理
 *              统一配置管理
 *              配置更改热更新，无需重启
 *                  方式1、在@Value注解变量所在类增加注解 @RefreshScope
 *                  方式二、使用@ConfigurationProperities注解，增加类properties
 *                  配合管理服务、
 *              配置热共享
 *
 *              1、（客户端）通过nocos客户端配置信息。将要热修改的数据进行配置。
 *              2、项目启动后先获取nacos地址得到配置文件配置（使用bootstrap.yml）获取地址，他加载优先级比application.yml
 *              (项目) 引入依赖包、在userservice（提供者 ）增加文件bootstrap.yml文件
 *              bootstrap.yml主要是配置nacos地址、当前环境、服务名称、文件后缀，决定去nacos读取那个文件
 *
 *              多环境配置： 读取文件配置有
 *                 环境配置(服务名 + profile.yaml) -> 共享配置 (服务名.yaml) -> 本地优先级
 *
 *  Feign 替换 restTemplate
 *      restTemplate 存在问题
 *          1、代码可读性差，有url路径
 *          2、复杂url难维护
 *      Feign是声明式http客户端，帮助我们优雅实现http请求
 *          方法 1、引入依赖
 *              2、加入注解 @EnableFeignClients 消费者 开启Feign
 *              3、编写Feign客户端，基于SpringMvc注解来声明远程调用信息
 *                      @FeignClient("服务名称“) 请求方式、路径、参数、返回值
 *      自定义配置：
 *          feign.logger.Level 修改日志级别  none basic headers full
 *          feign.coder.decoder 响应结果解析器 ，http远程结果调用解析  例如解析json   字符串为java对象
 *          feign.coder.Encoder 请求参数编码   将请求参数编码
 *          feign.Contract 响应结果解析器 默认是springmvc注解
 *          feign.Retryer 响应结果解析器 默认无、请求失败机制
 *          配置文件中配置，全局生效：
 *              default:
 *                 loggerLevel: headers
 *          局部生效：
 *              userservice:
 *                  loggerLevel: Full
 *          代码中注解配置
 *              @EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class)
 *      feign 底层优化：
 *          URLConnetion: 默认实现，不支持连接池。  启动断开三次握手 四次挥手，消耗资源
 *          Apache HttpClient: 支持连接池 需要引入包
 *          OKhttp: 支持连接池
 *          性能优化：
 *              底层连接池使替代 URL connetion
 *              日志级别最好用basic或者none 而不会用full
 *          通过注解方法引入 @EnableFeignClients(clients = UserClient.class
 *
 *
 *      springCloudGateway:  gateway、zuul
 *          功能： 身份认证 和权限校验 （让不让你进取）
 *                服务路由、负载均衡 （找到对应服务）
 *                请求限流
 *          springCloudGateway、 基于spring5中webFlux，属于响应式编程，性能更好
 *          zuul、基于servlet实现，是阻塞性编程
 *      网关拦截请求
 *          按照路由规则拉去服务列表，找到服务，负载均衡，发送请求
 *
 *      过滤器执行顺序：
 *          每个过滤器必须制定一个int类型order（通过@order实现），越小优先级越高执行顺序越前
 *          default > 路由过滤器 -> GlobalFilter 顺序执行
 *      跨域问题：域名不一样就是跨域 （域名不同、端口不同）
 *          浏览器禁止请求发送者 与服务端发送跨域ajax请求，请求被浏览器拦截的问题
 *          解决方式： CORS方案（简单配置一下），先询问一下服务端能否发生跨域，可以就跳过去
 *
 *
 *
 *      Sentinel
 *          流量控制
 *          隔离和降级
 *          授权规则
 *          规则持久化
 *
 */
// 增加注释启动eureka
@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}