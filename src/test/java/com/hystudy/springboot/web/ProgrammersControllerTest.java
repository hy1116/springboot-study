package com.hystudy.springboot.web;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProgrammersControllerTest {
	@Test
	public void solution_RepostResult_test() { // 신고 결과 받기 
		assertThat(ProgrammersController.solution_RepostResult(
				new String[]{"muzi", "frodo", "apeach", "neo"}, 
				new String[]{"muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi"}, 
				2)).isEqualTo(new int[]{2,1,1,0});
		assertThat(ProgrammersController.solution_RepostResult(
				new String[]{"con", "ryan"}, 
				new String[]{"ryan con", "ryan con", "ryan con", "ryan con"}, 
				3)).isEqualTo(new int[]{0,0});
	}
	
	@Test
	public void solution_TextCompression_test() { // 문자열 압축
		assertThat(ProgrammersController.solution_TextCompression("aabbaccc")).isEqualTo(7);
		assertThat(ProgrammersController.solution_TextCompression("ababcdcdababcdcd")).isEqualTo(9);
		assertThat(ProgrammersController.solution_TextCompression("abcabcdede")).isEqualTo(8);
		assertThat(ProgrammersController.solution_TextCompression("abcabcabcabcdededededede")).isEqualTo(14);
		assertThat(ProgrammersController.solution_TextCompression("xababcdcdababcdcd")).isEqualTo(17);
	}
	
	@Test
	public void solution_keypadPress_test() { // 키패드 누르기
		assertThat(ProgrammersController.solution_keypadPress(new int[] {1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5},"right")).isEqualTo("LRLLLRLLRRL");
		assertThat(ProgrammersController.solution_keypadPress(new int[] {7, 0, 8, 2, 8, 3, 1, 5, 7, 6, 2},"left")).isEqualTo("LRLLRRLLLRR");
		assertThat(ProgrammersController.solution_keypadPress(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0},"right")).isEqualTo("LLRLLRLLRL");
	}

	@Test
	public void solution_failureRate_test(){ // 실패율
		assertThat(ProgrammersController.solution_failureRate(5,new int[]{2, 1, 2, 6, 2, 4, 3, 3})).isEqualTo(new int[]{3, 4, 2, 1, 5});
		assertThat(ProgrammersController.solution_failureRate(4,new int[]{4, 4, 4, 4, 4})).isEqualTo(new int[]{4, 1, 2, 3});
		assertThat(ProgrammersController.solution_failureRate(4,new int[]{2, 2, 2, 2, 2})).isEqualTo(new int[]{2, 1, 3, 4});
	}
	
	@Test
	public void solution_secretMap_test() { // 비밀지도
		assertThat(ProgrammersController.solution_secretMap(5,new int[]{9, 20, 28, 18, 11}, new int[] {30, 1, 21, 17, 28})).isEqualTo(new String[] {"#####", "# # #", "### #", "#  ##", "#####"});
		assertThat(ProgrammersController.solution_secretMap(6,new int[]{46, 33, 33, 22, 31, 50},new int[]{27, 56, 19, 14, 14, 10})).isEqualTo(new String[] {"######","###  #","##  ##"," #### "," #####","### # "});
		
	}

	@Test
	public void solution_xn_test() { // x만큼 간격이 있는 n개의 숫자
		assertThat(ProgrammersController.solution_xn(2, 5)).isEqualTo(new long[] {2, 4, 6, 8, 10});
		assertThat(ProgrammersController.solution_xn(4, 3)).isEqualTo(new long[] {4, 8, 12});
		assertThat(ProgrammersController.solution_xn(-4, 2)).isEqualTo(new long[] {-4, -8});

	}

	@Test
	public void solution_moreSpicy_test() { // 비밀지도
		assertThat(ProgrammersController.solution_moreSpicy(new int[] {12, 1, 2, 3, 9, 10},7)).isEqualTo(2);
		assertThat(ProgrammersController.solution_moreSpicy(new int[] {1, 2, 9, 12, 10, 13},10)).isEqualTo(2);
		assertThat(ProgrammersController.solution_moreSpicy(new int[] {1, 2, 9, 12, 10, 13},160)).isEqualTo(-1);
		assertThat(ProgrammersController.solution_moreSpicy(new int[] {1, 2, 3, 4, 5, 6, 7},5)).isEqualTo(2);
	}
	
	@Test
	public void solution_openKaKao_test() { // 오픈채팅방
		assertThat(ProgrammersController.solution_openKaKao(new String[] {"Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"}))
		.isEqualTo(new String[] {"Prodo님이 들어왔습니다.", "Ryan님이 들어왔습니다.", "Prodo님이 나갔습니다.", "Prodo님이 들어왔습니다."});
	}

	@Test
	public void solution_DartGame_test() { // 다트게임
		assertThat(ProgrammersController.solution_DartGame("1D2S#10S")).isEqualTo(9);
		assertThat(ProgrammersController.solution_DartGame("1D2S0T")).isEqualTo(3);
		assertThat(ProgrammersController.solution_DartGame("1S*2T*3S")).isEqualTo(23);
		assertThat(ProgrammersController.solution_DartGame("1D#2S*3S")).isEqualTo(5);
		assertThat(ProgrammersController.solution_DartGame("1T2D3D#")).isEqualTo(-4);
		assertThat(ProgrammersController.solution_DartGame("1D2S3T*")).isEqualTo(59);
		assertThat(ProgrammersController.solution_DartGame("0S0S0S")).isEqualTo(0);
		assertThat(ProgrammersController.solution_DartGame("0D0D10S")).isEqualTo(10);
		assertThat(ProgrammersController.solution_DartGame("10D10S0D")).isEqualTo(110);
		assertThat(ProgrammersController.solution_DartGame("1S2D3T*")).isEqualTo(63);
		assertThat(ProgrammersController.solution_DartGame("1S2D*3T*")).isEqualTo(72);
		assertThat(ProgrammersController.solution_DartGame("1S*2D*3T*")).isEqualTo(74);
	}

	@Test
	public void solution_normalSquare_test() { // 멀쩡한 사각형
		assertThat(ProgrammersController.solution_normalSquare(8, 12)).isEqualTo(80); // 8X12	-(공약수4 X 나머지2X3=4)(16)		기울기2/3
		assertThat(ProgrammersController.solution_normalSquare(6, 12)).isEqualTo(60); // 6X12	-(공약수6 X 나머지1X2)(12)		기울기1/2X
		assertThat(ProgrammersController.solution_normalSquare(8, 8)).isEqualTo(56);  // 	8X8	-(공약수8 X 나머지1X1)(8)			기울기1/1X
		assertThat(ProgrammersController.solution_normalSquare(4, 12)).isEqualTo(36); // 4x12	-(공약수4 X 나머지1X3)(12)		기울기1/3X
		assertThat(ProgrammersController.solution_normalSquare(3, 12)).isEqualTo(24); // 3x12	-(공약수3 X 나머지1X4)(12)		기울기1/4X
		assertThat(ProgrammersController.solution_normalSquare(6, 8)).isEqualTo(36);  // 	6X8	-(공약수2 X 나머지3X4=9-3)(12)	기울기3/4
		assertThat(ProgrammersController.solution_normalSquare(6, 10)).isEqualTo(46); // 6X10	-(공약수2 X 나머지3X5=9-2)(14)	기울기3/5
		assertThat(ProgrammersController.solution_normalSquare(3, 5)).isEqualTo(8); // 	3X5	-(공약수1 X 나머지3X5=9-2)(7)		기울기3/5
		assertThat(ProgrammersController.solution_normalSquare(2, 5)).isEqualTo(4); // 	2X5	-(공약수1 X 나머지3X5=9-2)(7)		기울기3/5
	}

	@Test
	public void solution_addInt_test() { // 음양 더하기
		assertThat(ProgrammersController.solution_addInt(new int[]{4, 7, 12},new boolean[]{true, false, true})).isEqualTo(9);
		assertThat(ProgrammersController.solution_addInt(new int[]{1, 2, 3},new boolean[]{false, false, true})).isEqualTo(0);
	}

	@Test
	public void solution_existNum_test() { // 없는 숫자 더하기
		assertThat(ProgrammersController.solution_existNum(new int[]{1,2,3,4,6,7,8,0})).isEqualTo(14);
	}

	@Test
	public void solution_sumBetweenInt_test() { // 두정수 사이의 합
		assertThat(ProgrammersController.solution_sumBetweenInt(3,5)).isEqualTo(12);
		assertThat(ProgrammersController.solution_sumBetweenInt(3,3)).isEqualTo(3);
		assertThat(ProgrammersController.solution_sumBetweenInt(5,3)).isEqualTo(12);
		assertThat(ProgrammersController.solution_sumBetweenInt(-6,-3)).isEqualTo(-6-5-4-3);
	}

	@Test
	public void solution_strangeString_test() {
		assertThat(ProgrammersController.solution_strangeString("hello world")).isEqualTo("HeLlO WoRlD");
	}

	@Test
	public void solution_IntDesc_test() {
		assertThat(ProgrammersController.solution_IntDesc(123456)).isEqualTo(654321);
	}

	@Test
	public void solution_reverseIntArr_test() {
		assertThat(ProgrammersController.solution_reverseIntArr(123456)).isEqualTo(new int[]{6,5,4,3,2,1});
		assertThat(ProgrammersController.solution_reverseIntArr(0)).isEqualTo(new int[]{0});
	}

	@Test
	public void solution_SecretCode_test() {
		assertThat(ProgrammersController.solution_SecretCode("a",1)).isEqualTo("b");
		assertThat(ProgrammersController.solution_SecretCode("Z",25)).isEqualTo("Y");
	}

	@Test
	public void solution_findDecimal_test() {
		assertThat(ProgrammersController.solution_findDecimal(10)).isEqualTo(4);
		assertThat(ProgrammersController.solution_findDecimal(5)).isEqualTo(3);
	}
	@Test
	public void solution_three_test() {
		assertThat(ProgrammersController.solution_three(45)).isEqualTo(7);
	}

	@Test
	public void solution_MathTest_test() {
		assertThat(ProgrammersController.solution_MathTest(new int[]{1,2,3,4,5})).isEqualTo(new int[]{1});
	}

	@Test
	public void solution_addMeasure_test() {
		assertThat(ProgrammersController.solution_addMeasure(13,17)).isEqualTo(43);
	}

	@Test
	public void solution_makeDecimal_test() {
		assertThat(ProgrammersController.solution_makeDecimal(new int[]{1,2,3,4})).isEqualTo(1);
		assertThat(ProgrammersController.solution_makeDecimal(new int[]{1,2,7,6,4})).isEqualTo(4);
	}

	@Test
	public void solution_psyClothes_test() {
		assertThat(ProgrammersController.solution_gymClothes(5,new int[]{2,4},new int[]{1,3,5})).isEqualTo(5);
		assertThat(ProgrammersController.solution_gymClothes(5,new int[]{2,4},new int[]{3})).isEqualTo(4);
		assertThat(ProgrammersController.solution_gymClothes(3,new int[]{3},new int[]{1})).isEqualTo(2);
		assertThat(ProgrammersController.solution_gymClothes(3,new int[]{3},new int[]{3})).isEqualTo(3);
		assertThat(ProgrammersController.solution_gymClothes(5,new int[]{1,2,3,4,5},new int[]{})).isEqualTo(0);
		assertThat(ProgrammersController.solution_gymClothes(5,new int[]{1,2,3,4,5},new int[]{1,2,3,4,5})).isEqualTo(5);
		assertThat(ProgrammersController.solution_gymClothes(5,new int[]{1,2,3,4,5},new int[]{1,2,3,4,5})).isEqualTo(5);
		assertThat(ProgrammersController.solution_gymClothes(2,new int[]{1,2},new int[]{1,2})).isEqualTo(2);
		assertThat(ProgrammersController.solution_gymClothes(2,new int[]{},new int[]{})).isEqualTo(2);
		assertThat(ProgrammersController.solution_gymClothes(2,new int[]{},new int[]{1,2})).isEqualTo(2);
	}
}
