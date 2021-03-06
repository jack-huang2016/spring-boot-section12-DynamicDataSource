package com.springboot.sample;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private ConversionService conversionService = new DefaultConversionService(); 
	
	//存放数据源的公共配置，每个数据源都会绑定该PropertyValues集合中的属性
    private PropertyValues dataSourcePropertyValues;
    
    //如配置文件中未指定数据源类型，使用该默认值
    private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
    //private static final Object DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";
    
    //主数据源
    private DataSource defaultDataSource;
    
    //其他数据源，不包含主数据源
    private Map<String, DataSource> customDataSources = new HashMap<>();

    /**
     * 先走该方法，步奏一，加载多数据源配置
     */
	@Override
	public void setEnvironment(Environment env) {
		initDefaultDataSource(env);
        initCustomDataSources(env);
	}

	/**
	 * 步奏二
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		   Map<Object, Object> targetDataSources = new HashMap<Object, Object>(); //存放所有数据源
	        // 将主数据源添加到更多数据源中
	        targetDataSources.put("dataSource", defaultDataSource);
	        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
	        // 添加更多数据源
	        targetDataSources.putAll(customDataSources);
	        for (String key : customDataSources.keySet()) {
	            DynamicDataSourceContextHolder.dataSourceIds.add(key);
	        }

	        // 创建DynamicDataSource，相当于配置动态数据源的那些xml配置，改用成以下java代码的形式
	        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
	        beanDefinition.setBeanClass(DynamicDataSource.class);
	        beanDefinition.setSynthetic(true);
	        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
	        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
	        mpv.addPropertyValue("targetDataSources", targetDataSources);
	        registry.registerBeanDefinition("dataSource", beanDefinition);

	        System.out.println("Dynamic DataSource Registry");
	}
	
	/**
     * 创建数据源
     *
     * @param type
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @return
     * @author huang.yj
     * @create 2019年1月23日
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            Object type = dsMap.get("type");
            if (type == null)
                type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource

            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 初始化主数据源
     *
     * @author huang.yj
     * @create 2019年1月23日
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源配置
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("type", propertyResolver.getProperty("type"));
        dsMap.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
        dsMap.put("url", propertyResolver.getProperty("url"));
        dsMap.put("username", propertyResolver.getProperty("username"));
        dsMap.put("password", propertyResolver.getProperty("password"));

        defaultDataSource = buildDataSource(dsMap);

        dataBinder(defaultDataSource, env);
    }
    
    /**
     * 为每个DataSource绑定公共的配置
     *
     * @param dataSource
     * @param env
     * @author huang.yj
     * @create  2019年1月23日
     */
    private void dataBinder(DataSource dataSource, Environment env){
        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        //dataBinder.setValidator(new LocalValidatorFactory().run(this.applicationContext));
        dataBinder.setConversionService(conversionService);
        dataBinder.setIgnoreNestedProperties(false);//false
        dataBinder.setIgnoreInvalidFields(false);//false
        dataBinder.setIgnoreUnknownFields(true);//true
        if(dataSourcePropertyValues == null){
            Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
            Map<String, Object> values = new HashMap<>(rpr);
            // 排除已经设置的属性
            values.remove("type");
            values.remove("driver-class-name");
            values.remove("url");
            values.remove("username");
            values.remove("password");
            dataSourcePropertyValues = new MutablePropertyValues(values);
        }
        dataBinder.bind(dataSourcePropertyValues);
    }
    
    /**
     * 初始化更多数据源，不包含主数据源
     *
     * @author huang.yj
     * @create 2019年1月23日
     */
    private void initCustomDataSources(Environment env) {
        // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
        String dsPrefixs = propertyResolver.getProperty("names");
        for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
            Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
            DataSource ds = buildDataSource(dsMap);
            customDataSources.put(dsPrefix, ds);
            dataBinder(ds, env);
        }
    }
    
}
