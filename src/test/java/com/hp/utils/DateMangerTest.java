package com.hp.utils;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class DateMangerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetFirstSundayOfMonth() {
		int date= DateManger.getFirstSundayOfMonth(2017, 8);
		System.out.println(date);
	}

}
