package com.bulhosp.client.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.richfaces.model.Filter;

import com.bulhosp.model.Patient;

@ManagedBean(name = "carsBean")
@ViewScoped
public class UpdateController {

	private ArrayList<Patient> allAdmittedPatients;
	private int indexForUpdate;
	private String egnForUpdate;
	private Patient editedPatient;
	private String nameFilter;
	private String surnameFilter;
	private String familyFilter;
	private String egnFilter;
	private String lnchFilter;
	private String addresssFilter;
	private Date dobFilter;

	private String genderFilter;

	public Filter<?> getFilterGender() {
		return new Filter<Patient>() {
			public boolean accept(Patient patient) {
				String name = getGenderFilter();
				if (name == null || name.length() == 0
						|| name.equals(patient.getDob())) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<?> getFilterDob() {
		return new Filter<Patient>() {
			public boolean accept(Patient patient) {
				Date name = getDobFilter();
				if (name.equals(patient.getDob())) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<?> getFilterAddress() {
		return new Filter<Patient>() {
			public boolean accept(Patient patient) {
				String name = getAddresssFilter();
				if (name == null || name.length() == 0
						|| name.equals(patient.getAddress())) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<?> getFilterLnch() {
		return new Filter<Patient>() {
			public boolean accept(Patient patient) {
				String name = getLnchFilter();
				if (name == null || name.length() == 0
						|| name.equals(patient.getLnch())) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<?> getFilterEgn() {
		return new Filter<Patient>() {
			public boolean accept(Patient patient) {
				String name = getEgnFilter();
				if (name == null || name.length() == 0
						|| name.equals(patient.getEgn())) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<?> getFilterName() {
		return new Filter<Patient>() {
			public boolean accept(Patient patient) {
				String name = getNameFilter();
				if (name == null || name.length() == 0
						|| name.equals(patient.getFirstname())) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<?> getFilterSurname() {
		return new Filter<Patient>() {
			public boolean accept(Patient patient) {
				String name = getNameFilter();
				if (name == null || name.length() == 0
						|| name.equals(patient.getSurname())) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<?> getFilterFamily() {
		return new Filter<Patient>() {
			public boolean accept(Patient patient) {
				String name = getNameFilter();
				if (name == null || name.length() == 0
						|| name.equals(patient.getFamily())) {
					return true;
				}
				return false;
			}
		};
	}

	@PostConstruct
	private void initAllPatientData() {
		allAdmittedPatients = (ArrayList<Patient>) getAllAdmittedPatientsFromServer();
	}

	public void uploadFileToServer() {
		try {
			URI uri = new URIBuilder(
					"http://bulhosp-pentech.rhcloud.com/rest/patient").build();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private List<Patient> getAllAdmittedPatientsFromServer() {
		List<Patient> allPersons = null;
		try {
			URI uri = new URIBuilder(
					"http://bulhosp-pentech.rhcloud.com/rest/patient")
					.addParameter("status", "ADMITTED").build();

			HttpGet getRequest = new HttpGet(uri);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(getRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				System.out.println("Failed with HTTP error code : "
						+ statusCode);
			} else {
				System.out.println(response.toString());
			}

			HttpEntity httpEntity = response.getEntity();

			ObjectMapper om = new ObjectMapper();
			allPersons = om.readValue(
					EntityUtils.toString(httpEntity),
					om.getTypeFactory().constructCollectionType(List.class,
							Patient.class));
			for (Patient person : allPersons) {
				person.decryptData();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return allPersons;
	}

	public void updatePatient() {
		try {
			ObjectMapper om = new ObjectMapper();
			editedPatient.encryptData();
			StringEntity input = new StringEntity(
					om.writeValueAsString(editedPatient));

			HttpPut postRequest = new HttpPut(
					"http://bulhosp-pentech.rhcloud.com/rest/patient/update");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setEntity(input);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(postRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				System.out.println(statusCode);
			} else {
				System.out.println(statusCode);
				initAllPatientData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Patient> getAllAdmittedPatients() {
		return allAdmittedPatients;
	}

	public void setAllAdmittedPatients(ArrayList<Patient> allAdmittedPatients) {
		this.allAdmittedPatients = allAdmittedPatients;
	}

	public String getEgnForUpdate() {
		return egnForUpdate;
	}

	public void setEgnForUpdate(String egnForUpdate) {
		this.egnForUpdate = egnForUpdate;
	}

	public Patient getEditedPatient() {
		return editedPatient;
	}

	public void setEditedPatient(Patient editedPatient) {
		this.editedPatient = editedPatient;
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public String getSurnameFilter() {
		return surnameFilter;
	}

	public void setSurnameFilter(String surnameFilter) {
		this.surnameFilter = surnameFilter;
	}

	public String getFamilyFilter() {
		return familyFilter;
	}

	public void setFamilyFilter(String familyFilter) {
		this.familyFilter = familyFilter;
	}

	public String getEgnFilter() {
		return egnFilter;
	}

	public void setEgnFilter(String egnFilter) {
		this.egnFilter = egnFilter;
	}

	public String getLnchFilter() {
		return lnchFilter;
	}

	public void setLnchFilter(String lnchFilter) {
		this.lnchFilter = lnchFilter;
	}

	public String getAddresssFilter() {
		return addresssFilter;
	}

	public void setAddresssFilter(String addresssFilter) {
		this.addresssFilter = addresssFilter;
	}

	public Date getDobFilter() {
		return dobFilter;
	}

	public void setDobFilter(Date dobFilter) {
		this.dobFilter = dobFilter;
	}

	public String getGenderFilter() {
		return genderFilter;
	}

	public void setGenderFilter(String genderFilter) {
		this.genderFilter = genderFilter;
	}

	public int getIndexForUpdate() {
		return indexForUpdate;
	}

	public void setIndexForUpdate(int indexForUpdate) {
		this.indexForUpdate = indexForUpdate;
	}

}
