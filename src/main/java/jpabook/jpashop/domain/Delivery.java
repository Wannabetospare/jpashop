package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    // 기본키 매핑
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // 일대일 연관관계, 주인아님 읽기만 가능, 지연로딩
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    // 임베디드타입 주소
    @Embedded
    private Address address;

    // 이넘타입 문자형
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;  //ENUM [READY(준비), COMP(배송)]
}
