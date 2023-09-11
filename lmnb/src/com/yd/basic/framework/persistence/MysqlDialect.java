package com.yd.basic.framework.persistence;

/**
 *  Oracle��ݿ�Dialectʵ��
 * 
 * @author Wan.Jian
 */
public class MysqlDialect implements Dialect
{
    public String getLimitString(String sql, int limit, int pageSize)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(sql);
        buffer.append(" limit ");
        buffer.append(limit );
        buffer.append("," +pageSize );

        return buffer.toString();
    }
}
