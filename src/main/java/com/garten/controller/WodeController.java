package com.garten.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garten.service.WodeService;






@Controller
@RequestMapping("/wode")
public class WodeController {
	@Autowired
	private WodeService wodeService;
	
	
	@RequestMapping("/student")
	@ResponseBody
	public Map<String,Object> test(HttpServletRequest request,Integer pageNo){
		Map<String,Object> map=wodeService.find( request,pageNo);
		return map;
	}
}
