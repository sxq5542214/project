package com.yd.iotbusiness.mapper.model;

import java.util.ArrayList;
import java.util.List;

public class LlReportRoleModelExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    protected Integer rows;

    protected Integer page;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public LlReportRoleModelExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
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

    public LlReportRoleModelExample(Object pageInation) {
        oredCriteria = new ArrayList<>();
        LlReportRoleModel model = (LlReportRoleModel)pageInation;
        this.setPage(model.getPage());
        this.setRows(model.getRows());
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                return ;
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                return ;
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                return ;
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            if (value != null) {
                addCriterion("id =", value, "id");
            }
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            if (value != null) {
                addCriterion("id <>", value, "id");
            }
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            if (value != null) {
                addCriterion("id >", value, "id");
            }
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            if (value != null) {
                addCriterion("id >=", value, "id");
            }
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            if (value != null) {
                addCriterion("id <", value, "id");
            }
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            if (value != null) {
                addCriterion("id <=", value, "id");
            }
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            if (value != null) {
                addCriterion("`name` =", value, "name");
            }
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            if (value != null) {
                addCriterion("`name` <>", value, "name");
            }
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            if (value != null) {
                addCriterion("`name` >", value, "name");
            }
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("`name` >=", value, "name");
            }
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            if (value != null) {
                addCriterion("`name` <", value, "name");
            }
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("`name` <=", value, "name");
            }
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            if (value != null) {
                addCriterion("`name` like", "%"+value+"%", "name");
            }
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            if (value != null) {
                addCriterion("`name` not like", value, "name");
            }
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            if (value != null) {
                addCriterion("code =", value, "code");
            }
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            if (value != null) {
                addCriterion("code <>", value, "code");
            }
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            if (value != null) {
                addCriterion("code >", value, "code");
            }
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("code >=", value, "code");
            }
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            if (value != null) {
                addCriterion("code <", value, "code");
            }
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("code <=", value, "code");
            }
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            if (value != null) {
                addCriterion("code like", "%"+value+"%", "code");
            }
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            if (value != null) {
                addCriterion("code not like", value, "code");
            }
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            if (value != null) {
                addCriterion("`status` =", value, "status");
            }
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            if (value != null) {
                addCriterion("`status` <>", value, "status");
            }
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            if (value != null) {
                addCriterion("`status` >", value, "status");
            }
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            if (value != null) {
                addCriterion("`status` >=", value, "status");
            }
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            if (value != null) {
                addCriterion("`status` <", value, "status");
            }
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            if (value != null) {
                addCriterion("`status` <=", value, "status");
            }
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            if (value != null) {
                addCriterion("description =", value, "description");
            }
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            if (value != null) {
                addCriterion("description <>", value, "description");
            }
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            if (value != null) {
                addCriterion("description >", value, "description");
            }
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("description >=", value, "description");
            }
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            if (value != null) {
                addCriterion("description <", value, "description");
            }
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("description <=", value, "description");
            }
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            if (value != null) {
                addCriterion("description like", "%"+value+"%", "description");
            }
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            if (value != null) {
                addCriterion("description not like", value, "description");
            }
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            if (value != null) {
                addCriterion("create_time =", value, "createTime");
            }
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            if (value != null) {
                addCriterion("create_time <>", value, "createTime");
            }
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            if (value != null) {
                addCriterion("create_time >", value, "createTime");
            }
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("create_time >=", value, "createTime");
            }
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            if (value != null) {
                addCriterion("create_time <", value, "createTime");
            }
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("create_time <=", value, "createTime");
            }
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            if (value != null) {
                addCriterion("create_time like", "%"+value+"%", "createTime");
            }
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            if (value != null) {
                addCriterion("create_time not like", value, "createTime");
            }
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(String value) {
            if (value != null) {
                addCriterion("modify_time =", value, "modifyTime");
            }
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(String value) {
            if (value != null) {
                addCriterion("modify_time <>", value, "modifyTime");
            }
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(String value) {
            if (value != null) {
                addCriterion("modify_time >", value, "modifyTime");
            }
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("modify_time >=", value, "modifyTime");
            }
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(String value) {
            if (value != null) {
                addCriterion("modify_time <", value, "modifyTime");
            }
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("modify_time <=", value, "modifyTime");
            }
            return (Criteria) this;
        }

        public Criteria andModifyTimeLike(String value) {
            if (value != null) {
                addCriterion("modify_time like", "%"+value+"%", "modifyTime");
            }
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotLike(String value) {
            if (value != null) {
                addCriterion("modify_time not like", value, "modifyTime");
            }
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<String> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<String> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(String value1, String value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(String value1, String value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ll_report_role
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ll_report_role
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}