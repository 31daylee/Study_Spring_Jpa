package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){  // Model 이란 스프링 데이터를 실어서 보낼 수 있어
        model.addAttribute("data", "Hello!!");
        return "hello";
    }
}
