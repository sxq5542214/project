/**
 * 
 */
package com.yd.business.isp.bean;

import org.apache.ibatis.type.Alias;
import org.json.JSONObject;

/**
 * @author ice
 *
 */
@Alias("ydInterface")
public class YDInterfaceBean extends ISPInterfaceBean {
// http://120.209.204.229/open/abilityDetail?abilityId=107b76cb-154b-4067-b711-84e537c2083a
	
	public static final String RESCODE_SUCCESS = "0000000";
	
	private String userName;
	//业务
	private String service_no;
	private String master_serv_id = "1001"; //按文档提示，是写死的
	private String login_no;
	
	private Busi_info busi_info = new Busi_info();
	
	
	//返回
	private String loginAccept;
	private String effDate;
	private String expDate;
	private String brandId;
	private String spFlag;
	private String effexpMode;
	private String imsiNo;
	private String userprcFlag;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginAccept() {
		return loginAccept;
	}
	public void setLoginAccept(String loginAccept) {
		this.loginAccept = loginAccept;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
		busi_info.eff_date = effDate;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
		busi_info.exp_date = expDate;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getSpFlag() {
		return spFlag;
	}
	public void setSpFlag(String spFlag) {
		this.spFlag = spFlag;
	}
	public String getService_no() {
		return service_no;
	}
	public void setService_no(String service_no) {
		this.service_no = service_no;
	}
	public String getMaster_serv_id() {
		return master_serv_id;
	}
	public void setMaster_serv_id(String master_serv_id) {
		this.master_serv_id = master_serv_id;
	}
	public String getLogin_no() {
		return login_no;
	}
	public void setLogin_no(String login_no) {
		this.login_no = login_no;
	}
	public Busi_info getBusi_info() {
		return busi_info;
	}
	public void setBusi_info(Busi_info busi_info) {
		this.busi_info = busi_info;
	}
	public String getEffexpMode() {
		return effexpMode;
	}
	public void setEffexpMode(String effexpMode) {
		this.effexpMode = effexpMode;
	}
	public String getImsiNo() {
		return imsiNo;
	}
	public void setImsiNo(String imsiNo) {
		this.imsiNo = imsiNo;
	}
	public String getUserprcFlag() {
		return userprcFlag;
	}
	public void setUserprcFlag(String userprcFlag) {
		this.userprcFlag = userprcFlag;
	}
	/**
	 * 设置产品资费标识
	 * @param prod_prcid
	 */
	public void setProd_prcid(String prod_prcid){
		busi_info.prod_prcid = prod_prcid;
	}
	
	public String getProd_prcid(){
		return busi_info.prod_prcid ;
	}
	
	public void setGroup_id(long group_id){
		busi_info.groupmbr.group_id = group_id;
	}
	
	public class Busi_info {
		private String operate_type = "A";  //添加成员为A，删除为D  ，这里应该用不到删除，写死为A
		private String prod_prcid;	//资费编码
		private String eff_date;	//生效时间 格式：20160525000000	
		private String exp_date;	//失效时间 格式：20160531235959
		private String fun_finish_flag = "P"; // 按文档提示，是写死的
		private Attr_list attr_list = new Attr_list();
		private Groupmbr groupmbr = new Groupmbr();
		
		public String getOperate_type() {
			return operate_type;
		}

		public void setOperate_type(String operate_type) {
			this.operate_type = operate_type;
		}

		public String getProd_prcid() {
			return prod_prcid;
		}

		public void setProd_prcid(String prod_prcid) {
			this.prod_prcid = prod_prcid;
		}

		public String getEff_date() {
			return eff_date;
		}

		public void setEff_date(String eff_date) {
			this.eff_date = eff_date;
		}

		public String getExp_date() {
			return exp_date;
		}

		public void setExp_date(String exp_date) {
			this.exp_date = exp_date;
		}

		public String getFun_finish_flag() {
			return fun_finish_flag;
		}

		public void setFun_finish_flag(String fun_finish_flag) {
			this.fun_finish_flag = fun_finish_flag;
		}

		public Attr_list getAttr_list() {
			return attr_list;
		}

		public void setAttr_list(Attr_list attr_list) {
			this.attr_list = attr_list;
		}

		public Groupmbr getGroupmbr() {
			return groupmbr;
		}

		public void setGroupmbr(Groupmbr groupmbr) {
			this.groupmbr = groupmbr;
		}

		public class Attr_list{
			private Attr attr = new Attr();
			
			public Attr getAttr() {
				return attr;
			}

			public void setAttr(Attr attr) {
				this.attr = attr;
			}

			public class Attr{
				
				private String operate_type = "A";
				private String attr_type = "PRC";
				private String attr_id = "EA00001";
				private Attr_value_list attr_value_list = new Attr_value_list();
				
				public String getOperate_type() {
					return operate_type;
				}

				public void setOperate_type(String operate_type) {
					this.operate_type = operate_type;
				}

				public String getAttr_type() {
					return attr_type;
				}

				public void setAttr_type(String attr_type) {
					this.attr_type = attr_type;
				}

				public String getAttr_id() {
					return attr_id;
				}

				public void setAttr_id(String attr_id) {
					this.attr_id = attr_id;
				}

				public Attr_value_list getAttr_value_list() {
					return attr_value_list;
				}

				public void setAttr_value_list(Attr_value_list attr_value_list) {
					this.attr_value_list = attr_value_list;
				}

				public class Attr_value_list{
					
					private String attr_value = "0"; //是否给成员用户发送短信提醒。 0：否  1：是   默认为0

					public String getAttr_value() {
						return attr_value;
					}

					public void setAttr_value(String attr_value) {
						this.attr_value = attr_value;
					}
				}
			}
		}
		
		public class Groupmbr{
			private String operate_type = "A";
			private long group_id ;
			private int member_role_id = 7788; //按文档提示，这里写死
			public String getOperate_type() {
				return operate_type;
			}
			public void setOperate_type(String operate_type) {
				this.operate_type = operate_type;
			}
			public long getGroup_id() {
				return group_id;
			}
			public void setGroup_id(long group_id) {
				this.group_id = group_id;
			}
			public int getMember_role_id() {
				return member_role_id;
			}
			public void setMember_role_id(int member_role_id) {
				this.member_role_id = member_role_id;
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		YDInterfaceBean bean = new YDInterfaceBean();
		bean.setLogin_no("A0AZQ0014");
		bean.setService_no("12312312312");
		bean.setProd_prcid("PIBP2804");
		bean.setGroup_id(123123);
		
		JSONObject jso = new JSONObject(bean);
		System.out.println(jso.toString().toUpperCase());
		
	}
	
}
