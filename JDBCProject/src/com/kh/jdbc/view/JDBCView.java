package com.kh.jdbc.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.kh.jdbc.member.model.service.MemberService;
import com.kh.jdbc.member.model.vo.Member;

/**  JDBCProject View
 * @author 강수정
 */
public class JDBCView {
	
	private Scanner sc = new Scanner(System.in);
	private MemberService mService = new MemberService();
	
	private Member loginMember = null; // 로그인된 회원의 정보를 저장
	
	// alr + shift + j
	/** 메인 메뉴 View
	 * 작성자 : 강수정
	 */
	public void displayMain() {
		int sel = 0;
		
		do {
			
			try {
				if(loginMember == null) { // 로그인이 되어있지 않은 경우
					System.out.println("★★★★★ JDBC PROJECT ★★★★★");
					System.out.println("==========================");
					System.out.println("1. 로그인");
					System.out.println("2. 회원가입");
					System.out.println("0. 프로그램 종료");
					System.out.println("==========================");
					
					System.out.print("메뉴 선택 >> ");
					sel = sc.nextInt();
					sc.nextLine();
					
					System.out.println();
					switch(sel) {
					case 1: login(); break;
					case 2: signUp(); break;
					case 0: System.out.println("프로그램 종료.");break;
					default : System.out.println("잘못 입력하셨습니다.");
					}
					
				}else {
					System.out.println("==========================");
					System.out.println("[메인메뉴]");
					System.out.println("1. 회원 기능");
					System.out.println("9. 로그아웃");
					System.out.println("0. 프로그램 종료");
					System.out.println("==========================");
					
					System.out.print("메뉴 선택 >> ");
					sel = sc.nextInt();
					sc.nextLine();
					
					System.out.println();
					switch(sel) {
					case 1: break;
					case 9: 
						loginMember = null; // 회원 정보를 없앰
						System.out.println("로그아웃 되었습니다.");
						break;
					case 0: System.out.println("프로그램 종료.");break;
					default : System.out.println("잘못 입력하셨습니다.");
					}
					
				}				
				
			}catch (InputMismatchException e) {
				System.out.println("숫자만 입력해 주세요.");
				sel = -1;
				sc.nextLine(); // 버퍼에 남아있는 문자열 제거
			}
			
			
		}while(sel != 0);
	}

	/**
	 * 로그인용 View
	 */
	private void login() {
		System.out.println("[로그인]");
		System.out.print("아이디 : ");
		String memId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String memPw = sc.nextLine();
		
		// 아이디, 비밀번호를 하나의 Member 객체에 저장
		Member member = new Member();
		member.setMemId(memId);
		member.setMemPw(memPw);
		
		try {
			// 입력 받은 ID, PW를 이용하여 로그인 서비스 수행
			// -> 반환 받은 결과를 loginMember에 저장
			loginMember = mService.login(member);
			
			// 로그인이 성공한 경우
			if(loginMember != null) {
				System.out.println("***** " + loginMember.getMemNm() + "님 환영합니다. *****");
			}else {
				System.out.println("아이디 또는 비밀번호가 일치하지 않거나, 탈퇴한 회원입니다.");
			}
			
			
		} catch (Exception e) {
			System.out.println("로그인 과정에서 오류 발생");
			e.printStackTrace();
		}
		
	}

	/**
	 * 회원가입용 View
	 */
	private void signUp() {
		System.out.println("[회원 가입]");
		
		System.out.print("아이디 : ");
		String memId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String memPw = sc.nextLine();
		
		System.out.print("이름 : ");
		String memNm = sc.nextLine();
		
		System.out.print("전화번호 : ");
		String phone = sc.nextLine();
		
		System.out.print("성별 : ");
		char gender = sc.nextLine().toUpperCase().charAt(0); // 대문자로 입력 받기
		
		// 입력받은 값을 하나의 Member객체에 저장
		Member newMember = new Member(memId, memPw, memNm, phone, gender);
		
		// 입력받은 회원 정보를 DB에 삽입하기 위한 service 호출
		try {
			int result = mService.signUp(newMember);
			
			if(result > 0) {
				System.out.println("회원 가입 성공!!!");
			}else {
				System.out.println("회원 가입 실패");
			}
			
		} catch (Exception e) {
			System.out.println("회원 가입 과정에서 오류 발생");
			e.printStackTrace();
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
