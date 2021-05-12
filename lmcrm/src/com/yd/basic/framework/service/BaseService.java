package com.yd.basic.framework.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.yd.basic.framework.pageination.PageinationData;

public abstract class BaseService 
{
	protected Logger log =  Logger.getLogger(this.getClass());
	@SuppressWarnings("rawtypes")
	protected PageinationData calcPageInfo(List list,int totalCount,int limit, int pageSize) throws Exception
	{
		PageinationData data = new PageinationData();
		data.setDataList(list);
		data.setPageSize(pageSize);
		data.setTotalcount(totalCount);
		data.setNowpage(limit / pageSize );
		data.calculateTotalPage();
		
		return data;
	}
	
	public int calcMore(int page,int count,int total){
		int more = 1;
		if(page * count >= total){
			more = 0;
		}
		return more;
	}
	
	
}
