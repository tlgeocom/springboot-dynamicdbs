package com.wonders;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan({"com.wonders.dao"})
@SpringBootApplication
public class SpringbootDynamicdbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDynamicdbsApplication.class, args);
	}

}
