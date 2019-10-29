# 分布式测试

## 简介
基于jenkins pipeline、zookeeper、testng整的一个测试用例分布式运行的工具

## 与testng的parallel区别
testng的parallel只能在方法、类级别控制并行力度
目前工具的思想可以控制某些用例在某一批次执行,强调了调度能力

## 思路
### jenkins
* pipeline通过input参数得到并行任务数量
### maven
* 执行mvn clean test -Dxxx=xxx 时传递并行任务数
### testng
* testng拦截器获取全部yaml中的测试用例,在zk上注册持久化顺序节点,获取最后一位数字为taskId,如果**用例index % 节点id = taskId**,此用例为当前task执行(ExecutionListener类)
* 根据当前task用例列表动态启用、禁用当前task的测试用例(TestFilterAnnotationTransformer类)

## jenkins配置
```
Repository URL: https://github.com/zhish1228/distributed-test.git
branch: */master
Script Path: src/main/pipeline/pipeline.groovy
```

[jenkins pipeline配置](src/pic)

## 其它
Jenkins版本: ver. 2.190.2

注:jenkins旧版本可能对groovy部分语法不支持
