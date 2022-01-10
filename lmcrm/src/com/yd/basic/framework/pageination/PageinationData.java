package com.yd.basic.framework.pageination;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author 盛孝强
 *
 * 2010-1-29 下午03:21:09
 */
@SuppressWarnings("rawtypes")
public class PageinationData
{
	public static final int RESULTCODE_SUCCESS = 1;
	public static final int RESULTCODE_FAILD = -1;
	
	private int resultCode ;
	private String resultDesc ;
	//当前页
	private int nowpage =1;
	//页面显示最大数,默认50
	private int pageSize = 50;
	//总页数
	private int totalpage;
	//总记录数
	private int totalcount = -1;
	//分页的数据
	private String orderby;
	private String groupby;

	private List dataList;
		public int getNowpage()
	{
		return nowpage;
	}

	public void setNowpage(int nowpage)
	{
		this.nowpage = nowpage;
	}
	public int getTotalcount()
	{
		return totalcount;
	}

	public void setTotalcount(int totalcount)
	{
		this.totalcount = totalcount;
	}
	
	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getTotalpage()
	{
		return totalpage;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public void setTotalpage(int totalpage)
	{
		this.totalpage = totalpage;
	}

	public List getDataList()
	{
		return dataList;
	}
	public void setDataList(List dataList)
	{
		this.dataList = dataList;
	}

	/**
	 * fenye 分页的方法,适用于数据量小的list
	 * @param list 要进行分页的数据
	 * @param nowpage 当前页
	 * @param pageSize 页面的最大数据量
	 * @return 返回分好页的数据
	 */
	public PageinationData fenye(List list ,int nowpage,int pageSize)
	{
		this.nowpage = nowpage;
		this.pageSize = pageSize;
		this.totalcount = list.size();
		int count = list.size();
		//如果list中没有数据,则返回默认值
		if(count == 0)
		{
			dataList = new ArrayList<Object>(0);
			this.nowpage = 1;
			this.totalpage = 1;
			return this;
		}
		//如果记录数不能整除,总页数+1
		if((count % pageSize) == 0)
		{
			totalpage = count/pageSize;
		}else
		{
			totalpage = count/pageSize + 1;
		}
		
		if(nowpage >= totalpage)
		{
			this.nowpage = totalpage;
			this.dataList = list.subList( (this.nowpage - 1 ) * pageSize , count);
			
		}else
		{
			this.dataList = list.subList( (this.nowpage - 1 ) * pageSize , pageSize * nowpage);
		}
		return this;
	}

	
	public void calculateTotalPage(int pageSize,int totalcount,int nowpage) throws Exception
	{
		this.pageSize = pageSize;
		this.nowpage = nowpage;
		this.totalcount = totalcount;
		
		calculateTotalPage();
	}
	/**
	 * 此方法用于计算总页数
	 * @return
	 * @throws Exception 
	 */
	public void calculateTotalPage() throws Exception
	{
		if(pageSize == 0)
		{
			throw new Exception("pageSize不能为0!");
		}
		if(totalcount == -1)
		{
			throw new Exception("totalcount不能为空");
		}
		if(totalcount == 0)
		{
			totalpage = 1;
			nowpage = 1;
		}
		
		//如果记录数不能整除,总页数+1
		if((totalcount % pageSize) == 0)
		{
			totalpage = totalcount/pageSize;
		}else
		{
			totalpage = totalcount/pageSize + 1;
		}
		if(nowpage > totalpage)
		{
			nowpage = totalpage;
		}
		
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getGroupby() {
		return groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}
	
	
}
