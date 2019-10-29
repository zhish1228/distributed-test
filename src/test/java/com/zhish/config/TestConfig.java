package com.zhish.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 2086
 * @date 2019-10-25.
 * @description
 */

public class TestConfig {

  // zk创建根节点的名称,需要唯一
  public static final String ROOT_NODE = "/dist-test";
  // zk创建顺序子节点的名称
  public static final String TASK_NODE = "task_node";
  //zk二级节点
  public static String tagNode;
  public static String zkNode;
  public static int taskId;
  public static int taskCount;
  public static List<String> testCases = new ArrayList<>();
  public static List<String> targetCases = new ArrayList<>();

}
