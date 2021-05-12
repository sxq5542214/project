package com.yd.business.wechat.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("wechatParentMaterial")
public class WechatParentMaterialBean extends WechatMaterialBean {

	/**
	 * 子素材集合
	 */
	private List<WechatMaterialBean> materialList;
	private String deliveryOriginal;

	public List<WechatMaterialBean> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<WechatMaterialBean> materialList) {
		this.materialList = materialList;
	}

	public String getDeliveryOriginal() {
		return deliveryOriginal;
	}

	public void setDeliveryOriginal(String deliveryOriginal) {
		this.deliveryOriginal = deliveryOriginal;
	}
	
	
}
