package com.yd.iotbusiness.mapper.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class LmProductModel implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.factorycode
     *
     * @mbg.generated
     */
    private String factorycode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.daymax
     *
     * @mbg.generated
     */
    private BigDecimal daymax;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.fullvalue
     *
     * @mbg.generated
     */
    private BigDecimal fullvalue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.unit
     *
     * @mbg.generated
     */
    private String unit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.used
     *
     * @mbg.generated
     */
    private Byte used;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.metertype
     *
     * @mbg.generated
     */
    private Byte metertype;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.commtype
     *
     * @mbg.generated
     */
    private Byte commtype;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.valve
     *
     * @mbg.generated
     */
    private Byte valve;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.photo
     *
     * @mbg.generated
     */
    private String photo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lm_product.systemid
     *
     * @mbg.generated
     */
    private Integer systemid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lm_product
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    protected Integer rows;

    protected Integer page;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.id
     *
     * @return the value of lm_product.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.id
     *
     * @param id the value for lm_product.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.name
     *
     * @return the value of lm_product.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.name
     *
     * @param name the value for lm_product.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.factorycode
     *
     * @return the value of lm_product.factorycode
     *
     * @mbg.generated
     */
    public String getFactorycode() {
        return factorycode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.factorycode
     *
     * @param factorycode the value for lm_product.factorycode
     *
     * @mbg.generated
     */
    public void setFactorycode(String factorycode) {
        this.factorycode = factorycode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.daymax
     *
     * @return the value of lm_product.daymax
     *
     * @mbg.generated
     */
    public BigDecimal getDaymax() {
        return daymax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.daymax
     *
     * @param daymax the value for lm_product.daymax
     *
     * @mbg.generated
     */
    public void setDaymax(BigDecimal daymax) {
        this.daymax = daymax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.fullvalue
     *
     * @return the value of lm_product.fullvalue
     *
     * @mbg.generated
     */
    public BigDecimal getFullvalue() {
        return fullvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.fullvalue
     *
     * @param fullvalue the value for lm_product.fullvalue
     *
     * @mbg.generated
     */
    public void setFullvalue(BigDecimal fullvalue) {
        this.fullvalue = fullvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.unit
     *
     * @return the value of lm_product.unit
     *
     * @mbg.generated
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.unit
     *
     * @param unit the value for lm_product.unit
     *
     * @mbg.generated
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.used
     *
     * @return the value of lm_product.used
     *
     * @mbg.generated
     */
    public Byte getUsed() {
        return used;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.used
     *
     * @param used the value for lm_product.used
     *
     * @mbg.generated
     */
    public void setUsed(Byte used) {
        this.used = used;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.metertype
     *
     * @return the value of lm_product.metertype
     *
     * @mbg.generated
     */
    public Byte getMetertype() {
        return metertype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.metertype
     *
     * @param metertype the value for lm_product.metertype
     *
     * @mbg.generated
     */
    public void setMetertype(Byte metertype) {
        this.metertype = metertype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.commtype
     *
     * @return the value of lm_product.commtype
     *
     * @mbg.generated
     */
    public Byte getCommtype() {
        return commtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.commtype
     *
     * @param commtype the value for lm_product.commtype
     *
     * @mbg.generated
     */
    public void setCommtype(Byte commtype) {
        this.commtype = commtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.valve
     *
     * @return the value of lm_product.valve
     *
     * @mbg.generated
     */
    public Byte getValve() {
        return valve;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.valve
     *
     * @param valve the value for lm_product.valve
     *
     * @mbg.generated
     */
    public void setValve(Byte valve) {
        this.valve = valve;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.photo
     *
     * @return the value of lm_product.photo
     *
     * @mbg.generated
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.photo
     *
     * @param photo the value for lm_product.photo
     *
     * @mbg.generated
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lm_product.systemid
     *
     * @return the value of lm_product.systemid
     *
     * @mbg.generated
     */
    public Integer getSystemid() {
        return systemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lm_product.systemid
     *
     * @param systemid the value for lm_product.systemid
     *
     * @mbg.generated
     */
    public void setSystemid(Integer systemid) {
        this.systemid = systemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lm_product
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
        sb.append(", name=").append(name);
        sb.append(", factorycode=").append(factorycode);
        sb.append(", daymax=").append(daymax);
        sb.append(", fullvalue=").append(fullvalue);
        sb.append(", unit=").append(unit);
        sb.append(", used=").append(used);
        sb.append(", metertype=").append(metertype);
        sb.append(", commtype=").append(commtype);
        sb.append(", valve=").append(valve);
        sb.append(", photo=").append(photo);
        sb.append(", systemid=").append(systemid);
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