package br.com.edubarbieri.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadorMockTest {

	
	@Mock
	private Calculadora mockCalc;
	
	@Spy
	private Calculadora spyCalc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldShowDifferenceBetweenMockSpy() {
		when(mockCalc.sum(10, 20)).thenReturn(30);
		when(mockCalc.sum(10, 21)).thenCallRealMethod();
//		when(spyCalc.sum(10, 20)).thenReturn(30);
		doReturn(30).when(spyCalc).sum(10, 20);
		doNothing().when(spyCalc).print();
		
		System.out.println(mockCalc.sum(10, 21));
		mockCalc.print();
		System.out.println(spyCalc.sum(10, 21));
		spyCalc.print();
		
	}
}
