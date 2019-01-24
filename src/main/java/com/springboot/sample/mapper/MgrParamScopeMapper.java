package com.springboot.sample.mapper;

import java.util.List;
import java.util.Map;
import com.springboot.sample.entity.MgrParamScope;

public interface MgrParamScopeMapper {
	public List<MgrParamScope> selectByParams(Map<String,Object> params);
}
