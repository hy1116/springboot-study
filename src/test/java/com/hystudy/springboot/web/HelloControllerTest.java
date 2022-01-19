package com.hystudy.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // 스프링부트와 junit 연결자
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc; // WEB API테스트시 사용

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello =  "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) // HTTP Status code check (OK:200)
                .andExpect(content().string(hello));
    }
}
