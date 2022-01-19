package com.hystudy.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // 선언된 모든 필드의 get 메소드를 생성해줌
@RequiredArgsConstructor // 성성된 모든 final 필드의 생성자를 생성해줌
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
