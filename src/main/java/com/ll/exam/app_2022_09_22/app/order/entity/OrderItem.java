package com.ll.exam.app_2022_09_22.app.order.entity;

import com.ll.exam.app_2022_09_22.app.base.entity.BaseEntity;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
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
@ToString(callSuper = true)
public class OrderItem extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = LAZY)
    private ProductOption productOption;

    private int quantity;

    // 가격
    private int price; // 권장판매가
    private int salePrice; // 실제판매가
    private int wholesalePrice; // 도매가

    private int pgFee; // 결제대행사 수수료

    private int payPrice; // 결젝금액

    private int refundPrice; // 환불금액. (이벤트를 받아 샀거나 쿠폰을 사용했을 경우 환불금액이 다를 수 있기 때문에 자동계산 x)

    private int refundQuantity; // 환불개수. (5개 샀는데 4개만 환불할 수 있기 때문에)

    private boolean isPaid; // 결제여부. 무료상품도 있을 수 있으니까 결제여부 추가.


   public OrderItem(ProductOption productOption, int quantity) {
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = productOption.getPrice(); // 권장판매가
        this.salePrice = productOption.getSalePrice(); // 실제판매가
        this.wholesalePrice = productOption.getWholesalePrice(); // 도매가
    }
}