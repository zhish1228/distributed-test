package com.zhish.listener;


import com.zhish.config.TestConfig;
import com.zhish.pojo.TestCaseYaml;
import com.zhish.util.TaskUtil;

import lombok.extern.slf4j.Slf4j;

import org.testng.IExecutionListener;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author 2086
 * @date 2019-10-12.
 * @description
 */
@Slf4j
public class ExecutionListener implements IExecutionListener {

  @Override
  public void onExecutionStart() {

    log.info("开始读取测试用例配置信息");
    // 通过yaml获取所有用例
    getAllCases();
    // 获取jenkins环境变量
//    getJenkinsJobTag();
    // 获取子任务id
//    TaskUtil.getId();
    // 设置当前任务的用例列表
//    setTargetCase();
  }

  private void setTargetCase() {
    int caseCount = TestConfig.testCases.size();
    for (int i = 0; i < caseCount; i++) {
      if (i % TestConfig.taskCount == TestConfig.taskId) {
        TestConfig.targetCases.add(TestConfig.testCases.get(i));
      }
    }
    log.info("当前task执行用例为:" + String.join(",", TestConfig.targetCases));
  }

  private void getJenkinsJobTag() {

    String buildTag = System.getenv("BUILD_TAG");
    TestConfig.tagNode = TestConfig.ROOT_NODE + "/" + buildTag;
    TestConfig.taskNode = TestConfig.tagNode + "/" + TestConfig.TASK_NODE_NAME;
    String taskCount = System.getProperty("taskCount");
    TestConfig.taskCount = Integer.valueOf(taskCount);
    log.info("taskCount is:" + taskCount);
  }

  private void getAllCases() {
    String casefile = System.getProperty("caseFile");
    Yaml yaml = new Yaml();
    File file = new File("src/test/resources/case.yml");
    try {
      TestCaseYaml testCaseYaml = yaml.loadAs(new FileInputStream(file), TestCaseYaml.class);
      TestConfig.testCases.addAll(testCaseYaml.getCases());
    } catch (FileNotFoundException e) {
      log.error("无法读取yml文件" + e);
    }
    log.info("测试用例读取完毕,读取用例{}条", TestConfig.testCases.size());
    log.info("测试用例全集为:" + String.join(",", TestConfig.testCases));
  }

  /**
   * Invoked once all the suites have been run.
   */
  @Override
  public void onExecutionFinish() {
//    TaskUtil.clear();
  }
}
