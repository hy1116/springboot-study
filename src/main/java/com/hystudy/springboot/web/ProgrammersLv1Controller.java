package com.hystudy.springboot.web;

import java.math.BigInteger;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BinaryOperator;
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

		int answer = 0, firstscore = 0, prevscore = 0, starcnt = 0;
		for (int i = 0; i < 3; i++) {
			int nowscore = (int) Math.pow(Integer.parseInt(numarr[i]), sdt.valueOf(String.valueOf(oparr[i + 1].charAt(0))).ordinal() + 1);
			if (i == 0) firstscore = nowscore;
			if (oparr[i + 1].length() > 1) {
				if ('*' == oparr[i + 1].charAt(1)) { // 스타상 : 해당값과 이전 값 두배 (중첩가능)
					nowscore *= 2;
					nowscore += prevscore;
					starcnt++;
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
		//return (long)IntStream.rangeClosed(Math.min(a,b),Math.max(a,b)).sum();
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

	// LV1. 공원 산책
	public static int[] solution_walking_park(String[] park, String[] routes) {
		int[] answer = {};

		Arrays.stream(park).map(r-> Arrays.stream(r.split("")).collect(Collectors.toList()));


		System.out.println();

		return answer;
	}


	public static int solution_making_hamburger(int[] ingredient) {
/*
		List<Integer> list = Arrays.stream(ingredient).boxed().collect(Collectors.toList());
		list = list.subList(list.indexOf(1),list.lastIndexOf(1)+1);

		int answer =0,i = 0,idx = -1,bottom = 0;

		int[] pack = {1,2,3,1};
		while(idx++ < list.size()-1){
			if(i==pack.length-1 && list.get(idx) == pack[i]){ // 포장
				answer++;
				for (int j=0;j<idx-bottom;j++){
					list.remove(j);
				}
				idx=-1;
				i=0;
			} else if(list.get(idx) == pack[i]){ // 필요한 재료인 경우
				i++;
			} else if(i!=0 && list.get(idx) == 1){ // 빵이 끼어든 경우
				bottom = idx;
				i=1;
			}
		}

 */
		/*
		int answer =0;
		List<Integer> list = Arrays.stream(ingredient).boxed().collect(Collectors.toList());
		list = list.subList(list.indexOf(1),list.lastIndexOf(1)+1);

		int[] pack = {1,2,3,1};
		int packPoint = 0;
		Stack<Integer> st = new Stack<>();
		for(int i=0;i<list.size()-1;i++){
			if(pack[packPoint]==list.get(i)){
				if(++packPoint >= pack.length){
					answer++;
					System.out.println("answer");
					while (!st.empty()){
						System.out.println("pop");
						if(st.pop() == 1) break;
					}
					i=0;
					packPoint = 0;
				}
			} else if(list.get(i)==1){
				packPoint = 1;
			}
			st.push(list.get(i));
		}

		 */
		int answer = 0;
		List<Integer> Onelist = new ArrayList<>();
		for(int i=0;i<ingredient.length;i++){
			if(ingredient[i]==1) Onelist.add(i);
		}
		System.out.println(Onelist.size());
		for(int i=1;i<Onelist.size()-1;i++){
			System.out.println("list.get(i-1) : "+Onelist.get(i-1)+" | list.get(i) : "+Onelist.get(i));
			List<Integer> tmplist = Onelist.subList(Onelist.get(i-1),Onelist.get(i));

			if(tmplist.contains(2) && tmplist.contains(3) && tmplist.indexOf(2) < tmplist.lastIndexOf(3)){
				answer++;
				i=0;
				for(int j = i-1;j<i;j++){
					ingredient[j] = 0;
				}
			}
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

	/*
	static HashSet<String> rs;
	static String[] oparr,numarr,orgnumarr,resultarr;
	static boolean[] visitarr;
	public static int[] solution4(String str){
		int[] intarr = null;
		numarr = str.split("[-*+]");
		orgnumarr = str.split("[-*+]");
		oparr = str.split("[0-9]");
		resultarr = new String[oparr.length-1];
		visitarr = new boolean[oparr.length-1];
		Arrays.fill(visitarr,false);
		rs = new HashSet<>();
		getPermutations(0,0);

		return intarr;
	}
	public static void getPermutations(int digit, int flag){
		if(digit == oparr.length-1){
			System.out.println(Arrays.stream(resultarr).filter(Objects::nonNull).collect(Collectors.joining()));
			//rs.add(s);
			//resultarr = new String[oparr.length-1];

			return;
		}
		for (int i=1;i<oparr.length;i++){
			String left = numarr[i-1],right = numarr[i];
			if((flag &  1<<i) != 0) continue;

			if(i > 1 && resultarr[i-2]!=null){
				left = resultarr[i-2];
				resultarr[i-2] = null;
			}
			if(i < oparr.length-1 && resultarr[i]!=null){
				right = resultarr[i];
				resultarr[i] = null;
			}
			System.out.println("left : "+left+" | right : "+right);
			resultarr[i-1] = "(" +left +oparr[i] +right +")";
			getPermutations(digit+1,flag | 1 << i);
			resultarr[i-1] = null;
		}
		System.out.println("digit : "+digit+" | flag : "+Integer.toBinaryString( flag));
	}
	*/

	static HashSet<String> rs;
	static String[] oparr,numarr,resultarr;
	static Boolean[] visitarr;
	static Stack<Integer> st;
	public static int[] solution4(String str){
		int[] intarr = null;
		numarr = str.split("[-*+]");
		oparr = str.split("[0-9]");
		resultarr = new String[oparr.length-1];
		visitarr = new Boolean[oparr.length-1];
		Arrays.fill(visitarr,false);
		rs = new HashSet<>();
		st = new Stack();
		getPermutations(0,0);
		System.out.println("-------------------");
		rs.forEach(System.out::println);
		return intarr;
	}
	/*
	public static void getPermutations(int digit, int flag){
		if(digit == oparr.length-1){
			String result = "";
			result = Arrays.stream(resultarr).filter(Objects::nonNull).collect(Collectors.joining());
			//System.out.println(Arrays.toString(visitarr));
			//System.out.println(Arrays.toString(resultarr));
			for(int i=0;i< oparr.length-1;i++){
				if(!visitarr[i]){
					if(0 < i && i < oparr.length-2) result = "("+resultarr[i-1]+oparr[i+1]+resultarr[i+1]+")";
					else if(i==0) result = "("+numarr[i]+oparr[i+1]+resultarr[i+1]+")";
					else if(i==oparr.length-2) result = "("+resultarr[i-2]+oparr[i+1]+numarr[i+1]+")";
				}
			}
			st.forEach(System.out::println);
			rs.add(result);
			Arrays.fill(visitarr,false);
			return;
		}
		for (int i=0;i<oparr.length-1;i++){
			String left = numarr[i],right = numarr[i+1];
			if((flag &  1<<i) != 0) continue;

			if(i > 0 && resultarr[i-1]!=null){
				left = resultarr[i-1];
				resultarr[i-1] = null;
			}
			if(i+1 < oparr.length-1 && resultarr[i+1]!=null){
				right = resultarr[i+1];
				resultarr[i+1] = null;
			}
			System.out.println("left : "+left+ " | right : "+right+"| digit :"+digit) ;
			resultarr[i] = "(" +left +oparr[i+1] +right +")";
			st.push("(" +left +oparr[i+1] +right +")");
			visitarr[i] = true;
			getPermutations(digit+1,flag | 1 << i);
			resultarr[i] = null;
			visitarr[i] = false;
		}
	}
	*/

	public static void getPermutations(int digit, int flag){
		if(digit == oparr.length-1){
			String result = "";
			while(!st.empty()){
				result += String.valueOf(st.pop());
			}
			st.clear();
			rs.add("result : "+result);
			return;
		}
		for (int i=0;i<oparr.length-1;i++){
			if((flag & 1<<i) != 0) continue;
			st.push(i);
			getPermutations(digit+1,flag | 1 << i);
			resultarr[i] = null;
		}
	}
	public static int solution5(int[] intarr){
		int answer = 0,liz = 0, max = 0, happysum = 0,unhappysum = 0;
		for(int i : intarr){
			if(8 < i){
				liz++;
				max++;
				if(0 < liz) happysum++;
			} else{
				if(0 < liz--){
					max++;
					unhappysum++;
				}
				else max = 0;
				liz = 0;
			}

			if(answer < max) answer = max;
		}
		if(0 < answer && unhappysum - happysum >= 0) answer -= (unhappysum - happysum + 1);
		return answer;
	}
}
