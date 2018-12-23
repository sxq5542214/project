/**
 * 
 */
package com.yd.business.lottery.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.lottery.bean.CqsscInfoBean;
import com.yd.business.lottery.dao.ILotterySSCDao;
import com.yd.business.lottery.service.ILotterySSCService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;

/**
 * @author ice
 *
 */
@Service("lotterySSCService")
public class LotterySSCServiceImpl extends BaseService implements ILotterySSCService {
	@Autowired
	private ILotterySSCDao lotterySSCDao;
	@Autowired
	private IConfigAttributeService configAttributeService;
	
	
	/**
	 * 展示重庆时时彩信息
	 */
	@Override
	public List<CqsscInfoBean> queryAndUpdateCQSSCInfo(String date){
		
		CqsscInfoBean bean = new CqsscInfoBean();
		bean.setOrderby(" order by period desc limit 20");
		List<CqsscInfoBean> list = lotterySSCDao.queryCqsscInfo(bean );

		//判断时间是否间隔5分钟，间隔就去取接口
		String nowDate = DateUtil.getNowDateStr();
		if(list != null){
			CqsscInfoBean last = list.get(0);
			try {
				Date lastDate = DateUtil.parseDate(last.getOpenTime());
				long lastTime = lastDate.getTime();
				long currentTime = System.currentTimeMillis();
				
				// 超过5分钟
				if((currentTime - lastTime) >= 5 * 60 * 1000 ){
					//接口取彩票数据
					list = accessAPIGetLotteryData();
					createOrUpdateCqsscInfo(list);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
			}
		}
		
		list = lotterySSCDao.queryCqsscInfo(bean );
		
		return list;
	}
	
	
	private void createOrUpdateCqsscInfo(List<CqsscInfoBean> list) {
		CqsscInfoBean condition = new CqsscInfoBean();
		for(CqsscInfoBean bean : list){
			
			condition.setPeriod(bean.getPeriod());
			List<CqsscInfoBean> result = lotterySSCDao.queryCqsscInfo(condition);
			if(result.size() <= 0){
				bean.setInsertTime(DateUtil.getNowDateStr());
				lotterySSCDao.createCqsscInfo(bean);
			}
		}
		
	}


	private List<CqsscInfoBean> accessAPIGetLotteryData() throws Exception{
		List<CqsscInfoBean> list = new ArrayList<CqsscInfoBean>();
		String url = configAttributeService.getValueByCode(AttributeConstant.CODE_LOTTERY_API_URL);
		
		String response = HttpUtil.get(url);
		
		//解析接口
		JSONObject jso = new JSONObject(response);
		JSONArray array = jso.getJSONArray("data");
		CqsscInfoBean bean = null;
		for(int i = 0 ; i < array.length() ;i++){
			JSONObject  jo = array.getJSONObject(i);
			bean = new CqsscInfoBean();
			bean.setName("重庆时时彩");
			bean.setType("cqssc");
			bean.setPeriod(jo.getString("expect"));
			bean.setOpenCode(jo.getString("opencode"));
			bean.setOpenTime(jo.getString("opentime"));;
			
			String[] codes = bean.getOpenCode().split(",");
			
			String code = codes[0]+codes[1]+codes[2]+codes[3]+codes[4];
			bean.setCode(Integer.parseInt(code));
			int total = Integer.parseInt(codes[0])+Integer.parseInt(codes[1])+
					Integer.parseInt(codes[2])+Integer.parseInt(codes[3])+Integer.parseInt(codes[4]);
			bean.setTotal(total);;
			
			String bigOrSmall = total >= 23 ? "大":"小";
			bean.setBigOrSmall(bigOrSmall);
			
			
			String dragonOrTiger = "龙";
			if(codes[0].equals(codes[4])){
				dragonOrTiger = "和";
			}else if(Integer.parseInt(codes[0]) < Integer.parseInt(codes[4])){
				dragonOrTiger = "虎";
			}
			bean.setDragonOrTiger(dragonOrTiger );
			
			String singleOrDouble = total % 2 == 0 ? "双":"单";
			bean.setSingleOrDouble(singleOrDouble );
			list.add(bean);
		}
		
		
		return list;
	}
	
	public static void main(String[] args) {

		JSONObject jso = new JSONObject("{\"rows\":20}");
		System.out.println(jso.get("rows"));
	}
}
