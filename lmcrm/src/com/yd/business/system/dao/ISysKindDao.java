package com.yd.business.system.dao;

import java.util.List;

import com.yd.business.system.bean.SysKindBean;

public interface ISysKindDao {

	List<SysKindBean> querySysKindList(SysKindBean bean);

	int insertSysKind(SysKindBean bean);

	int updateSysKind(SysKindBean bean);

	int deleteSysKind(long id);

}
