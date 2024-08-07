package com.yd.iotbusiness.mapper.model;

import java.io.Serializable;
import java.util.Date;

public class LmPaperModel implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.systemid
     *
     * @mbg.generated
     */
    private Integer systemid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.globalid
     *
     * @mbg.generated
     */
    private Integer globalid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.code
     *
     * @mbg.generated
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.background
     *
     * @mbg.generated
     */
    private String background;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.createtime
     *
     * @mbg.generated
     */
    private Date createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.invalid
     *
     * @mbg.generated
     */
    private Byte invalid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_paper.style
     *
     * @mbg.generated
     */
    private String style;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lm_paper
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    protected Integer rows;

    protected Integer page;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.id
     *
     * @return the value of lm_paper.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.id
     *
     * @param id the value for lm_paper.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.systemid
     *
     * @return the value of lm_paper.systemid
     *
     * @mbg.generated
     */
    public Integer getSystemid() {
        return systemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.systemid
     *
     * @param systemid the value for lm_paper.systemid
     *
     * @mbg.generated
     */
    public void setSystemid(Integer systemid) {
        this.systemid = systemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.globalid
     *
     * @return the value of lm_paper.globalid
     *
     * @mbg.generated
     */
    public Integer getGlobalid() {
        return globalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.globalid
     *
     * @param globalid the value for lm_paper.globalid
     *
     * @mbg.generated
     */
    public void setGlobalid(Integer globalid) {
        this.globalid = globalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.code
     *
     * @return the value of lm_paper.code
     *
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.code
     *
     * @param code the value for lm_paper.code
     *
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.title
     *
     * @return the value of lm_paper.title
     *
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.title
     *
     * @param title the value for lm_paper.title
     *
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.background
     *
     * @return the value of lm_paper.background
     *
     * @mbg.generated
     */
    public String getBackground() {
        return background;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.background
     *
     * @param background the value for lm_paper.background
     *
     * @mbg.generated
     */
    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.createtime
     *
     * @return the value of lm_paper.createtime
     *
     * @mbg.generated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.createtime
     *
     * @param createtime the value for lm_paper.createtime
     *
     * @mbg.generated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.remark
     *
     * @return the value of lm_paper.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.remark
     *
     * @param remark the value for lm_paper.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.invalid
     *
     * @return the value of lm_paper.invalid
     *
     * @mbg.generated
     */
    public Byte getInvalid() {
        return invalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.invalid
     *
     * @param invalid the value for lm_paper.invalid
     *
     * @mbg.generated
     */
    public void setInvalid(Byte invalid) {
        this.invalid = invalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_paper.style
     *
     * @return the value of lm_paper.style
     *
     * @mbg.generated
     */
    public String getStyle() {
        return style;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_paper.style
     *
     * @param style the value for lm_paper.style
     *
     * @mbg.generated
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_paper
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", systemid=").append(systemid);
        sb.append(", globalid=").append(globalid);
        sb.append(", code=").append(code);
        sb.append(", title=").append(title);
        sb.append(", background=").append(background);
        sb.append(", createtime=").append(createtime);
        sb.append(", remark=").append(remark);
        sb.append(", invalid=").append(invalid);
        sb.append(", style=").append(style);
        sb.append("]");
        return sb.toString();
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getRows() {
        return rows;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }
}