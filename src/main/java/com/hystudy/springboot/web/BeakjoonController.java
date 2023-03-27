package com.hystudy.springboot.web;

import java.io.*;
import java.util.*;
import java.util.stream.*;

/* 백준 제출방법
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while(st.countTokens()!=0)
            bw.write(st.nextToken()+", ");

        bw.flush();
}
*/
public class BeakjoonController {
    // 숌 코드 https://www.acmicpc.net/problem/1651
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String lines = br.readLine();
        int answer = -1;
        /*
        Stream<String> params = lines;

        int answer = -1;
        // -1이 포함된 경우 무조건 0

        if(params.anyMatch("-1"::equals)) answer=0;
        // 길이 3이하인 경우 무조건 -1
        if(params.count() <= 3) answer = -1;
        // 길이 순 정렬

        List<String> list = params.sorted(Comparator.comparing(String::length)).collect(Collectors.toList());

        int cnt = 0;
        for(int i=2;i<list.size()-1;i++){
            String s = list.get(i);
            if(s.equals(list.get(i-1))) cnt ++;
            if(s.contains(list.get(i-1))) cnt ++;
            if(s.contains(list.get(i-1))) cnt ++;
            if(cnt >= 3) answer = s.length();
        }
        */
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(String.valueOf(answer));
        bw.flush();

        br.close();
        bw.close();
    }

    public Stream<String> getParamsLines(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stream<String> lines = br.lines();
        return lines;
    }

    public void submitAnswer(String answer) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(answer);
        bw.flush();
    }

}
