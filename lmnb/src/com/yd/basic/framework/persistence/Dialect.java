package com.yd.basic.framework.persistence;

/**
 * ��ݿ�Dialect�ӿ�
 * 
 * @author Wan.Jian
 */
public interface Dialect
{
    public String getLimitString(String sql, int offset, int limit);
}
