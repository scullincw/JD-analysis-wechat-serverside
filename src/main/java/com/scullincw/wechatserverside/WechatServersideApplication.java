package com.scullincw.wechatserverside;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.scullincw.wechatserverside.mapper") //设置mapper接口的扫描包
@SpringBootApplication()
//SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class WechatServersideApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatServersideApplication.class, args);
	}

}
