package com.scullincw.wechatserverside.common;
import  java.io.BufferedReader;
import java.io.File;
import  java.io.IOException;  
import  java.io.InputStream;  
import  java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import com.alibaba.fastjson.JSONObject;

public class PythonRunner implements Runnable {
	//private static final String PYTHON_PATH = "C:\\Users\\scullin\\AppData\\Local\\Programs\\Python\\Python38\\python.exe";
	final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String SOURCE_PATH = System.getProperty("user.dir") + "\\python-spider";
	private String URL;					//��Ʒ��ַ
	private String openId;				//�û�Ψһ��ʶ��openid
	private String SOURCE_NAME = null;	//���е�Python�ļ���
	private CountDownLatch latch;
	
	public PythonRunner(String url, String openId, String source_name, CountDownLatch latch) {
		this.URL = url;
		this.openId = openId;
		this.SOURCE_NAME = source_name;
		this.latch = latch;
		System.out.println(SOURCE_PATH);
	}
	
	public void run() {
		int exit_code = -1;		//python exit code
		System.out.println("[" + sdf.format(new Date()) + "] Runing Python Runtime...");
		String[] cmd = new String[] { "python ", SOURCE_NAME, URL,  openId};
		
        ProcessBuilder builder = new  ProcessBuilder();  
        builder.command(cmd);
        builder.directory(new File(SOURCE_PATH));
  
        try  {  
            Process pythonProcess = builder.start();
            final InputStream inputStream = pythonProcess.getInputStream();  
            final InputStream errorStream = pythonProcess.getErrorStream();
            
            //standard output
            new Thread() {  
                public   void  run() {  
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));  
                    try  {  
                        String lineA = null ;
                        while  ((lineA = br.readLine()) !=  null) {  
                            if  (lineA !=  null ) System.out.println(lineA);
                        }  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }.start();  
            
            //error output
            new Thread() { 
                public void run() {
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(errorStream));  
                    try {
                        String lineB = null ;  
                        while  ((lineB = br2.readLine()) !=  null) {
                            if  (lineB !=  null ) System.out.println(lineB);  
                        }  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }
            }.start();
            
            /*
             * TODO: waitFor() have parameter 'timeout' and 'timeunit' to set
             */
            exit_code = pythonProcess.waitFor();
            pythonProcess.destroy();
            inputStream.close();
            errorStream.close();
            latch.countDown();
        } catch  (Exception e) {  
            System.err.println(e);  
        }
        System.out.println("Python program exit with code " + exit_code);
    }
	
	public static void main(String[] args) {
		String url = "https://item.jd.com/100000205012.html";
		String openId = "oY1mB4g3MhuTNLA1de7JuRFCKZ0E";
		String source_name = "jd_comment.py";
		CountDownLatch latch = new CountDownLatch(1);
		Thread th = new Thread(new PythonRunner(url, openId, source_name, latch));
		th.start();
		try {
			latch.await();	//�����̵ȴ�
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("all work done at "+sdf.format(new Date()));
		
	}
}
