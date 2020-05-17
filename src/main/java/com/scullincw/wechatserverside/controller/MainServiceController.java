package com.scullincw.wechatserverside.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scullincw.wechatserverside.common.GlobalResult;
import com.scullincw.wechatserverside.common.PythonRunner;
import com.scullincw.wechatserverside.mapper.UserMapper;
import com.scullincw.wechatserverside.pojo.User;

@RequestMapping("/wechat")
@Controller
/** 
 * @Description: 负责应用主要业务的控制器：京东商品页的爬虫与分析
 * @Author: scullincw
 */
public class MainServiceController {
	final static String SOURCE_PATH = System.getProperty("user.dir") + "\\python-spider\\";
	
	@Autowired
    private UserMapper userMapper;
	
	@PostMapping("/analyze")
	@ResponseBody
	public GlobalResult analyzeItem(@RequestParam(value = "openid", required = true) String openid,
									@RequestParam(value = "sessionKey", required = true)String skey,
									@RequestParam(value = "url", required = true)String URL
			) {
		/**
		 * 验证openid和sessionKey
		 */
		User user = this.userMapper.selectById(openid);		//openid是用户数据表的主键，即可以唯一确定用户的标识
		if(user == null) {
			return new GlobalResult(501, "用户openid错误！", null);
		}
		if(!skey.equals(user.getSessionKey())) {
			//System.out.println("skey: " + skey);
			//System.out.println("user.getSessionKey: " + user.getSessionKey());
			return new GlobalResult(502, "用户登录态错误！", null);
		}
		
		/**
		 * 用正则表达式验证URL
		 */
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
			System.out.println("\n" + realURL);
		} else {
			return new GlobalResult(555, "URL不是有效的京东商品链接.", null);
		}
		
		/**
		 * 多线程 (1)运行python爬虫和分析 (2)获取商品基本信息
		 */
		CountDownLatch latch = new CountDownLatch(2);	//2个子线程
		Thread th1 = new Thread(new PythonRunner(realURL, openid, "jd_comment.py", latch));
		th1.start();
		Thread th2 = new Thread(new PythonRunner(realURL, openid, "jd_info.py", latch));
		th2.start();
		
		try {
			latch.await(3, TimeUnit.MINUTES);	//主进程等待，不超过3分钟
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("all work done at "+sdf.format(new Date()));
		
		
		/**
		 * 处理结果：商品名称和图片URL, average_sentiment.txt, jd_ciyun.jpg
		 */
		JSONObject httpResData = new JSONObject();
		
		//读取jd_info.txt
		try {
			BufferedReader br = new BufferedReader(new FileReader(SOURCE_PATH + "jd_info.txt"));
			String line = null;
			//读取第一行 商品名称
			if((line = br.readLine()) != null) {
				httpResData.put("itemName", line);
			}
			//读取第二行 商家名称
			if((line = br.readLine()) != null) {
				httpResData.put("shopName", line);
			}
			//读取第三行 商品主图URL
			if((line = br.readLine()) != null) {
				httpResData.put("imgUrl", line);
			}
			//关闭流
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
		
		//从jd_comment.csv截取评论
		JSONObject comments = new JSONObject();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(SOURCE_PATH + "jd_comment.csv"), "GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = br.readLine();
			//截取前4条评论
			for(int i = 0; line != null && i < 4; i++) {
				comments.put(Integer.valueOf(i).toString(), line);
				line = br.readLine();
			}
			isr.close();
			//System.out.println(comments.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		httpResData.put("comments", comments);
		
		System.out.println(httpResData.toString());
		/**
		 * 示例输出：
		 * {"itemName":"【微软Surface Pro 7】微软 Surface Pro 7 亮铂金+灰钴蓝键盘 二合一平板电脑笔记本电脑 | 12.3英寸 第十代酷睿i5 8G 256G SSD","comments_num":386,"average_sentiment":0.8626159394675835}
		 */
		
		
		
		
		return new GlobalResult(200, "查询成功", httpResData);
	}
}
