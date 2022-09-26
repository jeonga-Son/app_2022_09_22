package com.ll.exam.app_2022_09_22.app.base;

import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import com.ll.exam.app_2022_09_22.app.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
// @Profile 어노테이션은 특정한 프로파일에서만 특정한 빈을 등록 하고 싶을 때,
//애플리케이션의 동작을 특정 프로파일일 경우 빈 설정을 다르게 하고 동작을 다르게 하고 싶을 때 사용한다.
@Profile("dev")
public class DevInitData {
    @Bean
    // CommandLineRunner 인터페이스는 구동 시점에 실행되는 코드가 자바 문자열 아규먼트 배열에 접근해야할 필요가 있는 경우에 사용한다.
    // 프로그램이 다 로깅된 이후 실행된다.
    public CommandLineRunner initData(MemberService memberService) {
        return args ->
        {
            String password = "{noop}1234";
            Member member1 = memberService.join("user1", password, "user1@test.com");
            Member member2 = memberService.join("user2", password, "user2@test.com");
            Member member3 = memberService.join("user3", password, "user3@test.com");
            Member member4 = memberService.join("user4", password, "user4@test.com");
        };
    }
}
