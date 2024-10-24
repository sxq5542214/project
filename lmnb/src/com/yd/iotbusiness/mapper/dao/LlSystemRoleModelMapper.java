package com.yd.iotbusiness.mapper.dao;

import com.yd.iotbusiness.mapper.model.LlSystemRoleModel;
import com.yd.iotbusiness.mapper.model.LlSystemRoleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LlSystemRoleModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    long countByExample(LlSystemRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    int deleteByExample(LlSystemRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    int insert(LlSystemRoleModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    int insertSelective(LlSystemRoleModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    List<LlSystemRoleModel> selectByExample(LlSystemRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    LlSystemRoleModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") LlSystemRoleModel row, @Param("example") LlSystemRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") LlSystemRoleModel row, @Param("example") LlSystemRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LlSystemRoleModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_system_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LlSystemRoleModel row);
}