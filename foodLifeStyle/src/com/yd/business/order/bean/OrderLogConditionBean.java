package com.yd.business.order.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.business.isp.bean.InterfaceBean;
@Alias("orderLogCondition")

public class OrderLogConditionBean extends  OrderProductLogBean {
	private List<Integer> statusarr;

	public List<Integer> getStatusarr() {
		return statusarr;
	}

	public void setStatusarr(List<Integer> statusarr) {
		this.statusarr = statusarr;
	}

	
	
	
	
}
