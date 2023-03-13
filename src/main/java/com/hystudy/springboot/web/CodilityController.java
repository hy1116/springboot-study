package com.hystudy.springboot.web;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CodilityController {
	public static int solution_binary_gap(int N) {
		int result = 0;
		String[] s = Integer.toBinaryString(N).split("");
		int index = -1;
		for(String str : s){
			if("1".equals(str)){
				if(index!=-1&&index < result){
					result = index;
				}
				index = 0;
			} else if("0".equals(str)){
				index ++;
			}
		}
		return result;
	}
}
