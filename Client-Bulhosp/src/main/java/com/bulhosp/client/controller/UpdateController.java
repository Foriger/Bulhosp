package com.bulhosp.client.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.bulhosp.model.Patient;

@Model
public class UpdateController {
	private Patient patientForUpdate;
	private ArrayList<Patient> allAdmittedPatients;
	private HashMap<String, Object> valuesForUpdate;
	private int newDiagnoseCode;

	@PostConstruct
	private void initAllPatientData() {
		allAdmittedPatients = (ArrayList<Patient>) getAllAdmittedPatientsFromServer();
		valuesForUpdate = new HashMap<String, Object>();
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

	public void updatePatientForPopup(Patient patient) {
		patientForUpdate = patient;

		try {
			URI uri = new URIBuilder(
					"http://bulhosp-pentech.rhcloud.com/rest/patient").build();

			HttpPut putRequest = new HttpPut(uri);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(putRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				System.out.println("Failed with HTTP error code : "
						+ statusCode);
			} else {
				System.out.println(response.toString());
			}

			HttpEntity httpEntity = response.getEntity();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	public void updatePatientInServer() {
		HashMap<String, Object> newValues = new HashMap<String, Object>();
		if (newDiagnoseCode != -1) {
			newValues.put("diagnoseCode", this.newDiagnoseCode);
		}

	}

	public Patient getPatientForUpdate() {
		return patientForUpdate;
	}

	public void setPatientForUpdate(Patient patientForUpdate) {
		this.patientForUpdate = patientForUpdate;
	}

	public ArrayList<Patient> getAllAdmittedPatients() {
		return allAdmittedPatients;
	}

	public void setAllAdmittedPatients(ArrayList<Patient> allAdmittedPatients) {
		this.allAdmittedPatients = allAdmittedPatients;
	}

	public HashMap<String, Object> getValuesForUpdate() {
		return valuesForUpdate;
	}

	public void setValuesForUpdate(HashMap<String, Object> valuesForUpdate) {
		this.valuesForUpdate = valuesForUpdate;
	}

	public int getNewDiagnoseCode() {
		return newDiagnoseCode;
	}

	public void setNewDiagnoseCode(int newDiagnoseCode) {
		this.newDiagnoseCode = newDiagnoseCode;
	}

}
