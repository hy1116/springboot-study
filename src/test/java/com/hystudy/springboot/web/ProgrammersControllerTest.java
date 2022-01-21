package com.hystudy.springboot.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ProgrammersControllerTest {
	@Test
	public void solution_RepostResult_test() { // 신고 결과 받기 
		ProgrammersController pc = new ProgrammersController();
		
		assertThat(pc.solution_RepostResult(
				new String[]{"muzi", "frodo", "apeach", "neo"}, 
				new String[]{"muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi"}, 
				2
		)).isEqualTo(new int[]{2,1,1,0});
		assertThat(pc.solution_RepostResult(
				new String[]{"con", "ryan"}, 
				new String[]{"ryan con", "ryan con", "ryan con", "ryan con"}, 
				3
		)).isEqualTo(new int[]{0,0});
	}
	
	@Test
	public void solution_TextCompression_test() { // 문자열 압축
		ProgrammersController pc = new ProgrammersController();
		
		assertThat(pc.solution_TextCompression("aabbaccc")).isEqualTo(7);
		assertThat(pc.solution_TextCompression("ababcdcdababcdcd")).isEqualTo(9);
		assertThat(pc.solution_TextCompression("abcabcdede")).isEqualTo(8);
		assertThat(pc.solution_TextCompression("abcabcabcabcdededededede")).isEqualTo(14);
		assertThat(pc.solution_TextCompression("xababcdcdababcdcd")).isEqualTo(17);
	}
	
	@Test
	public void solution_keypadPress_test() { // 문자열 압축
		ProgrammersController pc = new ProgrammersController();
		
		assertThat(pc.solution_keypadPress(new int[] {1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5},"right")).isEqualTo("LRLLLRLLRRL");
		assertThat(pc.solution_keypadPress(new int[] {7, 0, 8, 2, 8, 3, 1, 5, 7, 6, 2},"left")).isEqualTo("LRLLRRLLLRR");
		assertThat(pc.solution_keypadPress(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0},"right")).isEqualTo("LLRLLRLLRL");
	}
}
