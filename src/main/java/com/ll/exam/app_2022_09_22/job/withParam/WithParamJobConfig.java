package com.ll.exam.app_2022_09_22.job.withParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Spring에서 Bean을 수동으로 등록하기 위해서는 설정 class 위에 @Configuration을 추가하고, @Bean을 사용해 수동으로 빈을 등록할 수 있다.
@Configuration
// @RequiredArgsConstructor 어노테이션은 생성자 주입을 편리하게 도와주는 lombok 어노테이션이다.
// final이나 @NotNull을 필드 앞에 붙이면 생성자를 자동으로 생성해준다.
// 의존성이 많아지는 경우 간결한 생성자 주입을 할 수 있도록 도와준다.
@RequiredArgsConstructor
// @Slf4j는 로깅을 남겨야할 필요가 있을 때 Formatting anchor로 불리는 '{}'를 메소드의 파라미터로 받은 값으로 교체. 로깅을 남길 필요가 없는 상황에서는 문자열을 구성하지 않는다.
// 자바 로깅 시스템을 쉽게 사용할 수 있도록 해주는 라이브러리이며, 다양한 자바 로깅 시스템을 사용할 수 있도록 파사드 패턴(facade pattern) 및 추상화를 통해 로깅 기능을 제공한다.
@Slf4j
public class WithParamJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job withParamJob(Step withParamStep1) {
        return jobBuilderFactory.get("withParamJob")
                .start(withParamStep1)
                .build();
    }

    @Bean
    @JobScope
    // @Bean 어노테이션 사용하려면 public이어야 됨.
    public Step withParamStep1(Tasklet withParamStep1Tasklet) {
        return stepBuilderFactory.get("withParamStep1")
                .tasklet(withParamStep1Tasklet)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet withParamStep1Tasklet(
            @Value("#{jobParameters['name']}") String name,
            @Value("#{jobParameters['age']}") Long age
    ) {
        return (contribution, chunkContext) -> {
            // debug로 하는 것이 나옴. debug는 항상 실행되는 것 아님.
            // info로 하면 개발할 때도 나오고 실제로 서버 운영될 때도 나옴.
            log.debug("name : {}, age : {}", name, age);

            return RepeatStatus.FINISHED;
        };
    }
}
