package com.hystudy.springboot.web;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CodilityControllerTest {
	@Test
	public void solution_binary_gap(){
		assertThat(CodilityController.solution_binary_gap(1)).isEqualTo(0);
		assertThat(CodilityController.solution_binary_gap(9)).isEqualTo(3);
	}
}
