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
	public void solution_normalSquare_test() { // 다트게임임
		assertThat(ProgrammersController.solution_normalSquare(8, 12)).isEqualTo(80); // 8X12-(4X6)   1/3
		assertThat(ProgrammersController.solution_normalSquare(6, 12)).isEqualTo(60); // 6X12-(6X2)   1/2
		assertThat(ProgrammersController.solution_normalSquare(8, 8)).isEqualTo(56); // 8X8-(8X1)     1/1
		assertThat(ProgrammersController.solution_normalSquare(4, 12)).isEqualTo(36); // 4x12-(4X3)   1/3
		assertThat(ProgrammersController.solution_normalSquare(6, 8)).isEqualTo(36); // 6X8-(2X6)     1/4
	}

	@Test
	public void solution_digit_test() { // 다트게임임
		//assertThat(ProgrammersController.solution_digit(123)).isEqualTo(6);
		//assertThat(ProgrammersController.solution_digit(987)).isEqualTo(24);
	}

	@Test
	public void solution_BasicString_test() {
		//assertThat(ProgrammersController.solution_BasicString("1234")).isEqualTo(true);
		//assertThat(ProgrammersController.solution_BasicString("1q35")).isEqualTo(false);
	}

	@Test
	public void solution_harshad_test() {
		//assertThat(ProgrammersController.solution_harshad(10)).isEqualTo(true);
		//assertThat(ProgrammersController.solution_harshad(11)).isEqualTo(false);
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
		//assertThat(ProgrammersController.solution_reverseIntArr(0)).isEqualTo(new int[]{0});
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

	@Test
	public void solution_124Country_test() {
		assertThat(ProgrammersController.solution_124Country(1)).isEqualTo("1");
		assertThat(ProgrammersController.solution_124Country(2)).isEqualTo("2");
		assertThat(ProgrammersController.solution_124Country(3)).isEqualTo("4");
		assertThat(ProgrammersController.solution_124Country(4)).isEqualTo("11");
		assertThat(ProgrammersController.solution_124Country(5)).isEqualTo("12");
	}

	@Test
	public void solution_keepDistance_test() {
		assertThat(ProgrammersController.solution_keepDistance(new String[][]{{"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"}, {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"}, {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"}, {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"}, {"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}})).isEqualTo(new int[]{1, 0, 1, 1, 1});
	}

	@Test
	public void solution_lcm_test() {
		assertThat(ProgrammersController.solution_lcm(new int[]{2,4,3,6})).isEqualTo(12);
		assertThat(ProgrammersController.solution_lcm(new int[]{4})).isEqualTo(4);
		assertThat(ProgrammersController.solution_lcm(new int[]{12,12,5})).isEqualTo(60);
	}

	@Test
	public void solution_jadenCase_test() {
		assertThat(ProgrammersController.solution_jadenCase("3people unFollowed me ")).isEqualTo("3people Unfollowed Me ");
	}

	@Test
	public void solution_max_int_test(){
		assertThat(ProgrammersController.solution_max_int(new int[]{344, 34})).isEqualTo("34434");
		assertThat(ProgrammersController.solution_max_int(new int[]{3, 30, 34, 5, 9,300})).isEqualTo("9534330300");
		assertThat(ProgrammersController.solution_max_int(new int[]{343, 344, 34})).isEqualTo("34434343");
		assertThat(ProgrammersController.solution_max_int(new int[]{1, 10, 100, 1000})).isEqualTo("1101001000");
	}

	@Test
	public void solution_matrix_rotation_test(){
		assertThat(ProgrammersController.solution_matrix_rotation(6,6,new int[][]{{2, 2, 5, 4},{3, 3, 6, 6}, {5, 1, 6, 3}})).isEqualTo(new int[]{8,10,25});
		assertThat(ProgrammersController.solution_matrix_rotation(3,5,new int[][]{{1,1,2,2},{2,3,3,4}, {1,2,3,4},{1,1,3,4},{2,2,3,5}})).isEqualTo(new int[]{1,8,1,1,3});
		assertThat(ProgrammersController.solution_matrix_rotation(3,4,new int[][]{{1,1,2,2},{1,2,2,3}, {1,3,2,4},{2,2,3,4}})).isEqualTo(new int[]{1,1,1,4});
		assertThat(ProgrammersController.solution_matrix_rotation(5,3,new int[][]{{2,1,3,3}})).isEqualTo(new int[]{4});
		assertThat(ProgrammersController.solution_matrix_rotation(4,3,new int[][]{{1,2,3,3},{3,1,4,3}})).isEqualTo(new int[]{2,6});
		//assertThat(ProgrammersController.solution_matrix_rotation(100,97,new int[][]{{1,1,2,2},{1,1,2,2},{1,1,100,97}})).isEqualTo(new int[]{1,1,2});
	}

	@Test
	public void test_test(){
		assertThat(ProgrammersController.test()).isEqualTo("");
	}

	@Test
	public void solution_expression_number_test(){
		assertThat(ProgrammersController.solution_expression_number(15)).isEqualTo(4);
	}

	@Test
	public void solution_fibonacci_test(){
		assertThat(ProgrammersController.solution_fibonacci(3)).isEqualTo(2);
	}

	@Test
	public void solution_next_max_int_test(){
		assertThat(ProgrammersController.solution_next_max_int(78)).isEqualTo(83);
		assertThat(ProgrammersController.solution_next_max_int(15)).isEqualTo(23);
	}

	@Test
	public void solution_max_square_test(){
		assertThat(ProgrammersController.solution_max_square(new int[][]{{0,1,1,1},{0,1,1,1},{1,1,1,1},{0,0,1,0}})).isEqualTo(9);
		assertThat(ProgrammersController.solution_max_square(new int[][]{{0,0,1,1}, {1,1,1,1}})).isEqualTo(4);
	}

	@Test
	public void solution_coloringbook_test(){
		assertThat(ProgrammersController.solution_coloringbook(6,4,new int[][]{{1, 1, 1, 0}, {1, 1, 1, 0}, {0, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}})).isEqualTo(new int[]{2, 6});
	}

	@Test
	public void solution_english_word_chain_game_test(){
		assertThat(ProgrammersController.solution_english_word_chain_game(3,new String[]{"tank", "kick", "know", "wheel", "land", "dream", "mother", "robot", "tank"})).isEqualTo(new int[]{3, 3});
	}

	@Test
	public void solution_target_number_test(){
		assertThat(ProgrammersController.solution_target_number(new int[]{1,1,1,1,1},3)).isEqualTo(5);
		assertThat(ProgrammersController.solution_target_number(new int[]{4,1,2,1},4)).isEqualTo(2);
	}

	@Test
	public void solution_correct_bracket_test(){
		assertThat(ProgrammersController.solution_correct_bracket("()")).isEqualTo(true);
	}

	@Test
	public void solution_matrix_multiple_test(){
		assertThat(ProgrammersController.solution_matrix_multiple(new int[][]{{0,0,0},{0,0,0}},new int[][]{{0,0},{0,0},{0,0}})).isEqualTo(new int[][]{{0,0},{0,0}});
		assertThat(ProgrammersController.solution_matrix_multiple(new int[][]{{1, 4}, {3, 2}, {4, 1}},new int[][]{{3, 3}, {3, 3}})).isEqualTo(new int[][]{{15, 15}, {15, 15}, {15, 15}});
	}

	@Test
	public void solution_eat_the_ground_test(){
		assertThat(ProgrammersController.solution_eat_the_ground(new int[][]{{1, 2, 3, 5}, {5, 6, 7, 18}, {4, 3, 2, 1}})).isEqualTo(25);
	}

	@Test
	public void solution_repeat_binary_test(){
		assertThat(ProgrammersController.solution_repeat_binary("110010101001")).isEqualTo(new int[]{3,8});
		assertThat(ProgrammersController.solution_repeat_binary("1111111")).isEqualTo(new int[]{4,1});
	}

	@Test
	public void solution_expect_tournament_test(){
		assertThat(ProgrammersController.solution_expect_tournament(4, 1, 4)).isEqualTo(2);
		assertThat(ProgrammersController.solution_expect_tournament(8, 4, 7)).isEqualTo(3);
		assertThat(ProgrammersController.solution_expect_tournament(8, 1, 2)).isEqualTo(1);
		assertThat(ProgrammersController.solution_expect_tournament(8, 2, 3)).isEqualTo(2);
		assertThat(ProgrammersController.solution_expect_tournament(16, 1, 16)).isEqualTo(4);
		assertThat(ProgrammersController.solution_expect_tournament(16, 8, 9)).isEqualTo(4);
		assertThat(ProgrammersController.solution_expect_tournament(16, 9, 12)).isEqualTo(2);
	}
}
