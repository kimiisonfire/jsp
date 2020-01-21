package com.jsp.dto;

public class MemberVO {
	private String id;
	private String pwd;
	private String phone;
	private String email;
	private String picture;
	private String authority;
	private int enabled;
	
	public MemberVO(){}
	
	public MemberVO(String id, String pwd, String phone, String email,
			String picture, String authority, int enabled) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.phone = phone;
		this.email = email;
		this.picture = picture;
		this.authority = authority;
		this.enabled = enabled;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pwd=" + pwd + ", phone=" + phone
				+ ", email=" + email + ", picture=" + picture + ", authority="
				+ authority + ", enabled=" + enabled + "]";
	}
}
