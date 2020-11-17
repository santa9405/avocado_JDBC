package com.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	// 1. 반복되는 Connection 객체의 생성을 간소화
	// 2. 트랜잭션 처리, close()처리의 간소화
	
	// ** 싱글톤(SingleTon) 패턴
	// 프로그램 구동 시 메모리 상에 딱 하나의 객체만 기록되게 하는 디자인 패턴
	// 대표적인 예시로 java.lang.Math가 있다.
	
	// 모든 필드, 메소드를 static으로 선언하여
	// 프로그램 구동 시 static 메모리 영역에
	// 모든 클래스 내용을 로드하여 하나의 객체 모양을 띄게 함
	
	// 하나의 공용 커넥션 참조 변수 선언
	private static Connection conn = null;
	// private 선언 이유 : 직접 접근 시 null 값이나
	//					닫혀진 커넥션 객체를 가져갈 수 있는 확률이 있어서 이를 미연에 차단
	
	// 해당 클래스의 내용은 모두 static에서 객체의 모양을 이루기 때문에
	// 추가적인 객체 생성을 막기위해 private 사용
	private JDBCTemplate() { }
	
	// DB 연결을 위한 Connection 객체를 간접적으로 얻어가는 메소드를 생성
	public static Connection getConnection() {
		try {
			if(conn == null || conn.isClosed()) {
				// 커넥션 객체가 없거나, 이전 사용자에 의해 닫혀진 경우
				// --> 새로운 커넥션 생성하여 반환
				
				/* DB 연결을 위한 Driver 정보나
				 * DB URL, 계정 정보는 상황에 따라 언제든지 바뀔 가능성이 높음
				 *  --> 코드를 직접 수정하게 되는 경우 유지보수에 좋지 않음
				 *  --> 외부 파일에 해당 정보들을 기입하여 읽어오는 형태로 변환
				 *    ---> 내부 코드에 변화가 없으므로 재컴파일이 필요없음
				 *        ----> 유지보수성 향상
				 * */
				Properties prop = new Properties();
				
				// driver.xml 파일에 작성된 entry를 prop에 저장
				prop.loadFromXML(new FileInputStream("driver.xml"));
				
				// JDBC 드라이버 등록 및 커넥션 얻어오기
				Class.forName(prop.getProperty("driver"));
				conn = DriverManager.getConnection(prop.getProperty("url"),
												   prop.getProperty("user"),
												   prop.getProperty("password"));
				
				// 자동 commit 비활성화
				conn.setAutoCommit(false);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// 트랜잭션 처리(commit, rollback)도 공통적으로 사용함
	// static으로 미리 선언하여 코드길이 감소 + 재사용성
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				// 참조하고 있는 커넥션이 닫혀있지 않은 경우
				conn.commit();
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				// 참조하고 있는 커넥션이 닫혀있지 않은 경우
				conn.rollback();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// DB 연결 자원 반환 구문도 static으로 작성
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				// 참조하고 있는 커넥션이 닫혀있지 않은 경우
				conn.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				// 참조하고 있는 커넥션이 닫혀있지 않은 경우
				rset.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Statement, PreparedStatement 두 객체를 반환하는 메소드
	// 어떻게? PreparedStatement는 Statement의 자식이므로
	// 매개변수 stmt에 다형성이 적용되어 자식 객체인 PreparedStatement를 참조 가능 
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				// 참조하고 있는 커넥션이 닫혀있지 않은 경우
				stmt.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
