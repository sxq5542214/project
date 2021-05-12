/**
 * 
 */
package com.yd.basic.framework.persistence.typehandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BigDecimalTypeHandler;


/**
 * @author ice
 *
 */
public class BigDecimalOptimizeTypeHandler extends BigDecimalTypeHandler {

	@Override
	public BigDecimal getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
//		System.out.println(" getResult(getNullableResult0 ........");
		return super.getNullableResult(cs, columnIndex);
	}
	
	@Override
	public BigDecimal getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
//		System.out.println(" getResult(getNullableResult1 ........");
		return super.getNullableResult(rs, columnIndex);
	}
	
	@Override
	public BigDecimal getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
//		System.out.println(" getResult(getNullableResult2 ........");
		return super.getNullableResult(rs, columnName);
	}
	
	@Override
	public BigDecimal getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
//		System.out.println(" getResult(CallableStatement ........");
		return super.getResult(cs, columnIndex);
	}
	
	@Override
	public BigDecimal getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
//		System.out.println(" getResult(ResultSet 1........");
		return super.getResult(rs, columnIndex);
	}
	
	@Override
	public BigDecimal getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
//		System.out.println(" getResult(ResultSet 2........");
		BigDecimal b = rs.getBigDecimal(columnName);
		if(b != null) return b.setScale(2, RoundingMode.HALF_UP);
		return super.getResult(rs, columnName);
	}
}
