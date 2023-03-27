package com.hystudy.springboot.web;

import java.io.PrintStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProgrammersController {
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

	// LV.2 문자열 압축
	public static int solution_TextCompression(String s) {
		int answer = s.length();
		for (int i = 1; i <= Math.floor(s.length() / 2); i++) {  // 문자 단위
			int cnt = 0, eqcnt = 0; // 반복 횟수, 같은문자 카운트
			int min = s.length(); // 압축 문자 길이
			String prevword = s.substring(0, i); // 이전 문자
			while (cnt++ < s.length() / i) { // 최대 문자열 반까지 자름
				// 현재 문자열 (마지막에 i개로 안잘릴수있으므로 마지막 카운트엔 length를 넣음)
				String str = s.substring(i * cnt, Math.min(i * (cnt + 1), s.length()));
				if (prevword.equals(str)) eqcnt++; // 이전 문자와 같을 시 카운트 증가
				else if (eqcnt > 0) { // 같은문자 카운트 끝났으면 줄어든 문자 수 계산
					// 줄어든 문자 수 계산 : (잘린 단위 X 반복된횟수-1 ) + 반복횟수 자릿 수(10일경우 2이므로)
					min = min - ((eqcnt) * i) + ((int) Math.log10(eqcnt + 1) + 1);
					eqcnt = 0; // 같은문자 카운트 초기화
				}
				prevword = str; // 이전 문자에 현재문자 저장
			}
			if (min < answer) answer = min; // i개로 잘랐을때의 갯수가 최솟값보다 작으면 갱신
		}
		return answer;
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

	// LV.2 더 맵게
	public static int solution_moreSpicy(int[] scoville, int K) {
		int answer = 0;
		PriorityQueue<Integer> heap = Arrays.stream(scoville).boxed()
				.collect(Collectors.toCollection(PriorityQueue::new));
		while (heap.peek() != null && heap.peek() < K) {
			if (heap.size() == 1) return -1;
			heap.add(heap.poll() + (heap.poll() * 2));
			answer++;
		}

		return answer > 0 ? answer : -1;
	}

	// LV2. 오픈채팅방
	public static String[] solution_openKaKao(String[] record) {
		Map<String, String> usermap = new HashMap<>();
		List<String> answer = new ArrayList<>();
		for (String str : record) {
			String[] temparr = str.split(" ");
			if ("Enter".equals(temparr[0])) {
				usermap.put(temparr[1], temparr[2]);
				answer.add(temparr[1] + "/님이 들어왔습니다.");
			} else if ("Leave".equals(temparr[0])) {
				answer.add(temparr[1] + "/님이 나갔습니다.");
			} else if ("Change".equals(temparr[0])) {
				usermap.put(temparr[1], temparr[2]);
			}
		}

		return answer.stream().map(a -> usermap.get(a.split("/")[0]) + a.split("/")[1]).toArray(String[]::new);
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

	// LV2. 멀쩡한 사각형
	public static long solution_normalSquare(long w, long h) {
		long answer = w * h; // 첫 사각형 크기

		long gcd = 1, idx = w;
		while (1 < idx) { // 공약수 구하기
			if (w % idx == 0 && h % idx == 0) {
				gcd *= idx;
				w /= idx;
				h /= idx;
				idx = w;
			} else idx--;
		}
		long gcd2 = BigInteger.valueOf(w).gcd(BigInteger.valueOf(h)).longValue();
		return answer - gcd * (w + h - 1);
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

	// LV2. 124 나라의 숫자
	public static String solution_124Country(int n) {
		StringBuffer sb = new StringBuffer();
		while(n > 0){
			int su = n%3==0?3:n%3;
			sb.append((int)Math.pow(2,su-1));
			n /= 3;
			if(su==3) n -= 1;
		}
		return sb.reverse().toString();
	}

	// LV2. 거리두기 확인하기
	public static int[] solution_keepDistance(String[][] places) {
		int[] answer = new int[]{1,1,1,1,1};
		for(int i=0;i<places.length;i++){ // 각 대기실
			// 참가자 리스트 확인
			List<int[]> plist = new ArrayList<>();
			for(int j=0;j<places[0].length;j++){ // 각 열
				String[] temparr = places[i][j].split("");
				for(int k=0;k<temparr.length;k++){ // 각 행
					if("P".equals(temparr[k])) plist.add(new int[]{j,k});
				}
			}

			// 거리 검색
			for (int j=0;j<plist.size()-1;j++){
				for (int k=j+1;k<plist.size();k++){
					int xdistance = Math.abs(plist.get(j)[0]-plist.get(k)[0]);
					int ydistance = Math.abs(plist.get(j)[1]-plist.get(k)[1]);
					int xpos = plist.get(j)[0]>plist.get(k)[0]?plist.get(k)[0]:plist.get(j)[0];
					int ypos = plist.get(j)[1]>plist.get(k)[1]?plist.get(k)[1]:plist.get(j)[1];

					if(xdistance+ydistance<=1){
						answer[i]=0;
						break;
					}else if(xdistance+ydistance==2){
						if(xdistance==ydistance){
							if ("0".equals(places[i][xpos].split("")[ypos])
							||"0".equals(places[i][xpos+1].split("")[ypos])
							||"0".equals(places[i][xpos].split("")[ypos+1])
							||"0".equals(places[i][xpos+1].split("")[ypos+1])){
								answer[i]=0;
								break;
							}
						} else if(xdistance==0 && !"X".equals(places[i][xpos].split("")[ypos+1])){
							answer[i]=0;
							break;
						} else if(ydistance==0 && !"X".equals(places[i][xpos+1].split("")[ypos])){
							answer[i]=0;
							break;
						}
					}
				}
				if(answer[i]==0) break;
			}
		}
		return answer;
	}

	// LV2. n개의 최소공배수
	public static int solution_lcm(int[] arr) {
		int answer = 1;

		for (int i=0;i<arr.length-1;i++){
			BigInteger gcd = BigInteger.valueOf(1);
			for(int k=i+1;k<arr.length;k++) {
				gcd = BigInteger.valueOf(arr[i]).gcd(BigInteger.valueOf(arr[k]));
				if (1 < gcd.intValue()) {
					for (int j = 0; j < arr.length; j++) {
						if (arr[j] % gcd.intValue() == 0) {
							arr[j] /= gcd.intValue();
						}
					}
					answer *= gcd.intValue();
				}
			}
		}
		for(int a: arr) answer *= a;

		return answer;
	}

	// LV2. JadenCase 문자열 만들기
	public static String solution_jadenCase(String s) {
		String answer =  Arrays.stream(s.split(" "))
				.map(a -> a.length()!=0 ? a.split("")[0].toUpperCase()+a.substring(1).toLowerCase():"")
				.collect(Collectors.joining(" "));
		answer = " ".equals(s.substring(s.length()-1))?answer+" ":answer;
		return answer;
	}

	// LV2. 행렬테두리 회전하기
	public static int[] solution_matrix_rotation(int rows,int columns,int[][] queries){
		int[][] matrix = new int[rows][columns];
		IntStream.range(0,rows).forEach(i->IntStream.range(0,columns).forEach(j-> matrix[i][j] = i*columns+j+1));

        return IntStream.range(0, queries.length).map(i -> rotate_matrix(rows,columns,matrix,queries[i])).toArray();
	}

	public static int rotate_matrix(int rows,int columns,int[][] matrix,int[] query){
		int min = 10000;

		int[][] copy_matrix = new int[rows][columns];
		IntStream.range(0,copy_matrix.length).forEach(a -> System.arraycopy(matrix[a],0,copy_matrix[a],0,matrix[a].length));

		int x_pos = query[1]-1, y_pos = query[0]-1;
		do{
			if(copy_matrix[y_pos][x_pos] < min) min = copy_matrix[y_pos][x_pos];
			if 		(x_pos <  query[3]-1 && y_pos == query[0]-1) matrix[y_pos][x_pos+1] = copy_matrix[y_pos][x_pos++];
			else if (x_pos == query[3]-1 && y_pos <  query[2]-1) matrix[y_pos+1][x_pos] = copy_matrix[y_pos++][x_pos];
			else if (x_pos >  query[1]-1 && y_pos == query[2]-1) matrix[y_pos][x_pos-1] = copy_matrix[y_pos][x_pos--];
			else if (x_pos == query[1]-1 && y_pos >= query[0]-1) matrix[y_pos-1][x_pos] = copy_matrix[y_pos--][x_pos];
		}while((query[1]-1 < x_pos && x_pos <= query[3]-1) || (query[0]-1 < y_pos && y_pos <= query[2]-1));

		return min;
	}

	public static void print_matrix(int[][] matrix){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				System.out.print(matrix[i][j]+"\t");
			}
		}
	}

	//LV2. 가장큰수
	public static String solution_max_int(int[] numbers) {
		String answer = Arrays.stream(numbers).boxed()
				.sorted(Comparator.comparing(String::valueOf,(a,b) -> (b+a).compareTo(a+b)))
				.map(String::valueOf)
				.collect(Collectors.joining());
		return answer.charAt(0)=='0'?"0":answer;
	}

	public static String test(){
		int [][] temparr = new int[][]{{1,2},{1,2},{0,9,8,4,5,6}};
		int[] newtemp = Arrays.stream(temparr).flatMap(a -> Arrays.stream(a).boxed()).sorted().mapToInt(b->b).toArray();

		return "";
	}

	//LV2. 최솟값만들기
	public int solution_make_min_value(int []A, int []B){
		Arrays.sort(A);
		Arrays.sort(B);
		return IntStream.range(0,A.length).map(i->A[i]*B[B.length-i-1]).sum();
	}

	//LV2.최솟값과 최대값
	public String solution_min_max_value(String s) {
		int[] arr = Arrays.stream(s.split(" ")).map(Integer::valueOf).sorted().mapToInt(a->a).toArray();
		return arr[0]+" "+arr[arr.length];
	}

	//LV2. 숫자의 표현
	public static int solution_expression_number(int n) {
		int answer = 1;

		int s = 1,e = 2;
		while(s < e){
			int sum = 0;//IntStream.rangeClosed(s,e).sum();
			for(int i=s;i<=e;i++) sum += i;
			if(sum > n) s++;
			else if(sum == n){
				answer++;
				s++;
				e++;
			}
			else e++;
		}

		return answer;
	}

	//LV2. 피보나치 수
	public static int solution_fibonacci(int n) {
		List<Integer> fibo = new ArrayList<>();
		fibo.add(0);
		fibo.add(1);
		while(fibo.size() <= n){
			fibo.add(fibo.get(fibo.size()-2)%1234567+fibo.get(fibo.size()-1)%1234567);
		}
		return fibo.get(fibo.size()-1)%1234567;
	}

	//LV2. 다음 큰 숫자
	public static int solution_next_max_int(int n) {
		int answer = 0;
		int cnt = Integer.toBinaryString(n).replace("0","").length();
		while(0 < n++){
			int cnt2 = Integer.toBinaryString(n).replace("0","").length();
			if(cnt==cnt2) break;
		}
		return n;
	}

	//LV2. 가장 큰 사각형 찾기
	public static int solution_max_square(int [][]board) {
		int answer = 1234;
		List<int[]> zeroList = new ArrayList<>();
		for(int i=0;i<board.length;i++){
			for (int j=0;j<board[0].length;j++){
				if(board[i][j] < 1){
					zeroList.add(new int[]{i,j});
				}
			}
		}
		return 0;
	}

	// LV2. 컬러링북
	public static int[] solution_coloringbook(int m, int n, int[][] picture) {
		int numberOfArea = 0;
		int maxSizeOfOneArea = 0;

		int[] answer = new int[2];
		answer[0] = numberOfArea;
		answer[1] = maxSizeOfOneArea;

		Map<Integer,List<int[]>> colormap = new HashMap<>();
		for (int i=0;i<picture.length;i++) {
			List<int[]> list = new ArrayList<>();
			for (int j=0;j<picture[0].length;j++){
				if(0 < picture[i][j]){
					if(colormap.get(picture[i][j]) != null && !colormap.get(picture[i][j]).isEmpty()){
						list = colormap.get(picture[i][j]);
					}
					list.add(new int[]{i,j});
					colormap.put(picture[i][j],list);
				}
			}
		}

		for(Integer i : colormap.keySet()){
			Map<String,List<int[]>> newlist = new HashMap<>();
			List<int[]> area = new ArrayList<>();
			for(int k=0;k<colormap.get(i).size();k++){
				int[] pos = colormap.get(i).get(k);
				if(newlist.size()==0){
					area.add(new int[]{pos[0],pos[1]});
					colormap.get(i).remove(colormap.get(i).indexOf(new int[]{pos[0]+1,pos[1]}));
				}
			}
		}

		return answer;
	}

	//LV2. 행렬의 곱셈
	public static int[][] solution_matrix_multiple(int[][] arr1, int[][] arr2) {
		int[][] answer = new int[arr1.length][arr2[0].length];
		for(int i=0;i<arr1.length;i++){
			for(int j=0;j<arr2[0].length;j++){
				answer[i][j] = 0;
				for(int k=0;k<arr2.length;k++){
					answer[i][j] += (arr1[i][k]*arr2[k][j]);
				}
			}
		}
		return answer;
	}

	//LV2. 영어로 끝말잇기
	public static int[] solution_english_word_chain_game(int n, String[] words) {
		int cnt = 0;
		String prev_word = words[0];
		while(++cnt < words.length){
			if(prev_word.charAt(prev_word.length()-1)!=words[cnt].charAt(0)) break;
			if(0 <= Arrays.asList(words).indexOf(words[cnt]) && Arrays.asList(words).indexOf(words[cnt]) < cnt) break;
			prev_word = words[cnt];
		}
		if(words.length <= cnt) return new int[]{0,0};
		return new int[]{cnt%n+1,cnt/n+1};
	}

	//LV2. 타켓넘버
	public static int solution_target_number(int[] numbers, int target) {
		int answer = 0;
		int cnt = -1;
		while(++cnt < Math.pow(2,numbers.length)){
			String bi = Integer.toBinaryString(cnt);
			int result_number = 0;
			for(int i=0;i<numbers.length;i++){
				if(i < numbers.length-bi.length()) result_number += numbers[i];
				else if(bi.charAt(i-(numbers.length-bi.length()))=='1') result_number -= numbers[i];
				else result_number += numbers[i];
			}
			if(result_number==target) answer++;
		}
		return answer;
	}

	//LV2. 올바른 괄호 : 효율성 통과 못함 ㅅㅂ
	public static boolean solution_correct_bracket(String s) {
		int i=0;
		Stack<Character> stack = new Stack<>();
		while(i<s.length()){
			char c = s.charAt(i++);
			if(stack.empty()){
				if('('==c) stack.push(c);
				else return false;
			}
			else if('('==c) stack.push(c);
			else if(')'==c){
				if('('==stack.peek()) stack.pop();
				else return false;
			}
			else return false;
		}
		return stack.empty()?true:false;
	}

	//LV2.이진변환 반복하기
	public static int[] solution_repeat_binary(String s) {
		int[] answer = new int[2];
		answer[0] = 0;
		answer[1] = 0;
		while (s.length() > 1){
			answer[0]++;
			answer[1] += Arrays.stream(s.split("")).filter(a->"0".equals(a)).count();
			s = Integer.toBinaryString(s.replace("0","").length());
		}
		return answer;
	}
	//LV2. 예상 대진표
	public static int solution_expect_tournament(int n, int a, int b) {
		int answer = (int)Math.floor(Math.log(n)/Math.log(2));
		while(Math.min(a,b) > n/2 || Math.max(a,b) <= n/2){
			n /= 2;
			answer--;
			if(Math.max(a,b) > n && Math.min(a,b) > n){
				a -= n;
				b -= n;
			}
		}
		return answer;
	}

	//LV2. 주식가격
	public static int[] solution_stock_price(int[] prices) {
		int[] answer = new int[prices.length];
		for(int i=0;i<prices.length;i++){
			int cnt = 0;
			for(int j=i+1;j<prices.length;j++){
				cnt++;
				if(prices[j] < prices[i]) break;
			}
			answer[i] = cnt;
		}
		return answer;
	}

	//LV2.n^2배열 자르기
	public static int[] solution_slice_array(int n, long left, long right) {
		int[] answer = new int[(int)(right-left+1)];
		int idx = 0;
		for(long i = (long)Math.floor(left/n);i<=Math.floor(right/n);i++){
			for(long j = 0;j<n;j++){
				if(left <= i*n+j && i*n+j <= right){
					answer[idx++] = (int)Math.max(i,j)+1;
				}
			}
		}
		return answer;
	}

	//LV2. 모음사전
	enum vowel{A,E,I,O,U};
	public static int solution_vowel_dictionary(String word) {
		int answer = 0;
		int[] jump = new int[5];
		for(int i=0;i<5;i++){
			jump[i] = (int)Math.pow(5,i) + (i-1<0?0:jump[i-1]);
		}
		String[] strarr = word.split("");
		for (int i=0;i<strarr.length;i++) {
			answer += vowel.valueOf(strarr[i]).ordinal()*jump[4-i] + 1;
		}
		return answer;
	}

	// LV2. 전화번호부
	public static boolean solution_phonebook(String[] phone_book) {
		boolean answer = true;
		PriorityQueue<String> queue = Arrays.stream(phone_book).collect(Collectors.toCollection(PriorityQueue::new));
		while(1 < queue.size()){
			String str = queue.poll();
			if(str.startsWith(queue.peek())||queue.peek().startsWith(str)){
				answer = false;
				break;
			}
		}
		return answer;
	}

	//LV2. 짝지어 제거하기
	public static int solution_pair_remove(String s){
		//int answer = -1;
		String[] stringarr = s.split("");
		Stack<String> st = new Stack<>();
		for(String str :stringarr ){
			if(st.size()!=0&&str.equals(st.peek())){
				st.pop();
			}else if(st.size()>stringarr.length/2){
				return 0;
			}else{
				st.push(str);
			}
		}
		return st.size()==0?1:0;
	}

	//LV2. 프린터
	public static int solution_printer(int[] priorities, int location) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		for(int i : priorities) list.add(new Integer(i));

		Integer idx = list.get(location);
		int cnt = 1;
		while(0 < list.size()){
			boolean flag = false;
			Integer i = list.pollFirst();
			for(Integer i2 : list){
				if(i2.compareTo(i) > 0){
					flag = true;
					break;
				}
			}
			if(flag) list.addLast(i);
			else{
				if(i == idx) break;
				cnt++;
			}
		}
		return cnt;
	}

	//LV2. 다리를 지나가는 트럭
	public static int solution_truck(int bridge_length, int weight, int[] truck_weights) {
		int answer = 1;

		Queue<Integer> bridgeQ = new LinkedList<>();
		int idx = 1;
		for(int i=0;i<bridge_length-1;i++) bridgeQ.add(0);

		int bridgeW = truck_weights[0];
		bridgeQ.add(truck_weights[0]);
		while(0 < bridgeW){
			bridgeW -= bridgeQ.poll();

			if(idx < truck_weights.length && bridgeW + truck_weights[idx] <= weight){
				bridgeW += truck_weights[idx];
				bridgeQ.add(truck_weights[idx]);
				idx++;
			} else{
				bridgeQ.add(0);
			}
			answer++;
		}
		return answer;
	}
	//lv2. 위장
	public static int solution_camouflage(String[][] clothes) {
		int answer = 1;

		Map<String,Integer> list = new HashMap<>();
		for (String[] s1 : clothes){
			if(list.containsKey(s1[1])) list.put(s1[1],list.get(s1[1])+1);
			else list.put(s1[1],1);
		}
		for(String s : list.keySet()){
			answer *= list.get(s)+1;
		}
		return answer-1;
	}
	//LV2. 카펫
	public static int[] solution_carpet(int brown, int yellow) {
		int idx = IntStream.rangeClosed(1,(int)Math.sqrt(yellow))
				.filter(i -> yellow % i == 0)
				.findFirst().getAsInt();
		return new int[]{yellow/idx+2,idx+2};
	}
	//LV2. 스킬트리
	public static int solution_skillTree(String skill, String[] skill_trees) {
		return (int)Arrays.stream(skill_trees)
				.filter(s->skill.startsWith(s.replaceAll("[^"+skill+"]","")))
				.count();
	}
	// 20220726 완료
	// LV2. 기능개발 [https://school.programmers.co.kr/learn/courses/30/lessons/42586]
	public static int[] solution_develop_skill(int[] progresses, int[] speeds) {
		List<Integer> answerList = new ArrayList<Integer>();
		boolean flag = false;
		int idx=0,date=0,cnt=1;
		while(idx < progresses.length) {
			if (progresses[idx] + (date * speeds[idx]) < 100) {
				if(flag){
					answerList.add(cnt);
					cnt = 1;
				}
				date++;
				flag = false;
			} else if (flag){
				idx++;cnt++;
			} else {
				idx ++;
				flag = true;
			}
			if(idx == progresses.length) answerList.add(cnt);
		}
		return answerList.stream().mapToInt(Integer::intValue).toArray();
	}

	//LV2. 2개이하로 다른 비트 2022.07.26 완료
	public static long[] solution_under_two_bit(long[] numbers) {
		long[] answer = new long[numbers.length];
		for(int i = 0; i < numbers.length;i++){
			String target = Long.toBinaryString(numbers[i]);
			int idx = target.lastIndexOf("0");

			if(idx == target.length()-1){ // 마지막숫자가 0일경우
				answer[i] = numbers[i]+1;
			}else if(idx == -1){ // 첫자리가 0일 경우
				answer[i] = Long.parseUnsignedLong("10"+target.substring(idx+2),2);;
			}else{
				answer[i] = Long.parseUnsignedLong(target.substring(0,idx)+"10"+target.substring(idx+2),2);
			}
		}
		return answer;
	}

	// LV.2 귤고르기
	public static int solution_choice_tangerine(int k, int[] tangerine) {
		int answer = 0;

		Long[] arr = Arrays.stream(tangerine).boxed()
				.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
				.values().toArray(Long[]::new);

		Arrays.sort(arr,Comparator.reverseOrder());

		int sum = 0;
		for(Long i : arr){
			sum += i;
			answer++;
			if(k <= sum) break;
		}

		return answer;
	}

	//============================================================================
	//LV2. 땅따먹기
	public static int solution_eat_the_ground(int[][] land) {
		int answer = 0;
		Map<Integer,Integer> map = new LinkedHashMap<>();
		for(int i=0;i<land.length;i++){
			map.put(i,Arrays.stream(land[i]).max().getAsInt());
		}
		map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey));
		for(int k : map.keySet()){
			for(int i=0;i<=land.length;i++){
				if(map.get(k)==land[k][i]){
					if(0 <= k-1) land[k-1][i] = 0;
					if(k+1 < land.length) land[k+1][i] = 0;
				}
			}
		}

		for(int i=0;i<land.length;i++){
			answer += Arrays.stream(land[i]).max().getAsInt();
		}

		return answer;
	}

	//LV2. 코스요리
	public static String[] solution_course(String[] orders, int[] course) {
		String[] answer = {};
		for(int i :course){
			for (String str : orders){
				str.split("");
			}
		}
		return answer;
	}
	//LV2. 구명보트
	public static int solution_rescue_boat(int[] people, int limit) {

		// 2022.10.07 60점
		Stack<Integer> st = Arrays.stream(people).boxed().sorted().collect(Collectors.toCollection(Stack::new));

		int cnt = 0;
		while(!st.empty()){
			cnt++;
			int sum = st.pop();
			if(limit-sum < 40){
				continue;
			}else if(st.indexOf(limit-sum) > -1) { // 최적값이 있는 경우
				st.remove(st.indexOf(limit - sum));
			}else{ // 아닌경우
				int top = st.size();
				while(0 < top--){
					if(st.get(top) <= limit-sum){
						sum += st.get(top);
						st.remove(top);
					}
				}
			}
		}
		return cnt;

		/*
		List<Integer> st = Arrays.stream(people).boxed().sorted().collect(Collectors.toList());
		int cnt = people.length;
		while(!st.isEmpty()){
			int sum = st.get(st.size()-1);
			st.remove(st.size()-1);
			if(limit-sum < Arrays.stream(people).min().getAsInt()) continue;
			if(st.indexOf(limit-sum)>-1){
				st.remove(st.indexOf(limit-sum));
				cnt--;
			}
			for(int i = st.size()-1;0 <= i;i--){
				if(sum+st.get(i) < limit){
					sum += st.get(i);
					st.remove(i);
					cnt--;
				}
				if(sum==limit||st.isEmpty()) break;
			}
			cnt++;
		}
		return cnt;
		*/
		/*
		int cnt = people.length;
		int sum = 0;
		Arrays.stream(people).boxed().sorted(Comparator.reverseOrder()).forEach( a -> {
			if(sum+a < limit){
				sum += a;
				cnt--;
			}
		});
		return cnt;
		*/
	}
	//LV2. 피로도
	public static int solution_fatigue(int k, int[][] dungeons) {
		int answer = 0;

		List<int[]> list = Arrays.stream(dungeons).collect(Collectors.toList());
		/*
		int idx = 0;
		while(idx < dungeons.length){
			if(k < list.get(idx)[0]){
				int[] temp = list.get(idx);
				list.set(idx,list.get(idx-1));
				list.set(idx-1,temp);
				idx = 0;
				answer = 0;
				continue;
			} else{
				answer++;
				idx++;
			}
			k -= list.get(idx-1)[1];
		}
		*/
		return answer;
	}

	//LV2. 조이스틱 - 20220805 48.1
	public static int solution_joystick(String name) {
		/*int answer = 0;

		//System.out.println(">> name : "+name);
		List<Integer> numList = new ArrayList<>();
		for(int i = 0; i < name.length(); i++) {
			int num = Math.min(Math.abs(name.charAt(i) - 'A'),Math.abs(name.charAt(i) - 'Z')+1);
			if(num!=0) numList.add(i);
			answer += num;
		}

		boolean reverse = false;
		int idx = 0,move =0;
		while(0 < numList.size()-1){
			move++;
			//System.out.println(">> idx : "+idx);
			int min = name.length();
			for(int i=0;i<numList.size();i++){
				if(numList.get(i) == idx){
					numList.remove(i);
					continue;
				}
				if(numList.get(i)-idx < min){ //
					min = numList.get(i)-idx;
					reverse = false;
				}
				if(name.length()-numList.get(i) < min){
					min = name.length() - numList.get(i);
					reverse = true;
				} else if(numList.get(i)-idx < min && name.length()-numList.get(i) < min){
					min = numList.get(i)-idx;
				}
			}

			if(reverse){
				if(--idx < 0) idx = name.length()-1;
			}else{
				if(name.length()-1 < ++idx) idx = 0;
			}
			if(name.length()-1 == move) break;
		}

		return answer+move;*/
		int answer = 0;

		List<Integer> numList = new ArrayList<>();
		for(int i = 0; i < name.length(); i++) {
			int num = Math.min(Math.abs(name.charAt(i) - 'A'),Math.abs(name.charAt(i) - 'Z')+1);
			if(num!=0) numList.add(i);
			answer += num;
		}

		boolean reverse = false;
		int idx = 0,move =0;
		while(0 < numList.size()-1){
			if(name.length()-1 == ++move) break;
			int min = name.length();
			for(int i=0;i<numList.size();i++){
				if(numList.get(i) == idx){
					numList.remove(i);
					continue;
				}
				if(numList.get(i)-idx == name.length()-numList.get(i) && numList.get(i)-idx < min){
					min = numList.get(i)-idx;
					continue;
				}
				if(name.length()-numList.get(i) < min){
					min = name.length() - numList.get(i);
					reverse = true;
				}
				if(numList.get(i)-idx < min){ //
					min = numList.get(i)-idx;
					reverse = false;
				}
			}

			if(reverse && idx-1 < 0) idx = name.length()-1;
			else if(!reverse && name.length()-1 < idx+1) idx = 0;
		}

		return answer+move;
	}

	// LV2. N-Queen
	public static int solution_n_queen(int n) {
		int answer = Math.round(n/2)*(Math.round(n/2)-(n%2==0?1:0));

		return answer;
	}


	// LV2. 점찍기
	public static long solution_drawing_comma(int k, int d) {
		/*
		//20221209
		long answer = (int)Math.pow(d/k+1,2);
		System.out.println("*----- d : "+d+" / k :"+k);
		double unit = d/k,idx = 0;
		while(idx <= d-k){
			System.out.println("unit : "+ unit);
			idx += k;
			answer -= (int)unit;
			unit = Math.sqrt(unit);
		}
		return answer;
		*/
		/*
		// 20221208
		int answer = 0;
		int yd = d;
		for(int x=0;x<=d;x+=k){
			for(int y=0;y<=Math.sqrt(x*y);y+=k){
				if(Math.pow(x,2)+Math.pow(y,2) <= Math.pow(d,2)){
					System.out.println("["+x+","+y+"]");
					answer++;
				}
			}
		}
		return answer;
		*/
		// 20221209

		//double mi = ((double)d/(double)k)*Math.PI;
		/*
		double mi = (k+1)*d;
		System.out.println(mi);
		System.out.println((Math.pow((d/k)+1,2)));
		int answer = (int)(Math.pow((d/k)+1,2) - mi);


		 */
		/*
		double answer = Math.pow((d/k)+1,2);
		for(int i = 0; i < d; i+=k){
			for(int j = 0; j < d;j+=k){
				d = (int)Math.sqrt(d);
				System.out.println("i :"+i+" / j : "+j);
				answer--;
			}
		}
		*/
		//20221212
		double answer = Math.pow((d/k)+1,2)*(Math.PI/4);

		return (int)answer;
	}

	//
	public static int solution_magical_elevator(int storey) {
		int answer = 0;

		String[] strarr = String.valueOf(storey).split("");
		for (int i=strarr.length-1;0 <= i;i--){
			int a = Integer.parseInt(strarr[i]);
			int b = Math.abs(10 - a);
			if(b < 0 || a <= b){
				answer += a;
			}else{
				answer += b;
				if(i!=0) strarr[i-1] = String.valueOf(Integer.valueOf(strarr[i-1])+1);
			}
		}

		/*
		String[] strarr = String.valueOf(storey).split("");
		for (int i=0;i<strarr.length;i++){
			int a = Integer.parseInt(strarr[i]);
			int b = Math.abs(10 - a);
			System.out.println("a : "+ a + "/ b : "+b);
			if(a <= b){
				answer += a;
			}else{
				answer += b;
				if(i < strarr.length-1 &&Integer.valueOf(strarr[i+1]) <= 5) answer++;
				else  answer--;
			}
			System.out.println("answer : "+ answer);
		}
		*/

		return answer;
	}


	//LV2. H-index
	public static int solution_h_index(int[] citations) {
		int[] first = Arrays.stream(citations).boxed().sorted(Comparator.reverseOrder())
				.filter(i -> i <= Arrays.stream(citations).filter(f->i<=f).count())
				.mapToInt(m->m).toArray();

		return Arrays.stream(first).filter(i -> Arrays.stream(first).filter(f->i<f).count() == 0).findFirst().getAsInt();
	}

	// LV2. 광물캐기
	enum mineral{diamond,iron,stone};
	public static int solution_mineral(int[] picks, String[] minerals) {
		int answer = 0;

		// int List 변환
		List<Integer> mineList = Arrays.stream(minerals).mapToInt(a->mineral.valueOf(a).ordinal()).boxed().collect(Collectors.toList());

		// 5개씩 나누기
		int unit = 5;
		List<List<Integer>> mineSubList = new ArrayList<>();
		System.out.println(minerals.length/unit);
		for(int i=0;i<Math.floor(minerals.length/unit);i++){
			mineSubList.add(mineList.subList(unit*i,unit*(i+1)));
		}
		if(minerals.length%5!=0) mineSubList.add(mineList.subList((int) (5 * Math.floor(minerals.length/unit)),minerals.length));

		// sum이 큰 순서대로 정렬
		//mineSubList.stream().sorted(Comparator.reverseOrder());

		int pick = 0;
		for(List<Integer> list : mineSubList){
			// 곡괭이 선택
			while(picks[pick]<=0){
				if(pick > picks.length) break;
				pick++;
			}

			// 피로도 계산
			for(Integer i :list){
				answer += Math.ceil(Math.pow(5,pick-i));
				System.out.println("sum : "+ Math.ceil(Math.pow(5,pick-i)));
			}

		}
		return answer;
	}

}
