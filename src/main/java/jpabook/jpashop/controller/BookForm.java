package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;


// 상품등록 폼을 클래스로 따로 생성
@Getter
@Setter
public class BookForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

}
