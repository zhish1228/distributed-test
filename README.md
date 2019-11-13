# 分布式测试

## 简介
基于jenkins pipeline、zookeeper、testng编写的分布式自动化测试运行工具

## 与testng的parallel区别
* testng的parallel是方法、类力度控制
* 此工具的思想可以控制某些用例在某一批次执行,增强了调度管理能力
* 参考了spring boot基于yaml配置项目的思路,测试用例管理以yaml形式配置
* 摆脱了传统的group、dependsOnXXX调度用例思路,使用拦截器动态启/停用例和执行顺序

## 工程结构
```
/src
    /main  jenkins pipeline脚本
    /pic   jenkins pipeline配置
    /test  测试代码
```

## 思路
### jenkins
* pipeline通过input参数得到并行任务数量
### maven
* jenkins多node执行mvn clean test -Dxxx=xxx 传递测试用例yaml文件和任务总数
### testng
* ExecutionListener解析yaml中的测试用例
* 每个测试任务都会在zk上注册持久化顺序节点,获取最后一位数字为taskId,如果**用例index % 节点id = taskId**,此用例为当前task
* TestFilterAnnotationTransformer根据当前task用例列表动态启用、禁用当前task的测试用例,并设置Test注解的priority

## jenkins配置
```
Repository URL: https://github.com/zhish1228/distributed-test.git
branch: */master
Script Path: src/main/pipeline/pipeline.groovy
```
图片参考:
[jenkins pipeline配置](src/pic)

## 其它
Jenkins版本: ver. 2.190.2

注:jenkins旧版本可能对groovy部分语法不支持,尽量保证版本接近
