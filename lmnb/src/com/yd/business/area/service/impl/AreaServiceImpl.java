/**
 * 
 */
package com.yd.business.area.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.bean.AddressBean;
import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.dao.IAreaDao;
import com.yd.business.area.service.IAreaService;
import com.yd.business.area.service.IBuildingService;
import com.yd.business.company.service.ICompanyService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.dao.LlAddressModelMapper;
import com.yd.iotbusiness.mapper.model.LlAddressModel;
import com.yd.iotbusiness.mapper.model.LlAddressModelExample;
import com.yd.iotbusiness.mapper.model.LlAddressModelExample.Criteria;
import com.yd.iotbusiness.mapper.model.LmUserModel;
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
	@Autowired
	private LlAddressModelMapper addressModelMapper;
	
	
	@Override
	public IOTWebDataBean queryAreaList(LlAddressModel model){
		
		IOTWebDataBean result = new IOTWebDataBean();
		LlAddressModelExample bean = new LlAddressModelExample();
		LlAddressModelExample.Criteria cri = bean.createCriteria();;
		cri.andSystemidEqualTo(model.getSystemid());
		cri.andLevelEqualTo(model.getLevel());
		cri.andParentIdEqualTo(model.getParentId());
		cri.andIdEqualTo(model.getId());
		
		List<LlAddressModel> list = addressModelMapper.selectByExample(bean );

		result.setData(list);
		result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
		
		return result;
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
	public IOTWebDataBean addOrUpdateAddress(LlAddressModel bean) throws Exception {
		IOTWebDataBean result = new IOTWebDataBean();

		// 填充fullname
		if(bean.getParentId() != null) {
			LlAddressModel parent = findAddressById(bean.getParentId());
			bean.setFullName(parent.getFullName()+bean.getName());
			bean.setLevel(parent.getLevel() + 1);
			if(parent.getSystemid().intValue() != bean.getSystemid()) {
				throw new Exception("update address systemid must equals parent systemid !");
			}
		}else {
			bean.setFullName(bean.getName());
		}
		
		//修改地址
		if(bean.getId() != null) {
			addressModelMapper.updateByPrimaryKeySelective(bean);
			//更新所有下级地址
			updateChildrensAddress(bean.getId(), bean.getFullName());
		}else {
			//  新增地址
			addressModelMapper.insertSelective(bean);
		}
		result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
		result.setData(bean);
		
		return result;
	}

	/**
	 * 更新下级地址的全名
	 * @param parentid
	 * @param fullName
	 */
	private void updateChildrensAddress(int parentid,String fullName) {
		LlAddressModelExample ex = new LlAddressModelExample();
		LlAddressModelExample.Criteria cri = ex.createCriteria();
		cri.andParentIdEqualTo(parentid);
		List<LlAddressModel> list = addressModelMapper.selectByExample(ex);
		for(LlAddressModel addr : list) {
			addr.setFullName(fullName + addr.getName());
			addressModelMapper.updateByPrimaryKeySelective(addr);
			// 继续更新下一级
			this.updateChildrensAddress(addr.getId(), addr.getFullName());
		}
	}
	
	
	@Override
	public List<AddressBean> queryAddressList(AddressBean bean)  {
		
				
		List<AddressBean> list = areaDao.queryAddressList(bean);
		
		return list;
	}

	@Override
	public LlAddressModel findAddressById(int id)  {
		
		LlAddressModel bean = addressModelMapper.selectByPrimaryKey(id);
		return bean;
	}
	@Override
	public LlAddressModel findAddressByFullname(String fullname)  {
		LlAddressModelExample ex = new LlAddressModelExample();
		LlAddressModelExample.Criteria cri = ex.createCriteria();
		cri.andFullNameEqualTo(fullname);
		
		List<LlAddressModel> list = addressModelMapper.selectByExample(ex);
		if(list.size()== 1 ) {
			return list.get(0);
		}else {
			throw new RuntimeException("未找到地址 或 地址有重复！请检查："+ fullname +" " +list.size());
		}
	}

	@Override
	public IOTWebDataBean deleteAddressByIdAndCompany(int id , int systemid) {
		IOTWebDataBean result = new IOTWebDataBean();
		LmUserModel user = new LmUserModel();
		user.setAddressid(id);

		List<UserInfoBean> list = (List<UserInfoBean>) userInfoService.queryUserList(user ).getData();
		if(list.size() > 0) {
			throw new RuntimeException("地址下有用户信息，不允许删除") ;
		}else {
			
			LlAddressModel model = new LlAddressModel();
			model.setParentId(id);
			List addressList = (List) queryAreaList(model ).getData();
			if(addressList.size() > 0) {
				throw new RuntimeException("有下级地址信息，不允许删除") ;
			}
			
			LlAddressModelExample ex = new LlAddressModelExample();
			LlAddressModelExample.Criteria cri = ex.createCriteria();
			cri.andIdEqualTo(id);
			cri.andSystemidEqualTo(systemid);
			addressModelMapper.deleteByExample(ex );
			result.setMessage("操作成功");
			return result;
			
		}
	}
	
	@Override
	public Set<Integer> querySubAddressIdsByArray(List<LlAddressModel> addressids){

		Set<Integer> result = new HashSet<>();
		for(LlAddressModel addr : addressids) {
			result.add(addr.getId());
			
			LlAddressModelExample ex = new LlAddressModelExample();
			LlAddressModelExample.Criteria cri = ex.createCriteria();
			cri.andParentIdEqualTo(addr.getId());
			
			List<LlAddressModel> list = addressModelMapper.selectByExample(ex );
			if(list.size()>=0) {
				Set<Integer> subIds = querySubAddressIdsByArray(list);
				result.addAll(subIds);
			}
			
		}
		return result ;
	}
	
	
}
