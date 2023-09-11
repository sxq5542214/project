/**
 * 
 */
package com.yd.basic.framework.persistence.typehandler;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.type.DateTypeHandler;


/**
 * @author ice
 *
 */
public class DateType extends DateTypeHandler {

	@Override
	public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return super.getNullableResult(cs, columnIndex);
	}
	
	@Override
	public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return super.getNullableResult(rs, columnIndex);
	}
	
	@Override
	public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return super.getNullableResult(rs, columnName);
	}
	
	@Override
	public Date getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return super.getResult(cs, columnIndex);
	}
	
	@Override
	public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return super.getResult(rs, columnIndex);
	}
	
	@Override
	public Date getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return super.getResult(rs, columnName);
	}
}
