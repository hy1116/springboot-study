package com.hystudy.springboot.web;

import org.springframework.security.core.parameters.P;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProgrammersLv1Controller {
	// Lv.1 신고 결과 받기
	public static int[] solution_RepostResult(String[] id_list, String[] report, int k) {
		Map<String, Integer> repomap = new HashMap<>(); // 신고 당한 횟수
		Map<String, List<String>> repomapList = new HashMap<>(); // 신고 기록
		Arrays.asList(id_list).forEach(id -> {
			repomap.put(id, 0);
			repomapList.put(id, new ArrayList<>());
		});

		Arrays.stream(report).distinct() //중복제거
				.forEach(r -> {
					String[] repo = r.split(" ");
					repomap.put(repo[1], repomap.get(repo[1]) + 1); // 신고당한사람 카운트
					repomapList.get(repo[0]).add(repo[1]); // 신고기록 추가
				});
		return IntStream.range(0, id_list.length).boxed().mapToInt(i -> (int) repomapList.get(id_list[i]).stream().filter(a -> repomap.get(a) >= k).count()).toArray();
	}

	// LV.1 키패드 누르기
	public static String solution_keypadPress(int[] numbers, String hand) {
		String answer = "";

		int[] left_current = {3, 0}, right_current = {3, 2}; // 왼손, 오른손 현재위치
		for (int num : numbers) {
			switch (num) {
				case 1:
				case 4:
				case 7: // 1,4,7은 무조건 왼손
					answer += "L";
					left_current = clac_curr_location(num);
					break;
				case 3:
				case 6:
				case 9: // 3,6,9는 무조건 오른손
					answer += "R";
					right_current = clac_curr_location(num);
					break;
				case 2:
				case 5:
				case 8:
				case 0:
					int[] curr_loca = clac_curr_location(num);
					int left_distinct = Math.abs(curr_loca[0] - left_current[0]) + Math.abs(curr_loca[1] - left_current[1]);
					int right_distinct = Math.abs(curr_loca[0] - right_current[0]) + Math.abs(curr_loca[1] - right_current[1]);

					if (left_distinct < right_distinct || (left_distinct == right_distinct) && "left".equals(hand)) {
						answer += "L";
						left_current = clac_curr_location(num);
					} else {
						answer += "R";
						right_current = clac_curr_location(num);
					}
					break;
			}
		}

		return answer;
	}

	public static int[] clac_curr_location(int num) { // 키패드 위치 계산
		String[][] keypads = {{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}, {"*", "0", "#"}}; // 2차원 키패드 배열

		int[] result = {0, 0};
		for (int i = 0; i < keypads.length; i++) {
			if (Arrays.asList(keypads[i]).contains(String.valueOf(num))) {
				result[0] = i;
				result[1] = Arrays.asList(keypads[i]).indexOf(String.valueOf(num));
			}
		}
		return result;
	}

	// LV1. 실패율
	public static int[] solution_failureRate(int N, int[] stages) {
		return IntStream.range(1, N + 1).boxed()
				.collect(Collectors.toMap(Function.identity(), i ->
						(Arrays.stream(stages).filter(s2 -> s2 >= i).count() > 0) ?
								(float) Arrays.stream(stages).filter(s1 -> s1 == i).count() / Arrays.stream(stages).filter(s2 -> s2 >= i).count() : (float) 0))
				.entrySet().stream().sorted(Map.Entry.<Integer, Float>comparingByValue().reversed()
						.thenComparing(Map.Entry.comparingByKey()))
				.mapToInt(Map.Entry::getKey).toArray();
	}

	// LV.1 [1차] 비밀지도
	public static String[] solution_secretMap(int n, int[] arr1, int[] arr2) {
		String[] answer = new String[n];

		String[] str_arr1 = Arrays.stream(arr1).boxed()
				.map(a -> String.format("%0" + n + "d", Long.parseLong(Long.toBinaryString(a))))
				.toArray(String[]::new);
		String[] str_arr2 = Arrays.stream(arr2).boxed()
				.map(a -> String.format("%0" + n + "d", Long.parseLong(Long.toBinaryString(a))))
				.toArray(String[]::new);

		IntStream.range(0, n).forEach(i -> {
			answer[i] = "";
			IntStream.range(0, n).forEach((j) -> {
				if ('0' == (str_arr1[i].charAt(j)) && '0' == (str_arr2[i].charAt(j))) answer[i] += ' ';
				else answer[i] += '#';
			});
		});

		return answer;
	}

	// LV.1 x만큼 간격이 있는 n개의 숫자
	public static long[] solution_xn(int x, int n) {
		long[] answer = new long[n];

		int cnt = 0;
		while (cnt++ < n) {
			answer[cnt - 1] = (long) x * cnt;
		}

		return answer;
	}

	// LV.1 [1차] 다트게임
	enum sdt {S, D, T}
	public static int solution_DartGame(String dartResult) {
		String[] oparr = dartResult.split("[0-9]+");
		String[] numarr = dartResult.split("([SDT])([*#])?");

		int answer = 0, firstscore = 0, prevscore = 0;
		for (int i = 0; i < 3; i++) {
			int nowscore = (int) Math.pow(Integer.parseInt(numarr[i]), sdt.valueOf(String.valueOf(oparr[i + 1].charAt(0))).ordinal() + 1);
			if (i == 0) firstscore = nowscore;
			if (oparr[i + 1].length() > 1) {
				if ('*' == oparr[i + 1].charAt(1)) { // 스타상 : 해당값과 이전 값 두배 (중첩가능)
					nowscore *= 2;
					nowscore += prevscore;
					if (i > 1 && oparr[i].length() > 1 && '*' == oparr[i].charAt(1)) {
						nowscore -= firstscore;
						if (oparr[i - 1].length() > 1 && '*' == oparr[i + 1].charAt(1)) {
							nowscore -= firstscore;
						}
					}
				} else if ('#' == oparr[i + 1].charAt(1)) { // 아차상 : 해당 값 마이너스
					nowscore = -nowscore;
				}
			}
			prevscore = nowscore;
			answer += nowscore;
		}
		return answer;
	}

	// LV1. 음양 더하기
	public static int solution_addInt(int[] absolutes, boolean[] signs) {
		return IntStream.range(0, absolutes.length).boxed().mapToInt(i -> signs[i] ? absolutes[i] : -absolutes[i]).sum();
	}

	// LV1. 없는 숫자 더하기
	public static int solution_existNum(int[] numbers) {
		int answer = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
		for (int n : numbers) answer -= n;
		return answer;
	}

	// LV1. 두정수 사이의 합
	public static long solution_sumBetweenInt(int a, int b) {
		int min = Math.min(a, b);
		int max = Math.max(a, b);
		long answer = min;
		while (min <= max) {
			if (min == max) return answer;
			answer += ++min;
		}
		return answer;
	}

	// LV1. 수박수박수박수..
	public String solution_watermelon(int n) {
		String answer = "";
		for (int i = 0; i < n; i++) {
			answer += n % 2 == 0 ? "수" : "박";
		}
		return answer;
	}

	// LV1. p와 y의 갯수
	boolean solution_py(String s) {
		int p = 0, y = 0;
		for (char c : s.toLowerCase().toCharArray()) {
			if (c == 'p') p++;
			if (c == 'y') y++;
		}
		if (p == y) return true;
		else return false;
	}

	// LV1. 평균
	public double solution_avg(int[] arr) {
		return Arrays.stream(arr).average().getAsDouble();
	}

	// LV1. 나머지가 1이 되는 수 찾기
	public int solution_namaji(int n) {
		return IntStream.rangeClosed(3, n).filter(i -> n % i == 1).findFirst().getAsInt();
	}

	// LV1. 나머지가 1이 되는 수 찾기
	public int solution_1(int n) {
		int answer = 0;
		for (int i = 1; i <= n; i++) {
			if (n % i == 0) answer += i;
		}
		return answer;
	}

	// LV1. 작은 수 빼기
	public int[] solution_removeMin(int[] arr) {
		if (arr.length < 1) return new int[]{-1};
		return Arrays.stream(arr).filter(a -> a != Arrays.stream(arr).min().getAsInt()).toArray();
	}

	// LV1. 전화번호 가리기
	public String solution_maskingPhoneNumber(String phone_number) {
		String answer = "";
		for (int i = 0; i < phone_number.length(); i++) {
			if (phone_number.length() - 4 < i) {
				answer += phone_number.charAt(i);
			} else answer += "*";
		}
		return answer;
	}

	// LV1. 이상한 문자열만들기
	public static String solution_strangeString(String s) {
		String answer = "";
		boolean flag = false;
		int idx = -1;
		while (++idx < s.length()) {
			if (s.charAt(idx) == ' ') {
				answer += " ";
				flag = false;
			} else if (flag) {
				answer += String.valueOf(s.charAt(idx)).toLowerCase();
				flag = false;
			} else {
				answer += String.valueOf(s.charAt(idx)).toUpperCase();
				flag = true;
			}
		}
		return answer;
	}

	//LV1. 정수 내림차순으로 정렬하기
	public static long solution_IntDesc(long n) {
		long answer = 0;
		List<Long> numarr = new ArrayList<>();
		while (0 < n) {
			numarr.add(n % 10);
			n /= 10;
		}
		numarr.sort(Comparator.reverseOrder());
		for (int i = 0; i < numarr.size(); i++) {
			answer += numarr.get(i) * Math.pow(10, numarr.size() - i - 1);
		}

		return answer;
	}

	// LV1. 자연수 뒤집어 배열로 만들기
	public static int[] solution_reverseIntArr(long n) {
		List<Long> numarr = new ArrayList<>();
		while (0 < n) {
			numarr.add(n % 10);
			n /= 10;
		}
		return numarr.stream().mapToInt(a ->a.intValue()).toArray();
	}

	//LV1. 문자열 내 쪼대로 정렬하기
	public String[] solution_StringOrder(String[] strings, int n) {
		return Arrays.stream(strings)
				.sorted(Comparator.comparing(s1 -> String.valueOf(s1).charAt(n)).thenComparing(s2 -> String.valueOf(s2)))
				.toArray(String[]::new);
	}

	//LV1. 시저암호
	public static String solution_SecretCode(String s, int n) {
		String answer = "";
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)==' ') answer += ' ';
			else if(s.charAt(i) <= 90 && 90 < s.charAt(i)+n){
				answer += Character.toString(s.charAt(i)+n-26);
			}
			else if(97 <= s.charAt(i) && 122 < s.charAt(i)+n){
				answer += Character.toString(s.charAt(i)+n-26);
			}
			else answer += Character.toString(s.charAt(i)+n);
		}
		return answer;
	}

	//LV1. 소수찾기
	public static int solution_findDecimal(int n) {
		int answer = 0;
		for(int i =2;i<=n;i++){
			boolean flag = false;
			int idx = 1;
			while (++idx <= Math.sqrt(i)){
				if(i%idx==0){
					flag = true;
					break;
				}
			}
			if(!flag) answer++;
		}
		return answer;
	}

	//LV1. 3진법
	public static int solution_three(int n) {
		int answer = 0;
		String three = "";
		while(0 < n){
			three += String.valueOf(n%3);
			n /= 3;
		}
		for(int i=0;i<three.length();i++){
			answer += Integer.parseInt(String.valueOf(three.charAt(three.length()-i-1)))*((int)Math.pow(3,i));
		}
		return answer;
	}
	//LV1. 예산
	public int solution_budget(int[] d, int budget) {
		Arrays.sort(d);
		int idx = -1;
		while (++idx < d.length){
			if(d[idx] > budget) break;
			budget -= d[idx];
			d[idx] = 0;
		}
		return (int)Arrays.stream(d).filter(a -> a==0).count();
	}

	//LV1. 약수의 갯수와 덧셈
	public static int solution_addMeasure(int left, int right) {
		return IntStream.rangeClosed(left,right).map(i -> IntStream.rangeClosed(1,i).filter(j-> i %j==0).count()%2==0?i:-i).sum();
	}

	//LV1. 부족한 금액 계산하기
	public static long solution_calcMoney(int price, long money, int count) {
		for(int i=1;i<=count;i++){
			money -= price*i;
		}
		return money<0?Math.abs(money):0;
	}

	//LV1. 소수 만들기
	public static int solution_makeDecimal(int[] nums) {
		int answer = 0;
		for (int i=0;i<nums.length-2;i++){
			for (int j=i+1;j<nums.length-1;j++){
				for (int k=j+1;k<nums.length;k++){
					int sum = nums[i]+nums[j]+nums[k];
					if(IntStream.range(2,sum).filter(a -> (sum)%a==0).count()==0) answer++;
				}
			}
		}
		return answer;
	}

	//LV1. 최소 직사각형
	public int solution_leastSquare(int[][] sizes) {
		int maxw = 0,maxh=0;
		for(int i=0;i<sizes.length;i++){
			if(maxw < sizes[i][0]) maxw = sizes[i][0];
			if(maxh < sizes[i][1]) maxw = sizes[i][1];
		}
		return maxw*maxh;
	}

	//LV1. 폰켓몬
	public int solution_phoneKetMon(int[] nums) {
		int answer = nums.length/2;
		int kind = (int)Arrays.stream(nums).distinct().count();
		return answer<kind?answer:kind;
	}

	//LV1. 체육복
	public static int solution_gymClothes(int n, int[] lost, int[] reserve) {
		int answer = n;
		Map<Integer,String> map = new HashMap<>();
		for(int i=1;i<=n;i++) map.put(i,null); // 학생 수 만큼 배열 생성
		for(int i=0;i<lost.length;i++) map.put(lost[i],"lost"); // 도둑맞은 학생
		for(int i=0;i<reserve.length;i++){
			// 잃어버린 학생이 여분있을경우 상쇄
			if("lost".equals(map.get(reserve[i]))) map.put(reserve[i],null);
			else map.put(reserve[i],"reserve");
		}
		for(int i=0;i<=map.size();i++){
			if("lost".equals(map.get(i))){
				if(map.get(i-1)!=null && "reserve".equals(map.get(i-1))) {
					map.put(i,null);
					map.put(i-1,null);
				} else if(map.get(i+1)!=null && "reserve".equals(map.get(i+1))) {
					map.put(i,null);
					map.put(i+1,null);
				}
			}
		}
		for (int k :map.keySet()) if("lost".equals(map.get(k))) answer--;
		return answer;
	}

	//LV1. 모의고사
	public static int[] solution_MathTest(int[] answers) {
		List<Integer> answer = new ArrayList<>();

		int[] supo1arr = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5};
		int[] supo2arr = {2, 1, 2, 3, 2, 4, 2, 5, 2, 1, 2, 3, 2, 4, 2, 5};
		int[] supo3arr = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5, 3, 3, 1, 1, 2, 2, 4, 4, 5, 5};
		int supo1=0,supo2=0,supo3=0;

		for (int i=0;i<answers.length;i++){
			if(supo1arr[i-(int)(Math.floor(i/supo1arr.length)*supo1arr.length)]==answers[i]) supo1++;
			if(supo2arr[i-(int)(Math.floor(i/supo2arr.length)*supo2arr.length)]==answers[i]) supo2++;
			if(supo3arr[i-(int)(Math.floor(i/supo3arr.length)*supo3arr.length)]==answers[i]) supo3++;
		}
		int max = Math.max(supo1,supo2);
		max = Math.max(max,supo3);

		if(supo1==max) answer.add(1);
		if(supo2==max) answer.add(2);
		if(supo3==max) answer.add(3);

		return answer.stream().mapToInt(a->a).toArray();
	}

	/*
	1번 지표	라이언형(R), 튜브형(T)
	2번 지표	콘형(C), 프로도형(F)
	3번 지표	제이지형(J), 무지형(M)
	4번 지표	어피치형(A), 네오형(N)
	*/
	// LV1. 성격유형검사 2023.02.14 완료
	public static String solution_mbti(String[] survey, int[] choices) {
		String answer = "";
		Map<String,Integer> surveymap = new HashMap<>();

		for(int i=0;i<survey.length;i++){
			String key = "";
			int value = 0;
			if(choices[i]==4) continue;
			else if(choices[i] < 4){
				key = String.valueOf(survey[i].charAt(0));
				value = 4-choices[i];
			}else if(choices[i] > 4){
				key = String.valueOf(survey[i].charAt(1));
				value = choices[i]-4;
			}

			if(surveymap.get(key)!=null){
				surveymap.put(key,surveymap.get(key)+value);
			} else{
				surveymap.put(key,value);
			}
		}

		String[] arr = {"RT","CF","JM","AN"};
		for(String s : arr){
			String first = String.valueOf(s.charAt(0));
			String second = String.valueOf(s.charAt(1));

			int front = surveymap.get(first)!=null?surveymap.get(first):0;
			int back = surveymap.get(second)!=null?surveymap.get(second):0;
			if(front < back)
				answer+=second;
			else
				answer+=first;
		}
		return answer;
	}

	// LV1. 개인정보 유효기간 2023.02.14 완료
	public static int[] solution_privacyInfo(String today, String[] terms, String[] privacies){
		List<Integer> answer = new ArrayList<>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			Date to_date = sdf.parse(today);
			Calendar to_c = Calendar.getInstance();
			to_c.setTime(to_date);

			Map<String,Integer> term_map = Arrays.stream(terms)
					.collect(Collectors.toMap(a -> a.split(" ")[0], a -> Integer.valueOf(a.split(" ")[1])));

			for(int i=0;i<privacies.length;i++){
				Date from_date = sdf.parse(privacies[i].split(" ")[0]);
				String type = privacies[i].split(" ")[1];
				Calendar from_c = Calendar.getInstance();
				from_c.setTime(from_date);
				from_c.add(Calendar.MONTH,term_map.get(type));

				if(to_c.compareTo(from_c) >= 0){
					answer.add(i+1);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return answer.stream().mapToInt(a->a).toArray();
	}

	// LV1. 숫자 짝궁 2023.02.15 완료
	public static String solution_number_partner(String X, String Y) {
		String answer = "";

		long x_len = X.split("").length;
		long y_len = Y.split("").length;
		for(int i=9;i>=0;i--){
			X = X.replace(String.valueOf(i),"");
			Y = Y.replace(String.valueOf(i),"");
			long x_cnt = x_len - X.length();
			long y_cnt = y_len - Y.length();
			answer += String.valueOf(i).repeat((int)Math.min(x_cnt,y_cnt));
			x_len = X.length();
			y_len = Y.length();
		}

		if("".equals(answer)){
			answer = "-1";
		} else if("".equals(answer.replace("0",""))){
			answer = "0";
		}

		return answer;
	}

	// LV1. 삼총사
	public static int solution_trio(int[] number) {
		int answer = 0;
		for(int i=0;i<number.length-2;i++){
			for(int j=i+1;j<number.length-1;j++){
				for(int k=j+1;k<number.length;k++){
					if((number[i]+number[j]+number[k])==0) answer++;
				}
			}
		}

		return answer;
	}
	// LV1. 콜라문제 23.03.15
	public static int solution_coke(int a, int b, int n) {
		// a병당 b개의 병 받음, n개 소유
		// n:20 -> 10병 -> 5병 -> 2병+1=3 -> 1병+1=2 -> 1병
		int answer = 0;
		while(a <= n){
			answer += (n/a)*b;
			n = ((n/a)*b + n%a);
		}
		return answer;
	}

	public static int solution1(String[] strarr){
		for(int i=0;i<strarr.length;i++){
			if("+".equals(strarr[i])) strarr[i] = String.valueOf(Integer.valueOf(strarr[i-1])+Integer.valueOf(strarr[i-2]));
			else if("R".equals(strarr[i])) strarr[i] = strarr[i-1] = String.valueOf(0);
			else if("D".equals(strarr[i])) strarr[i] = String.valueOf(Integer.valueOf(strarr[i-1]) *2);
		}
		return Arrays.stream(strarr).mapToInt(Integer::valueOf).sum();
	}

	public static int solution2(int[] arr1, int[] arr2){
		List<Integer> arr2list = Arrays.stream(arr2).boxed().collect(Collectors.toList());
		return Arrays.stream(arr1).filter(arr2list::contains).min().getAsInt();
	}

	public static String solution3(String str){
		return Arrays.stream(str.split("")).distinct()
				.collect(Collectors.toMap(Function.identity(),r -> Arrays.stream(str.split("")).filter(r::equals).count()))
				.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry::getKey))
				.map(r->r.getKey().repeat(r.getValue().intValue())).collect(Collectors.joining());
	}

	static String[] opArr, numArr;
	static String[] caseArr;
	static List<String> caseList;
	public static int[] solution4_v2(String str){
		numArr = str.split("[-*+]");
		opArr = str.split("[0-9]");

		// 경우의 수 List 만들기(순열)
		caseArr = new String[opArr.length-1];
		caseList = new ArrayList<>();
		getPermutations(0,0);

		Map<String,Integer> map = new HashMap<>();
		for(String s : caseList){
			int[] calcArr = new int[opArr.length-1];
			String[] formulaArr = new String[opArr.length-1];
			int lastIdx = 0;
			for(String c : s.split("")){
				int idx = Integer.valueOf(c);
				boolean leftFlag = false,rightFlag = false;
				if( 0 < idx && formulaArr[idx-1]!=null) leftFlag = true;
				if( idx+1 < opArr.length-1 && formulaArr[idx+1]!=null) rightFlag = true;
				formulaArr[idx] = "("
						+ (leftFlag?formulaArr[idx-1]: numArr[idx])
						+ opArr[idx+1]
						+ (rightFlag?formulaArr[idx+1]: numArr[idx+1])
						+")";
				calcArr[idx] = calc(
						leftFlag? calcArr[idx-1]:Integer.valueOf(numArr[idx])
						, rightFlag? calcArr[idx+1]:Integer.valueOf(numArr[idx+1])
						, opArr[idx+1]
				);
				if (leftFlag){
					formulaArr[idx-1] = formulaArr[idx];
					calcArr[idx-1] = calcArr[idx];
				}
				if (rightFlag){
					formulaArr[idx+1] = formulaArr[idx];
					calcArr[idx+1] = calcArr[idx];
				}
				lastIdx = idx;
			}
			map.put(formulaArr[lastIdx],calcArr[lastIdx]);
		}
		map.entrySet().forEach(m-> System.out.println(m.getKey()+" = "+m.getValue()));
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).mapToInt(Map.Entry::getValue).toArray();
	}
	public static void getPermutations(int digit, int flag){
		if(digit == opArr.length-1){
			caseList.add(Arrays.stream(caseArr).collect(Collectors.joining()));
			return;
		}
		for (int i = 0; i< opArr.length-1; i++){
			if((flag & 1<<i) != 0) continue;
			caseArr[digit] = String.valueOf(i);
			getPermutations(digit+1,flag | 1 << i);
		}
	}
	public static int calc(int num1,int num2, String op){
		int n1 = Integer.valueOf(num1), n2 = Integer.valueOf(num2);
		if("*".equals(op)) return n1 * n2;
		else if("+".equals(op)) return n1 + n2;
		else if("-".equals(op)) return n1 - n2;
		else return 0;
	}

	public static int solution5_v2(int[] intarr){
		long totalHappy = Arrays.stream(intarr).filter(a->8<a).count();
		if(intarr.length == totalHappy*2) return intarr.length-1;
		if(intarr.length < totalHappy*2) return intarr.length;

		Queue<Boolean> queue = new LinkedList<>();
		int max = 0;
		for(int i=0;i<intarr.length;i++){
			queue.add(8<intarr[i]);
			long happy = queue.stream().filter(Boolean::booleanValue).count();
			if (happy*2 == queue.size()) continue;
			while(!queue.isEmpty() && happy <= queue.size()/2){
				if(queue.poll()) happy--;
			}
			if(max < queue.size()) max = queue.size();
		}
		// 뒤집어서 한번 더
		queue.clear();
		int reverseMax = 0;
		for(int i=intarr.length-1;0<=i;i--){
			queue.add(8<intarr[i]);
			long happy = queue.stream().filter(Boolean::booleanValue).count();
			if (happy*2 == queue.size()) continue;
			while(!queue.isEmpty() && happy <= queue.size()/2){
				if(queue.poll()) happy--;
			}
			if(reverseMax < queue.size()) reverseMax = queue.size();
		}

		return Math.max(reverseMax,max);
	}

	// LV1. 햄버거 만들기
	public static int solution_making_hamburger_stack(int[] ingredient) {
		int answer = 0,point = 0;
		int[] order = {1,2,3,1};
		Stack<Integer> st = new Stack<>();
		for (int i = 0;i<ingredient.length;i++){
			if(ingredient[i]==1 && point != order.length-1) st.push(point=1);
			else if(ingredient[i]==order[point]) st.push(++point);
			else{
				point=0;
				st.clear();
			}

			if(order.length-1 < point){
				answer++;
				while(!st.empty() && st.pop() != 1);
				point = (st.empty())? 0 : st.peek();
			}
		}
		return answer;
	}

	// LV2. 가장 작은 문자열
	public static int solution_mini_subString(String t, String p) {
		return (int) IntStream.rangeClosed(0,t.length()-p.length())
				.mapToLong(a->Long.valueOf(t.substring(a,a+p.length())))
				.filter(r->r<=Long.valueOf(p))
				.count();
	}

	// LV1. 가장 가까운 문자열
	public static int[] solution_near_string(String s) {
		return IntStream.range(0,s.length())
				.map(i->{
					int position = s.substring(0,i).lastIndexOf(s.charAt(i));
					if(-1 < position) position = i-position;
					return position;
				})
				.toArray();
	}

	// LV1. 푸드 파이트 대회
	public static String solution_food_fight(int[] food) {
		StringBuilder sb = new StringBuilder().append(0);
		IntStream.iterate(food.length-1,i->i-1).limit(food.length-1)
			.forEach(m->{
				sb.append(String.valueOf(m).repeat((int)Math.ceil(food[m]/2)));
				sb.insert(0,String.valueOf(m).repeat((int)Math.ceil(food[m]/2)));
			});
		return sb.toString();
	}

	// LV1. 과일 장수
	public static int solution_fruit_seller(int k, int m, int[] score) {
		List<Integer> list = Arrays.stream(score).boxed()
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());

		return IntStream.range(0,score.length/m)
				.map(i->list.get(m*(i+1)-1)*m)
				.sum();
	}

	// LV1. 명예의 전당
	public static int[] solution_hall_of_fame(int k, int[] score) {
		int[] answer = new int[score.length];

		PriorityQueue<Integer> queue = new PriorityQueue<>();
		for (int i=0;i<score.length;i++){
			queue.add(score[i]);
			if(k <= i) queue.poll();
			answer[i] = queue.peek();
		}

		return answer;
	}

	// LV1. 카드뭉치
	public static String solution_trump_bundle(String[] cards1, String[] cards2, String[] goal) {
		Queue<String> queue1 = Arrays.stream(cards1).collect(Collectors.toCollection(LinkedList::new));
		Queue<String> queue2 = Arrays.stream(cards2).collect(Collectors.toCollection(LinkedList::new));

		for(String s : goal){
			if(!queue1.isEmpty() && queue1.peek().equals(s)) queue1.poll();
			else if(!queue2.isEmpty() && queue2.peek().equals(s)) queue2.poll();
			else return "No";
		}

		return "Yes";
	}

	// LV1. 문자열 나누기
	public static int solution_split_string(String s) {
		int answer = 0;
		String x = "";
		int matchCnt = 0;
		for(String str : s.split("")){
			if("".equals(x)){
				x = str;
				matchCnt = 1;
			} else if(x.equals(str)){
				matchCnt++;
			} else{
				matchCnt--;
			}

			if(matchCnt==0){
				answer++;
				x="";
			}
		}
		if(matchCnt!=0) answer++;
		return answer;
	}

	// LV1. 옹알이(2)
	public static int solution_babbling(String[] babbling) {
		List<String> canList = Arrays.asList(new String[]{"aya", "ye", "woo", "ma"});
		int answer = 0;

		for (String s : babbling){
			for(String canStr : canList){
				if(s.contains(canStr)) {
					if(s.contains(canStr.repeat(2))) continue;
					s = s.replaceAll(canStr," ");
				}
				if("".equals(s.trim())){
					answer++;
					break;
				}
			}
		}

		return answer;
	}

	// LV1. 덧칠하기
	public static int solution_painting(int n, int m, int[] section) {
		List<Integer> list = Arrays.stream(section).boxed().collect(Collectors.toList());
		int answer = 0;
		while(!list.isEmpty()){
			list.removeIf(a->list.get(0)<=a && a<=list.get(0)+m-1);
			answer++;
		}
		return answer;
	}

	// LV1. 추억 점수
	public static int[] solution_memory_score(String[] name, int[] yearning, String[][] photo) {
		int[] answer = new int[photo.length-1];
		List<String> nameList = Arrays.asList(name);
		for (int i=0;i<photo[0].length-1;i++){
			int miss = 0;
			for(String s : photo[i]){
				if(nameList.indexOf(s) < 0) continue;
				miss += yearning[nameList.indexOf(s)];
			}
			answer[i] = miss;
		}

		return answer;
	}

	// LV1. 기사 단원의 무기
	public static int solution_orders_weapon(int number, int limit, int power) {
		return (int)IntStream.rangeClosed(1,number).mapToLong(i->{
			long cnt = IntStream.rangeClosed(1,(int) Math.sqrt(i)).filter(a->i%a==0).map(m->m*m==i?1:2).distinct().sum();
			if(limit < cnt) cnt = power;
			return cnt;
		}).sum();
	}

	// LV1. 둘만의 암호
	public static String solution_secret_code(String s, String skip, int index) {
		StringBuffer sb = new StringBuffer();
		for(String str : s.split("")){
			char c = str.charAt(0);
			int cnt = 0;
			while(cnt < index){
				if('z' < ++c) c -= 'z'-'a'+1;
				if(skip.contains(String.valueOf(c))){
					continue;
				}
				cnt++;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	// LV1. 대충만든 자판
	public static int[] solution_roughly_made_keyboard(String[] keymap, String[] targets) {
		int[] answer = new int[targets.length];
		String str = Arrays.stream(keymap).collect(Collectors.joining());
		for(int i=0;i<targets.length;i++){
			for (int j=0;j<targets[i].length();j++){
				if(str.indexOf(targets[i].charAt(j)) < 0){
					answer[i] = -1;
					break;
				}

				int minidx = 100;
				for(String s : keymap){
					if(-1 < s.indexOf(targets[i].charAt(j)) && s.indexOf(targets[i].charAt(j)) <= minidx){
						minidx = s.indexOf(targets[i].charAt(j));
					}
				}
				answer[i] += minidx+1;
			}
		}
		return answer;
	}

	// LV1. 달리기 경주
	public static String[] solution_running_race(String[] players, String[] callings) {
		/*
		List<String> playerList = Arrays.stream(players).collect(Collectors.toList());
		for (int i=0;i<callings.length;i++){
			int callIdx = playerList.indexOf(callings[i]);
			if(callIdx == 0) continue;
			String temp = playerList.get(callIdx);
			playerList.set(callIdx,playerList.get(callIdx-1));
			playerList.set(callIdx-1,temp);
		}
		return playerList.toArray(String[]::new);
		*/
		/*
		AtomicInteger i = new AtomicInteger(1);
		Map<String,Integer> playerMap = Arrays.stream(players)
				.collect(Collectors.toMap(Function.identity(),i.getAndAdd(callings.length+1)));
		*/

		System.out.println("----------------------------------");
		Map<String,Integer> playerMap =
				IntStream.range(0,players.length).boxed()
				.collect(Collectors.toMap(m->players[m],Function.identity()));

		for (String s: callings){
			playerMap.put(s,playerMap.get(s)-(callings.length+1));
			playerMap.entrySet().forEach(System.out::print);
			System.out.println();
		}

		playerMap.entrySet().forEach(System.out::println);

		return playerMap.entrySet().stream()
				.sorted(Comparator.comparing(Map.Entry::getValue))
				.map(Map.Entry::getKey).toArray(String[]::new);
		/*
		Map<String,Integer> playerMap = IntStream.range(0,players.length).boxed()
				.collect(Collectors.toMap(i->players[i],Function.identity()));
		AtomicInteger i = new AtomicInteger(1);

		Map<String,AtomicInteger> playerMap = Arrays.stream(players).collect(Collectors.toMap(Function.identity(),i.incrementAndGet()));
		for (int i=0;i<callings.length;i++){
			int rank = playerMap.get(callings[i]);
			playerMap.entrySet().stream().filter(p->p.getValue()==rank-1).findAny().get().setValue(rank);
			playerMap.put(callings[i],rank-1);
		}

		return playerMap.entrySet().stream()
				.sorted(Comparator.comparing(Map.Entry::getValue))
				.map(Map.Entry::getKey).toArray(String[]::new);

		 */
	}

	/******************************************************************************************************/
	// LV1. 바탕화면 정리
	public static int[] solution_cleaning_background(String[] wallpaper) {
		int[] answer = {};
		return answer;
	}

	// LV1. 공원 산책
	public static int[] solution_walking_park(String[] park, String[] routes) {
		int[] answer = {};

		Arrays.stream(park).map(r-> Arrays.stream(r.split("")).collect(Collectors.toList()));


		System.out.println();

		return answer;
	}

}
