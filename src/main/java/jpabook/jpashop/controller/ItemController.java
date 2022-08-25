package jpabook.jpashop.controller;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


// 컨트롤러 계층
@Controller
@RequiredArgsConstructor // 생성자 자동생성
public class ItemController {
    private final ItemService itemService; // 아이템서비스 클래스 사용


    // 매핑 종류 - Get 가져온다
    // 매핑 주소 - items/new
    // 이름 - 상품 폼 만들기
    // 매개변수 - 모델
    // 동작 - 상품 폼(북폼)을 매개로 싣어서 전달한다.
    // 반환 - items/createItemForm 으로 모델값을 문자형으로 전달한다.
    @GetMapping(value = "/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    // 매핑 종류 - Post 보낸다
    // 매핑 주소 - items/new
    // 이름 - 상품 만들기
    // 매개변수 - 상품 폼을 매개변수로 받는다.
    // 동작 - 상품(북)을 생성하고, 상품의 필드값을 채우고, 아이템서비스로 상품을 저장한다.
    // 반환 - items 경로로 모델 값을 문자형으로 반환한다.
    @PostMapping(value = "/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/items";
    }

    // 상품 목록
    // 매핑 종류 - Get 가져온다.
    // 매핑 주소 - items
    // 이름 - 아이템 리스트
    // 매개변수 - 모델 값
    // 동작 - 아이템서비스에서 아이템들을 전부 찾고, 찾은 값을 모델로 싣는다.
    // 반환 - items/itemsList 로 모델 값을 문자형으로 전달한다.
    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }


    //상품 수정 폼
    // 매핑 종류 - Get  가져온다.
    // 매핑 주소 - items/{itemsid}/edit
    // 이름 - 상품 폼을 수정
    // 매개변수 - itemId 경로 값, 모델 값
    // 동작 - 상품을 생성하고, 서비스로 매개변수를 받아 상품을 찾은 다음, 필드를 다시 세팅하고, 모델에 싣는다.
    // 반환 - 모델값을 items/updateItemForm 에 반환한다.
    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    //상품 수정
    // 매핑 종류 - Post 보낸다.
    // 매핑 주소 - items/{itemId}/edit
    // 이름 - 상품 수정
    // 매개변수 - itemId 경로 값, 상품 폼 값
    // 동작 - 아이템서비스에서 아이템을 업데이트하는 메서드를 통해 업데이트한다.
    // 반환 - items 경로로 상품 폼 값을 싣어서 문자형으로 반환한다.
    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }



}

