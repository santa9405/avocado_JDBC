package com.kh.scott.model.vo;

import java.sql.Date;
/* VO(Value Object) === DTO(Data Transfer Object)
 * --> DB 테이블의 한 행의 정보가 기록되는 저장용 객체
 * 
 * 1. 반드시 캡슐화 적용
 * 2. getter / setter 반드시 작성
 * 3. 기본 생성자 필수, 매개변수 있는 생성자 선택
 * 4. VO에 로직이 포함된 메소드는 지양하는게 좋음
 * 
 */

public class Emp {
	private int empNo; // 사번
	private String eName; // 이름
	private String job; // 직책
	private int mgr; // 직속상사
	
	// java.sql.Date
	private Date hireDate; // 입사일 
	private int sal; // 급여
	private int comm; // 커미션
	private int deptNo; // 부서번호
	
	public Emp() {} // 기본 생성자

	public Emp(int empNo, String eName, String job, int mgr, Date hireDate, int sal, int comm, int deptNo) {
		super();
		this.empNo = empNo;
		this.eName = eName;
		this.job = job;
		this.mgr = mgr;
		this.hireDate = hireDate;
		this.sal = sal;
		this.comm = comm;
		this.deptNo = deptNo;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getMgr() {
		return mgr;
	}

	public void setMgr(int mgr) {
		this.mgr = mgr;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public int getSal() {
		return sal;
	}

	public void setSal(int sal) {
		this.sal = sal;
	}

	public int getComm() {
		return comm;
	}

	public void setComm(int comm) {
		this.comm = comm;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	@Override
	public String toString() {
		return " [empNo=" + empNo + ", eName=" + eName + ", job=" + job + ", mgr=" + mgr + ", hireDate=" + hireDate
				+ ", sal=" + sal + ", comm=" + comm + ", deptNo=" + deptNo + "]";
	}
	
}
