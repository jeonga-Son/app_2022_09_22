package com.ll.exam.app_2022_09_22.app.product.entity;

import com.ll.exam.app_2022_09_22.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
// callSuper = true로 설정하면 부모 클래스 필드 값들도 동일한지 체크하며,
// callSuper = false로 설정(기본값)하면 자신 클래스의 필드 값들만 고려한다.
@ToString(callSuper = true)
public class ProductOption extends BaseEntity {
    private String color;
    private String size;

    private int price;

    // Lazy는 '게으른'이란 뜻을 가지며, 사전 의미처럼 LAZY Fetch 타입은 실제로 엔티티 조회시에 바로 가지고 오지 않고, 연관 관계에 있는 엔티티를 참조할때 그때 가지고 온다.
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude // ManyToOne에 이 어노테이션 붙이는 것이 좋다. 양방향일때만 붙이도록 한다.
    private Product product;

    private boolean isSoldOut; // 사입처에서의 품절여부

    private int stockQuantity; // 쇼핑몰에서 보유한 물건 개수

    public ProductOption(String color, String size) {
        this.color = color;
        this.size = size;
    }

    public boolean isOrderable(int quantity) {
        if (isSoldOut() == false) return true;

        return getStockQuantity() >= quantity;
    }
}