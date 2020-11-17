package com.kh.jdbc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class TestProperties {
	public static void main(String[] args) {
		
		// Map<K, V> --> 키와 값에 모든 자료형이 가능
		// Map<String, String>
		// == Properties
		
		// Properties 클래스
		// 키와 값이 모두 String인 컬렉션
		// + 외부 파일 입출력이 간단하게 구현되어 있음
		
		Properties prop = new Properties();
		
		/*
		// 값 세팅
		prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
		prop.setProperty("url", "jdbc:oracle:thin:@localhost:1521:xe");
		prop.setProperty("user", "jdbc");
		prop.setProperty("password", "jdbc");
		
		// 세팅한 Properties 객체를 외부 XML파일로 출력
		// XML 장점 : 모든 프로그래밍 언어에서 입출력 가능한 파일
		try {
			prop.storeToXML(new FileOutputStream("driver.xml"), 
							"driver information");
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
		
		// 꺼내 쓰는 법
		
		// 외부 xml 파일 읽어오기
		try {
			prop.loadFromXML(new FileInputStream("driver.xml"));
			
			System.out.println( prop.getProperty("driver") );
			System.out.println( prop.getProperty("url") );
			System.out.println( prop.getProperty("user") );
			System.out.println( prop.getProperty("password") );
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
