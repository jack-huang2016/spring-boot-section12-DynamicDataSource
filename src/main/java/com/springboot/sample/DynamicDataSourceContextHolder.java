package com.springboot.sample;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源操作类
 */
public class DynamicDataSourceContextHolder {
    //存放当前线程使用的数据源类型信息
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    //存放所有数据源
    public static List<String> dataSourceIds = new ArrayList<>(); 

    /**
     * 绑定当前线程数据源路由的key
     * 使用完成后必须调用clearDataSourceType()方法删除
     */
    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

   /**
    * 获取当前线程的数据源路由的key
    */
    public static String getDataSourceType() {
        return contextHolder.get();
    }

   /**
    * 删除与当前线程绑定的数据源路由的key
    */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce在配置文件定义的多数据源集合中是否存在
     *
     * @param dataSourceId
     * @return
     * @author huang.yj
     * @create  2019年1月23日
     */
    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }
}
