/**
 * 
 */
package com.yd.business.material.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.material.bean.MaterialTypeBean;
import com.yd.business.material.dao.IMaterialTypeDao;

/**
 * @author ice
 *
 */
@Repository("materialTypeDao")
public class MaterialTypeDaoImpl extends BaseDao implements IMaterialTypeDao {
    public static final  String NAMESPACE = "materialType.";
    

    @Override
    public List<MaterialTypeBean> queryMaterialTypeList(MaterialTypeBean bean){
    	return sqlSessionTemplate.selectList(NAMESPACE + "queryMaterialTypeList", bean);
    }
    
    @Override
    public int insertMaterialType(MaterialTypeBean bean) {
    	return sqlSessionTemplate.insert(NAMESPACE + "insertMaterialType", bean);
    }

    @Override
    public int updateMaterialType(MaterialTypeBean bean) {
    	return sqlSessionTemplate.update(NAMESPACE +"updateMaterialType", bean);
    }

    @Override
    public int deleteMaterialType(long id) {
    	return sqlSessionTemplate.update(NAMESPACE +"deleteMaterialType", id);
    }
    
    
    
}
