package com.yd.iotbusiness.mapper.model;

import java.util.ArrayList;
import java.util.List;

public class LlReportParamsModelExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    protected Integer rows;

    protected Integer page;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    public LlReportParamsModelExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
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
     * This method corresponds to the database table ll_report_params
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
     * This method corresponds to the database table ll_report_params
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ll_report_params
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

    public LlReportParamsModelExample(Object pageInation) {
        oredCriteria = new ArrayList<>();
        LlReportParamsModel model = (LlReportParamsModel)pageInation;
        this.setPage(model.getPage());
        this.setRows(model.getRows());
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ll_report_params
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

        public Criteria andReportIdIsNull() {
            addCriterion("report_id is null");
            return (Criteria) this;
        }

        public Criteria andReportIdIsNotNull() {
            addCriterion("report_id is not null");
            return (Criteria) this;
        }

        public Criteria andReportIdEqualTo(Integer value) {
            if (value != null) {
                addCriterion("report_id =", value, "reportId");
            }
            return (Criteria) this;
        }

        public Criteria andReportIdNotEqualTo(Integer value) {
            if (value != null) {
                addCriterion("report_id <>", value, "reportId");
            }
            return (Criteria) this;
        }

        public Criteria andReportIdGreaterThan(Integer value) {
            if (value != null) {
                addCriterion("report_id >", value, "reportId");
            }
            return (Criteria) this;
        }

        public Criteria andReportIdGreaterThanOrEqualTo(Integer value) {
            if (value != null) {
                addCriterion("report_id >=", value, "reportId");
            }
            return (Criteria) this;
        }

        public Criteria andReportIdLessThan(Integer value) {
            if (value != null) {
                addCriterion("report_id <", value, "reportId");
            }
            return (Criteria) this;
        }

        public Criteria andReportIdLessThanOrEqualTo(Integer value) {
            if (value != null) {
                addCriterion("report_id <=", value, "reportId");
            }
            return (Criteria) this;
        }

        public Criteria andReportIdIn(List<Integer> values) {
            addCriterion("report_id in", values, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotIn(List<Integer> values) {
            addCriterion("report_id not in", values, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdBetween(Integer value1, Integer value2) {
            addCriterion("report_id between", value1, value2, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotBetween(Integer value1, Integer value2) {
            addCriterion("report_id not between", value1, value2, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportNameIsNull() {
            addCriterion("report_name is null");
            return (Criteria) this;
        }

        public Criteria andReportNameIsNotNull() {
            addCriterion("report_name is not null");
            return (Criteria) this;
        }

        public Criteria andReportNameEqualTo(String value) {
            if (value != null) {
                addCriterion("report_name =", value, "reportName");
            }
            return (Criteria) this;
        }

        public Criteria andReportNameNotEqualTo(String value) {
            if (value != null) {
                addCriterion("report_name <>", value, "reportName");
            }
            return (Criteria) this;
        }

        public Criteria andReportNameGreaterThan(String value) {
            if (value != null) {
                addCriterion("report_name >", value, "reportName");
            }
            return (Criteria) this;
        }

        public Criteria andReportNameGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("report_name >=", value, "reportName");
            }
            return (Criteria) this;
        }

        public Criteria andReportNameLessThan(String value) {
            if (value != null) {
                addCriterion("report_name <", value, "reportName");
            }
            return (Criteria) this;
        }

        public Criteria andReportNameLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("report_name <=", value, "reportName");
            }
            return (Criteria) this;
        }

        public Criteria andReportNameLike(String value) {
            if (value != null) {
                addCriterion("report_name like", "%"+value+"%", "reportName");
            }
            return (Criteria) this;
        }

        public Criteria andReportNameNotLike(String value) {
            if (value != null) {
                addCriterion("report_name not like", value, "reportName");
            }
            return (Criteria) this;
        }

        public Criteria andReportNameIn(List<String> values) {
            addCriterion("report_name in", values, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameNotIn(List<String> values) {
            addCriterion("report_name not in", values, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameBetween(String value1, String value2) {
            addCriterion("report_name between", value1, value2, "reportName");
            return (Criteria) this;
        }

        public Criteria andReportNameNotBetween(String value1, String value2) {
            addCriterion("report_name not between", value1, value2, "reportName");
            return (Criteria) this;
        }

        public Criteria andParamNameIsNull() {
            addCriterion("param_name is null");
            return (Criteria) this;
        }

        public Criteria andParamNameIsNotNull() {
            addCriterion("param_name is not null");
            return (Criteria) this;
        }

        public Criteria andParamNameEqualTo(String value) {
            if (value != null) {
                addCriterion("param_name =", value, "paramName");
            }
            return (Criteria) this;
        }

        public Criteria andParamNameNotEqualTo(String value) {
            if (value != null) {
                addCriterion("param_name <>", value, "paramName");
            }
            return (Criteria) this;
        }

        public Criteria andParamNameGreaterThan(String value) {
            if (value != null) {
                addCriterion("param_name >", value, "paramName");
            }
            return (Criteria) this;
        }

        public Criteria andParamNameGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_name >=", value, "paramName");
            }
            return (Criteria) this;
        }

        public Criteria andParamNameLessThan(String value) {
            if (value != null) {
                addCriterion("param_name <", value, "paramName");
            }
            return (Criteria) this;
        }

        public Criteria andParamNameLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_name <=", value, "paramName");
            }
            return (Criteria) this;
        }

        public Criteria andParamNameLike(String value) {
            if (value != null) {
                addCriterion("param_name like", "%"+value+"%", "paramName");
            }
            return (Criteria) this;
        }

        public Criteria andParamNameNotLike(String value) {
            if (value != null) {
                addCriterion("param_name not like", value, "paramName");
            }
            return (Criteria) this;
        }

        public Criteria andParamNameIn(List<String> values) {
            addCriterion("param_name in", values, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotIn(List<String> values) {
            addCriterion("param_name not in", values, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameBetween(String value1, String value2) {
            addCriterion("param_name between", value1, value2, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamNameNotBetween(String value1, String value2) {
            addCriterion("param_name not between", value1, value2, "paramName");
            return (Criteria) this;
        }

        public Criteria andParamCodeIsNull() {
            addCriterion("param_code is null");
            return (Criteria) this;
        }

        public Criteria andParamCodeIsNotNull() {
            addCriterion("param_code is not null");
            return (Criteria) this;
        }

        public Criteria andParamCodeEqualTo(String value) {
            if (value != null) {
                addCriterion("param_code =", value, "paramCode");
            }
            return (Criteria) this;
        }

        public Criteria andParamCodeNotEqualTo(String value) {
            if (value != null) {
                addCriterion("param_code <>", value, "paramCode");
            }
            return (Criteria) this;
        }

        public Criteria andParamCodeGreaterThan(String value) {
            if (value != null) {
                addCriterion("param_code >", value, "paramCode");
            }
            return (Criteria) this;
        }

        public Criteria andParamCodeGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_code >=", value, "paramCode");
            }
            return (Criteria) this;
        }

        public Criteria andParamCodeLessThan(String value) {
            if (value != null) {
                addCriterion("param_code <", value, "paramCode");
            }
            return (Criteria) this;
        }

        public Criteria andParamCodeLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_code <=", value, "paramCode");
            }
            return (Criteria) this;
        }

        public Criteria andParamCodeLike(String value) {
            if (value != null) {
                addCriterion("param_code like", "%"+value+"%", "paramCode");
            }
            return (Criteria) this;
        }

        public Criteria andParamCodeNotLike(String value) {
            if (value != null) {
                addCriterion("param_code not like", value, "paramCode");
            }
            return (Criteria) this;
        }

        public Criteria andParamCodeIn(List<String> values) {
            addCriterion("param_code in", values, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotIn(List<String> values) {
            addCriterion("param_code not in", values, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeBetween(String value1, String value2) {
            addCriterion("param_code between", value1, value2, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotBetween(String value1, String value2) {
            addCriterion("param_code not between", value1, value2, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamSqlIsNull() {
            addCriterion("param_sql is null");
            return (Criteria) this;
        }

        public Criteria andParamSqlIsNotNull() {
            addCriterion("param_sql is not null");
            return (Criteria) this;
        }

        public Criteria andParamSqlEqualTo(String value) {
            if (value != null) {
                addCriterion("param_sql =", value, "paramSql");
            }
            return (Criteria) this;
        }

        public Criteria andParamSqlNotEqualTo(String value) {
            if (value != null) {
                addCriterion("param_sql <>", value, "paramSql");
            }
            return (Criteria) this;
        }

        public Criteria andParamSqlGreaterThan(String value) {
            if (value != null) {
                addCriterion("param_sql >", value, "paramSql");
            }
            return (Criteria) this;
        }

        public Criteria andParamSqlGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_sql >=", value, "paramSql");
            }
            return (Criteria) this;
        }

        public Criteria andParamSqlLessThan(String value) {
            if (value != null) {
                addCriterion("param_sql <", value, "paramSql");
            }
            return (Criteria) this;
        }

        public Criteria andParamSqlLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_sql <=", value, "paramSql");
            }
            return (Criteria) this;
        }

        public Criteria andParamSqlLike(String value) {
            if (value != null) {
                addCriterion("param_sql like", "%"+value+"%", "paramSql");
            }
            return (Criteria) this;
        }

        public Criteria andParamSqlNotLike(String value) {
            if (value != null) {
                addCriterion("param_sql not like", value, "paramSql");
            }
            return (Criteria) this;
        }

        public Criteria andParamSqlIn(List<String> values) {
            addCriterion("param_sql in", values, "paramSql");
            return (Criteria) this;
        }

        public Criteria andParamSqlNotIn(List<String> values) {
            addCriterion("param_sql not in", values, "paramSql");
            return (Criteria) this;
        }

        public Criteria andParamSqlBetween(String value1, String value2) {
            addCriterion("param_sql between", value1, value2, "paramSql");
            return (Criteria) this;
        }

        public Criteria andParamSqlNotBetween(String value1, String value2) {
            addCriterion("param_sql not between", value1, value2, "paramSql");
            return (Criteria) this;
        }

        public Criteria andParamTypeIsNull() {
            addCriterion("param_type is null");
            return (Criteria) this;
        }

        public Criteria andParamTypeIsNotNull() {
            addCriterion("param_type is not null");
            return (Criteria) this;
        }

        public Criteria andParamTypeEqualTo(String value) {
            if (value != null) {
                addCriterion("param_type =", value, "paramType");
            }
            return (Criteria) this;
        }

        public Criteria andParamTypeNotEqualTo(String value) {
            if (value != null) {
                addCriterion("param_type <>", value, "paramType");
            }
            return (Criteria) this;
        }

        public Criteria andParamTypeGreaterThan(String value) {
            if (value != null) {
                addCriterion("param_type >", value, "paramType");
            }
            return (Criteria) this;
        }

        public Criteria andParamTypeGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_type >=", value, "paramType");
            }
            return (Criteria) this;
        }

        public Criteria andParamTypeLessThan(String value) {
            if (value != null) {
                addCriterion("param_type <", value, "paramType");
            }
            return (Criteria) this;
        }

        public Criteria andParamTypeLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_type <=", value, "paramType");
            }
            return (Criteria) this;
        }

        public Criteria andParamTypeLike(String value) {
            if (value != null) {
                addCriterion("param_type like", "%"+value+"%", "paramType");
            }
            return (Criteria) this;
        }

        public Criteria andParamTypeNotLike(String value) {
            if (value != null) {
                addCriterion("param_type not like", value, "paramType");
            }
            return (Criteria) this;
        }

        public Criteria andParamTypeIn(List<String> values) {
            addCriterion("param_type in", values, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotIn(List<String> values) {
            addCriterion("param_type not in", values, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeBetween(String value1, String value2) {
            addCriterion("param_type between", value1, value2, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotBetween(String value1, String value2) {
            addCriterion("param_type not between", value1, value2, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamUrlIsNull() {
            addCriterion("param_url is null");
            return (Criteria) this;
        }

        public Criteria andParamUrlIsNotNull() {
            addCriterion("param_url is not null");
            return (Criteria) this;
        }

        public Criteria andParamUrlEqualTo(String value) {
            if (value != null) {
                addCriterion("param_url =", value, "paramUrl");
            }
            return (Criteria) this;
        }

        public Criteria andParamUrlNotEqualTo(String value) {
            if (value != null) {
                addCriterion("param_url <>", value, "paramUrl");
            }
            return (Criteria) this;
        }

        public Criteria andParamUrlGreaterThan(String value) {
            if (value != null) {
                addCriterion("param_url >", value, "paramUrl");
            }
            return (Criteria) this;
        }

        public Criteria andParamUrlGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_url >=", value, "paramUrl");
            }
            return (Criteria) this;
        }

        public Criteria andParamUrlLessThan(String value) {
            if (value != null) {
                addCriterion("param_url <", value, "paramUrl");
            }
            return (Criteria) this;
        }

        public Criteria andParamUrlLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_url <=", value, "paramUrl");
            }
            return (Criteria) this;
        }

        public Criteria andParamUrlLike(String value) {
            if (value != null) {
                addCriterion("param_url like", "%"+value+"%", "paramUrl");
            }
            return (Criteria) this;
        }

        public Criteria andParamUrlNotLike(String value) {
            if (value != null) {
                addCriterion("param_url not like", value, "paramUrl");
            }
            return (Criteria) this;
        }

        public Criteria andParamUrlIn(List<String> values) {
            addCriterion("param_url in", values, "paramUrl");
            return (Criteria) this;
        }

        public Criteria andParamUrlNotIn(List<String> values) {
            addCriterion("param_url not in", values, "paramUrl");
            return (Criteria) this;
        }

        public Criteria andParamUrlBetween(String value1, String value2) {
            addCriterion("param_url between", value1, value2, "paramUrl");
            return (Criteria) this;
        }

        public Criteria andParamUrlNotBetween(String value1, String value2) {
            addCriterion("param_url not between", value1, value2, "paramUrl");
            return (Criteria) this;
        }

        public Criteria andParamCategoryIsNull() {
            addCriterion("param_category is null");
            return (Criteria) this;
        }

        public Criteria andParamCategoryIsNotNull() {
            addCriterion("param_category is not null");
            return (Criteria) this;
        }

        public Criteria andParamCategoryEqualTo(String value) {
            if (value != null) {
                addCriterion("param_category =", value, "paramCategory");
            }
            return (Criteria) this;
        }

        public Criteria andParamCategoryNotEqualTo(String value) {
            if (value != null) {
                addCriterion("param_category <>", value, "paramCategory");
            }
            return (Criteria) this;
        }

        public Criteria andParamCategoryGreaterThan(String value) {
            if (value != null) {
                addCriterion("param_category >", value, "paramCategory");
            }
            return (Criteria) this;
        }

        public Criteria andParamCategoryGreaterThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_category >=", value, "paramCategory");
            }
            return (Criteria) this;
        }

        public Criteria andParamCategoryLessThan(String value) {
            if (value != null) {
                addCriterion("param_category <", value, "paramCategory");
            }
            return (Criteria) this;
        }

        public Criteria andParamCategoryLessThanOrEqualTo(String value) {
            if (value != null) {
                addCriterion("param_category <=", value, "paramCategory");
            }
            return (Criteria) this;
        }

        public Criteria andParamCategoryLike(String value) {
            if (value != null) {
                addCriterion("param_category like", "%"+value+"%", "paramCategory");
            }
            return (Criteria) this;
        }

        public Criteria andParamCategoryNotLike(String value) {
            if (value != null) {
                addCriterion("param_category not like", value, "paramCategory");
            }
            return (Criteria) this;
        }

        public Criteria andParamCategoryIn(List<String> values) {
            addCriterion("param_category in", values, "paramCategory");
            return (Criteria) this;
        }

        public Criteria andParamCategoryNotIn(List<String> values) {
            addCriterion("param_category not in", values, "paramCategory");
            return (Criteria) this;
        }

        public Criteria andParamCategoryBetween(String value1, String value2) {
            addCriterion("param_category between", value1, value2, "paramCategory");
            return (Criteria) this;
        }

        public Criteria andParamCategoryNotBetween(String value1, String value2) {
            addCriterion("param_category not between", value1, value2, "paramCategory");
            return (Criteria) this;
        }

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Integer value) {
            if (value != null) {
                addCriterion("seq =", value, "seq");
            }
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Integer value) {
            if (value != null) {
                addCriterion("seq <>", value, "seq");
            }
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Integer value) {
            if (value != null) {
                addCriterion("seq >", value, "seq");
            }
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Integer value) {
            if (value != null) {
                addCriterion("seq >=", value, "seq");
            }
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Integer value) {
            if (value != null) {
                addCriterion("seq <", value, "seq");
            }
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Integer value) {
            if (value != null) {
                addCriterion("seq <=", value, "seq");
            }
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Integer> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Integer> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Integer value1, Integer value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("seq not between", value1, value2, "seq");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ll_report_params
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
     * This class corresponds to the database table ll_report_params
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