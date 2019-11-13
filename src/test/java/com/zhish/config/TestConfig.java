package com.zhish.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 2086
 * @date 2019-10-25.
 * @description
 */

public class TestConfig {

  // zk根节点的名称
  public static final String ROOT_NODE = "/dist-test";
  // zk三级顺序子节点的名称
  public static final String TASK_NODE_NAME = "task_node";
  //zk二级节点,取jenkins tag,每次build互相隔离,如jenkins-distributed-test-35
  public static String tagNode;
  // 当前任务的节点名称,如/dist-test/jenkins-distributed-test-35/task_node_000000001
  public static String taskNode;
  // 任务id,类似elastic job的shardingItem
  public static int taskId;
  // 并行任务总数
  public static int taskCount;
  // 全量用例列表
  public static List<String> testCases = new ArrayList<>();
  // 当前任务用例列表
  public static List<String> targetCases = new ArrayList<>();

}
