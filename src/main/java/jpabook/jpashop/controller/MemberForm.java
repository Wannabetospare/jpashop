package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

// 멤버폼 클래스
@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원이름은 필수입니다.") // null 과 "" 모두 허용하지 않고, 메세지를 옵션으로 넣음
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
