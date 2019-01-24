package com.springboot.sample.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * 在方法上使用，用于指定使用哪个数据源
 *
 * @author  黄耀杰
 * @create  2019年1月23日
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {
	String name();
}
