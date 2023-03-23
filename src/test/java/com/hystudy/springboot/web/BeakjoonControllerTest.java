package com.hystudy.springboot.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.Stream;

public class BeakjoonControllerTest {
    @Test
    public void testSolution1() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write("4");
        bw.write("1");
        bw.write("1010");
        bw.write("10");
        bw.write("10101");
        bw.flush();

        BeakjoonController bc = new BeakjoonController();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        if(br.read() > -1){
            answer = br.readLine();
        }

        Assertions.assertThat(answer).isEqualTo("5");
    }

}

