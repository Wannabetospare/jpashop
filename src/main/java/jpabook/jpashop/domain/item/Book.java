package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("B") // 구분하기위한 별명
@Getter
@Setter
public class Book extends Item { // 아이템을 상속

    private String author;
    private String isbn;

}
