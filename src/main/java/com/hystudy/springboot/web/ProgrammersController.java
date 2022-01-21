package com.hystudy.springboot.web;

import java.util.*;

public class ProgrammersController {
	// Lv.1 신고 결과 받기
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
	
	// LV.2 문자열 압축
	public int solution_TextCompression(String s) {
		int answer = s.length();
        for(int i = 1; i <= Math.floor(s.length()/2); i++){  // 문자 단위
        	int cnt = 0, eqcnt = 0; // 반복 횟수, 같은문자 카운트
            int min = s.length(); // 압축 문자 길이
        	String prevword = s.substring(0,i); // 이전 문자
        	while(cnt++ < s.length()/i){ // 최대 문자열 반까지 자름
        		// 현재 문자열 (마지막에 i개로 안잘릴수있으므로 마지막 카운트엔 length를 넣음)
        		String str = s.substring( i*cnt, (i*(cnt+1) > s.length()) ? s.length() : i*(cnt+1) ); 
        		if(prevword.equals(str)) eqcnt++; // 이전 문자와 같을 시 카운트 증가
                else if(eqcnt > 0){ // 같은문자 카운트 끝났으면 줄어든 문자 수 계산
                	// 줄어든 문자 수 계산 : (잘린 단위 X 반복된횟수-1 ) + 반복횟수 자릿 수(10일경우 2이므로)
                    min = min - ( (eqcnt)*i) + ((int)Math.log10(eqcnt + 1) + 1 );
        			eqcnt = 0; // 같은문자 카운트 초기화
                }
        		prevword = str; // 이전 문자에 현재문자 저장
        	}
            if(min < answer) answer = min; // i개로 잘랐을때의 갯수가 최솟값보다 작으면 갱신
        }
        return answer;
    }
	
	// LV.1 키패드 누르기
	public String solution_keypadPress(int[] numbers, String hand) {
        String answer = "";
       
        int[] left_current = {3,0}, right_current = {3,2}; // 왼손, 오른손 현재위치
        for(int num : numbers) {
        	switch(num) {
        	case 1: case 4: case 7: // 1,4,7은 무조건 왼손
        		answer+="L";
        		left_current = clac_curr_location(num);
        		break;
        	case 3: case 6: case 9: // 3,6,9는 무조건 오른손
        		answer+="R";
        		right_current = clac_curr_location(num);
        		break;
        	case 2: case 5: case 8: case 0:
        		int[] curr_loca = clac_curr_location(num);
        		
        		int left_distinct = 0;
        		left_distinct += Math.abs(curr_loca[0] - left_current[0]);
        		left_distinct += Math.abs(curr_loca[1] - left_current[1]);
        		
				int right_distinct = 0;
				right_distinct += Math.abs(curr_loca[0] - right_current[0]);
				right_distinct += Math.abs(curr_loca[1] - right_current[1]);
        		
				if(left_distinct < right_distinct || (left_distinct == right_distinct) &&"left".equals(hand)) {
					answer += "L";
        			left_current = clac_curr_location(num);
				}
        		else {
        			answer += "R";
					right_current = clac_curr_location(num);
        		}
        		break;
        	}
        }
        
        return answer;
    }
	
	public int[] clac_curr_location(int num) { // 키패드 위치 계산
		String[][] keypads = {{"1","2","3"},{"4","5","6"},{"7","8","9"},{"*","0","#"}}; // 2차원 키패드 배열
        
		int[] result = {0,0};
		for(int i = 0; i < keypads.length; i++) {
			if(Arrays.asList(keypads[i]).contains(String.valueOf(num))) {
				result[0] = i;
				result[1] = Arrays.asList(keypads[i]).indexOf(String.valueOf(num));
			}
		}
		return result;
	}
}
