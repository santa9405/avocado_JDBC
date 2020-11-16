package com.kh.scott.model.dao;

import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.scott.model.vo.Emp;

public class EmpDAO {

	// DB에 있는 EMP 테이블의 모든 사원 정보를 조회 DAO
	public List<Emp> selectAll() {

		// JDBC 객체 선언(java.sql 패키지에 존재하는 객체)
		Connection conn = null;
		// DB와의 연결 정보를 담은 객체
		// -> 프로그램과 DB 사이를 연결해주는 일종의 길

		Statement stmt = null;
		// Connection 객체를 통해 DB에 SQL문을 전달하고 실행하여
		// 결과를 반환받는 역할을 하는 객체

		ResultSet rset = null;
		// DB에서 SELECT 질의 성공 시 반환되는 객체
		// - SELECT문의 결과로 생성된 테이블을 담고 있으며
		// '커서(CURSOR)'를 이용해 한 행씩을 참조 할 수 있음.

		List<Emp> empList = null;

		try {
			// 1_5. JDBC 드라이버 클래스를 메모리에 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 프로그램과 DB사이의 연결 역할을 해주는 JDBC 드라이버 로드
			// 오라클 DB와 연결하기 위한 JDBC 드라이버 : "oracle.jdbc.driver.OracleDriver"
			// 해당 드라이버를 호출함으로써 메모리에 로드됨.

			// 1_6. DB와의 연결 작업 준비
			/*
			 * 연결 정보를 담을 Connection을 얻어옴. -> Connection을 얻어오기 위해서는 DriverManager가 필요
			 */
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "scott", "tiger");
			/*
			 * jdbc:oracle:thin -> JDBC 드라이버의 타입이 thin 타입임을 의미함
			 * 
			 * @127.0.0.1 -> 자신의 컴퓨터 로컬 ip (== @localhost 대체가능)
			 *
			 * :1521 -> 오라클 listener의 포트번호 (포트 : 응용프로그램이 외부와 통신하기 위한 통로)
			 * 
			 * :xe -> 오라클 DB 이름
			 * 
			 * "scott" : 접속할 사용자 이름(계정) "tiger" : 해당 계정의 비밀번호
			 * 
			 */

			// DB와의 연결과 관련된 요소들은 모두 SQLException을 발생시킬 가능성이 있다.

			// DB 접속 성공 시 conn을 출력하면 연결 객체의 주소가 출력됨.
			// 실패 시 null이 출력
			// System.out.println(conn);
			// oracle.jdbc.driver.T4CConnection@6438a396

			// 1_7. DB 접속 성공 시
			// 이를 전달하여 결과를 얻어올 객체 Statement를 생성
			String query = "SELECT * FROM EMP ORDER BY EMPNO";
			// ***** 주의사항 *****
			// JAVA 단에서 SQL 구문 작성 시 세미콜론 붙이면 큰일남

			stmt = conn.createStatement();

			rset = stmt.executeQuery(query);
			// *** Statement.executeQuery(query)
			// -> DB로 SELECT문을 전달하고 결과로 ResultSet을 반환받음.

			// 1_8. ResultSet rset에 있는 SELECT 처리 결과를
			// JAVA단에서 쉽게 사용할 수 있도록
			// List객체에 저장시키기.

			// DB와의 통신 성공 시 List 객체를 생성
			// -> 오류가 났을 경우에 메모리 손실을 방지하기 위해
			empList = new ArrayList<>();

			while (rset.next()) {
				// ResultSet.next() : 반환받은 조회 결과를
				// 커서를 이용해 순차적으로 한 행씩 접근
				// 접근한 행이 존재하면 true, 없으면 false

				// ResultSet에서 현재 접근한 행의 데이터를 꺼내는 방법
				// get[타입]("컬럼명")
				int empNo = rset.getInt("EMPNO"); // 7369
				String eName = rset.getString("ENAME"); // SMITH
				String job = rset.getString("JOB");
				int mgr = rset.getInt("MGR");
				Date hireDate = rset.getDate("HIREDATE");
				int sal = rset.getInt("SAL");
				int comm = rset.getInt("COMM");
				int deptNo = rset.getInt("DEPTNO");

				// 얻어온 행의 정보를 통해 Emp 객체를 만들어
				// empList에 추가하기

				empList.add(new Emp(empNo, eName, job, mgr, hireDate, sal, comm, deptNo));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 1_9. JDBC 관련 객체 연결 끊기(자원 반환)
			// 연결을 끊을 경우 생성 역순으로 끊어 나가기
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return empList;
	}

	// 2. 사번으로 사원 정보 조회 DAO
	public Emp selectOne(int empNo) {

		// JDBC 객체 선언(java.sql 패키지에 존재하는 객체)
		Connection conn = null;
		// DB와의 연결 정보를 담은 객체
		// -> 프로그램과 DB 사이를 연결해주는 일종의 길

		Statement stmt = null;
		// Connection 객체를 통해 DB에 SQL문을 전달하고 실행하여
		// 결과를 반환받는 역할을 하는 객체

		ResultSet rset = null;
		// DB에서 SELECT 질의 성공 시 반환되는 객체
		// - SELECT문의 결과로 생성된 테이블을 담고 있으며
		// '커서(CURSOR)'를 이용해 한 행씩을 참조 할 수 있음.

		Emp emp = null;
		// DB에서 조회한 결과를 저장할 Emp 참조변수

		try {
			// 2_5. JDBC 드라이버 로드 및 커넥션 얻어오기
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 객체를 만드는 구문과 같음

			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "scott", "tiger");

			// 2_6. 사원 한 명의 정보를 조회하기 위한 SQL 구문을 준비하고
			// Statement 객체를 생성하여
			// DB로 전달 및 수행 후 결과를 반환 받아오기
			String query = "SELECT * FROM EMP WHERE EMPNO = " + empNo;
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			// 2_7. 조회 결과가 있을 경우
			// Emp 참조변수에 조회 결과를 저장

			// 조회 결과가 0 또는 1행이 반환되므로
			// rset.next() 한 번 호출했을 때
			// true가 나오면 조회 결과가 있는 것,
			// false가 나오면 조회 결과가 없는 것
			if (rset.next()) {

				// 조회 결과에서 각 컬럼의 값을 얻어와 변수에 저장
				int empNumber = rset.getInt("EMPNO"); // 7369
				String eName = rset.getString("ENAME"); // SMITH
				String job = rset.getString("JOB");
				int mgr = rset.getInt("MGR");
				Date hireDate = rset.getDate("HIREDATE");
				int sal = rset.getInt("SAL");
				int comm = rset.getInt("COMM");
				int deptNo = rset.getInt("DEPTNO");

				// 조회 결과를 이용하여 Emp 객체를 생성
				emp = new Emp(empNumber, eName, job, mgr, hireDate, sal, comm, deptNo);
			}

			// 조회 성공 시 emp는 참조하는 객체가 있음
			// 조회 실패 시 emp는 참조하는 객체가 없음(null)

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 2_8. 사용한 JDBC 객체 반환
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 2_9. 조회 결과가 저장된 emp 반환
		return emp; // 메소드 반환형을 Emp로 수정

	}

	public Emp selectOne2(int empNo, String eName) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		Emp emp = null;

		try {
			// 2_5. JDBC 드라이버 로드 및 커넥션 얻어오기
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 객체를 만드는 구문과 같음

			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "scott", "tiger");

			// 2_6. 사원 한 명의 정보를 조회하기 위한 SQL 구문을 준비하고
			// Statement 객체를 생성하여
			// DB로 전달 및 수행 후 결과를 반환 받아오기
			String query = "SELECT * FROM EMP WHERE EMPNO = " + empNo + "AND UPPER(ENAME) = \'" + eName + "\'";
			//

			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			// 2_7. 조회 결과가 있을 경우
			// Emp 참조변수에 조회 결과를 저장

			// 조회 결과가 0 또는 1행이 반환되므로
			// rset.next() 한 번 호출했을 때
			// true가 나오면 조회 결과가 있는 것,
			// false가 나오면 조회 결과가 없는 것
			if (rset.next()) {

				// 조회 결과에서 각 컬럼의 값을 얻어와 변수에 저장
				int empNumber = rset.getInt("EMPNO"); // 7369
				String empName = rset.getString("ENAME"); // SMITH
				String job = rset.getString("JOB");
				int mgr = rset.getInt("MGR");
				Date hireDate = rset.getDate("HIREDATE");
				int sal = rset.getInt("SAL");
				int comm = rset.getInt("COMM");
				int deptNo = rset.getInt("DEPTNO");

				// 조회 결과를 이용하여 Emp 객체를 생성
				emp = new Emp(empNumber, empName, job, mgr, hireDate, sal, comm, deptNo);
			}

			// 조회 성공 시 emp는 참조하는 객체가 있음
			// 조회 실패 시 emp는 참조하는 객체가 없음(null)

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 2_8. 사용한 JDBC 객체 반환
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return emp;

	}

	// 3. 새로운 사원 정보 삽입용 Service
	public int insertEmp(Emp emp) {

		// 3_4. JDBC 드라이버 등록 및 DB 연결 관련 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		/*
		 * PreparedStatement
		 * 
		 * - Statement의 자식으로 좀 더 향상된 기능을 제공함 - ? (위치 홀더)를 이용하여 SQL에 작성되는 리터럴 값을 동적으로 작성함
		 * 
		 * - 코드 안정성과 가독성이 증가함 - 코드길이가 늘어나는 단점이 있음
		 */

		try {

			// 3_5. JDBC 드라이버 로드 및 커넥션 얻어오기
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 객체를 만드는 구문과 같음

			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "scott", "tiger");

			// 3_6. 자동 커밋 방지
			// JDBC에서 DML 구문 수행 시 별도 조작이 없으면
			// 한 행이 수행되자마자 COMMIT이 수행됨
			conn.setAutoCommit(false);
			// -> 한 행 수행 시 자동 COMMIT이 되지 않게 함
			// 단, 추후 COMMIT, ROLLBACK 없이
			// conn.close()가 수행되면
			// 모든 내용이 COMMIT됨

			// 3_7. SQL 작성 후 PreparedStatement 객체 생성
			String query = "INSERT INTO EMP VALUES(?, ?, ?, ?, SYSDATE, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);

			// 3_8. SQL구문의 ?(위치 홀더)에 알맞은 값 대입
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.geteName());
			pstmt.setString(3, emp.getJob());
			pstmt.setInt(4, emp.getMgr());
			pstmt.setInt(5, emp.getSal());
			pstmt.setInt(6, emp.getComm());
			pstmt.setInt(7, emp.getDeptNo());

			// 3_9. SQL 구문 수행 후 결과를 전달받음
			// DML 구문 수행 시 executeUpdate() 호출

			// DB에서 DML 구문 수행 시 DML 구문 수행이 성공한 행의 개수를 반환
			result = pstmt.executeUpdate();
			// executeUpdate() : DML의 성공한 행의 개수를 반환 == 0||1
			// executeQeury() : SELECT의 조회 결과인 ResultSet을 반환

			// 트랜잭션 처리(commit, rollback 처리)
			if (result > 0)
				conn.commit();
			else
				conn.rollback();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 3_10. 사용한 JDBC 객체 반환
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 3_11. DML 수행 결과(result)를 반환
		return result; // 반환형 int로 수정

	}

	// 사원 정보 수정용 DAO
	public int updateEmp(Emp temp) {

		// JDBC 드라이버 등록 및 DB 연결 관련 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0; // 결과 저장용 변수

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 객체를 만드는 구문과 같음
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "scott", "tiger");
			conn.setAutoCommit(false); // DML 자동 커밋 방지

			String query = "UPDATE EMP SET JOB =?, SAL=?, COMM=? WHERE EMPNO=?";
			pstmt = conn.prepareStatement(query); // 4개짜리 전용 교통수단이 만들어짐

			pstmt.setString(1, temp.getJob());
			pstmt.setInt(2, temp.getSal());
			pstmt.setInt(3, temp.getComm());
			pstmt.setInt(4, temp.getEmpNo());

			result = pstmt.executeUpdate();

			if (result > 0)
				conn.commit();
			else
				conn.rollback();

		} catch (Exception e) {
			e.printStackTrace();
		} finally { // JDBC 객체 반환
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public int existsEmp(int empNo) {
		// JDBC 관련 변수 선언 및 결과 저장 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		int result = 0;

		try {
			// JDBC드라이버 로드 및 커넥션 얻어오기
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 객체를 만드는 구문과 같음
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "scott", "tiger");

			stmt = conn.createStatement();

			// SQL 작성 및 Statement 객체 생성
			String query = "SELECT COUNT(*) FROM EMP WHERE EMPNO = " + empNo;

			// SQL 수행 및 결과 반환
			rset = stmt.executeQuery(query);

			// 조회 결과가 있을 경우 해당 결과를 result에 저장
			if (rset.next()) {
				result = rset.getInt(1);
				// 조회 결과 컬럼 인덱스
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// 사용한 JDBC 객체 반환
			try {
				if (rset != null)
					rset.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public int deleteEmp(int empNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0; // 결과 저장용 변수

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver"); // 객체를 만드는 구문과 같음
				conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "scott", "tiger");
				conn.setAutoCommit(false);
				
				String query = "DELETE FROM EMP WHERE EMPNO = ?";
				pstmt = conn.prepareStatement(query);
				
				pstmt.setInt(1, empNo);
				
				result = pstmt.executeUpdate();

				if (result > 0)		conn.commit();
				else				conn.rollback();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				// 사용한 JDBC 객체 반환
				try {
					if (pstmt != null)		pstmt.close();
					if (conn != null)		conn.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		return result;
	}
}