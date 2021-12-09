package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")    // router 같은 역할
    public String hello(Model model) {
        model.addAttribute("data", "hello Java Spring!!");
        return "hello";
    }

    //MVC Pattern Controller
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value= "name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    //API Controller: MVC 패턴과 다르게 페이지 소스를 보면 HTML 없이 return한 데이터만 그대로 전달
    // @ResponseBody 문자 반환
    @GetMapping("hello-string")
    @ResponseBody //HTTP body에 직접 넣어준다는 의미 (API 방식에 쓰임)
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    // @ResponseBody 객체 반환(JSON 객체 데이터 자체를 반환)
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    // static이면 클래스 내부에 클래스 정의 가능
    static class Hello {
        private String name;

        //이렇게 getter, setter 지정하는 것: JavaBean 표준방식, 프로퍼티 지정 방식
       public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
