/**
 * 
 */
package com.yd.business.area.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.bean.AddressBean;
import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.bean.AreaExtBean;
import com.yd.business.area.bean.BuildingExtBean;
import com.yd.business.area.dao.IAreaDao;
import com.yd.business.area.service.IAreaService;
import com.yd.business.area.service.IBuildingService;
import com.yd.business.company.bean.CompanyBean;
import com.yd.business.company.bean.CompanyExtBean;
import com.yd.business.company.service.ICompanyService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Service("areaService")
public class AreaServiceImpl extends BaseService implements IAreaService {
	@Autowired
	private IAreaDao areaDao;
	@Autowired
	private IBuildingService buildingService;
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IUserInfoService userInfoService;
	
	@Override
	public List<AreaExtBean> queryAreaList(Long companyid){
		AreaBean bean = new AreaBean();
		bean.setA_companyid(companyid);
		return areaDao.queryAreaList(bean);
	}
	
	@Override
	public int addOrUpdateArea(AreaBean bean) {
		int result = 0 ;
		if(bean.getA_id() == null) {
			bean.setA_createdate(new Date());
			bean.setA_updatedate(bean.getA_createdate());
			result = areaDao.insertArea(bean);
		}else {
			bean.setA_updatedate(new Date());
			result = areaDao.updateArea(bean);
		}
		return result;
	}

	@Override
	public CompanyExtBean queryAreaAndBuildingTree(Long companyId) throws Exception {
		
		CompanyBean companyBean = companyService.findCompanyById(companyId);
		CompanyExtBean companyExtBean = new CompanyExtBean();
		AutoInvokeGetSetMethod.autoInvoke(companyBean, companyExtBean);
		companyExtBean.setHref("javascript:updateUserData(1," + companyBean.getC_id() +",'"+ companyBean.getC_name() +"',this);");
		companyExtBean.setText(companyBean.getC_name());
		
		List<AreaExtBean> areaList = queryAreaList(companyId);
		
		for(AreaExtBean area : areaList) {
			List<BuildingExtBean> buildExtList = buildingService.queryBuildingByArea(area.getA_id());
			area.setNodes(buildExtList);
		}
		
		companyExtBean.setNodes(areaList);
		
		return companyExtBean;
	}
	
	

	@Override
	public int addOrUpdateAddress(AddressBean bean) {
		//修改地址全称
		if(bean.getParent_id() != null) {
			AddressBean parent = areaDao.findAddressById(bean.getParent_id());
			bean.setFull_name(parent.getFull_name() + bean.getName());
		}
		
		
		int result = 0 ;
		if(bean.getId() == null) {
			bean.setCreate_time(DateUtil.getNowDateStr());
			bean.setUpdate_time(DateUtil.getNowDateStr());
			result = areaDao.insertAddress(bean);
		}else {
			bean.setUpdate_time(DateUtil.getNowDateStr());
			result = areaDao.updateAddress(bean);
		}
		return result;
	}

	@Override
	public List<AddressBean> queryAddressList(AddressBean bean)  {
		
				
		List<AddressBean> list = areaDao.queryAddressList(bean);
		
		return list;
	}
	
	@Override
	public AddressBean findAddressById(int id)  {
		AddressBean bean = new AddressBean();
		bean.setId(id);
		List<AddressBean> list = queryAddressList(bean);
		if(list.size()> 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public String deleteAddressByIdAndCompany(int id , int companyId) {
		
		UserInfoBean user = new UserInfoBean();
		user.setAddressId(id);

		List<UserInfoBean> list = userInfoService.queryUserInfo(user );
		if(list.size() > 0) {
			return "地址下有用户信息，不允许删除";
		}else {

			areaDao.deleteAddress(id,companyId);
			return "操作成功";
			
		}
	}
	
}
