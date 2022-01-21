package com.hystudy.springboot.web;

import java.util.*;

public class ProgrammersController {
	public int[] solution_RepostResult(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];
        
        Map<String,Integer> repomap = new HashMap<String,Integer>(); // 신고 당한 횟수
        Map<String,List<String>> repomapList = new HashMap<String,List<String>>(); // 신고 기록
        Arrays.asList(id_list).forEach(id -> {
        	repomap.put(id,0);
        	repomapList.put(id,new ArrayList<String>());
        });
        
        Arrays.asList(report).stream().distinct() //중복제거
        	.forEach(r -> {
        		String[] repo = r.split(" ");
        		repomap.put(repo[1], repomap.get(repo[1]) + 1); // 신고당한사람 카운트
        		repomapList.get(repo[0]).add(repo[1]); // 신고기록 추가
        	});
        
        for(int i =0; i<id_list.length;i++){ // 유저별 확인
        	answer[i] = 0;
        	for(String repo : repomapList.get(id_list[i])){ // 신고기록 가져오기
        		if(repomap.get(repo) >= k) answer[i]++; // 신고한사람 기준보다 높으면 카운트
        	}
        }
        return answer;
    }
	
	public int solution_TextCompression(String s) {
        int answer = s.length();
        for(int i=1;i<=Math.floor(s.length()/2);i++){ 
        	int cnt = 0; int eqcnt = 1;
            int min = s.length();
        	String prvword = s.substring(0,i);
        	while(cnt < s.length()/i){
        		String str = s.substring(i*++cnt,(i*(cnt+1) > s.length())?s.length():i*(cnt+1));
        		if(prvword.equals(str)) eqcnt++;
                else if(eqcnt > 1){
                    min = min - ((eqcnt-1)*i)+((int)Math.log10(eqcnt)+1);
        			eqcnt = 1;
                }
        		prvword = str;
        	}
            if(min < answer) answer = min;
        }
        return answer;
    }
}
