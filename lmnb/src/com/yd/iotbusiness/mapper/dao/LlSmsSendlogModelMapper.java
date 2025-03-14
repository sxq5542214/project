package com.yd.iotbusiness.mapper.dao;

import com.yd.iotbusiness.mapper.model.LlSmsSendlogModel;
import com.yd.iotbusiness.mapper.model.LlSmsSendlogModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LlSmsSendlogModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    long countByExample(LlSmsSendlogModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    int deleteByExample(LlSmsSendlogModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    int insert(LlSmsSendlogModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    int insertSelective(LlSmsSendlogModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    List<LlSmsSendlogModel> selectByExample(LlSmsSendlogModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    LlSmsSendlogModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") LlSmsSendlogModel row, @Param("example") LlSmsSendlogModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") LlSmsSendlogModel row, @Param("example") LlSmsSendlogModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LlSmsSendlogModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_sms_sendlog
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LlSmsSendlogModel row);
}