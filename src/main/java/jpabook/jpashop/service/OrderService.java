package jpabook.jpashop.service;


import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 서비스 계층, 실질적인 비즈니스로직이 존재
@Service
@Transactional(readOnly = true) // 읽기전용, 저장x
@RequiredArgsConstructor // 생성자 생략
public class OrderService {


    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;


    /* 주문 */
    // 이름 - 주문
    // 매개변수 - 멤버 id, 아이템 id, 갯수
    // 동작 - 매개변수로 받은 id들을 통해 멤버와 회원을 초기화하고, 배송정보를 초기화한다. 주문 상품을 만들고, 주문을 만들어 최종적으로 주문을 저장한다.
    // 반환값 -
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송 정보
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.creatOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /* 주문 취소 */
    // 이름 - 주문 취소
    // 매개변수 - 오더 id
    // 동작 - 매개변수로 받은 오더 id 값으로 리퍼지토리에서 오더값을 찾고 취소메서드를 통해 취소한다.
    // 반환값 - x
    @Transactional
    public void cancelOrder(Long orderId) {

        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

     //주문 검색
    // 이름 - 주문 전부 찾기
    // 매개변수 - 오더 검색
    // 동작 - 매개변수로 받은 오더 검색으로 오더 리퍼지토리에서 오더를 전부 찾는다.
    // 반환값 - 동작으로 찾은 오더들을 반환한다.
      public List<Order> findOrders(OrderSearch orderSearch) {

        return orderRepository.findAll(orderSearch);

}




}
