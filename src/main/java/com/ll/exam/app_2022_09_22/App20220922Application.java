package com.ll.exam.app_2022_09_22;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableBatchProcessing 어노테이션을 안붙이면 배치앱이 아니다. 배치로서의 작업을 하지 않는다.
@EnableBatchProcessing
public class App20220922Application {

	public static void main(String[] args) {
		SpringApplication.run(App20220922Application.class, args);
	}

}
