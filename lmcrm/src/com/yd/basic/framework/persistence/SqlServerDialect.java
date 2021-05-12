package com.yd.basic.framework.persistence;

/**
 *  sqlserver��ݿ�Dialectʵ��
 * 
 * @author ice
 */
public class SqlServerDialect implements Dialect
{
//	可参考git项目：
	//	https://github.com/lidatui/mybatis-paginator/tree/master/src/main/java/com/github/miemiedev/mybatis/paginator/dialect
		
		public String getLimitString(String sql, int offset, int limit)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select top  from  (");
        buffer.append(sql);
        buffer.append(") pagination where rownum < ");
        buffer.append(offset + limit + 1);
        buffer.append(") where paginationstartn > ");
        buffer.append(offset);

        return buffer.toString();
    }
}
