package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// 컨트롤러 계층
@Controller
@RequiredArgsConstructor // 생성자 자동생성
public class OrderController {
    private final OrderService orderService; // 오더 서비스
    private final MemberService memberService; // 멤버 서비스
    private final ItemService itemService; // 아이템 서비스


    // 매핑 종류 - Get 가져온다.
    // 매핑 주소 - order
    // 이름 - 오더 폼(주문 품)을 만든다.
    // 매개변수 - 모델 값
    // 동작 - 멤버서비스로 멤버 값을 찾고, 아이템 서비스로 아이템 값을 찾고 모델로 싣는다.
    // 반환 - return 주소인 order/orderForm 에 문자형으로 모델 값을 넘긴다.
    @GetMapping(value = "/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    // 매핑 종류 - Post 보낸다.
    // 매핑 주소 - order
    // 이름 - 오더(주문) 만들기
    // 매개변수 - 멤버 id, 상품 id, 갯수
    // 동작 - 오더 서비스로 매개변수를 이용해서 오더를 주문한다.
    // 반환 - 동작 값을 retrurn 주소에 문자형으로 넘긴다.
    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    // 매핑 종류 - Get 가져온다.
    // 매핑 주소 - orders
    // 이름 - 오더 리스트 가져오기
    // 매개변수 - 오더서치(주문검색), 모델
    // 동작 - 오더서비스로 매개변수인 오더서치를 이용해서 오더들을 찾아오고 모델에 오더들을 싣는다.
    // 반환 - 동작 값으로 모델 값을 싣고 반환값 주소로 모델 값을 넘긴다.
    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    // 매핑 종류 - Post 보낸다.
    // 매핑 주소 - order/{orderId}/cancel
    // 이름 - 오더캔슬(주문취소)
    // 매개변수 - 오더 id 를 매개변수로 받는 동시에 주소 경로로 설정한다.
    // 동작 - 매개변수로 받은 오더 id 로 오더서비스를 이용해서 오더(주문)을 취소한다.
    // 반환 - 동작 값으로 반환 값 주소로 결과를 넘긴다.
    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }


}
