package com.ll.exam.app_2022_09_22.app.member.repository;

import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// 스프링부트에서는 Entity의 기본적인 CRUD가 가능하도록 JpaRepository 인터페이스를 제공한다.
// Spring Data JPA에서 제공하는 JpaRepository 인터페이스를 상속하기만 해도 되며,
//인터페이스에 따로 @Repository등의 어노테이션을 추가할 필요가 없다.
// JpaRepository를 상속받을 때는 사용될 Entity 클래스와 ID 값이 들어가게 된다.
//즉, JpaRepository<T, ID> 가 된다.
public interface MemberRepository extends JpaRepository<Member, Long> {
}