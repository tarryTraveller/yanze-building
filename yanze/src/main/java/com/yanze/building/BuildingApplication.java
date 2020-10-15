package com.yanze.building;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"com.yanze.**"})
@MapperScan("com.yanze.building")
public class BuildingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuildingApplication.class, args);
	}

}
