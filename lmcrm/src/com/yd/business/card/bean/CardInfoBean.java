/**
 * 
 */
package com.yd.business.card.bean;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
public class CardInfoBean extends BaseBean {
	
	public static final int CARDKIND_NEW = 0 ; // 新卡
	public static final int CARDKIND_USER = 1 ; // 用户卡
	public static final int CARDKIND_SET = 2 ; // 设置卡
	public static final int CARDKIND_CLEAR = 3 ; // 清空卡
	public static final int CARDKIND_CHECK = 4 ; // 检查卡
	public static final int CARDKIND_BADCLEAR = 5 ; // 故障清除卡
	public static final int CARDKIND_VALVE = 6 ; // 阀门卡
	public static final int CARDKIND_TIME = 7 ; // 校时卡
	public static final int CARDKIND_STOPSTART = 13; // 暂停恢复卡

	public static final int METERKIND_MON_0x31 = 49; //复费率YM表，按日扣
	public static final int METERKIND_MON_0x21 = 33; //复费率G表
	public static final int METERKIND_MON_0x41 = 65; //复费率大口径表
	public static final int METERKIND_YEAR = 81; //年阶梯
	
	
	private Integer icardkind;
	private Integer imeterkind;
	private Integer isyscode;
	private Integer isupper;
	private SysParam stru_sysparm;
	private UserParm stru_userparm;
	private PriceParm stru_priceparm;
	private OtherParm stru_otherparm;
	
	private String result_desc;
	private Integer result_code;
	private Long chargeDetailId;
	
	public Long getChargeDetailId() {
		return chargeDetailId;
	}
	public void setChargeDetailId(Long chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}
	public Integer getIcardkind() {
		return icardkind;
	}
	public void setIcardkind(Integer icardkind) {
		this.icardkind = icardkind;
	}
	public Integer getImeterkind() {
		return imeterkind;
	}
	public void setImeterkind(Integer imeterkind) {
		this.imeterkind = imeterkind;
	}
	public Integer getIsyscode() {
		return isyscode;
	}
	public void setIsyscode(Integer isyscode) {
		this.isyscode = isyscode;
	}
	public Integer getIsupper() {
		return isupper;
	}
	public void setIsupper(Integer isupper) {
		this.isupper = isupper;
	}
	public String getResult_desc() {
		return result_desc;
	}
	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}
	public Integer getResult_code() {
		return result_code;
	}
	public void setResult_code(Integer result_code) {
		this.result_code = result_code;
	}
	public SysParam getStru_sysparm() {
		return stru_sysparm;
	}
	public void setStru_sysparm(SysParam stru_sysparm) {
		this.stru_sysparm = stru_sysparm;
	}
	public UserParm getStru_userparm() {
		return stru_userparm;
	}
	public void setStru_userparm(UserParm stru_userparm) {
		this.stru_userparm = stru_userparm;
	}
	public PriceParm getStru_priceparm() {
		return stru_priceparm;
	}
	public void setStru_priceparm(PriceParm stru_priceparm) {
		this.stru_priceparm = stru_priceparm;
	}
	public OtherParm getStru_otherparm() {
		return stru_otherparm;
	}
	public void setStru_otherparm(OtherParm stru_otherparm) {
		this.stru_otherparm = stru_otherparm;
	}
	public class OtherParm{
		private Integer iyear;
		private Integer imonth;
		private Integer iday;
		private Integer ihour;
		private Integer imin;
		private Integer isec;
		private Integer itotalamount;
		private Integer idemoamount;
		public Integer getIyear() {
			return iyear;
		}
		public void setIyear(Integer iyear) {
			this.iyear = iyear;
		}
		public Integer getImonth() {
			return imonth;
		}
		public void setImonth(Integer imonth) {
			this.imonth = imonth;
		}
		public Integer getIday() {
			return iday;
		}
		public void setIday(Integer iday) {
			this.iday = iday;
		}
		public Integer getIhour() {
			return ihour;
		}
		public void setIhour(Integer ihour) {
			this.ihour = ihour;
		}
		public Integer getImin() {
			return imin;
		}
		public void setImin(Integer imin) {
			this.imin = imin;
		}
		public Integer getIsec() {
			return isec;
		}
		public void setIsec(Integer isec) {
			this.isec = isec;
		}
		public Integer getItotalamount() {
			return itotalamount;
		}
		public void setItotalamount(Integer itotalamount) {
			this.itotalamount = itotalamount;
		}
		public Integer getIdemoamount() {
			return idemoamount;
		}
		public void setIdemoamount(Integer idemoamount) {
			this.idemoamount = idemoamount;
		}
	}
	public class PriceParm{

		private Integer iton1;
		private Integer iton2;
		private Integer iton3;
		private Integer iton4;
		private Integer iton5;
		private Integer iprice1;
		private Integer iprice2;
		private Integer iprice3;
		private Integer iprice4;
		private Integer iprice5;
		private Integer iprice6;
		public Integer getIton1() {
			return iton1;
		}
		public void setIton1(Integer iton1) {
			this.iton1 = iton1;
		}
		public Integer getIton2() {
			return iton2;
		}
		public void setIton2(Integer iton2) {
			this.iton2 = iton2;
		}
		public Integer getIton3() {
			return iton3;
		}
		public void setIton3(Integer iton3) {
			this.iton3 = iton3;
		}
		public Integer getIton4() {
			return iton4;
		}
		public void setIton4(Integer iton4) {
			this.iton4 = iton4;
		}
		public Integer getIton5() {
			return iton5;
		}
		public void setIton5(Integer iton5) {
			this.iton5 = iton5;
		}
		public Integer getIprice1() {
			return iprice1;
		}
		public void setIprice1(Integer iprice1) {
			this.iprice1 = iprice1;
		}
		public Integer getIprice2() {
			return iprice2;
		}
		public void setIprice2(Integer iprice2) {
			this.iprice2 = iprice2;
		}
		public Integer getIprice3() {
			return iprice3;
		}
		public void setIprice3(Integer iprice3) {
			this.iprice3 = iprice3;
		}
		public Integer getIprice4() {
			return iprice4;
		}
		public void setIprice4(Integer iprice4) {
			this.iprice4 = iprice4;
		}
		public Integer getIprice5() {
			return iprice5;
		}
		public void setIprice5(Integer iprice5) {
			this.iprice5 = iprice5;
		}
		public Integer getIprice6() {
			return iprice6;
		}
		public void setIprice6(Integer iprice6) {
			this.iprice6 = iprice6;
		}
	}
	public class SysParam{
		private Integer ioverflat;
		private Integer ilimitamount;
		private Integer istoredamount;
		private Integer iweakprompt;
		public Integer getIoverflat() {
			return ioverflat;
		}
		public void setIoverflat(Integer ioverflat) {
			this.ioverflat = ioverflat;
		}
		public Integer getIlimitamount() {
			return ilimitamount;
		}
		public void setIlimitamount(Integer ilimitamount) {
			this.ilimitamount = ilimitamount;
		}
		public Integer getIstoredamount() {
			return istoredamount;
		}
		public void setIstoredamount(Integer istoredamount) {
			this.istoredamount = istoredamount;
		}
		public Integer getIweakprompt() {
			return iweakprompt;
		}
		public void setIweakprompt(Integer iweakprompt) {
			this.iweakprompt = iweakprompt;
		}
		
	}
	public class UserParm{
		private Integer iuserno;
		private Integer iamount;
		private Integer isavingno;
		private Retparm stru_retparm;
		

		public Integer getIuserno() {
			return iuserno;
		}

		public void setIuserno(Integer iuserno) {
			this.iuserno = iuserno;
		}

		public Integer getIamount() {
			return iamount;
		}

		public void setIamount(Integer iamount) {
			this.iamount = iamount;
		}

		public Integer getIsavingno() {
			return isavingno;
		}

		public void setIsavingno(Integer isavingno) {
			this.isavingno = isavingno;
		}

		public Retparm getStru_retparm() {
			return stru_retparm;
		}

		public void setStru_retparm(Retparm stru_retparm) {
			this.stru_retparm = stru_retparm;
		}

		class Retparm{
			private Integer itotalamount;
			private Integer ioverflat;
			private Integer imagneticstatus;
			private Integer ielectric;
			private Integer iopenstatus;
			private Integer iclosestatus;
			private Integer iuserflag;
			private Integer isetflag;
			private Integer iflag;
			private Integer iuseramount;
			private Integer iuseramount1;
			private Integer iuseramount2;
			private Integer iuseramount3;
			private Integer iuseramount4;
			private Integer iuseramount5;
			private Integer iyear;
			private Integer imonth;
			private Integer iday;
			public Integer getItotalamount() {
				return itotalamount;
			}
			public void setItotalamount(Integer itotalamount) {
				this.itotalamount = itotalamount;
			}
			public Integer getIoverflat() {
				return ioverflat;
			}
			public void setIoverflat(Integer ioverflat) {
				this.ioverflat = ioverflat;
			}
			public Integer getImagneticstatus() {
				return imagneticstatus;
			}
			public void setImagneticstatus(Integer imagneticstatus) {
				this.imagneticstatus = imagneticstatus;
			}
			public Integer getIelectric() {
				return ielectric;
			}
			public void setIelectric(Integer ielectric) {
				this.ielectric = ielectric;
			}
			public Integer getIopenstatus() {
				return iopenstatus;
			}
			public void setIopenstatus(Integer iopenstatus) {
				this.iopenstatus = iopenstatus;
			}
			public Integer getIclosestatus() {
				return iclosestatus;
			}
			public void setIclosestatus(Integer iclosestatus) {
				this.iclosestatus = iclosestatus;
			}
			public Integer getIuserflag() {
				return iuserflag;
			}
			public void setIuserflag(Integer iuserflag) {
				this.iuserflag = iuserflag;
			}
			public Integer getIsetflag() {
				return isetflag;
			}
			public void setIsetflag(Integer isetflag) {
				this.isetflag = isetflag;
			}
			public Integer getIflag() {
				return iflag;
			}
			public void setIflag(Integer iflag) {
				this.iflag = iflag;
			}
			public Integer getIuseramount() {
				return iuseramount;
			}
			public void setIuseramount(Integer iuseramount) {
				this.iuseramount = iuseramount;
			}
			public Integer getIuseramount1() {
				return iuseramount1;
			}
			public void setIuseramount1(Integer iuseramount1) {
				this.iuseramount1 = iuseramount1;
			}
			public Integer getIuseramount2() {
				return iuseramount2;
			}
			public void setIuseramount2(Integer iuseramount2) {
				this.iuseramount2 = iuseramount2;
			}
			public Integer getIuseramount3() {
				return iuseramount3;
			}
			public void setIuseramount3(Integer iuseramount3) {
				this.iuseramount3 = iuseramount3;
			}
			public Integer getIuseramount4() {
				return iuseramount4;
			}
			public void setIuseramount4(Integer iuseramount4) {
				this.iuseramount4 = iuseramount4;
			}
			public Integer getIuseramount5() {
				return iuseramount5;
			}
			public void setIuseramount5(Integer iuseramount5) {
				this.iuseramount5 = iuseramount5;
			}
			public Integer getIyear() {
				return iyear;
			}
			public void setIyear(Integer iyear) {
				this.iyear = iyear;
			}
			public Integer getImonth() {
				return imonth;
			}
			public void setImonth(Integer imonth) {
				this.imonth = imonth;
			}
			public Integer getIday() {
				return iday;
			}
			public void setIday(Integer iday) {
				this.iday = iday;
			}
			
		}
	}
}
