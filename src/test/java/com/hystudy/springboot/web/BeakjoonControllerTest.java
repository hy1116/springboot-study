package com.hystudy.springboot.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BeakjoonControllerTest {
    @Test
    public void testSolution1(){
        Assertions.assertThat(BeakjoonController.solution1(new String[]{"1","1010","01","10101"})).isEqualTo(5);
        Assertions.assertThat(BeakjoonController.solution1(new String[]{"0","11","11","11"})).isEqualTo(2);
        Assertions.assertThat(BeakjoonController.solution1(new String[]{"0000","001","01001","01010","01011"})).isEqualTo(-1);
        Assertions.assertThat(BeakjoonController.solution1(new String[]{"1","10","00"})).isEqualTo(-1);
    }

}

