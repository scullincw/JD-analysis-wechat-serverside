package com.scullincw.wechatserverside.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scullincw.wechatserverside.common.GlobalResult;
import com.scullincw.wechatserverside.common.PythonRunner;

@RequestMapping("/wechat")
@Controller
/** 
 * @Description: 负责应用主要业务的控制器：京东商品页的爬虫与分析
 * @Author: scullincw
 */
public class MainServiceController {
	
	@PostMapping("/analyze")
	@ResponseBody
	public GlobalResult analyzeItem(@RequestParam(value = "openid", required = true) String openid,
									@RequestParam(value = "sessionKey", required = true)String skey,
									@RequestParam(value = "url", required = true)String URL
			) {
		/**
		 * TODO: 验证openid和sessionKey, 验证url
		 */
		
		//用正则表达式验证URL
		String realURL = null;
		
		if(URL == null) {
			return new GlobalResult(555, "URL字符串不能为空.", null);
		}
		
		String regex = "(?<httpitem>https:(\\/)(\\/)item(\\.))(\\D*)(?<jdcomhtml>jd(\\.)com(\\/)(\\d+)\\.html)(.*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(URL);
		boolean isFind = matcher.find();
		if(isFind) {
			//System.out.println(matcher.group("httpitem") + matcher.group("jdcomhtml"));
			realURL = matcher.group("httpitem") + matcher.group("jdcomhtml");
		} else {
			return new GlobalResult(555, "URL不是有效的京东商品链接.", null);
		}
		
		//多线程 (1)运行python爬虫和分析 (2)获取商品基本信息
		CountDownLatch latch = new CountDownLatch(2);	//2个子线程
		Thread th1 = new Thread(new PythonRunner(realURL, "jd_comment.py", latch));
		th1.start();
		Thread th2 = new Thread(new PythonRunner(realURL, "jd_picture.py", latch));
		th2.start();
		
		try {
			latch.await();	//主进程等待
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("all work done at "+sdf.format(new Date()));
		
		
		//处理结果：商品名称和图片URL, average_sentiment.txt, jd_ciyun.jpg
		JSONObject httpResData = new JSONObject();
		
		//处理
		
		//读取average_sentiment.txt
		try {
			BufferedReader br = new BufferedReader(new FileReader("\\python-spider\\average_sentiment.txt"));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line.trim());
			}
			br.close();
			JSONObject json_result = JSON.parseObject(sb.toString());
			httpResData.put("result", json_result);
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(httpResData);
		
//		//读取result.csv
//		try {
//			BufferedReader br = new BufferedReader(new FileReader("\\python-spider\\result.csv"));
//			br.readLine();	//第一行信息为标题信息
//			String line = null;
//			while((line = br.readLine()) != null) {
//				String item[] = line.split(",");	//CSV格式文件为逗号分隔符文件，这里根据逗号切分
//				//TODO: 获取和处理数据项
//			}
//		} catch (Exception e) {  
//            e.printStackTrace();  
//      }
		
		
		
		return null;
	}
}
