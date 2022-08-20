package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Setter
@Getter
public class OrderItem {

    // 기본키 매핑
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // 다대일 연관관계, 지연로딩, 조인컬럼으로 외래키 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 주문 상품

    // 다대일 연관관계, 지연로딩, 조인컬럼으로 외래키 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문

    private int OrderPrice; // 주문 가격
    private int count; // 주문 수량

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    /* 주문 취소 */
    public void cancel(){
        getItem().addStock(count);
    }

    //==조회 로직==//
    /* 주문상품 전체 가격 조회 */
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }




}
