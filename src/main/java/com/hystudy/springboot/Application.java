package com.hystudy.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 부트, Spring Bean 읽기와 생성 자동으로 설정 (위치부터 설정까지 읽어가기때문에 항상 프로젝트 최상단에 작성)
public class Application {
    public  void main(String[] args){
        SpringApplication.run(Application.class,args); // 내장 WAS실행 -> 항상 같은 환경에서 스프링 부트 배포를 가능하게 함
    }
}
