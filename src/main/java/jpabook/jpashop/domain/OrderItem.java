package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item") // 테이블명 설정
@Setter
@Getter
public class OrderItem {

    // 기본키 매핑
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // 다대일 연관관계, 지연로딩, 조인컬럼으로 외래키 매핑 (연관관계 주인)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 주문 상품

    // 다대일 연관관계, 지연로딩, 조인컬럼으로 외래키 매핑 (연관관계 주인)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문

    private int OrderPrice; // 주문 가격
    private int count; // 주문 수량

    //== 생성 메서드 ==//
    // 이름 - 오더아이템 생성
    // 매개변수 - 아이템, 오더가격, 갯수
    // 동작 - 오더아이템을 생성하고, 매개변수들을 넣고, 재고를 빼는 아이템의 메서드도 추가한다.
    // 반환값 - 매개변수들이 추가된 오더아이템을 반환한다.
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
    // 이름 - 주문취소
    // 매개변수 - x
    // 동작 - 아이템을 가져와서, 갯수를 추가한다.
    // 반환값 - x
    public void cancel(){

        getItem().addStock(count);

    }

    //==조회 로직==//
    /* 주문상품 전체 가격 조회 */
    // 이름 - 주문 상품 전체 가격 조회
    // 매개변수 - x
    // 동작 - 오더의 가격과 오더의 갯수를 곱한다.
    // 반환값 - 동작값을 int 형으로 반환한다.
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }




}
