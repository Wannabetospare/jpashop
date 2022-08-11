package jpabook.jpashop;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") // localhost:8080 뒤에 /hello 로 붙는다.
    String hello(Model model){     // model 데이터를 받아서 뷰로 넘겨준다.
        model.addAttribute("data", "hello!!!"); // 데이터를 짝으로 맞춰줌
        return "hello"; // hello.html 로 자동으로 넘어간다. (경로는 생략되어있음)
    }

}
