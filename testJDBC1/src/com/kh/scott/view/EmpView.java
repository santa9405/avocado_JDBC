package com.kh.scott.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.kh.scott.model.service.EmpService;
import com.kh.scott.model.vo.Emp;

// View
// - 사용자의 요청을 입력받고, 응답을 출력하는 부분

public class EmpView {

	private Scanner sc = new Scanner(System.in);
	private EmpService empService = new EmpService();

	// 메인메뉴
	public void displayMain() {
		int sel = 0;

		do {
			try {

				System.out.println();
				System.out.println("====================================");
				System.out.println("[Main Menu]");
				System.out.println("1. 전체 사원 정보 조회");
				System.out.println("2. 사번으로 사원 정보 조회");
				System.out.println("3. 새로운 사원 정보 추가");
				System.out.println("4. 사번으로 사원 정보 수정");
				System.out.println("5. 사번으로 사원 정보 삭제");
				System.out.println("6. 사번, 이름이 모두 일치하는 사원 정보 조회");
				System.out.println("0. 프로그램 종료");
				System.out.println("====================================");

				System.out.print("메뉴 선택 : ");
				sel = sc.nextInt();
				System.out.println();

				switch (sel) {
				case 1: selectAll(); break; // 1-1번 같은 클래스에 있는 selectAll() 호출
				case 2: selectOne(); break; // 2_1번 같은 클래스에 있는 selectOne() 호출
				case 3: insertEmp(); break;
				case 4:	updateEmp(); break;
				case 5:	deleteEmp(); break;
				case 6: selectOne2(); break;
				case 0:	System.out.println("프로그램이 종로됐습니다.");	break;
				default: System.out.println("잘못 입력하셨습니다.");
				}

			} catch (InputMismatchException e) {
				System.out.println("숫자만 입력해 주세요.");
				sel = -1;
				sc.nextLine();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (sel != 0);
	}

	// 1_2. 전체 사원 정보를 출력하는 View
	private void selectAll() {
		System.out.println("[전체 사원 정보 조회]");

		// 1_3. EmpService.selectAll()을 호출하여
		// 전체 사원 정보가 담겨있는 List를 반환받기
		List<Emp> empList = empService.selectAll();

		// 1_11. 전체 사원 조회 결과 출력
		if (empList == null) {
			// DB 연결간 문제 발생으로 empList 객체가 생성되지 않은 경우
			System.out.println("전체 사원 정보 조회 과정에서 오류가 발생했습니다.");
		}
		if (empList.isEmpty()) {
			// DB 연결, 조회는 성공했으나 조회 결과가 없는 경우
			System.out.println("조회 결과가 없습니다.");
		} else {
			// 조회 성공 시
			int i = 1;
			for (Emp e : empList) {
				System.out.println(i + "" + e);
				i++;
			}
		}
	}

	// 2_2. 사번을 입력받아 사원 한 명의 정보를 조회하는 View
	private void selectOne() {
		System.out.println("[사번으로 사원 검색]");
		
		System.out.print("사번 입력 : ");
		int empNo = sc.nextInt();
		sc.nextLine();
		
		// 2_3. 입력받은 사번을 매개변수로 하는
		// EmpService.selectOnt(empNo)를 호출하여
		// 사원 한 명의 정보를 반환 받음
		Emp emp = empService.selectOne(empNo);
		
		// 2_11. 조회 결과가 있을 경우에 정보 조회
		// 없을 경우에는 "조회 결과가 없습니다." 출력
		if(emp == null) {
			System.out.println("조회 결과가 없습니다.");
		}else {
			System.out.println("사번 : " + emp.getEmpNo());
			System.out.println("이름 : " + emp.geteName());
			System.out.println("직책 : " + emp.getJob());
			System.out.println("상사 : " + emp.getMgr());
			System.out.println("입사일 : " + emp.getHireDate());
			System.out.println("급여 : " + emp.getSal());
			System.out.println("커미션 : " + emp.getComm());
			System.out.println("부서번호 : " + emp.getDeptNo());
		}

	}
	
	private void selectOne2() {
		System.out.println("[사번, 이름으로 사원 검색]");
		
		System.out.print("사번 입력 : ");
		int empNo = sc.nextInt();
		sc.nextLine();
		
		System.out.print("이름 입력 : ");
		String eName = sc.nextLine();
		
		Emp emp = empService.selectOne2(empNo, eName.toUpperCase());
		
		if(emp == null) {
			System.out.println("조회 결과가 없습니다.");
		}else {
			System.out.println("사번 : " + emp.getEmpNo());
			System.out.println("이름 : " + emp.geteName());
			System.out.println("직책 : " + emp.getJob());
			System.out.println("상사 : " + emp.getMgr());
			System.out.println("입사일 : " + emp.getHireDate());
			System.out.println("급여 : " + emp.getSal());
			System.out.println("커미션 : " + emp.getComm());
			System.out.println("부서번호 : " + emp.getDeptNo());
		}
		
	}
	
	// 3. 새로운 사원 정보 삽입용 View
	private void insertEmp() {
		System.out.println("[사원 정보 추가]");
		
		System.out.print("사번 : ");
		int empNo = sc.nextInt();
		sc.nextLine(); // 개행문자 제거
		
		System.out.print("이름 : ");
		String eName = sc.nextLine();
		
		System.out.print("직책 : ");
		String job = sc.nextLine();
		
		System.out.print("직속 상사 사번 : ");
		int mgr = sc.nextInt();
		sc.nextLine();
		
		// HIREDATE는 insert 구문에서 SYSDATE로 작성
		
		System.out.print("급여 : ");
		int sal = sc.nextInt();
		
		System.out.print("커미션 : ");
		int comm = sc.nextInt();
		
		System.out.print("부서 번호 : ");
		int deptNo = sc.nextInt();
		sc.nextLine();
		
		// 3_1. 입력받은 사원 정보를 하나의 Emp 객체에 저장할 수 있도록
		// Emp 클래스에 생성자를 새로 생성한 후 객체 생성
		Emp emp = new Emp(empNo, eName, job, mgr, sal, comm, deptNo);
		
		// 3_2. EmpService객체의 insertEmp(emp) 메소드를 호출하여
		// 데이터를 DB 삽입한 후 결과를 반환 받음
		int result = empService.insertEmp(emp);
		
		// 3_13. 삽입 결과에 따라 출력하기
		if(result > 0) {
			System.out.println("사원 정보 삽입 성공!!!");
		}else {
			System.out.println("사원 정보 삽입 실패...");
		}
		
	}
	
	// 4. 사번으로 사원 정보 수정
	private void updateEmp() {
		System.out.println("[사원 정보 수정]");
		
		// 1단계 : 사번을 입력받아 해당 사번을 가진 사원이 있는지 확인
		System.out.print("정보를 수정할 사원의 사번 입력 : ");
		int empNo = sc.nextInt();
		sc.nextLine();
		
		// selectOne(empNo) 재활용
		//Emp emp = empService.selectOne(empNo);
		
		// existsEmp(empNo) 메소드를 새로 작성
		int check = empService.existsEmp(empNo);
		
		// 2단계 : 1단계의 결과로 있다고 판별된 경우
	    //       수정할 정보를 입력받은 후 DB 내용을 수정
		//if(emp != null) {
		if(check > 0) {	
			// 수정할 정보 입력받기
			System.out.print("직책 : ");
			String job = sc.nextLine();
			
			System.out.print("급여 : ");
			int sal = sc.nextInt();
			
			System.out.print("커미션 : ");
			int comm = sc.nextInt();
			sc.nextLine();
			
			// 입력받은 값을 한 번에 전달하기 위한 Emp 객체 생성
			Emp temp = new Emp(empNo, job, sal, comm);
			
			// 생성한 Emp 객체를 DB에 전달하고 결과를 반환받기
			//  -> DML 수행 결과가 행의 개수(int형)로 반환 될것을 예상해서
			//     미리 int result로 수행 결과를 받도록 작성
			int result = empService.updateEmp(temp);
			
			// DB 수정 결과에 따른 결과 출력
			if(result > 0) {
				System.out.println("수정 성공!!!");
			}else {
				System.out.println("수정 실패...");
			}
			
		}else {
			System.out.println("해당 사번을 가진 사원이 없습니다.");
		}
		
	}
	
	// 5. 사원 정보 삭제 View
	private void deleteEmp() {
		// 1단계 : 입력받은 사번을 가진 사원이 존재하는지 확인
		System.out.println("[사원 정보 삭제]");
		System.out.print("정보를 수정할 사원의 사번 입력 : ");
		int empNo = sc.nextInt();
		sc.nextLine();
		
		int check = empService.existsEmp(empNo);
		
		// 2단계 : 1단계 결과가 있다고 판별된 경우
		// "정말 삭제하시겠습니까?(Y/N)" 출력 후
		// "Y"를 입력한 경우 DB에서 삭제
		
		
		if(check > 0) {	
			System.out.print("정말 삭제하시겠습니까?(Y/N)");
			char input = sc.nextLine().toUpperCase().charAt(0);
			
			if(input == 'Y') {
				int result = empService.deleteEmp(empNo);
				
				if(result > 0) {
					System.out.println("삭제 성공");
				}else {
					System.out.println("삭제 실패");
				}
		
			}else {
				System.out.println("삭제 취소");
			}
		}else {
			System.out.println("해당 사번을 가진 사원이 없습니다.");
		}
		
	}
	
}