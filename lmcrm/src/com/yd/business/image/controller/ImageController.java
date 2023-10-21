/**
 * 
 */
package com.yd.business.image.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;

/**
 * @author ice
 *
 */
@Controller
public class ImageController extends BaseController {
	
	
	@RequestMapping("**/testImg.do")
	public ModelAndView testImg(HttpServletRequest request, HttpServletResponse response){
		
		try {
			System.out.println(Thread.currentThread().getName()+" sleep....");
			
			Thread.sleep(1000 * 120);
			
			
		} catch (Exception e) {
			log.error(e, e);
		}
		System.out.println(Thread.currentThread().getName()+" over....");
		
		return null;
		
	}
}
