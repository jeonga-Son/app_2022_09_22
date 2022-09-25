package com.ll.exam.app_2022_09_22.job.helloWorld;

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

// @Configuration 어노테이션은 스프링 IOC Container에게 해당 클래스를 Bean 구성 Class임을 알려주는 것이다.
@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {

    // Job을 Build해주는 Builder를 만들어주는 Factory이다.
    private final JobBuilderFactory jobBuilderFactory;

    // Job에 관련된 Step들을 만들어낸다.
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    // 메서드 이름이 Bean의 이름이다. 이것과 똑같은 메서든는 다른 클래스라도 만들면 안된다. 이 값은 유니크해야한다.
    public Job helloWorldJob() {
        // get("") 안에있는 것은 job의 이름이다. 메서드명과 똑같이 하는 것이 관례이다.
        // jobBuilder를 가져오는 코드이다.
        return jobBuilderFactory.get("helloWorldJob")
                .incrementer(new RunIdIncrementer()) // 강제로 매번 다른 ID를 실행시에 파라미터로 부여.
                .start(helloWorldStep1())
                .build();
    }

    @Bean
    public Step helloWorldStep1() {
        return stepBuilderFactory.get("helloWorldStep1")
                .tasklet(helloWorldTasklet())
                .build();
    }

    @Bean
    public Tasklet helloWorldTasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("hello spring batch!!!");

            return RepeatStatus.FINISHED;
        };
    }
    // job은 여러가지의 스텝들로 구성된다.
}
