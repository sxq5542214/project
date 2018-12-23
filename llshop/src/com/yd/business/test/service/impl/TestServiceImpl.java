/**
 * 
 */
package com.yd.business.test.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.test.bean.BuyInfoBean;
import com.yd.business.test.bean.TechnicalVerificationBean;
import com.yd.business.test.dao.ITestDao;
import com.yd.business.test.service.ITestService;

/**
 * @author ice
 *
 */
@Service("testService")
public class TestServiceImpl extends BaseService implements ITestService {
	@Resource
	private ITestDao testDao;
	
	public static final int jionPersonCount = 10; // 参加人数
	/**
	 * 失败多少次开始计算保本
	 */
	public static final int numOfLossCountCost = 5;
	/**
	 * 最多连续不中奖次数
	 */
	public final static int maxLongLossDays = 12; //最多连续不中奖次数
	/**
	 * 失败后投保的基础上赚点
	 */
	private static final double numOfLossCountCostMultiple = 1.0d;
	
	/**
	 * 每个用户加入时投入的资金
	 */
	public static final int onePersonJionMoney = 12800;
	
	/**
	 * 交易返点折扣
	 */
	public static final double flowDiscount = 0.08;
	
	/**
	 * 计算本次投入金额，保证赢了就回本，不赚都行
	 * @param lotteryMoney   本次中奖金额
	 * @param lossMoneySum   已连续亏损金额
	 * @param currentLossDays 当前连续亏本天数
	 * @return
	 */
	private int calcCurrentJoinMoney(Integer lotteryMoney,int lossMoneySum,int currentLossDays,int initMoney){
		// 大奖池没计算未写
		int x = initMoney;
		if(lotteryMoney != null){

			if(currentLossDays >= numOfLossCountCost && currentLossDays < maxLongLossDays){
				//要加上本次投入的钱 , 向上取整，计算出要投入多少钱才能保本
				x = (int)(Math.ceil( ((double) lossMoneySum/ ((double)lotteryMoney/100d - 1d )))  * numOfLossCountCostMultiple );
				
			}
		}
		
		return x;
	}
	
	
	
	@Override
	public void test() {
		// TODO Auto-generated method stub
		System.out.println("11234455678");
		
		String[] names ={"黄药师","好彩头","飞龙在天","老鸭汤","红单会","逐鹿中原","鹤壁王者荣耀"};
		StringBuffer sb = new StringBuffer();
		StringBuffer sbend = new StringBuffer();
		int totalMoneySum = 0; // 总的资金池
		int totalFlowSum = 0;  // 总的交易流水
		double discount = 0.08; //交易流水的折扣返点
		
		for(int x = 0 ; x <names.length;x++)
		{

			String name = names[x];
			sb.append("开始为名称为：" + name + "进行计算。。。。。。\r\n");
			List<TechnicalVerificationBean> list = testDao.queryTVB(name);
			
			
			int initMoney = 100; // 初始金额
			int moneySum = 100; // 资金池
			int sumLotteryMoney = 0; //总中奖金额
			int sumLossMoney = 0 ; // 总亏本金额
			int sumLotteryCount = 0; // 总中奖次数
			int sumLossCount = 0 ; //总亏本次数
			int sumNotJoinCount = 0;//总未参加次数
			int longLossDays = 0; //最长亏本天数
			int lossDaysCount = 0; //连续亏本次数
			int lossMoneyContinueSum = 0; //连续亏本金额
			int sumFlow = 0 ; //交易流水
			int currentLossDays = 0; //目前亏本天数
			int i = 0;
			for(TechnicalVerificationBean bean : list){
//				int currentjoinMoney = initMoney; // 本次投入的钱是根据上次投入结果确定的
				int currentjoinMoney = calcCurrentJoinMoney(bean.getLottery_money(),lossMoneyContinueSum,currentLossDays,initMoney);
				moneySum = moneySum - currentjoinMoney;  // 本次投入的钱是从资金池出的
				
				
				i++;
				String isLottery = bean.getIs_lottery();
				Integer lotteryMoney = bean.getLottery_money();
				if("中".equalsIgnoreCase(isLottery)){
					if(currentLossDays > 0 && currentLossDays < maxLongLossDays){
						lotteryMoney = (int) Math.floor( (double)lotteryMoney * (double)currentjoinMoney/100d ); //加倍投入的中奖金额也加倍
					}
					moneySum = moneySum + lotteryMoney; //资金池增加
					sumLotteryMoney = sumLotteryMoney +lotteryMoney; //总中奖金额增加
					sumLotteryCount++; //总中奖次数加1
					
					sumFlow = sumFlow + currentjoinMoney;   // 交易流水增加实际中奖额度
					currentLossDays = 0;
					lossMoneyContinueSum = 0;
					initMoney = 100; // 中奖后投入金额还原为100
				}else if("否".equalsIgnoreCase(isLottery)){
					sumFlow = sumFlow + currentjoinMoney;
					lossMoneyContinueSum = lossMoneyContinueSum + currentjoinMoney ; // 当前连续亏本金额增加
					sumLossMoney = sumLossMoney + initMoney; //总亏本金额增加
					sumLossCount++; //总亏本次数加1
					initMoney = initMoney + initMoney; //下次投入资金加倍
					
					currentLossDays++;  //当前连续亏本天数增加1
					if(currentLossDays > longLossDays){
						longLossDays = currentLossDays;
					}
					if(currentLossDays == maxLongLossDays){
						lossDaysCount = lossDaysCount+1;
						initMoney = 100; // 达到7天，投入金额重置
					}
					lotteryMoney = 0; //中奖额度
				}else{

					moneySum = moneySum + initMoney;  // 本次未参加，则把钱还如资金池
					sumNotJoinCount++; //总未参加次数增加
				}
				
				sb.append(name + "： "
						+ i +"次记录，本次投入："+ currentjoinMoney +"，中奖额基数:"+bean.getLottery_money()+",中奖额度："+lotteryMoney+",资金池还有:"+moneySum+",总中奖金额:"+sumLotteryMoney+",总中奖次数:"+sumLotteryCount+",总亏本金额:"
						+sumLossMoney + ",连续亏本金额："+lossMoneyContinueSum+",总亏本次数:"+sumLossCount+",总未参加次数:"+sumNotJoinCount+",最长亏本天数:"+longLossDays+",连续"+maxLongLossDays+"天亏本次数："
						+ lossDaysCount +",交易流水总计："+ sumFlow +"\r\n");
			}

			double avgLotteryDays = sumLotteryCount / (sumLossCount + sumLotteryCount); //平均中奖天数
			totalMoneySum = totalMoneySum + moneySum;   //计算所有人剩余总的资金池
			totalFlowSum = totalFlowSum + sumFlow;		//计算所有人总的交易流水

			sb.append("名称为：" + name + "的结果已计算完毕。。。。。。\r\n");
			sbend.append("名称为：" + name + "的计算结果为   "
					+ list.size() +"次记录，资金池还有:"+moneySum+",总中奖金额:"+sumLotteryMoney+",总中奖次数:"+sumLotteryCount+",总亏本金额:"
					+sumLossMoney + ",总亏本次数:"+sumLossCount+",总未参加次数:"+sumNotJoinCount+",最长亏本天数:"+longLossDays+",连续"+maxLongLossDays+"天亏本次数："
					+ lossDaysCount +",交易流水总计："+sumFlow +"\r\n");
			
		}
		
		try {
			FileWriter fw = new FileWriter("G:\\项目\\project_l\\test.txt");
			fw.write(sb.toString()+"\r\n");
			fw.write(sbend.toString()+"全部计算完毕，总资金池 :"+totalMoneySum +",总的交易流水:"+totalFlowSum+",交易折扣:"+discount+",返点:" + totalFlowSum*discount);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	
	
	
	/**
	 * 计算本次投入金额，保证赢了就回本，不赚都行
	 * @param lotteryMoney   本次中奖金额
	 * @param lossMoneySum   已连续亏损金额
	 * @param currentLossDays 当前连续亏本天数
	 * @return
	 */
	private int calcCurrentJoinMoney(Integer lotteryMoney,int lossMoneySum,int currentLossDays,BuyInfoBean buyInfo,BuyInfoBean[] buyInfos){
		// 大奖池没计算未写
		int x = buyInfo.getInitMoney();
		if(lotteryMoney != null){

			if(currentLossDays >= numOfLossCountCost && currentLossDays < maxLongLossDays){
				//要加上本次投入的钱 , 向上取整，计算出要投入多少钱才能保本
				x = (int)(Math.ceil( ((double) lossMoneySum/ ((double)lotteryMoney/100d - 1d )))  * numOfLossCountCostMultiple );
				
			}
		}
		
		return x;
	}
	/**
	 * 测算分组投注模式
	 */
	@Override
	public void testGroup() {
		// TODO Auto-generated method stub
		System.out.println("11234455678");
		
		String[] names ={"黄药师","好彩头","飞龙在天","老鸭汤","红单会","逐鹿中原","鹤壁王者荣耀","才子","大汉"};
		StringBuffer sb = new StringBuffer("计算规则：最多允许连续不中奖次数："+maxLongLossDays+",失败"+numOfLossCountCost+"次开始计算保本。\r\n");
		StringBuffer sbend = new StringBuffer();
		int totalMoneySum = jionPersonCount * onePersonJionMoney; // 总的资金池
		int totalFlowSum = 0;  // 总的交易流水
		double discount = flowDiscount; //交易流水的折扣返点
		BuyInfoBean[] buyInfos = new BuyInfoBean[names.length];//每位专家的购买信息
		
		//月份
		for(int m = 10; m < 17; m++){
			for(int d=1;d<32;d++){//按日循环
				String date = m+"月"+ d + "日"; //拼接日期
//				String sql = "select * from technical_verification tv where date = '"+date+"'";
//				List<TechnicalVerificationBean> list = testDao.querySql(sql);
//				sb.append(date +" 共"+ list.size()+"参与：");
				

				int dayJoinCount = 0;//当日参加的人数
				int dayLotteryCount = 0; //当日中奖人数
				int dayLossCount = 0; //当日失败人数
				int dayNotJoinCount = 0;//当日未参加人数
				int dayMoneySum = 0; //当日资金池
				int dayLossSum = 0 ; //当日总亏损
				int dayLotterySum = 0 ; //当日总中奖金额
				int dayJoinSum = 0; //当日投入资金
				int dayMoneyFlow = 0; //当日交易流水
				for(int x = 0 ; x <names.length;x++)
				{
					String name = names[x];
					if(buyInfos[x] == null){//初始化
						buyInfos[x] = new BuyInfoBean();
						buyInfos[x].setName(name);
						buyInfos[x].setMoneySum(onePersonJionMoney); //个人资金池初始化
					}
					BuyInfoBean buyInfo = buyInfos[x];
					
					String sql = "select * from technical_verification2 tv where tv.name='"+name+"' and date = '"+date+"'";
					
					List<TechnicalVerificationBean> list = testDao.querySql(sql);
					if(list.size() == 0) {
//						sb.append(name + " 本次没有购买记录，跳过。。。");
						continue;
					}
					dayJoinCount++; //当日参加人数
//					sb.append(date +","+name +",是否中奖：");
					buyInfo.getSb().append("开始为名称为：" + name + "进行计算。。。。。。\r\n");
					TechnicalVerificationBean bean = list.get(0);
					
					int initMoney = buyInfo.getInitMoney(); // 初始金额
					int moneySum = buyInfo.getMoneySum(); // 资金池
					int sumLotteryMoney = buyInfo.getSumLossMoney(); //总中奖金额
					int sumLossMoney = buyInfo.getSumLossMoney() ; // 总亏本金额
					int sumLotteryCount = buyInfo.getSumLotteryCount(); // 总中奖次数
					int sumLossCount = buyInfo.getSumLossCount() ; //总亏本次数
					int sumNotJoinCount = buyInfo.getSumNotJoinCount();//总未参加次数
					int longLossDays = buyInfo.getLongLossDays(); //最长亏本天数
					int lossDaysCount = buyInfo.getLossDaysCount(); //连续亏本次数
					int lossMoneyContinueSum = buyInfo.getLossMoneyContinueSum(); //连续亏本金额
					int sumFlow = buyInfo.getSumFlow(); //交易流水
					int currentLossDays = buyInfo.getCurrentLossDays(); //目前亏本天数
					int i = buyInfo.getCount();
//					for(TechnicalVerificationBean bean : list){
//						int currentjoinMoney = initMoney; // 本次投入的钱是根据上次投入结果确定的
					
					
						int currentjoinMoney = calcCurrentJoinMoney(bean.getLottery_money(),lossMoneyContinueSum,currentLossDays ,buyInfo,buyInfos);
						moneySum = moneySum - currentjoinMoney;  // 本次投入的钱是从资金池出的
						dayMoneySum = dayMoneySum + moneySum; //当日投入后资金池剩余的金额
						dayJoinSum = dayJoinSum + currentjoinMoney; //当日投入总资金
						dayMoneyFlow = dayMoneyFlow + currentjoinMoney;
						
						String isLottery = bean.getIs_lottery();
						Integer lotteryMoney = bean.getLottery_money();
						if("中".equalsIgnoreCase(isLottery)){
							if(currentLossDays > 0 && currentLossDays < maxLongLossDays){
								lotteryMoney = (int) Math.floor( (double)lotteryMoney * (double)currentjoinMoney/100d ); //加倍投入的中奖金额也加倍
							}
							moneySum = moneySum + lotteryMoney; //资金池增加
							sumLotteryMoney = sumLotteryMoney +lotteryMoney; //总中奖金额增加
							sumLotteryCount++; //总中奖次数加1
							dayLotterySum = dayLotterySum + lotteryMoney; //当日总中奖增加
							
							
							
							sumFlow = sumFlow + currentjoinMoney;   // 交易流水增加实际投入
							currentLossDays = 0;
							lossMoneyContinueSum = 0;
							initMoney = 100; // 中奖后投入金额还原为100
							dayLotteryCount++;
						}else if("否".equalsIgnoreCase(isLottery)){
							sumFlow = sumFlow + currentjoinMoney;// 交易流水增加实际投入
							lossMoneyContinueSum = lossMoneyContinueSum + currentjoinMoney ; // 当前连续亏本金额增加
							sumLossMoney = sumLossMoney + initMoney; //总亏本金额增加
							sumLossCount++; //总亏本次数加1
							initMoney = initMoney + initMoney; //下次投入资金加倍
							dayLossSum = dayLossSum + initMoney; // 当日总亏损金额增加
							
							
							currentLossDays++;  //当前连续亏本天数增加1
							if(currentLossDays > longLossDays){
								longLossDays = currentLossDays;
							}
							if(currentLossDays == maxLongLossDays){
								lossDaysCount = lossDaysCount+1;
								initMoney = 100; // 达到7天，投入金额重置
							}
							lotteryMoney = 0; //中奖额度
							dayLossCount++;
						}else{

							moneySum = moneySum + initMoney;  // 本次未参加，则把钱还如资金池
							dayMoneySum = dayMoneySum + initMoney ; //本次未参加，则把钱归还当日资金池
							dayJoinSum = dayJoinSum - initMoney; //本次未参加，则把钱归还当日总投入
							dayMoneyFlow = dayMoneyFlow - initMoney;  //本次未参加，则把钱归还交易流水
							
							sumNotJoinCount++; //总未参加次数增加
							dayNotJoinCount++;
						}
						
						
//						sb.append("日期："+date +","+name +",中奖标识："+isLottery+",中奖额度："+lotteryMoney+",个人资金池还有:"+moneySum+",中奖额基数:"+bean.getLottery_money()+",总中奖金额:"+sumLotteryMoney+",总中奖次数:"+sumLotteryCount+",总亏本金额:"
//								+sumLossMoney + ",连续亏本金额："+lossMoneyContinueSum+",总亏本次数:"+sumLossCount+",总未参加次数:"+sumNotJoinCount+",最长亏本天数:"+longLossDays+",连续"+ maxLongLossDays + "天亏本次数："
//								+ lossDaysCount +",交易流水总计："+ sumFlow +"\r\n");
						
						buyInfo.getSb().append(name + "： "
								+ i +"次记录，本次投入："+ currentjoinMoney +",中奖额度："+lotteryMoney+",个人资金池还有:"+moneySum+"，中奖额基数:"+bean.getLottery_money()+",总中奖金额:"+sumLotteryMoney+",总中奖次数:"+sumLotteryCount+",总亏本金额:"
								+sumLossMoney + ",连续亏本金额："+lossMoneyContinueSum+",总亏本次数:"+sumLossCount+",总未参加次数:"+sumNotJoinCount+",最长亏本天数:"+longLossDays+",连续"+ maxLongLossDays + "天亏本次数："
								+ lossDaysCount +",交易流水总计："+ sumFlow +"\r\n");

						buyInfo.setInitMoney(initMoney); // 初始金额
						buyInfo.setMoneySum(moneySum); // 资金池
						buyInfo.setSumLossMoney(sumLotteryMoney); //总中奖金额
						buyInfo.setSumLossMoney(sumLossMoney) ; // 总亏本金额
						buyInfo.setSumLotteryCount(sumLotteryCount); // 总中奖次数
						buyInfo.setSumLossCount(sumLossCount) ; //总亏本次数
						buyInfo.setSumNotJoinCount(sumNotJoinCount);//总未参加次数
						buyInfo.setLongLossDays(longLossDays); //最长亏本天数
						buyInfo.setLossDaysCount(lossDaysCount); //连续亏本次数
						buyInfo.setLossMoneyContinueSum(lossMoneyContinueSum); //连续亏本金额
						buyInfo.setSumFlow(sumFlow); //交易流水
						buyInfo.setCurrentLossDays(currentLossDays); //目前亏本天数
						buyInfo.setCount(i+1); // 购买次数
						
//					}

//					double avgLotteryDays = sumLotteryCount / (sumLossCount + sumLotteryCount); //平均中奖天数
//					totalMoneySum = totalMoneySum + moneySum;   //计算所有人剩余总的资金池
//					totalFlowSum = totalFlowSum + sumFlow;		//计算所有人总的交易流水

//					sb.append("名称为：" + name + "的结果已计算完毕。。。。。。\r\n");
//					sbend.append("名称为：" + name + "的计算结果为   "
//							+ list.size() +"次记录，资金池还有:"+moneySum+",总中奖金额:"+sumLotteryMoney+",总中奖次数:"+sumLotteryCount+",总亏本金额:"
//							+sumLossMoney + ",总亏本次数:"+sumLossCount+",总未参加次数:"+sumNotJoinCount+",最长亏本天数:"+longLossDays+",连续"+maxLongLossDays+"天亏本次数："
//							+ lossDaysCount +",交易流水总计："+sumFlow +"\r\n");
					
				}
				sb.append(date+"共"+dayJoinCount+"人参与,"+dayLotteryCount+"人中奖,"+dayLossCount+"人未中,"+dayNotJoinCount+"人未参加,当日总投入："+dayJoinSum
						+",当日总亏损："+dayLossSum+ ",当日投入后资金池剩余："+dayMoneySum +",当日总中奖："+dayLotterySum+",中奖后资金池剩余："+(dayLotterySum+dayMoneySum)
						+",当日交易流水："+dayMoneyFlow+"\r\n");
				
			}
			
		}
		
		try {
			int makeMoneySum = 0;
			FileWriter fw = new FileWriter("G:\\项目\\project_l\\test.txt");
			FileWriter fw2 = new FileWriter("G:\\项目\\project_l\\test_person.txt");

			fw.write(sb.toString()+"\r\n");
			
			for(BuyInfoBean b : buyInfos){
				fw2.write(b.getSb().toString());
				totalFlowSum = totalFlowSum + b.getSumFlow();
				fw.write(b.getFinalResult());
				
				makeMoneySum = makeMoneySum + b.getMakeMoney();
			}
			
			fw.write(sbend.toString()+"全部计算完毕，总计中奖赚钱："+ makeMoneySum +"总的交易流水:"+totalFlowSum+",交易折扣:"+discount+",返点:" + totalFlowSum*discount);
			fw.close();
			
			//每位专家的计算轨迹
			fw2.write("最多允许连续不中奖次数："+maxLongLossDays+",失败"+numOfLossCountCost+"次开始计算保本。\r\n");
			
			fw2.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		
		String xml = "H:\\workspaces\\jixun\\llshop\\WebRoot\\WEB-INF\\conf\\applicationContext.xml";
		ApplicationContext ac = new FileSystemXmlApplicationContext(xml);
		ITestService testService = (ITestService) ac.getBean("testService");
		testService.testGroup();
		
		
		
	}
	
}
