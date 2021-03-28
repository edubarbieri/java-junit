package br.com.edubarbieri.services;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {

	public static int contador = 0;
	
	@Test
	public void init() {
		contador = 1;
	}
	
	@Test
	public void verify() {
		assertEquals(1, contador);
	}
}
