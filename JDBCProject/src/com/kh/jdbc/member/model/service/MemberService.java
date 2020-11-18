package com.kh.jdbc.member.model.service;

// static import : 특정 static 필드, 메소드 호출 시 클래스명을 생략할 수 있게하는 구문
import static com.kh.jdbc.common.JDBCTemplate.close;
import static com.kh.jdbc.common.JDBCTemplate.commit;
import static com.kh.jdbc.common.JDBCTemplate.getConnection;
import static com.kh.jdbc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

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

	/** 검색어 포함 이름 검색 Service
	 * @param name
	 * @return list
	 * @throws Exception
	 */
	public List<Member> selectMemberName(String name) throws Exception {
//	 	* 1. Connection 생성
		Connection conn = getConnection();
		
//	 	* 2. DAO에서 알맞은 메소드 호출 후 결과 반환 받기
		List<Member> list = mDAO.selectMemberName(conn, name);
		
//	 	* 3. Connection 반환
		close(conn);
		
		// ** 전화번호 가운데를 **** 로 치환하기
		// 010-1234-1234 --> 010-****-1234
		// 1) split() 사용
		for(Member mem : list) {
			String[] arr = mem.getPhone().split("-");
			mem.setPhone( arr[0] + "-****-" + arr[2]);
		}
		
		
//	    * 4. DAO 호출 결과를 그대로 View로 반환
		return list;
		
	}

	public List<Member> selectGender(char gender)  throws Exception{
		Connection conn = getConnection();
		
		List<Member> list = mDAO.selectGender(conn, gender);
		
		close(conn);
		
		for(Member mem : list) {
//			String[] arr = mem.getPhone().split("-");
//			mem.setPhone(arr[2]);
//		}
		
		String ph = mem.getPhone();
		mem.setPhone (ph.substring( ph.lastIndexOf("-")+1 , ph.length() ) );
		//			  	start <=		    str			  	  < end
		//				010-1234-5678 마지막 "-"+1 부터 길이까지
		}
		return list;
	}

	/** 내 정보 수정 Service
	 * @param upMember
	 * @return result
	 * @throws Exception
	 */
	public int updateMyInfo(Member upMember) throws Exception {
		// Connection 생성
		Connection conn = getConnection();
		
		// 생성한 커넥션과 매개변수 upMember를 DAO메소드의 매개변수로 전달하고
		// 결과를 반환 받음
		int result = mDAO.updateMyInfo(conn, upMember);
		
		// UPDATE(DML)을 진행 하였으므로 트랜잭션 처리
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 비밀번호 변경 Service
	 * @param upMember
	 * @param newPw
	 * @return result
	 * @throws Exception
	 */
	public int updatePw(Member upMember, String newPw) throws Exception {
		// Connection 생성
		Connection conn = getConnection();
		
		// 생성한 커넥션과 매개변수 upMember를 DAO메소드의 매개변수로 전달하고
		// 결과를 반환 받음
		int result = mDAO.updatePw(conn, upMember, newPw);
		
		// UPDATE(DML)을 진행 하였으므로 트랜잭션 처리
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 회원 탈퇴 Service
	 * @param upMember
	 * @return result
	 * @throws Exception
	 */
	public int updateSecessionMember(Member upMember) throws Exception {
		// Connection 생성
		Connection conn = getConnection();
		
		// 생성한 커넥션과 매개변수 upMember를 DAO메소드의 매개변수로 전달하고
		// 결과를 반환 받음
		int result = mDAO.updateSecessionMember(conn, upMember);
		
		// UPDATE(DML)을 진행 하였으므로 트랜잭션 처리
		if(result > 0)	commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}
	
}
