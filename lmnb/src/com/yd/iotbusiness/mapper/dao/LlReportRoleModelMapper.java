package com.yd.iotbusiness.mapper.dao;

import com.yd.iotbusiness.mapper.model.LlReportRoleModel;
import com.yd.iotbusiness.mapper.model.LlReportRoleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LlReportRoleModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    long countByExample(LlReportRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    int deleteByExample(LlReportRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    int insert(LlReportRoleModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    int insertSelective(LlReportRoleModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    List<LlReportRoleModel> selectByExample(LlReportRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    LlReportRoleModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") LlReportRoleModel row, @Param("example") LlReportRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") LlReportRoleModel row, @Param("example") LlReportRoleModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LlReportRoleModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LlReportRoleModel row);
}