package com.hystudy.springboot.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class BeakjoonController {
    // 숌 코드 https://www.acmicpc.net/problem/1651
    public static int solution1(String[] arr){
        /* 백준 제출방법
        public class Main {
            public static void main(String[] args) throws IOException {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
                StringTokenizer st = new StringTokenizer(br.readLine());

                while(st.countTokens()!=0)
                    bw.write(st.nextToken()+", ");

                bw.flush();

        }
        * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //for (br.lines())
        //br.readLine();
        int answer = -1;
        // -1이 포함된 경우 무조건 0
        if(Arrays.stream(arr).anyMatch(a->"-1".equals(a))) return 0;
        // 길이 2이하인 경우 무조건 -1
        if(arr.length<3) return -1;
        // 길이 순 정렬
        Arrays.sort(arr,Comparator.comparing(String::length));

        int cnt = 0;
        for(int i=2;i<arr.length;i++){
            String s = arr[i];
            if(s.equals(arr[i-1])) cnt ++;
            if(s.contains(arr[i-1])) cnt ++;
            if(s.contains(arr[i-1])) cnt ++;
            if(cnt>=3) return s.length();
        }

        return answer;
    }
}
