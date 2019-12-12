package com.scullincw.wechatserverside.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequestMapping("/wechat")
public class ControllerText {
	
	@RequestMapping("/getUser")
	public Map<String, Object> getUser(){
		System.out.println("微信小程序正在调用。。。");
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
 		list.add("zhangsan");
 		list.add("lisi");
 		list.add("wanger");
 		list.add("mazi");
 		map.put("list",list);
		System.out.println("微信小程序调用完成。。。");
		return map;
	}
	
	@RequestMapping("/getWord")
	public Map<String, Object> getText(String word){
		Map<String, Object> map = new HashMap<String, Object>();
		String message = "我能力有限，不要为难我";
		if ("后来".equals(word)) {
			message="正在热映的后来的我们是刘若英的处女作。";
		}else if("微信小程序".equals(word)){
			message= "想获取更多微信小程序相关知识，请更多的阅读微信官方文档，还有其他更多微信开发相关的内容，学无止境。";
		}else if("西安工业大学".equals(word)){
			message="西安工业大学（Xi'an Technological University）简称”西安工大“，位于世界历史名城古都西安，是中国西北地区唯一一所以兵工为特色，以工为主，理、文、经、管、法协调发展的教学研究型大学。原中华人民共和国兵器工业部直属的七所本科院校之一（“兵工七子”），陕西省重点建设的高水平教学研究型大学、陕西省人民政府与中国兵器工业集团、国防科技工业局共建高校、教育部“卓越工程师教育培养计划”试点高校、陕西省大学生创新能力培养综合改革试点学校。国家二级保密资格单位，是一所以\"军民结合，寓军于民\"的国防科研高校。";
		}
		map.put("message", message);
		return map;
	}
	
	@RequestMapping("/")
	public String getText(){
		return "hello world";
	}


}
