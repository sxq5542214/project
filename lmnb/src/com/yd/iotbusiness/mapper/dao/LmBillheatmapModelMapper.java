package com.yd.iotbusiness.mapper.dao;

import com.yd.iotbusiness.mapper.model.LmBillheatmapModel;
import com.yd.iotbusiness.mapper.model.LmBillheatmapModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LmBillheatmapModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    long countByExample(LmBillheatmapModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    int deleteByExample(LmBillheatmapModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    int insert(LmBillheatmapModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    int insertSelective(LmBillheatmapModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    List<LmBillheatmapModel> selectByExample(LmBillheatmapModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    LmBillheatmapModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") LmBillheatmapModel row, @Param("example") LmBillheatmapModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") LmBillheatmapModel row, @Param("example") LmBillheatmapModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LmBillheatmapModel row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_billheatmap
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LmBillheatmapModel row);
}