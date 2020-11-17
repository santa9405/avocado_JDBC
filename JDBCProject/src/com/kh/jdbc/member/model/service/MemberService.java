package com.kh.jdbc.member.model.service;

// static import : 특정 static 필드, 메소드 호출 시 클래스명을 생략할 수 있게하는 구문
import static com.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;

import com.kh.jdbc.common.JDBCTemplate;
import com.kh.jdbc.member.model.dao.MemberDAO;
import com.kh.jdbc.member.model.vo.Member;

// Service
// 1. 요청, 응답 데이터의 가공처리
// 2. 여러 DAO 메소드를 호출하여 DML 진행 후
//	    수행된 DML을 하나의 트랜잭션으로 묶어 처리하는 역할
//	  ***** 트랜잭션 처리를 위해 Connection 객체의 생성과 반환을
//			Service에서 진행해야 함!

public class MemberService {

	private MemberDAO mDAO = new MemberDAO();
	
	public int signUp(Member newMember) throws Exception {
		
		// Connection 생성 ==> JDBCTemplate에서 커넥션 얻어오기
		Connection conn = getConnection();
		
		// 요청을 처리할 수 있는 알맞은 DAO 메소드를 호출하여
		// 커넥션과 매개변수를 전달하고 결과를 반환받음
		int result = mDAO.signUp(conn, newMember);
		
		// result 값에 따를 트랜잭션 처리
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		// conn 반환
		close(conn);
		
		// DB 삽입 결과 반환
		return result;
	}

	public Member login(Member member) throws Exception {
		// 커넥션 객체 얻어오기
		Connection conn = getConnection();
		
		// 얻어온 커넥션과 매개변수를 DAO 메소드로 전달
		Member loginMember = mDAO.login(conn, member);
		
		// select는 트랜잭션 처리가 필요 없으므로 바로 커넥션 반환
		close(conn);
		
		// DB 조회 결과인 loginMember 반환
		return loginMember;
	}
	
}
