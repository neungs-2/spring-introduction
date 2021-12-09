package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("hello")    // router 같은 역할
    public String hello(Model model) {
        model.addAttribute("data", "hello Java Spring!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value= "name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
}