package com.springboot.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.sample.entity.AreaNew;
import com.springboot.sample.entity.MgrParamScope;
import com.springboot.sample.service.MgrParamScopeService;

@RestController
public class DynamicDataSourceController {

	@Resource
	private MgrParamScopeService mgrParamScopeService;
	
    @RequestMapping(value = "/search")
    public Map<String,Object> index(){
    	List<MgrParamScope> list = mgrParamScopeService.getList();
    	List<AreaNew> selectList = mgrParamScopeService.selectList();
    	
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("list", list);
    	resultMap.put("selectList", selectList);
    	
    	return resultMap;
    }

}
