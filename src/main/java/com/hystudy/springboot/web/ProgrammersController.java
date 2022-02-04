package com.hystudy.springboot.web;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProgrammersController {
	// Lv.1 신고 결과 받기
	public static int[] solution_RepostResult(String[] id_list, String[] report, int k) {
        Map<String,Integer> repomap = new HashMap<String,Integer>(); // 신고 당한 횟수
        Map<String, List<String>> repomapList = new HashMap<String,List<String>>(); // 신고 기록
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
        return IntStream.range(0, id_list.length).boxed().mapToInt(i -> (int)repomapList.get(id_list[i]).stream().filter(a -> repomap.get(a) >= k).count()).toArray();
    }
	
	// LV.2 문자열 압축
	public static int solution_TextCompression(String s) {
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
	public static String solution_keypadPress(int[] numbers, String hand) {
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
        		int left_distinct = Math.abs(curr_loca[0] - left_current[0]) + Math.abs(curr_loca[1] - left_current[1]);
        		int right_distinct = Math.abs(curr_loca[0] - right_current[0]) + Math.abs(curr_loca[1] - right_current[1]);
        		
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
	public static int[] clac_curr_location(int num) { // 키패드 위치 계산
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

	// LV1. 실패율
	public static int[] solution_failureRate(int N, int[] stages) {
		return IntStream.range(1,N+1).boxed()
			.collect(Collectors.toMap(Function.identity(), i -> 
				(Arrays.stream(stages).filter(s2 -> s2 >= i).count() > 0) ? 
						(float)Arrays.stream(stages).filter(s1 -> s1 == i).count() / Arrays.stream(stages).filter(s2 -> s2 >= i).count():(float)0))
			.entrySet().stream().sorted(Map.Entry.<Integer,Float>comparingByValue().reversed()
					.thenComparing(Map.Entry.comparingByKey()))
			.mapToInt(Map.Entry::getKey).toArray();
	}
	
	// LV.1 [1차] 비밀지도
	public static String[] solution_secretMap(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];
        
        String[] str_arr1 = Arrays.stream(arr1).boxed()
        		.map(a -> String.format("%0"+n+"d", Long.parseLong(Long.toBinaryString(a))))
        		.toArray(String[]::new);
        String[] str_arr2 = Arrays.stream(arr2).boxed()
        		.map(a -> String.format("%0"+n+"d", Long.parseLong(Long.toBinaryString(a))))
        		.toArray(String[]::new);
        
        IntStream.range(0,n).forEach(i-> {
        	answer[i] = "";
			IntStream.range(0,n).forEach((j) ->{
	    		if('0'==(str_arr1[i].charAt(j)) && '0'==(str_arr2[i].charAt(j))) answer[i] += ' ';
	    		else answer[i] += '#';
			});
        });
        
        return answer;
    }
	
	// LV.1 x만큼 간격이 있는 n개의 숫자
	public static long[] solution_xn(int x, int n) {
        long[] answer = new long[n];
        
        int cnt = 0;
        while(cnt++ < n) answer[cnt] = x*cnt;
        
        return answer;
    }
	
	// LV.2 더 맵게
	public static int solution_moreSpicy(int[] scoville, int K) {
		int answer = 0;
		PriorityQueue<Integer> heap = Arrays.stream(scoville).boxed()
				.collect(Collectors.toCollection(PriorityQueue::new));
		while(heap.peek() < K) {
			if(heap.size()==1) return -1;
			heap.add(heap.poll()+(heap.poll()*2));
			answer++;
		}
		
		return answer>0?answer:-1;
    }
	
	// LV2. 오픈채팅방
	public static String[] solution_openKaKao(String[] record) {
		Map<String,String> usermap = new HashMap<>();
		List<String> answer = new ArrayList<>();
        for(String str : record) {
        	String[] temparr = str.split(" ");
        	if("Enter".equals(temparr[0])) {
        		usermap.put(temparr[1], temparr[2]);
        		answer.add(temparr[1]+"/님이 들어왔습니다.");
        	} else if("Leave".equals(temparr[0])) {
        		answer.add(temparr[1]+"/님이 나갔습니다.");
        	} else if("Change".equals(temparr[0])) {
        		usermap.put(temparr[1], temparr[2]);
        	}
        }
        
        return answer.stream().map(a -> usermap.get(a.split("/")[0]) + a.split("/")[1]).toArray(String[]::new);
    }

	// LV.1 [1차] 다트게임
	enum sdt{ S,D,T }
	public static int solution_DartGame(String dartResult) {
		int answer = 0;
		
        String[] oparr = dartResult.split("[0-9]+");
        String[] numarr = dartResult.split("(S|D|T)(\\*|\\#)?");
        
        int prevscore = 0;
        for(int i=0;i<3;i++) {
        	int nowscore = 0;
        	nowscore = (int) Math.pow(Integer.valueOf(numarr[i]), sdt.valueOf(String.valueOf(oparr[i+1].charAt(0))).ordinal() + 1);
        	if(oparr[i+1].length() > 1) {
        		if('*' == oparr[i+1].charAt(1)) {
        			nowscore *= 2;
        			nowscore += prevscore;
        		} else if('#' == oparr[i+1].charAt(1)) {
        			nowscore = -nowscore;
        		}
        	}
        	System.out.println("now : "+nowscore);
        	System.out.println("prev : "+prevscore);
        	System.out.println("ans : "+answer);
        	prevscore = nowscore;
        	answer += nowscore;

        }
        
        return answer;
    }
}
