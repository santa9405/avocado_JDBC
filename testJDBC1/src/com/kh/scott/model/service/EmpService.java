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

}
