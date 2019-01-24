package com.springboot.sample;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 *
 * @author   黄耀杰
 * @create   2019年1月23日
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		 return DynamicDataSourceContextHolder.getDataSourceType();
	}

}
