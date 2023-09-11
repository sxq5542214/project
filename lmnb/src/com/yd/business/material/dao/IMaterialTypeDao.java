package com.yd.business.material.dao;

import java.util.List;

import com.yd.business.material.bean.MaterialTypeBean;

public interface IMaterialTypeDao {

	List<MaterialTypeBean> queryMaterialTypeList(MaterialTypeBean bean);

	int insertMaterialType(MaterialTypeBean bean);

	int updateMaterialType(MaterialTypeBean bean);

	int deleteMaterialType(long id);

}
