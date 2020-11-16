package com.kh.scott.model.service;

import java.util.List;

import com.kh.scott.model.dao.EmpDAO;
import com.kh.scott.model.vo.Emp;

// Service (비즈니스 로직)
// 1. 전달받은 데이터 또는 DAO의 수행 결과 데이터를 필요한 형태로 가공처리 

public class EmpService {
	
	private EmpDAO empDAO = new EmpDAO();

	public List<Emp> selectAll() {
		// 1_4. 전체 사원 정보 조회는 별도의 데이터 가공 처리가 필요 없음.
		// --> 바로 EmpDAO.selectAll을 호출하여 결과를 반환 받기.
		List<Emp> empList = empDAO.selectAll();
		
		// 1_10. DAO 반환 데이터의 가공처리가 필요 없으므로
		// 그대로 다시 반환
		return empList;
	}

	// 2. 사번으로 사원 정보 조회
	public Emp selectOne(int empNo) {
		
		// 2_4. 전달받은 empNo의 별도의 가공처리가 필요 없으므로
		// EmpDAO.selectOne(empNo)를 호출하여
		// 조회된 사원 한 명의 정보를 반환 받음
		Emp emp = empDAO.selectOne(empNo);
		
		// 2_10. 전달받은 emp를 반환
		return emp; // 메소드 반환형을 Emp로 수정
	}

	public Emp selectOne2(int empNo, String eName) {
		// 대소문자 구분없이 모두 검색 가능하게 하는 방법
		// -> 입력받은 값, SQL에서 조회된 결과 값을
		//	    모두 대문자 또는 소문자로 통일
		
		return empDAO.selectOne2(empNo, eName.toUpperCase());
										// 대문자
	}

	// 3. 새로운 사원 정보 삽입용 Service
	public int insertEmp(Emp emp) {
		
		// 3_3. 전달 받은 emp를 EmpDAO객체의 insertEmp(emp)로 전달하여
		// 결과를 반환 받음
		int result = empDAO.insertEmp(emp);

		// 3_12. DB 수행 결과를 그대로 반환
		return result; // 반환형 int로 수정
	
	}
	
	// 사원 정보 수정용 Service
	public int updateEmp(Emp temp) {
		// view에서 전달받은 temp를 DAO로 전달하고
		// 반환 받은 결과를 다시 view로 전달
		
		return empDAO.updateEmp(temp);
	}

	// 사번이 일치하는 사원 유무 판단용 Service
	public int existsEmp(int empNo) {
		// 별도의 가공 처리 없이 EmpDAO.existsEmp(empNo)를 호출하여
		// 결과를 바로 View에 반환
		return empDAO.existsEmp(empNo);
	}

	public int deleteEmp(int empNo) {
		
		return empDAO.deleteEmp(empNo);
		
	}
	
	
}
