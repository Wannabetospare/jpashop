package jpabook.jpashop.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


// 컨트롤러 게층 = 웹을 컨트롤한다. 움직임, 통제
@Controller
@Slf4j // 로깅 추상화 라이브러리 = 로깅이 하는일을 직접하지 않고 로깅 구현체를 찾아서 실제 로깅 라이브러리(구현체)를 찾아서 사용할 수 있게 해준다.
public class HomeController {


    // "/" 매핑을 찍으면 retrun 의 값을 resoreces.static.리턴값 으로 바꿔서 html 을 띄운다.
    @RequestMapping("/")
    public String home(){
        log.info("home controller");
        return "home";
    }
}
