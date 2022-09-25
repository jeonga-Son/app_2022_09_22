package com.ll.exam.app_2022_09_22.job.withParam;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Spring에서 Bean을 수동으로 등록하기 위해서는 설정 class 위에 @Configuration을 추가하고, @Bean을 사용해 수동으로 빈을 등록할 수 있다.
@Configuration

// @RequiredArgsConstructor 어노테이션은 생성자 주입을 편리하게 도와주는 lombok 어노테이션이다.
// final이나 @NotNull을 필드 앞에 붙이면 생성자를 자동으로 생성해준다.
// 의존성이 많아지는 경우 간결한 생성자 주입을 할 수 있도록 도와준다.
@RequiredArgsConstructor
public class WithParamJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job withParamJob() {
        return jobBuilderFactory.get("withParamJob")
                .start(withParamStep1())
                .build();
    }

    private Step withParamStep1() {
        return stepBuilderFactory.get("withParamStep1")
                .tasklet(withParamStep1Tasklet())
                .build();
    }

    private Tasklet withParamStep1Tasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("WithParam 테스클릿 1");

            return RepeatStatus.FINISHED;
        };
    }
}
