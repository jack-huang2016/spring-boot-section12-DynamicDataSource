package com.springboot.sample.service;

import java.util.List;

import com.springboot.sample.entity.AreaNew;
import com.springboot.sample.entity.MgrParamScope;

public interface MgrParamScopeService {
	 public List<AreaNew> selectList();
	 
	 public List<MgrParamScope> getList();
}
