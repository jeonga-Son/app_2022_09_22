package com.ll.exam.app_2022_09_22;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// @EnableBatchProcessing 어노테이션을 안붙이면 배치앱이 아니다. 배치로서의 작업을 하지 않는다.
@EnableBatchProcessing
// 보통 Entity를 기획할때 모든 엔티티에 들어가는 공통 컬럼들이 있다.
// 예를들어 등록일자, 수정일자 등이 해당되는데,
// 이때 사용할 수 있는 방법으로 공통Entity를 뽑아내서 사용할때 @EnableJpaAuditing을 사용한다.
@EnableJpaAuditing
public class App20220922Application {

	public static void main(String[] args) {
		SpringApplication.run(App20220922Application.class, args);
	}

}
