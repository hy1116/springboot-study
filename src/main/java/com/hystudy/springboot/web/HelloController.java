package com.hystudy.springboot.web;

import com.hystudy.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 json을 반환하는 컨트롤러로 만듬 (Responsebody와 같은 역할)
public class HelloController {
    @GetMapping("/hello") // HTTP Method Get 요청을 받음 (RequestMapping(method=RequestMethod.GET)과 같음)
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,@RequestParam("amount") int amount){
        return new HelloResponseDto(name,amount);
    }
}
