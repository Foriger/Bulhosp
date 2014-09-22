package com.bulhosp.client.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.bulhosp.model.Patient;

@ManagedBean(name = "carsBean")
@ViewScoped
public class UpdateController {

	private ArrayList<Patient> allAdmittedPatients;
	private String egnForUpdate;
	private Patient editedPatient;

	@PostConstruct
	private void initAllPatientData() {
		allAdmittedPatients = (ArrayList<Patient>) getAllAdmittedPatientsFromServer();
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
					"http://bulhosp-pentech.rhcloud.com/rest/patient/"+editedPatient.getPatient_id()+"/update");
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

}
