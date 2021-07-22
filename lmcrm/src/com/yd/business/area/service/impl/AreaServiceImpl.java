/**
 * 
 */
package com.yd.business.area.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.bean.AreaExtBean;
import com.yd.business.area.bean.BuildingExtBean;
import com.yd.business.area.dao.IAreaDao;
import com.yd.business.area.service.IAreaService;
import com.yd.business.area.service.IBuildingService;
import com.yd.business.company.bean.CompanyBean;
import com.yd.business.company.bean.CompanyExtBean;
import com.yd.business.company.service.ICompanyService;
import com.yd.util.AutoInvokeGetSetMethod;

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
	
}
