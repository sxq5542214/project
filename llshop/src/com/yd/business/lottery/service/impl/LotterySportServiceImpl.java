/**
 * 
 */
package com.yd.business.lottery.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.lottery.bean.ExpertBean;
import com.yd.business.lottery.bean.ExpertResultAndFlowBean;
import com.yd.business.lottery.bean.ExpertResultBean;
import com.yd.business.lottery.bean.ExpertResultFlowBean;
import com.yd.business.lottery.dao.ILotterySportDao;
import com.yd.business.lottery.service.ILotterySportService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Service("lotterySportService")
public class LotterySportServiceImpl extends BaseService implements ILotterySportService {
	@Resource
	private ILotterySportDao lotterySportDao;
	@Resource
	private IConfigAttributeService configAttributeService;
	
	@Override
	public List<ExpertResultAndFlowBean> queryExpertResultInfo(ExpertResultBean bean){
		
		if(bean == null){
			bean = new ExpertResultBean();
		}
		if(bean.getLottery_result() != null){
			bean.setLottery_result("'"+bean.getLottery_result()+"'");
		}
		
		bean.setOrderby(" order by enter_time desc,modify_time desc limit 50");
		return lotterySportDao.queryExpertResultAndFlow(bean);
	}
	
	@Override
	public Integer createExpertResultInfo(ExpertResultBean bean){
		if(bean != null){
			return lotterySportDao.createExpertResultInfo(bean);
		}
		return null;
	}
	
	@Override
	public void handleExpertResult(ExpertResultBean result){

		try {
			// 查询专家的最后一次购买记录
			ExpertResultFlowBean flow = new ExpertResultFlowBean();
			ExpertResultFlowBean lastFlow = null;
			// 中奖金额
			Integer lottery_money = ( result.getJoin_money().intValue() * result.getOdds().intValue()) / 100 ;
			// 查询专家
			ExpertBean expert = lotterySportDao.findExpertById(result.getExpert_id());
			if(expert == null){
				return ;
			}
			
			//是待开的状态，只需要修改
			if(result.getId() != null ){
				//查找数据库里的状态是否为待开
				ExpertResultBean oldResult = lotterySportDao.findExpertResultById(result.getId());
				if(ExpertResultBean.LOTTER_RESULT_WAIT.equals(oldResult.getLottery_result())){
					lastFlow = lotterySportDao.findExpertResultFlowByResultId(result.getId());
					updateResultAndFlow(lottery_money,result,lastFlow);
					return;
				}
				
			}else{
				lastFlow = lotterySportDao.findLastExpertResultFlowByExpertId(result.getExpert_id());
				result.setCreate_time(DateUtil.getNowDateStr());
			}
			
			if(lastFlow != null){
				//有过历史
				AutoInvokeGetSetMethod.autoInvoke(lastFlow, flow);
				flow.setId(null);
				int cash_pool = lotterySportDao.queryCashPool();
				flow.setCash_pool(cash_pool);
				
			}else{
				//第一次
				int init_money = configAttributeService.getIntValueByCode(AttributeConstant.CODE_LOTTERY_SPORT_INIT_MONEY);
				int money_sum = 0;

				int cash_pool = lotterySportDao.queryCashPool();
				flow.setExpert_id(expert.getId());
				flow.setExpert_name(expert.getName());
				flow.setCurrent_join_money(result.getJoin_money());
				flow.setLottery_money(lottery_money);
				flow.setInit_money(init_money);
				flow.setMoney_sum(money_sum);
				flow.setSum_lottery_count(0);
				flow.setSum_lottery_money(0);
				flow.setSum_loss_money(0);
				flow.setSum_loss_count(0);
				flow.setLong_loss_days(0);
				flow.setSum_not_join_count(0);
				flow.setLoss_days_count(0);
				flow.setLoss_money_continue_sum(0);
				flow.setSum_flow(0);
				flow.setCurrent_loss_days(0);
				flow.setIndex_no(0);
				flow.setCash_pool(cash_pool);
			}
			
			result.setExpert_name(expert.getName());
			//中奖
			if(ExpertResultBean.LOTTER_RESULT_TRUE.equalsIgnoreCase(result.getLottery_result())){
				lotteryTrue(lottery_money, result, flow);
			}
			//未中
			if(ExpertResultBean.LOTTER_RESULT_FALSE.equalsIgnoreCase(result.getLottery_result())){
				lotteryFalse(lottery_money, result, flow);
			}
			//待开
			if(ExpertResultBean.LOTTER_RESULT_WAIT.equalsIgnoreCase(result.getLottery_result())){
				result.setLottery_money(0);
			}
			//休息
			if(ExpertResultBean.LOTTER_RESULT_REST.equalsIgnoreCase(result.getLottery_result())){
				result.setLottery_money(0);
				
				flow.setSum_not_join_count(flow.getSum_not_join_count() + 1);
			}
			lotterySportDao.createExpertResultInfo(result);
			
			int num_of_break_even = configAttributeService.getIntValueByCode(AttributeConstant.CODE_LOTTERY_SPORT_BREAK_EVEN);
			int max_long_loss_days = configAttributeService.getIntValueByCode(AttributeConstant.CODE_LOTTERY_SPORT_MAX_LOSS_DAYS);
			String loss_count_cost_multiple = configAttributeService.getValueByCode(AttributeConstant.CODE_LOTTERY_SPORT_LOSS_MULTIPLE);
			flow.setNum_of_break_even(num_of_break_even);
			flow.setIndex_no(flow.getIndex_no() + 1);
			flow.setCreate_time(DateUtil.getNowDateStr());
			flow.setMax_long_loss_days(max_long_loss_days);
			flow.setLoss_count_cost_multiple(loss_count_cost_multiple);
			flow.setExpert_result_id(result.getId());
			flow.setCurrent_join_money(result.getJoin_money());
			lotterySportDao.createResultFlow(flow);
			
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	
	private void lotteryTrue( Integer lottery_money,ExpertResultBean result, ExpertResultFlowBean flow){
		result.setLottery_money(lottery_money);

		flow.setLottery_money(lottery_money);
		flow.setSum_lottery_money(flow.getSum_lottery_money() + lottery_money);
		flow.setSum_lottery_count(flow.getSum_lottery_count() + 1);
		flow.setLoss_days_count(0);
		flow.setLoss_money_continue_sum(0);
		flow.setSum_flow(flow.getSum_flow() + result.getJoin_money());
		flow.setCurrent_loss_days(0);
		flow.setMoney_sum(lottery_money + flow.getMoney_sum() - result.getJoin_money());
		
		int cash_pool = lotterySportDao.queryCashPool();
		flow.setCash_pool(cash_pool + lottery_money - result.getJoin_money());
		lotterySportDao.updateCashPool(lottery_money - result.getJoin_money());
	}
	
	private void lotteryFalse( Integer lottery_money,ExpertResultBean result, ExpertResultFlowBean flow){
		result.setLottery_money(0);
		
		flow.setLottery_money(0);
		flow.setSum_loss_money(flow.getSum_loss_money() + result.getJoin_money());
		flow.setSum_loss_count(flow.getSum_loss_count() + 1);
		flow.setCurrent_loss_days(flow.getCurrent_loss_days() + 1);
		if(flow.getLong_loss_days().intValue() < flow.getCurrent_loss_days() ){
			flow.setLong_loss_days(flow.getCurrent_loss_days());
		}
		flow.setLoss_days_count(flow.getLong_loss_days() + 1);
		flow.setLoss_money_continue_sum(flow.getLoss_money_continue_sum() + result.getJoin_money());
		flow.setSum_flow(flow.getSum_flow() + result.getJoin_money());
		flow.setMoney_sum(flow.getMoney_sum() - result.getJoin_money());
		
		int cash_pool = lotterySportDao.queryCashPool();
		flow.setCash_pool(cash_pool - result.getJoin_money());
		lotterySportDao.updateCashPool(-result.getJoin_money());
	}
	
	/**
	 * 更新结果和流水
	 * @param result
	 * @param lastFlow
	 */
	private void updateResultAndFlow(Integer lottery_money,ExpertResultBean result, ExpertResultFlowBean flow) {
		
		//中奖
		if(ExpertResultBean.LOTTER_RESULT_TRUE.equalsIgnoreCase(result.getLottery_result())){
			lotteryTrue(lottery_money, result, flow);
		}
		//未中
		if(ExpertResultBean.LOTTER_RESULT_FALSE.equalsIgnoreCase(result.getLottery_result())){
			lotteryFalse(lottery_money, result, flow);
		}
		result.setModify_time(DateUtil.getNowDateStr());
		lotterySportDao.updateExpertResultInfo(result);
		lotterySportDao.updateExpertResultFlow(flow);
		
		
	}

	@Override
	public Integer calcCurrentJoinMoney(int expertId,int odds){
		int joinMoney = 0;
		// 查询专家的最后一次购买记录
		ExpertResultBean result = lotterySportDao.findLastJoinExpertResultByExpertId(expertId);
		int init_money = configAttributeService.getIntValueByCode(AttributeConstant.CODE_LOTTERY_SPORT_INIT_MONEY);
		if(result == null){ //如果没有记录，返回初始值
			joinMoney = init_money;
			return joinMoney;
		}
		
		ExpertResultFlowBean flow = lotterySportDao.findExpertResultFlowByResultId(result.getId());
		//如果上次是成功的，直接返回初始金额
		if( ExpertResultBean.LOTTER_RESULT_TRUE.equalsIgnoreCase(result.getLottery_result())){
			joinMoney = init_money;
		}else{
			int num_of_break_even = configAttributeService.getIntValueByCode(AttributeConstant.CODE_LOTTERY_SPORT_BREAK_EVEN);
			int max_long_loss_days = configAttributeService.getIntValueByCode(AttributeConstant.CODE_LOTTERY_SPORT_MAX_LOSS_DAYS);
			String loss_count_cost_multiple = configAttributeService.getValueByCode(AttributeConstant.CODE_LOTTERY_SPORT_LOSS_MULTIPLE);
			
			int currentLossDays = flow.getCurrent_loss_days();
			int lossMoneySum = flow.getLoss_money_continue_sum();
			if(currentLossDays  >= num_of_break_even && currentLossDays < max_long_loss_days){
				//要加上本次投入的钱 , 向上取整，计算出要投入多少钱才能保本
				joinMoney = (int)(Math.ceil( ((double) lossMoneySum/ ((double)odds/100d - 1d )))  * Double.parseDouble(loss_count_cost_multiple) );
			}else{
				joinMoney = result.getJoin_money().intValue() + result.getJoin_money() ;
				
				// 如果当前赔率太低，赢了也有可能赚不回来，要计算 当前赔率下，投入多少才能赚回初始投入
				int calc_break_even_money = (int)(Math.ceil( ((double) (lossMoneySum + init_money )/ ((double)odds/100d - 1d ))));
				if(joinMoney < calc_break_even_money){
					joinMoney = calc_break_even_money;
				}
				
			}
		}
		
		
		
		return joinMoney;
	}
	
	
	
	@Override
	public Integer updateExpertResultInfo(ExpertResultBean bean){
		if(bean != null){
			return lotterySportDao.updateExpertResultInfo(bean);
		}
		return null;
	}
	
	@Override
	public List<ExpertBean> queryExpertInfo(ExpertBean bean){
		if(bean == null){
			bean = new ExpertBean();
		}
		bean.setStatus(ExpertBean.STATUS_ENABLE);
		return lotterySportDao.queryExpertInfo(bean);
	}
	
}
