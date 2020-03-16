package com.scullincw.wechatserverside.common;
import  java.io.BufferedReader;
import java.io.File;
import  java.io.IOException;  
import  java.io.InputStream;  
import  java.io.InputStreamReader;

public class MultiThreadProcess implements Runnable {
	private static final String PYTHON_PATH = "C:\\Users\\scullin\\AppData\\Local\\Programs\\Python\\Python38\\python.exe";
	private static final String SOURCE_PATH = System.getProperty("user.dir") + "\\python-source";
	private static final String SOURCE_NAME = "jd_comment.py";
	private String URL;
	
	public MultiThreadProcess(String url) {
		this.URL = url;
		System.out.println(SOURCE_PATH);
	}
	
	public void run() {
		int exit_code = -1;		//python exit code
		System.out.println("Runing Python Runtime...");
		String[] cmd = new String[] { "python ", SOURCE_NAME, URL };
		
        ProcessBuilder builder = new  ProcessBuilder();  
        builder.command(cmd);
        builder.directory(new File(SOURCE_PATH));
  
        try  {  
            Process pythonProcess = builder.start();
            final InputStream inputStream = pythonProcess.getInputStream();  
            final InputStream errorStream = pythonProcess.getErrorStream();
            
            //�����׼�����߳�
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
            
            //������������߳�
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
            
            exit_code = pythonProcess.waitFor();
            pythonProcess.destroy();
            inputStream.close();
            errorStream.close();
        } catch  (Exception e) {  
            System.err.println(e);  
        }
        System.out.println("Python program exit with code " + exit_code);
    }
	
	public static void main(String[] args) {
		new MultiThreadProcess("https://item.jd.com/100009177424.html").run();
	}
}
