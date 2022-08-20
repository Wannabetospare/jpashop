package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블 명을 따로 적어둠, DB에 order 예약어가 있는 가능성이 높아서 order 로 하면 겹친다.
@Setter
@Getter
public class Order {

    // 기본키 매핑
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 연관관계 매핑 여기가 다쪽이므로 주인이고, 지연로딩으로 설정했다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 조인컬럼은 외래키를 매핑할떄 사용한다.
    private Member member;

    // 연관관계 매핑 여기가 1이므로 읽기전용, 케이스는 ALL 로 설정
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 원투원이고, 지연로딩, 조인컬럼으로 외래키를 가지고있어서 여기가 주인이다.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 배송정보

    private LocalDateTime orderDate; // 주문시간

    // Enum 타입을 매핑할때 사용용    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 (ORDER, CANCEL)

    //==연관관계 메서드==// 양방향으로 연관관계가 묵여있을때 사용한다. 위치는 컨트롤하는쪽에 가진다.(다쪽)
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드 ==//
    public static Order creatOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    //==비즈니스 로직==//
    /* 주문 취소*/
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /* 전체 주문 가격 조회*/
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }




    
    
}
