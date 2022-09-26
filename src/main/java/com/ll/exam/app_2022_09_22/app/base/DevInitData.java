package com.ll.exam.app_2022_09_22.app.base;

import com.ll.exam.app_2022_09_22.app.cart.service.CartService;
import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import com.ll.exam.app_2022_09_22.app.member.service.MemberService;
import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
import com.ll.exam.app_2022_09_22.app.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
// @Profile 어노테이션은 특정한 프로파일에서만 특정한 빈을 등록 하고 싶을 때,
//애플리케이션의 동작을 특정 프로파일일 경우 빈 설정을 다르게 하고 동작을 다르게 하고 싶을 때 사용한다.
@Profile("dev")
public class DevInitData {
    @Bean
    // CommandLineRunner 인터페이스는 구동 시점에 실행되는 코드가 자바 문자열 아규먼트 배열에 접근해야할 필요가 있는 경우에 사용한다.
    // 프로그램이 다 로깅된 이후 실행된다.
    public CommandLineRunner initData(MemberService memberService, ProductService productService, CartService cartService) {
        return args ->
        {
            String password = "{noop}1234";
            Member member1 = memberService.join("user1", password, "user1@test.com");
            Member member2 = memberService.join("user2", password, "user2@test.com");
            Member member3 = memberService.join("user3", password, "user3@test.com");
            Member member4 = memberService.join("user4", password, "user4@test.com");

            Product product1 = productService.create("단가라 OPS", 68000, "청평화 A-1-15", Arrays.asList(new ProductOption("RED", "44"), new ProductOption("RED", "55"), new ProductOption("BLUE", "44"), new ProductOption("BLUE", "55")));
            Product product2 = productService.create("쉬폰 OPS", 72000, "청평화 A-1-15", Arrays.asList(new ProductOption("BLACK", "44"), new ProductOption("BLACK", "55"), new ProductOption("WHITE", "44"), new ProductOption("WHITE", "55")));

            ProductOption productOption__RED_44 = product1.getProductOptions().get(0);
            ProductOption productOption__BLUE_44 = product1.getProductOptions().get(2);
            cartService.addItem(member1, productOption__RED_44, 1); // productOption__RED_44 총 수량 1
            cartService.addItem(member1, productOption__RED_44, 2); // productOption__RED_44 총 수량 3
            cartService.addItem(member1, productOption__BLUE_44, 1); // productOption__BLUE_44 총 수량 1
        };
    }
}
