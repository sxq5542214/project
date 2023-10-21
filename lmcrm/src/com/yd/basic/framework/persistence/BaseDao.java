package com.yd.basic.framework.persistence;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.yd.basic.framework.pageination.PageinationData;

/**
 * 
 * Dao的基础类
 * @author ice
 */
public abstract class BaseDao 
{
    private static final String MyBATIS_NAMESPACE = "base.";
    protected Logger log = Logger.getLogger(this.getClass());

	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

    public void initialize() throws Exception
    {
    }
    
    /**
     * oracle数据库用
     * @param tableName
     * @return
     */
    public long createSequence(String tableName)
    {
        return (Long) sqlSessionTemplate.selectOne(MyBATIS_NAMESPACE+"sequence", "S_" + tableName + ".NEXTVAL");
    }

	/**
	 * 分页数据参数
	 * @param page
	 * @return
	 */
	protected RowBounds rowBound(PageinationData page) {
		int offset = 0;
		int limit = Integer.MAX_VALUE;
		if(page != null && page.getNowpage() != 0){
			offset = (page.getNowpage() - 1) * page.getPageSize();
//			limit = page.getNowpage() * page.getPageSize();
			limit = page.getPageSize();
		}
		return new RowBounds(offset, limit);
	}
}
