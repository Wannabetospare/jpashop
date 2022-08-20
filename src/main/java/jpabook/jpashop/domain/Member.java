package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 엔티티설정
@Entity
// 롬복을 이용하여 게터, 세터 자동생성
@Getter
@Setter
public class Member {

    // 기본키 매핑, id 값 자동생성 전략
    @Id
    @GeneratedValue
    @Column(name = "member_id") // 필드를 테이블의 컬럼과 매핑
    private Long id;

    // 회원의 이름
    private String name;


    // 회원의 주소값을 임베디드타입으로 매핑 ( 시티,스트릿,집코드 )
    @Embedded
    private Address address;

    // 연관관계 매핑, 주인은 member, 여기는 주인이 아니므로 읽기만 한다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


    // 주소 세팅
    public void setAddress(Address address) {
        this.address = address;
    }

    // 주소 게터
    public Address getAddress() {
        return address;
    }
}
