/**
 * 
 */
package com.yd.business.material.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.material.bean.MaterialBean;
import com.yd.business.material.dao.IMaterialDao;
import com.yd.business.user.bean.UserInfoBean;

/**
 * @author ice
 *
 */
@Repository("materialDao")
public class MaterialDaoImpl extends BaseDao implements IMaterialDao {
    public static final  String NAMESPACE = "material.";
    

    @Override
    public List<MaterialBean> queryMaterialList(MaterialBean bean){
    	return sqlSessionTemplate.selectList(NAMESPACE + "queryMaterialList", bean);
    }
    
    @Override
    public int insertMaterial(MaterialBean bean) {
    	return sqlSessionTemplate.insert(NAMESPACE + "insertMaterial", bean);
    }

    @Override
    public int updateMaterial(MaterialBean bean) {
    	return sqlSessionTemplate.update(NAMESPACE +"updateMaterial", bean);
    }

    @Override
    public int deleteMaterial(long id) {
    	return sqlSessionTemplate.update(NAMESPACE +"deleteMaterial", id);
    }
    
    
    
}
