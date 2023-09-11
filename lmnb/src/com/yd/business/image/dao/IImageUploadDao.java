/**
 * 
 */
package com.yd.business.image.dao;

import com.yd.business.image.bean.UploadLogBean;

/**
 * @author ice
 *
 */
public interface IImageUploadDao {

	void createUploadLog(UploadLogBean bean);

	void updateUploadLog(UploadLogBean bean);

}
