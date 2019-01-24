package com.springboot.sample.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.springboot.sample.DynamicDataSourceContextHolder;
import com.springboot.sample.annotation.TargetDataSource;

/**
 * 切换数据源Advice
 *
 * @author huang.yj 
 * @create 2019年1月23日
 */
@Aspect
@Order(-100)// 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
        String dsId = ds.name();
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            System.out.println("数据源[" + ds.name() + "]不存在，使用默认数据源 > " + point.getSignature());
        } else {
        	System.out.println("Use DataSource : " + ds.name() + " > " + point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(ds.name());
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
    	System.out.println("Revert DataSource : " + ds.name() + " > " + point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
