package com.ll.exam.app_2022_09_22.app.base.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@Getter
@NoArgsConstructor
@SuperBuilder
// @ToString 어노테이션을 클래스 상단에 써주면 변수 값들을 리턴해주는 toString 메소드를 자동생성해준다.
@ToString
// @EntityListeners(AuditingEntityListener.class)는 해당 클래스에 Auditing 기능을 포함한다.
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Transient // 아래 필드가 DB 필드가 되는 것을 막는다.
    @Builder.Default // 빌더 패턴을 통해 인스턴스를 만들 때 특정 필드를 특정 값으로 초기화하고 싶다면 @Builder.Default를 쓰면 된다.
    private Map<String, Object> extra = new LinkedHashMap<>();

    public BaseEntity(long id) {
        this.id = id;
    }
}