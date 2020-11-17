package com.kh.jdbc.member.model.dao;

import static com.kh.jdbc.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.kh.jdbc.member.model.vo.Member;

public class MemberDAO {
	
	// DAO에서 자주 사용하는 JDBC객체 참조 변소를 멤버 변수로 선언 --> 재활용
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	// 외부 xml파일에 작성된 SQL 구문을 읽어들일 Properties 변수 선언
	private Properties prop = null;
	
	public MemberDAO() {
		// 기본 생성자를 통한 객체 생성 시 xml파일을 읽어오게 함
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("member-query.xml"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/** 회원 가입용 DAO
	 * @param conn
	 * @param newMember
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Connection conn, Member newMember) throws Exception{
		/* 이전 DAO에서 하던 역할
		 * 1) JDBC 드라이버 등록
		 * 2) DB 연결 커넥션 생성
		 * 3) SQL 수행
		 * 4) 트랜잭션 처리
		 * 5) JDBC 객체 반환
		 * 6) SQL 수행 결과 반환
		 * 
		 * --> 3, 5, 6번 수행
		 * */
		
		int result = 0; // DML 수행 결과 저장용 변수
		
		try {
			String query = prop.getProperty("signUp");
			
			// SQL 구문을 DB 전달할 준비
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newMember.getMemId());
			
			pstmt.setString(2, newMember.getMemPw());
			
			pstmt.setString(3, newMember.getMemNm());
			
			pstmt.setString(4, newMember.getPhone());
			
			pstmt.setString(5, newMember.getGender() + "");
			// char 데이터에 빈문자열("")을 더하여 String 형태로 형변환
			
			// SQL 수행 후 결과를 반환 받아 result에 저장
			result = pstmt.executeUpdate();
			
		}finally {
			// catch가 없어도 finally 작성 가능
			// DB 자원 반환
			close(pstmt);
		}

		return result;
	}


	public Member login(Connection conn, Member member) throws Exception {
		
		Member loginMember = null; // 로그인된 회원 정보를 저장할 임시 변수
		
		try {
			
			// member-query.xml 파일에서 키값이 "login"인 태그의 값을 얻어옴
			String query = prop.getProperty("login");
			
			// DB 전달을 위한 pstmt 생성
			pstmt = conn.prepareStatement(query);
			
			// SQL 구문의 위치홀더에 알맞는 값을 배치
			pstmt.setNString(1, member.getMemId());
			pstmt.setNString(2, member.getMemPw());
			
			// SQL 수행 후 결과를 ResultSet으로 반환 받음
			rset = pstmt.executeQuery();
			
			// 조회 결과가 있는지 확인하는 if문 작성
			if(rset.next()) {
				// 조회 결과가 있을 경우 Member 객체를 만들어 로그인 정보를 저장
				loginMember 
				= new Member(rset.getInt("MEM_NO"), 
						rset.getString("MEM_ID"), 
						rset.getString("MEM_NM"), 
						rset.getString("PHONE"), 
						rset.getString("GENDER").charAt(0), 
						rset.getDate("HIRE_DT"));
			}
		}finally {
			// DB 자원 반환
			close(rset);
			close(pstmt);
		}
		
		// 조회 결과가 담긴 loginMember 반환
		return loginMember;
	}

}
