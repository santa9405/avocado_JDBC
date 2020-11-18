package com.kh.jdbc.view;

import java.util.InputMismatchException;
import java.util.List;
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
					case 1: memberMenu(); break;
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

	

	/**
	 * 회원 기능 메뉴 View
	 */
	private void memberMenu() {
		int sel = 0;
		
		do {
			try {
				if(loginMember == null) return;
				// 회원 탈퇴로 인해 로그아웃 될 경우 메인 메뉴로 리턴(돌아가기)
				
				System.out.println("========================================");
				System.out.println("[회원 기능 메뉴]");
				System.out.println("1. 내 정보 조회");
				System.out.println("2. 검색어가 이름에 포함된 모든 회원 검색");
				/* view
				 * 1. 검색어 입력 받기
				 * 2. Service에 알맞은 메소드 호출 후 결과 반환 받기
				 * 3. 결과로 List<Member> 를 반환받고 모두 출력
				 * 
				 * service
				 * 1. Connection 생성
				 * 2. DAO에서 알맞은 메소드 호출 후 결과 반환 받기
				 * 3. Connection 반환
				 * 4. DAO 호출 결과를 그대로 View로 반환
				 * 
				 * dao
				 * 1. 반환할 결과를 저장할 변수 List<Member> 선언
				 * 2. SQL 작성
				 * SELECT * FROM MEMBER
				 * WHERE MEM_NM LIKE '%' || ? || '%'
				 * 3. PreparedStatement 객체 생성
				 * 4. 위치홀더(?)에 알맞은 값 배치
				 * 5. SQL 수행 후 결과(ResultSet)를 반환 받음
				 * 6. 수행 결과를 모두 List에 담기
				 * 7. 사용한 JDBC 객체 반환
				 * 8. 조회 결과가 담긴 List 반환
				 * */
				System.out.println("3. 내 정보 수정");
				/* update 수행
				 * 결과 값 int
				 *  + service에서 commit/rollback 수행
				 * */
				System.out.println("4. 비밀번호 변경");
				System.out.println("5. 회원 탈퇴");
				System.out.println("6. 성별 조회");
				System.out.println("0. 메인 메뉴로 돌아가기");
				System.out.println("========================================");
				
				System.out.println("메뉴 선택 >> ");
				sel = sc.nextInt();
				sc.nextLine();
				
				System.out.println();
				switch (sel) {
				case 1: selectMyInfo(); break;
				case 2: selectMemberName(); break;
				case 3: updateMyInfo(); break;
				case 4: updatePw(); break;
				case 5: updateSecessionMember(); break;
				case 6: selectGender(); break;
				case 0: System.out.println("메인 메뉴로 돌아갑니다."); break;
				default: System.out.println("잘못 입력하셨습니다.");
				}
				
			}catch(InputMismatchException e) {
				System.out.println("숫자만 입력해주세요.");
				sel = -1;
				sc.nextLine();
			}
			
			
		}while(sel != 0);
	}

	/**
	 * 내 정보 조회 View
	 */
	private void selectMyInfo() {
		// DB에서 회원 정보를 조회할 필요 없이
		// loginMember에 담겨있는 값을 이용해 출력
		System.out.println("[내 정보 조회]");
		System.out.println("회원번호 : " + loginMember.getMemNo());
		System.out.println("아이디 : " + loginMember.getMemId());
		System.out.println("이름 : " + loginMember.getMemNm());
		System.out.println("전화번호 : " + loginMember.getPhone());
		System.out.println("성별 : " + loginMember.getGender());
		System.out.println("가입일 : " + loginMember.getHireDt());
	}

	/**
	 * 회원 이름에 검색어가 포함되는 모든 회원 조회 View
	 */
	private void selectMemberName() {
//		 * 1. 검색어 입력 받기
		System.out.println("[검색어 포함 이름 검색]");
		System.out.println("검색어 입력 : ");
		String name = sc.nextLine();
		
//		 * 2. Service에 알맞은 메소드 호출 후 결과 반환 받기
		try {
			List<Member> list = mService.selectMemberName(name);
			
//		 * 3. 결과로 List<Member> 를 반환받고 모두 출력
			if(list.isEmpty()) { // 리스트가 비어있다 == 조회 결과가 없다
				System.out.println("조회 결과가 없습니다.");
			}else {
				for(Member mem : list) {
					//System.out.println(mem);
					System.out.printf("%s / %s / %s / %c / %s\n", 
							mem.getMemId(), 
							mem.getMemNm(),
							mem.getPhone(),
							mem.getGender(),
							mem.getHireDt()
							);
				}
			}
			
			
			
		}catch(Exception e) {
			System.out.println("검색어 포함 이름 검색 과정에서 오류 발생");
			e.printStackTrace();
		}
		
	}


	/**
	 * 성별 검색 View
	 */
	private void selectGender() {
		// 성별(M 또는 F, 대소문자 구분 X)을 입력받아
		// DB에서 일치하는 회원을 모두 조회
		// 아이디, 이름, 전화번호 마지막자리, 성별만 출력
			
		System.out.println("[성별 검색]");
		System.out.print("조회할 성별을 입력해주세요(F/M) : ");
		char gender = sc.nextLine().toUpperCase().charAt(0);
		
		try {
			List<Member> list = mService.selectGender(gender);
			
			for(Member mem : list) {
				System.out.printf("%s / %s / %s / %c\n", 
						mem.getMemId(),
						mem.getMemNm(),
						mem.getPhone(),
						mem.getGender());
			}
		
		} catch (Exception e) {
			System.out.println("검색어 포함 이름 검색 과정에서 오류 발생");
			e.printStackTrace();
		}
		
	}
	
	

	/**
	 * 내 정보 수정 View
	 */
	private void updateMyInfo() {
		System.out.println("[내 정보 수정]");
		System.out.print("이름 : ");
		String memNm = sc.nextLine();
		
		System.out.print("전화번호 : ");
		String phone = sc.nextLine();
		
		System.out.print("성별 : ");
		char gender = sc.nextLine().toUpperCase().charAt(0);
		
		// 입력받은 값 + 회원 번호를 저장할 Member 객체 생성
		Member upMember 
			= new Member(loginMember.getMemNo(), memNm, phone, gender);
			// -> 회원 번호는 loginMember에서  얻어옴 (update where절에 사용)
		
		// 생성한 member객체를 매개변수로 하여 Service 메소드 호출 및 결과 반환
		try {
			// update == dml --> int 반환(성공한 행의 갯수)
			int result = mService.updateMyInfo(upMember);
			
			if(result > 0) {
				System.out.println("내 정보 수정 성공!!!");
				
				// loginMember는 수정 전 데이터를 가지고 있으므로
				// 수정 성공 시 데이터를 최신화 해야 함(입력 받은 값으로 바꿔줌)
				loginMember.setMemNm(memNm);
				loginMember.setPhone(phone);
				loginMember.setGender(gender);
				
			}else {
				System.out.println("내 정보 수정 실패...");
			}
			
		} catch (Exception e) {
			System.out.println("내 정보 수정 과정에서 오류 발생");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 비밀번호 변경 View
	 */
	private void updatePw() {
		System.out.println("[비밀번호 변경]");
		System.out.print("현재 비밀번호 : ");
		String currPw = sc.nextLine();
		
		System.out.print("새 비밀번호 : ");
		String newPw = sc.nextLine();
		
		System.out.print("새 비밀번호 확인 : ");
		String newPw2 = sc.nextLine();

		// 새 비밀번호와 확인이 일치하는지 판별
		if(newPw.equals(newPw2)) { // 같을 경우
			// 현재 비밀번호와 새 비밀번호 + 회원 번호
			
			Member upMember = new Member();
			upMember.setMemNo( loginMember.getMemNo() ); // 회원 번호
			upMember.setMemPw( currPw ); // 입력받은 현재 비밀번호
			
			try {
				int result = mService.updatePw(upMember, newPw);
				
				if(result > 0) {
					System.out.println("비밀번호 변경 성공!!!");
					
					loginMember.setMemPw(newPw);
					
				}else {
					System.out.println("현재 비밀번호가 일치하지 않습니다.");
				}
			
			}catch(Exception e) {
				System.out.println("비밀번호 변경 과정에서 오류 발생");
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * 회원 탈퇴 View
	 */
	private void updateSecessionMember() {
		System.out.println("[회원 탈퇴]");
		
		System.out.print("비밀번호 입력 : ");
		String memPw = sc.nextLine(); // 비밀번호 일치 여부는 DB에서 확인
		
		System.out.println("*** 정말 탈퇴하시겠습니까??(Y/N)");
		char check = sc.nextLine().toUpperCase().charAt(0);
		
		if(check == 'Y') {
			// 삭제 진행
			try {
				Member upMember = new Member();
				upMember.setMemNo( loginMember.getMemNo()); // 회원번호 세팅(누가 탈퇴할건지?)
				upMember.setMemPw(memPw); // 비밀번호 세팅(비밀번호 일치하면 탈퇴 진행)
				
				int result = mService.updateSecessionMember(upMember);
				
				if(result > 0) {
					System.out.println("탈퇴 성공...");
					loginMember = null;
				}else {
					System.out.println("비밀번호가 일치하지 않습니다.");
				}
				
			}catch(Exception e) {
				System.out.println("회원 탈퇴 과정에서 오류 발생.");
				e.printStackTrace();
			}
			
			
		}else {
			System.out.println("탈퇴가 취소됨.");
		}

	}
	
}
