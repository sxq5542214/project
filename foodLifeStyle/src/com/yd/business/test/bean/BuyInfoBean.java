/**
 * 
 */
package com.yd.business.test.bean;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.business.test.service.impl.TestServiceImpl;

/**
 * @author ice
 *
 */
public class BuyInfoBean extends BaseBean {
	
	private String name ;
	private int initMoney = 100; // 初始金额
	private int moneySum = 12800; // 个人资金池
	private int sumLotteryMoney = 0; //总中奖金额
	private int sumLossMoney = 0 ; // 总亏本金额
	private int sumLotteryCount = 0; // 总中奖次数
	private int sumLossCount = 0 ; //总亏本次数
	private int sumNotJoinCount = 0;//总未参加次数
	private int longLossDays = 0; //最长亏本天数
	private int lossDaysCount = 0; //连续亏本次数
	private int lossMoneyContinueSum = 0; //连续亏本金额
	private int sumFlow = 0 ; //交易流水
	private int currentLossDays = 0; //目前亏本天数
	private int count = 1;
	private StringBuffer sb = new StringBuffer();
	private int makeMoney = 0;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFinalResult(){
		makeMoney = moneySum - TestServiceImpl.onePersonJionMoney;
		return "名称为：" + name + "的计算结果为 个人资金池还有:"+moneySum+",总计盈亏为："+ makeMoney +",总中奖金额:"+sumLotteryMoney+",总中奖次数:"+sumLotteryCount+",总亏本金额:"
				+sumLossMoney + ",总亏本次数:"+sumLossCount+",总未参加次数:"+sumNotJoinCount+",最长亏本天数:"+longLossDays+",连续"+TestServiceImpl.maxLongLossDays+"天亏本次数："
				+ lossDaysCount +",交易流水总计："+sumFlow +",返点总计："+ sumFlow * TestServiceImpl.flowDiscount   +"\r\n";
	}
	
	public int getMakeMoney() {
		return makeMoney;
	}
	public void setMakeMoney(int makeMoney) {
		this.makeMoney = makeMoney;
	}
	public StringBuffer getSb() {
		return sb;
	}
	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getInitMoney() {
		return initMoney;
	}
	public void setInitMoney(int initMoney) {
		this.initMoney = initMoney;
	}
	public int getMoneySum() {
		return moneySum;
	}
	public void setMoneySum(int moneySum) {
		this.moneySum = moneySum;
	}
	public int getSumLotteryMoney() {
		return sumLotteryMoney;
	}
	public void setSumLotteryMoney(int sumLotteryMoney) {
		this.sumLotteryMoney = sumLotteryMoney;
	}
	public int getSumLossMoney() {
		return sumLossMoney;
	}
	public void setSumLossMoney(int sumLossMoney) {
		this.sumLossMoney = sumLossMoney;
	}
	public int getSumLotteryCount() {
		return sumLotteryCount;
	}
	public void setSumLotteryCount(int sumLotteryCount) {
		this.sumLotteryCount = sumLotteryCount;
	}
	public int getSumLossCount() {
		return sumLossCount;
	}
	public void setSumLossCount(int sumLossCount) {
		this.sumLossCount = sumLossCount;
	}
	public int getSumNotJoinCount() {
		return sumNotJoinCount;
	}
	public void setSumNotJoinCount(int sumNotJoinCount) {
		this.sumNotJoinCount = sumNotJoinCount;
	}
	public int getLongLossDays() {
		return longLossDays;
	}
	public void setLongLossDays(int longLossDays) {
		this.longLossDays = longLossDays;
	}
	public int getLossDaysCount() {
		return lossDaysCount;
	}
	public void setLossDaysCount(int lossDaysCount) {
		this.lossDaysCount = lossDaysCount;
	}
	public int getLossMoneyContinueSum() {
		return lossMoneyContinueSum;
	}
	public void setLossMoneyContinueSum(int lossMoneyContinueSum) {
		this.lossMoneyContinueSum = lossMoneyContinueSum;
	}
	public int getSumFlow() {
		return sumFlow;
	}
	public void setSumFlow(int sumFlow) {
		this.sumFlow = sumFlow;
	}
	public int getCurrentLossDays() {
		return currentLossDays;
	}
	public void setCurrentLossDays(int currentLossDays) {
		this.currentLossDays = currentLossDays;
	}
	
}
