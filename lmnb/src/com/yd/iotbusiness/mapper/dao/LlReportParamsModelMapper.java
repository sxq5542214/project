package com.yd.iotbusiness.mapper.dao;

import com.yd.iotbusiness.mapper.model.LlReportParamsModel;
import com.yd.iotbusiness.mapper.model.LlReportParamsModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LlReportParamsModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    long countByExample(LlReportParamsModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    int deleteByExample(LlReportParamsModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    int insert(LlReportParamsModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    int insertSelective(LlReportParamsModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    List<LlReportParamsModel> selectByExample(LlReportParamsModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    LlReportParamsModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") LlReportParamsModel row, @Param("example") LlReportParamsModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") LlReportParamsModel row, @Param("example") LlReportParamsModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LlReportParamsModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LlReportParamsModel row);
}