package com.yd.basic.framework.persistence;

/**
 *  Oracle��ݿ�Dialectʵ��
 * 
 * @author Wan.Jian
 */
public class OracleDialect implements Dialect
{
    public String getLimitString(String sql, int offset, int limit)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select * from (select pagination.*, rownum paginationstartn from (");
        buffer.append(sql);
        buffer.append(") pagination where rownum < ");
        buffer.append(offset + limit + 1);
        buffer.append(") where paginationstartn > ");
        buffer.append(offset);

        return buffer.toString();
    }
}
