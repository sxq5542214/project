package com.yd.iotbusiness.mapper.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class LmPricedetailModel implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.priceid
     *
     * @mbg.generated
     */
    private Integer priceid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.percent
     *
     * @mbg.generated
     */
    private BigDecimal percent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.paperflag
     *
     * @mbg.generated
     */
    private Byte paperflag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.rate
     *
     * @mbg.generated
     */
    private BigDecimal rate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.price1
     *
     * @mbg.generated
     */
    private BigDecimal price1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.price2
     *
     * @mbg.generated
     */
    private BigDecimal price2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.price3
     *
     * @mbg.generated
     */
    private BigDecimal price3;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.price4
     *
     * @mbg.generated
     */
    private BigDecimal price4;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.price5
     *
     * @mbg.generated
     */
    private BigDecimal price5;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.baseflag
     *
     * @mbg.generated
     */
    private Byte baseflag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_pricedetail.papertext
     *
     * @mbg.generated
     */
    private String papertext;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lm_pricedetail
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    protected Integer rows;

    protected Integer page;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.id
     *
     * @return the value of lm_pricedetail.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.id
     *
     * @param id the value for lm_pricedetail.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.priceid
     *
     * @return the value of lm_pricedetail.priceid
     *
     * @mbg.generated
     */
    public Integer getPriceid() {
        return priceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.priceid
     *
     * @param priceid the value for lm_pricedetail.priceid
     *
     * @mbg.generated
     */
    public void setPriceid(Integer priceid) {
        this.priceid = priceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.name
     *
     * @return the value of lm_pricedetail.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.name
     *
     * @param name the value for lm_pricedetail.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.percent
     *
     * @return the value of lm_pricedetail.percent
     *
     * @mbg.generated
     */
    public BigDecimal getPercent() {
        return percent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.percent
     *
     * @param percent the value for lm_pricedetail.percent
     *
     * @mbg.generated
     */
    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.paperflag
     *
     * @return the value of lm_pricedetail.paperflag
     *
     * @mbg.generated
     */
    public Byte getPaperflag() {
        return paperflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.paperflag
     *
     * @param paperflag the value for lm_pricedetail.paperflag
     *
     * @mbg.generated
     */
    public void setPaperflag(Byte paperflag) {
        this.paperflag = paperflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.rate
     *
     * @return the value of lm_pricedetail.rate
     *
     * @mbg.generated
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.rate
     *
     * @param rate the value for lm_pricedetail.rate
     *
     * @mbg.generated
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.price1
     *
     * @return the value of lm_pricedetail.price1
     *
     * @mbg.generated
     */
    public BigDecimal getPrice1() {
        return price1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.price1
     *
     * @param price1 the value for lm_pricedetail.price1
     *
     * @mbg.generated
     */
    public void setPrice1(BigDecimal price1) {
        this.price1 = price1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.price2
     *
     * @return the value of lm_pricedetail.price2
     *
     * @mbg.generated
     */
    public BigDecimal getPrice2() {
        return price2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.price2
     *
     * @param price2 the value for lm_pricedetail.price2
     *
     * @mbg.generated
     */
    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.price3
     *
     * @return the value of lm_pricedetail.price3
     *
     * @mbg.generated
     */
    public BigDecimal getPrice3() {
        return price3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.price3
     *
     * @param price3 the value for lm_pricedetail.price3
     *
     * @mbg.generated
     */
    public void setPrice3(BigDecimal price3) {
        this.price3 = price3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.price4
     *
     * @return the value of lm_pricedetail.price4
     *
     * @mbg.generated
     */
    public BigDecimal getPrice4() {
        return price4;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.price4
     *
     * @param price4 the value for lm_pricedetail.price4
     *
     * @mbg.generated
     */
    public void setPrice4(BigDecimal price4) {
        this.price4 = price4;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.price5
     *
     * @return the value of lm_pricedetail.price5
     *
     * @mbg.generated
     */
    public BigDecimal getPrice5() {
        return price5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.price5
     *
     * @param price5 the value for lm_pricedetail.price5
     *
     * @mbg.generated
     */
    public void setPrice5(BigDecimal price5) {
        this.price5 = price5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.baseflag
     *
     * @return the value of lm_pricedetail.baseflag
     *
     * @mbg.generated
     */
    public Byte getBaseflag() {
        return baseflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.baseflag
     *
     * @param baseflag the value for lm_pricedetail.baseflag
     *
     * @mbg.generated
     */
    public void setBaseflag(Byte baseflag) {
        this.baseflag = baseflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_pricedetail.papertext
     *
     * @return the value of lm_pricedetail.papertext
     *
     * @mbg.generated
     */
    public String getPapertext() {
        return papertext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_pricedetail.papertext
     *
     * @param papertext the value for lm_pricedetail.papertext
     *
     * @mbg.generated
     */
    public void setPapertext(String papertext) {
        this.papertext = papertext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_pricedetail
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
        sb.append(", priceid=").append(priceid);
        sb.append(", name=").append(name);
        sb.append(", percent=").append(percent);
        sb.append(", paperflag=").append(paperflag);
        sb.append(", rate=").append(rate);
        sb.append(", price1=").append(price1);
        sb.append(", price2=").append(price2);
        sb.append(", price3=").append(price3);
        sb.append(", price4=").append(price4);
        sb.append(", price5=").append(price5);
        sb.append(", baseflag=").append(baseflag);
        sb.append(", papertext=").append(papertext);
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