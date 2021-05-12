package com.yd.business.material.dao;

import java.util.List;

import com.yd.business.material.bean.MaterialBean;

public interface IMaterialDao {

	List<MaterialBean> queryMaterialList(MaterialBean bean);

	int insertMaterial(MaterialBean bean);

	int updateMaterial(MaterialBean bean);

	int deleteMaterial(long id);

}
