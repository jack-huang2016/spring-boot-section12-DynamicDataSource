package com.springboot.sample.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.springboot.sample.annotation.TargetDataSource;
import com.springboot.sample.entity.AreaNew;
import com.springboot.sample.entity.MgrParamScope;
import com.springboot.sample.mapper.AreaNewMapper;

@Service
public class MgrParamScopeServiceImpl implements MgrParamScopeService{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private AreaNewMapper areaNewMapper;
	
	 /**
     * 不指定数据源则使用默认数据源
     *
     * @return
     * @author huang.yj
     * @create  2019年1月23日
     */
    public List<MgrParamScope> getList(){
    	List<MgrParamScope> result = null;
    	try{
    		String sql = "select * from U_DB_PT.MGR_PARAM_SCOPE";
    		result = jdbcTemplate.query(sql,new RowMapper<MgrParamScope>(){

                @Override
                public MgrParamScope mapRow(ResultSet rs, int rowNum) throws SQLException {
                	MgrParamScope mgrParamScope = new MgrParamScope();
            		mgrParamScope.setApiId(rs.getInt("API_ID"));
            		mgrParamScope.setParamKey(rs.getString("PARAM_KEY"));
            		mgrParamScope.setToken(rs.getString("TOKEN"));
            		mgrParamScope.setValue(rs.getString("VALUE"));
                    return mgrParamScope;
                }
            });
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    			
        return result;
    }
    
    
    /**
     * 指定数据源
     *
     * @return
     * @author huang.yj
     * @create  2019年1月23日
     */
    @TargetDataSource(name="bi1")
    public List<AreaNew> selectList(){
        return areaNewMapper.selectList();
    }
    
}
