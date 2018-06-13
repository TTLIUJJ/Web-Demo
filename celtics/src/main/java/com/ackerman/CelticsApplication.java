package com.ackerman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * @Author: Ackerman
 * @Description: 调试模式
 * @Date: Created in 下午2:33 18-6-4
 */
@SpringBootApplication
public class CelticsApplication {
    public static void main(String []args){
        SpringApplication.run(CelticsApplication.class, args);
    }
}



///**
//* @Description: 线上模式
//* @Date: 下午5:06 18-6-5
//*/
//@SpringBootApplication
//public class CelticsApplication extends SpringBootServletInitializer {
//
//	public static void main(String[] args) {
//		SpringApplication.run(CelticsApplication.class, args);
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(CelticsApplication.class);
//	}
//}