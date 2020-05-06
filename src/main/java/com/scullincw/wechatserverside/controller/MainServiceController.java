package com.scullincw.wechatserverside.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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
	final static String SOURCE_PATH = System.getProperty("user.dir") + "\\python-spider\\";
	
	@PostMapping("/analyze")
	@ResponseBody
	public GlobalResult analyzeItem(@RequestParam(value = "openid", required = true) String openid,
									@RequestParam(value = "sessionKey", required = true)String skey,
									@RequestParam(value = "url", required = true)String URL
			) {
		/**
		 * TODO: 验证openid和sessionKey
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
		Thread th2 = new Thread(new PythonRunner(realURL, "jd_info.py", latch));
		th2.start();
		
		try {
			latch.await(3, TimeUnit.MINUTES);	//主进程等待，不超过3分钟
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("all work done at "+sdf.format(new Date()));
		
		
		//处理结果：商品名称和图片URL, average_sentiment.txt, jd_ciyun.jpg
		JSONObject httpResData = new JSONObject();
		
		//读取jd_info.txt，即商品在京东的完整名称，只有一行
		try {
			BufferedReader br = new BufferedReader(new FileReader(SOURCE_PATH + "jd_info.txt"));
			String line = null;
			if((line = br.readLine()) != null) {
				httpResData.put("itemName", line);
			}
			br.close();
			//System.out.println(httpResData.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//读取average_sentiment.txt，即情感指数和评论数
		JSONObject tempObj = new JSONObject();
		try {
			BufferedReader br = new BufferedReader(new FileReader(SOURCE_PATH + "average_sentiment.txt"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			tempObj = JSONObject.parseObject(sb.toString());
			//System.out.println(tempObj.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		httpResData.putAll(tempObj);
		System.out.println(httpResData.toString());
		/**
		 * 示例输出：
		 * {"itemName":"【微软Surface Pro 7】微软 Surface Pro 7 亮铂金+灰钴蓝键盘 二合一平板电脑笔记本电脑 | 12.3英寸 第十代酷睿i5 8G 256G SSD","comments_num":386,"average_sentiment":0.8626159394675835}
		 */
		
		
		//TODO: 处理图片: jd_ciyun.jpg
		
		
		return new GlobalResult(200, "查询成功", httpResData);
	}
}
