package com.zhish.util;

import com.zhish.config.TestConfig;

import lombok.extern.slf4j.Slf4j;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author zhengda
 * @date 2019-10-25.
 * @description
 */
@Slf4j
public class TaskUtil {
  //会话超时时间
  private static final int SESSION_TIMEOUT = 30 * 1000;

  //连接超时时间
  private static final int CONNECTION_TIMEOUT = 3 * 1000;

  //ZooKeeper服务地址
  private static final String CONNECT_ADDR = "192.168.15.197:2181";

  //创建连接实例
  private static CuratorFramework client = null;

  public static void getId() {
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
    client = CuratorFrameworkFactory.builder()
        .connectString(CONNECT_ADDR).connectionTimeoutMs(CONNECTION_TIMEOUT)
        .sessionTimeoutMs(SESSION_TIMEOUT)
        .retryPolicy(retryPolicy)
        .build();
    client.start();
    try {
      String path =
          client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(TestConfig.taskNode);
      String split = path.split(TestConfig.TASK_NODE_NAME)[1];
      TestConfig.taskId = Integer.valueOf(split);
      log.info("task id is :" + TestConfig.taskId);
    } catch (Exception e) {
      log.error("create zk node failed", e);
    }
  }

  public static void clear() {
    try {
      log.info(TestConfig.tagNode);
      client.delete().deletingChildrenIfNeeded().forPath(TestConfig.tagNode);
      log.info("delete zk node success");
    } catch (Exception e) {
      log.error("delete zk node ignore");
    }
  }
}
