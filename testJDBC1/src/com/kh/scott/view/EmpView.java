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
				System.out.println("0. 프로그램 종료");
				System.out.println("====================================");
				
				System.out.print("메뉴 선택 : ");
				sel = sc.nextInt();
				System.out.println();
				
				switch(sel) {
				case 1 : selectAll(); break; // 1-1번 같은 클래스에 있는 selectAll() 호출
				case 2 : break;
				case 3 : break;
				case 4 : break;
				case 5 : break;
				case 0 : System.out.println("종료한다 애송이."); break;
				default : System.out.println("잘못 입력했데수.");
				}
				
			}catch (InputMismatchException e) {
				System.out.println("숫자만 입력해 주세요.");
				sel = -1;
				sc.nextLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} while(sel != 0);
	}

	// 1_2. 전체 사원 정보를 출력하는 View
	private void selectAll() {
		System.out.println("[전체 사원 정보 조회]");
		
		// 1_3. EmpService.selectAll()을 호출하여
		//      전체 사원 정보가 담겨있는 List를 반환받기
		List<Emp> empList = empService.selectAll();
		
		// 1_11. 전체 사원 조회 결과 출력
		if(empList == null) {
			// DB 연결간 문제 발생으로 empList 객체가 생성되지 않은 경우
			System.out.println("전체 사원 정보 조회 과정에서 오류가 발생했습니다.");
		} if(empList.isEmpty()) {
			// DB 연결, 조회는 성공했으나 조회 결과가 없는 경우
			System.out.println("조회 결과가 없습니다.");
		} else {
			// 조회 성공 시
			int i = 1;
			for(Emp e : empList) {
				System.out.println(i + "" + e);
				i++;
			}
		}		
	}

}
