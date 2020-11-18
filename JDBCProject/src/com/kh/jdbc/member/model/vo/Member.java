package com.kh.jdbc.member.model.vo;

import java.sql.Date;

public class Member { // 회원 한명의 정보를 저장하기 위함
	private int memNo; // 회원 번호
	private String memId; // 아이디
	private String memPw; // 비밀번호
	private String memNm; // 이름
	private String phone; // 전화번호
	private char gender; // 성별
	private Date hireDt; // 가입일
	private char scsnFl; // 탈퇴여부
	
	public Member() { }
	
	
	// 내정보 수정용 생성자
	public Member(int memNo, String memNm, String phone, char gender) {
		super();
		this.memNo = memNo;
		this.memNm = memNm;
		this.phone = phone;
		this.gender = gender;
	}



	// 회원 가입용 생성자
	public Member(String memId, String memPw, String memNm, String phone, char gender) {
		super();
		this.memId = memId;
		this.memPw = memPw;
		this.memNm = memNm;
		this.phone = phone;
		this.gender = gender;
	}
	
	// 회원 검색용 생성자
	public Member(String memId, String memNm, String phone, char gender, Date hireDt) {
		super();
		this.memId = memId;
		this.memNm = memNm;
		this.phone = phone;
		this.gender = gender;
		this.hireDt = hireDt;
	}
	
	// 성별 검색용 생성자
	public Member(String memId, String memNm, String phone, char gender) {
		super();
		this.memId = memId;
		this.memNm = memNm;
		this.phone = phone;
		this.gender = gender;
	}
	



	// 로그인용 생성자
	public Member(int memNo, String memId, String memNm, String phone, char gender, Date hireDt) {
		super();
		this.memNo = memNo;
		this.memId = memId;
		this.memNm = memNm;
		this.phone = phone;
		this.gender = gender;
		this.hireDt = hireDt;
	}




	public Member(int memNo, String memId, String memPw, String memNm, String phone, char gender, Date hireDt,
			char scsnFl) {
		super();
		this.memNo = memNo;
		this.memId = memId;
		this.memPw = memPw;
		this.memNm = memNm;
		this.phone = phone;
		this.gender = gender;
		this.hireDt = hireDt;
		this.scsnFl = scsnFl;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public String getMemPw() {
		return memPw;
	}

	public void setMemPw(String memPw) {
		this.memPw = memPw;
	}

	public String getMemNm() {
		return memNm;
	}

	public void setMemNm(String memNm) {
		this.memNm = memNm;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getHireDt() {
		return hireDt;
	}

	public void setHireDt(Date hireDt) {
		this.hireDt = hireDt;
	}

	public char getScsnFl() {
		return scsnFl;
	}

	public void setScsnFl(char scsnFl) {
		this.scsnFl = scsnFl;
	}

	@Override
	public String toString() {
		return "Member [memNo=" + memNo + ", memId=" + memId + ", memPw=" + memPw + ", memNm=" + memNm + ", phone="
				+ phone + ", gender=" + gender + ", hireDt=" + hireDt + ", scsnFl=" + scsnFl + "]";
	}
	
	
}
