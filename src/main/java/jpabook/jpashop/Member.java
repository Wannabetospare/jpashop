package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // 클래스를 엔티티로 설정
@Getter // 롬복 자동게터
@Setter // 롬복 자동세터
public class Member {

    @Id // id 설정
    @GeneratedValue // id 키값을 자동으로 만들어줌, 따로 선언 안해도 됨
    private Long id;
    private String username;



}
