package com.yd.business.test.main;

import java.lang.reflect.Method;

import com.yd.business.lottery.bean.ExpertResultAndFlowBean;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExpertResultAndFlowBean bean = new ExpertResultAndFlowBean();
		try {
			
			Method[] methods = bean.getClass().getMethods();
			
			for(Method m : methods){
				Class[] params = m.getParameterTypes();
				Class r = m.getReturnType();
				System.out.print(m.getName()+":");
				for(Class p : params){
					System.out.print(p.getName()+",");
				}
				System.out.println(" return :"+r.getName());
			}
			
			Method get = bean.getClass().getMethod("getNowpage" );
			Object gr = get.invoke(bean, null);
			System.out.println(gr.getClass());
			System.out.println(get.getReturnType());
			Method set = bean.getClass().getMethod("setNowpage",get.getReturnType());
			System.out.println(set.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
