package com.yd.business.activity.bean;

import java.util.ArrayList;
import java.util.List;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.util.StringUtil;

/**
 * 活动配置信息的展示用类
 * @author BoBo
 *
 */
public class ActivityConfigShowBean extends BaseBean {
	
	public static final String SHOW_BEAN_BASE_INFO = "基本信息";
	public static final String SHOW_BEAN_BASE_INFO_CODE = "baseinfo";
	public static final String SHOW_BEAN_RULE_INFO = "规则信息";
	public static final String SHOW_BEAN_RULE_INFO_CODE = "rule";
	public static final String SHOW_BEAN_ORIGINAL_INFO = "公众号信息";
	public static final String SHOW_BEAN_ORIGINAL_INFO_CODE = "original";
	public static final String SHOW_BEAN_PARAM_INFO = "限制信息";
	public static final String SHOW_BEAN_PARAM_INFO_CODE = "param";
	public static final String SHOW_BEAN_PRIZE_INFO = "奖品信息";
	public static final String SHOW_BEAN_PRIZE_INFO_CODE = "prize";
	
	/**
	 * 属性节点的头部信息，显示活动的名称
	 */
	private String text;
	
	private String code;
	
	/**
	 * 节点的详细信息
	 */
	private Object detailInfo;
	
	private Integer activity_id;
	private String activity_code;
	
	public String getActivity_code() {
		return activity_code;
	}

	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}

	/**
	 * 展示分支（基本信息、活动规则、关联公众号、活动奖品、活动限制等）
	 */
	private List<ActivityConfigShowBean> nodes;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(Object detailInfo) {
		this.detailInfo = detailInfo;
	}

	public List<ActivityConfigShowBean> getNodes() {
		return nodes;
	}

	public void setNodes(ActivityConfigShowBean node) {
		if(StringUtil.isNull(this.nodes)){
			this.nodes = new ArrayList<ActivityConfigShowBean>();
			this.nodes.add(node);
		}else{
			this.nodes.add(node);
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	
	
	
}
