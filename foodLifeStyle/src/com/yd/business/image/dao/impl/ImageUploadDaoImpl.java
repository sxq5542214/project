/**
 * 
 */
package com.yd.business.image.dao.impl;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.image.bean.UploadLogBean;
import com.yd.business.image.dao.IImageUploadDao;

/**
 * @author ice
 *
 */
@Repository("imageUploadDao")
public class ImageUploadDaoImpl extends BaseDao implements IImageUploadDao {
	
	private static final String namespace = "imageUpload.";
	
	@Override
	public void createUploadLog(UploadLogBean bean){
		sqlSessionTemplate.insert(namespace + "createUploadLog", bean);
	}
	
	@Override
	public void updateUploadLog(UploadLogBean bean){
		sqlSessionTemplate.update(namespace +"updateUploadLog", bean);
	}
	
	
	
}
