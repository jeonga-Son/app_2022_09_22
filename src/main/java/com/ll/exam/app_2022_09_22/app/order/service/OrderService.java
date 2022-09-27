package com.ll.exam.app_2022_09_22.app.order.service;

import com.ll.exam.app_2022_09_22.app.cart.entity.CartItem;
import com.ll.exam.app_2022_09_22.app.cart.service.CartService;
import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import com.ll.exam.app_2022_09_22.app.member.service.MemberService;
import com.ll.exam.app_2022_09_22.app.order.entity.Order;
import com.ll.exam.app_2022_09_22.app.order.entity.OrderItem;
import com.ll.exam.app_2022_09_22.app.order.repository.OrderRepository;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
// @Transactional(readOnly = true) 를 선언하면 읽기 전용으로 데이터가 변경되어도 commit되지 않는다.
// 데이터의 변경이 없는 읽기 전용 메서드에사용되며, 영속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상을 가질 수 있다.
@Transactional(readOnly = true)
public class OrderService {
    private final MemberService memberService;
    private final CartService cartService;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createFromCart(Member member) {
        // 입력된 회원의 장바구니를 전부 가져온다.

        // 만약에 특정 장바구니의 상품옵션이 판매불능이면 삭제
        // 만약에 특정 장바구니의 상품옵션이 판매가능이면 주문품목으로 옮긴 후 삭제제

        List<CartItem> cartItems = cartService.getItemsByMember(member);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            ProductOption productOption = cartItem.getProductOption();

            if (productOption.isOrderable(cartItem.getQuantity())) {
                orderItems.add(new OrderItem(productOption, cartItem.getQuantity()));
            }

            cartService.deleteItem(cartItem);
        }

        return create(member, orderItems);
    }

    @Transactional
    public Order create(Member member, List<OrderItem> orderItems) {
        Order order = Order
                .builder()
                .member(member)
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return order;
    }

    //@Transactional은 클래스, 메소드, 인터페이스 위에 추가하여 사용하는 것으로, 해당 범위 내 메소드가 트랜잭션이 되도록 보장해준다.
    // 다시 말해 메소드를 하나의 트랜젝션(단위)로 간주하여 해당 메소드가 종료되기 전까지 어떠한 개입이나 다른 변화가 반영되지 않는 것을 말한다.
    // 이것을 선억적 트랜잭션이라고도 하는데, 직접 객체를 만들 필요 없이 선언만으로도 관리를 용이하게 해주기 때문이다.
    @Transactional
    public void payByRestCashOnly(Order order) {
        Member orderer = order.getMember();

        // 주문자가 가지고 있는 돈을 가져온다.
        long restCash = orderer.getRestCash();

        int payPrice = order.calculatePayPrice();

        // 지불금액이 예치금보다 크다면..
        if (payPrice > restCash) {
            throw new RuntimeException("예치금이 부족합니다.");
        }

        memberService.addCash(orderer, payPrice * -1, "주문결제__예치금결제");

        order.setPaymentDone();
        orderRepository.save(order);
    }

    @Transactional
    public void refund(Order order) {
        int payPrice = order.getPayPrice();
        memberService.addCash(order.getMember(), payPrice, "주문환불__예치금환불");

        order.setRefundDone();
        orderRepository.save(order);
    }
}