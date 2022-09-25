package com.ll.exam.app_2022_09_22.job.helloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
                .next(helloWorldStep2()) // helloWorldStep1()이 끝난 후 실행된다. 동시에 실행 x
                .build();
    }


    // @JobScope와 @StepScope는 스프링의 기본 Scope인 싱글톤 방식과는 대치되는 역할이다.
    // Bean의 생성 시점이 스프링 애플리케이션이 실행되는 시점이 아닌 @JobScope, @StepScope가 명시된 메서드가 실행될 때까지 지연시키는 것을 의미한다.

    @Bean
    @JobScope
    public Step helloWorldStep1() {
        return stepBuilderFactory.get("helloWorldStep1")
                .tasklet(helloWorldStep1Tasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet helloWorldStep1Tasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("헬로 테스클릿 1");

            return RepeatStatus.FINISHED;
        };
    }
    // job은 여러가지의 스텝들로 구성된다.

    @Bean
    @JobScope
    public Step helloWorldStep2() {
        return stepBuilderFactory.get("helloWorldStep2")
                .tasklet(helloWorldStep2Tasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet helloWorldStep2Tasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("헬로 테스클릿 2");

            if ( true ) {
                throw new Exception("실패 : 헬로 테스클릿 2");
            }

            return RepeatStatus.FINISHED;
        };
    }


}
