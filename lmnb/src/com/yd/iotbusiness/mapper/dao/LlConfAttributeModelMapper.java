package com.yd.iotbusiness.mapper.dao;

import com.yd.iotbusiness.mapper.model.LlConfAttributeModel;
import com.yd.iotbusiness.mapper.model.LlConfAttributeModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LlConfAttributeModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    long countByExample(LlConfAttributeModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    int deleteByExample(LlConfAttributeModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    int insert(LlConfAttributeModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    int insertSelective(LlConfAttributeModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    List<LlConfAttributeModel> selectByExample(LlConfAttributeModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    LlConfAttributeModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") LlConfAttributeModel row, @Param("example") LlConfAttributeModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") LlConfAttributeModel row, @Param("example") LlConfAttributeModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LlConfAttributeModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_conf_attribute
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LlConfAttributeModel row);
}