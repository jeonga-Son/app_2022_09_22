package com.ll.exam.app_2022_09_22.app.member.entity;

import com.ll.exam.app_2022_09_22.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
// callSuper = true 로 설정하면 부모 클래스 필드 값들도 동일한지 체크하며,
// callSuper = false 로 설정(기본값)하면 자신 클래스의 필드 값들만 고려한다.
@ToString(callSuper = true)
public class Member extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private long restCash;
}