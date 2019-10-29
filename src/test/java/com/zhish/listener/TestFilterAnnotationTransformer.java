package com.zhish.listener;

import com.zhish.config.TestConfig;

import lombok.extern.slf4j.Slf4j;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.IListenersAnnotation;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Slf4j
public class TestFilterAnnotationTransformer implements IAnnotationTransformer {

  @Override
  public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

    String testName = annotation.getTestName();
    if (testName != null) {
      if (TestConfig.targetCases.contains(testName)) {
        // 根据yaml配置重排用例执行顺序
        annotation.setPriority(TestConfig.targetCases.indexOf(testName));
      } else {
        annotation.setEnabled(false);
      }
    }
  }

  @Override
  public void transform(IConfigurationAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

  }

  @Override
  public void transform(IDataProviderAnnotation annotation, Method method) {

  }

  @Override
  public void transform(IFactoryAnnotation annotation, Method method) {

  }

  @Override
  public void transform(IListenersAnnotation annotation, Class testClass) {

  }

}