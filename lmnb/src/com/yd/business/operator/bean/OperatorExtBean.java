/**
 * 
 */
package com.yd.business.operator.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.business.system.bean.SystemMenuExtModel;
import com.yd.iotbusiness.mapper.model.LlSystemMenuModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;

/**
 * @author ice
 *
 */
@Alias("operatorExt")
public class OperatorExtBean extends LmOperatorModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2091680158106665598L;
	
	private String name;
	private String avatar;
	private String introduction;
	private List<String> roles;
	private List<SystemMenuExtModel> menus;
	
	private String company_name;

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<SystemMenuExtModel> getMenus() {
		return menus;
	}

	public void setMenus(List<SystemMenuExtModel> menus) {
		this.menus = menus;
	}


	
	
}
