package model;

import java.sql.Timestamp;
public class Doc {
	private long id = -1;
	private String name;
	private String fullName;
	private Timestamp uploadedTime;
//	private long userId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Timestamp getUploadedTime() {
		return uploadedTime;
	}
	public void setUploadedTime(Timestamp uploadedTime) {
		this.uploadedTime = uploadedTime;
	}
//	public long getUserId() {
//		return userId;
//	}
//	public void setUserId(long userId) {
//		this.userId = userId;
//	}
	@Override
	public String toString() {
		return "Doc [id=" + id + ", name=" + name + ", fullName=" + fullName + ", uploadedTime=" + uploadedTime + "]";
	}
	

	
}
