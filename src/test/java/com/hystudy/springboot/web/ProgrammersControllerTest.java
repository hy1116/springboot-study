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
	public void solution_DartGame_test() { // 다트게임임		assertThat(ProgrammersController.solution_DartGame("1S2D*3T")).isEqualTo(37);
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
	public void solution_normalSquare_test() { // 다트게임임		assertThat(ProgrammersController.solution_DartGame("1S2D*3T")).isEqualTo(37);
		assertThat(ProgrammersController.solution_normalSquare(8, 12)).isEqualTo(80);
	}
}
