package com.bulhosp.model;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jasypt.util.text.StrongTextEncryptor;

import com.bulhosp.utils.Constants;

public class Patient {

	@JsonIgnore
	private Date createdAt;

	@JsonIgnore
	private Date updatedAt;

	@Size(min = 10, max = 10, message = "Единният граждански номер трябва да е точно 10 цифри.")
	@Pattern(regexp = "[0-9]+", message = "Единният граждански номер трябва да съдръжа само цифри.")
	private String egn;


	private String lnch;

	private String ss_no;

	private Long patient_id;
	private String firstname;
	private String surname;
	private String family;
	private String address;
	private Date dob;
	private String gender;
	private String status;
	@Size(min = 0, max = 4000)
	private String notes;
	private int diagnoseCode;

	public void encryptData() {
		StrongTextEncryptor ste = new StrongTextEncryptor();
		ste.setPassword(Constants.ENCRYPTION_PASSWORD);
		this.setNotes(ste.encrypt(this.notes));
		this.setAddress(ste.encrypt(this.address));

		this.setFirstname(ste.encrypt(this.firstname));
		this.setSurname(ste.encrypt(this.surname));
		this.setFamily(ste.encrypt(this.family));

		if (this.egn != null) {
			this.setEgn(ste.encrypt(this.egn));
		}
		if (this.lnch != null) {
			this.setLnch(ste.encrypt(this.lnch));
		}
		if (this.ss_no != null) {
			this.setSs_no(ste.encrypt(this.ss_no));
		}

	}
	
	public void decryptData() {
		StrongTextEncryptor ste = new StrongTextEncryptor();
		ste.setPassword(Constants.ENCRYPTION_PASSWORD);
		this.setNotes(ste.decrypt(this.notes));
		this.setAddress(ste.decrypt(this.address));

		this.setFirstname(ste.decrypt(this.firstname));
		this.setSurname(ste.decrypt(this.surname));
		this.setFamily(ste.decrypt(this.family));

		if (this.egn != null) {
			this.setEgn(ste.decrypt(this.egn));
		}
		if (this.lnch != null) {
			this.setLnch(ste.decrypt(this.lnch));
		}
		if (this.ss_no != null) {
			this.setSs_no(ste.decrypt(this.ss_no));
		}

	}
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
		this.dob = dob;
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

	public Long getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(Long patient_id) {
		this.patient_id = patient_id;
	}
	
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

}