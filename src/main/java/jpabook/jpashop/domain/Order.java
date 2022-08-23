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

    // 다대일 연관관계 매핑, 여기가 다쪽이므로 주인이고, 지연로딩으로 설정했다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 조인컬럼은 외래키를 매핑할떄 사용한다.(외래키를 가진다 = 연관관계의 주인)
    private Member member;

    // 일대다 연관관계 매핑, 여기가 1이므로 읽기전용, 케이스는 ALL 로 설정
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 원투원이고, 지연로딩, 조인컬럼으로 외래키를 가지고있어서 여기가 주인이다.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 배송정보

    private LocalDateTime orderDate; // 주문시간

    // Enum 타입을 매핑할때 사용용    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 (ORDER, CANCEL)

    //==연관관계 메서드==//
    // 연관관계 메서드란 양방향 관계를 설정하는 메서드
    // 양방향으로 연관관계가 묵여있을때 사용한다. 위치는 컨트롤하는쪽에 가진다.(다쪽)

    // 이름 - 멤버 세팅
    // 매개변수 - 멤버
    // 동작 - 멤버를 매개변수로 받아 세팅하고, 멤버의 오더값에 this(order) 값을 추가한다. (멤버와 관계가 오더가 주인이라서 this에 멤버 세팅)
    // 반환값 - x
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    // 이름 - 오더아이템추가
    // 매개변수 - 오더아이템
    // 동작 - 오더아이템을 매개변수로 받아 오더아이템에 추가하고, 오더아이템의 오더값에 this 로 값을 추가한다.
    // 반환값 - x
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 이름 - 딜리버리 세팅
    // 매개변수 - 딜리버리
    // 동작 - 딜리버리를 매개변수로 받아 오더의 딜리버리에 세팅하고, 딜리버리의 오더의 this 값을 세팅한다.(딜리버리와의 관계가 오더가 주인이라서 this에 딜리버리 세팅)
    // 반환값 - x
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드 ==//
    // 이름 - 오더 생성
    // 매개변수 - 멤버, 딜리버리, 오더아이템
    // 동작 - 오더를 생성하고, 매개변수를 넣어준다. 오더아이템을 여러개일 수 있기때문에 향상된 for문으로 만들어주고, 오더 상태와 시간을 넣어준다.
    // 반환값 - 매개변수들로 세팅된 오더값을 반화한다.
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
    // 이름 - 주문취소
    // 매개변수 - x
    // 동작 - 만약 딜리버리의 상태가 배송중이면, 상품취소가 안되게 오류를 띄우고, 아니라면 이 클래스의 오더상태를 취소로 바꾼다
    // 또한 반복문을 돌려 오더아이템들을 주문취소 메서드를 적용시킨다.
    // 반환값 - x
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
    // 이름 - 주문 가격 조회
    // 매개변수 - x
    // 동작 - 총 주문 가격을 0으로 초기화하고, 오더아이템을 향상된 for 문으로 돌리면서 하나씩 오더아이템의 가격들을 추가한다.
    // 반환값 - 총 주문 가격을 int 형으로 반환
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }




    
    
}
