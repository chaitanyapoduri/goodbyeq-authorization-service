package com.goodbyeq.user.service.impl;

import java.security.SecureRandom;

public class RandomGen {

	public static void main(String[] args) {
		 SecureRandom random = new SecureRandom();
	      int i = random.nextInt(99999999);
	      System.out.println(i);
	      
	}

}
