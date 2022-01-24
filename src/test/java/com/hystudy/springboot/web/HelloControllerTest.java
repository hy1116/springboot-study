package com.hystudy.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // 스프링부트와 junit 연결자
@WebMvcTest
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc; // WEB API 테스트시 사용

    @Test
    public void hello_return_test() throws Exception{
        String hello =  "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) // HTTP Status code check (OK:200)
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto_return_test() throws Exception{
        String name =  "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
            .andExpect(status().isOk()) // HTTP Status code check (OK:200)
            .andExpect(jsonPath("$.name", is(name))) // jsonPath : json 응답값을 필드별로 검증하는 메소드
            .andExpect(jsonPath("$.amount",is(amount))); // $.로 필드명 명시
    }
}
