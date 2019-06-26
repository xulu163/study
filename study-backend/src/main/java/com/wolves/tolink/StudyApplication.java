package com.wolves.tolink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.wolves")
/**
 * @author xulu on 2019/3/11.
 */
public class StudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	}

	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	}
}
