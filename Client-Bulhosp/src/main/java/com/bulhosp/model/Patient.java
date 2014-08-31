package com.bulhosp.model;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Patient {
	
	@JsonIgnore
	private Date createdAt;
	
	@JsonIgnore
	private Date updatedAt;
	
	@Size(min=10,max=10,message="Единният граждански номер трябва да е точно 10 цифри.")
	@Pattern(regexp="[0-9]+",message="Единният граждански номер трябва да съдръжа само цифри.")
	private String egn;
	
	@Size(min=10,max=10,message="ЛНЧ трябва да е точно 10 цифри.")
	@Pattern(regexp="[0-9]+",message="ЛНЧ трябва да съдръжа само цифри.")
	private String lnch;
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Size(min=10,max=10,message="СС_НО трябва да е точно 10 цифри.")
	@Pattern(regexp="[0-9]+",message="СС_НО трябва да съдръжа само цифри.")
	private String ss_no;
	
	private String firstname;
	private String surname;
	private String family;
	private String address;
	private Date dob;
	private String gender;
	private String status;
	
	@Size(min=20,max=4000)
	private String notes;
	
	private String objectId;

	private int diagnoseCode;
	private String ward_for_hospital_stay;
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getWard_for_hospital_stay() {
		return ward_for_hospital_stay;
	}
	public void setWard_for_hospital_stay(String ward_for_hospital_stay) {
		this.ward_for_hospital_stay = ward_for_hospital_stay;
	}
	public int getDiagnoseCode() {
		return diagnoseCode;
	}
	public void setDiagnoseCode(int diagnoseCode) {
		this.diagnoseCode = diagnoseCode;
	}
	public String getEgn() {
		return egn;
	}
	public void setEgn(String egn) {
		this.egn = egn;
	}
	public String getLnch() {
		return lnch;
	}
	public void setLnch(String lnch) {
		this.lnch = lnch;
	}
	public String getSs_no() {
		return ss_no;
	}
	public void setSs_no(String ss_no) {
		this.ss_no = ss_no;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob =dob;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	
	
}