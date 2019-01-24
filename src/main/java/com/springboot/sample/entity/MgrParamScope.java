package com.springboot.sample.entity;

import java.io.Serializable;

/**
 * 请写出类的用途 
 * @author user
 * @date  2018-01-11 11:50:13
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public class MgrParamScope implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -803904122948458856L;

	/**
     * API ID
     */
    private Integer apiId;

    /**
     * TOKEN
     */
    private String token;

    /**
     * 参数名
     */
    private String paramKey;

    /**
     * 参数值范围
     */
    private String value;

    /**
     * 
     * {@linkplain #apiId}
     *
     * @return the value of U_DB_PT.MGR_PARAM_SCOPE.API_ID
     */
    public Integer getApiId() {
        return apiId;
    }

    /**
     * 
     * {@linkplain #apiId}
     * @param apiId the value for U_DB_PT.MGR_PARAM_SCOPE.API_ID
     */
    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    /**
     * 
     * {@linkplain #token}
     *
     * @return the value of U_DB_PT.MGR_PARAM_SCOPE.TOKEN
     */
    public String getToken() {
        return token;
    }

    /**
     * 
     * {@linkplain #token}
     * @param token the value for U_DB_PT.MGR_PARAM_SCOPE.TOKEN
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 
     * {@linkplain #paramKey}
     *
     * @return the value of U_DB_PT.MGR_PARAM_SCOPE.PARAM_KEY
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * 
     * {@linkplain #paramKey}
     * @param paramKey the value for U_DB_PT.MGR_PARAM_SCOPE.PARAM_KEY
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * 
     * {@linkplain #value}
     *
     * @return the value of U_DB_PT.MGR_PARAM_SCOPE.VALUE
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * {@linkplain #value}
     * @param value the value for U_DB_PT.MGR_PARAM_SCOPE.VALUE
     */
    public void setValue(String value) {
        this.value = value;
    }
}