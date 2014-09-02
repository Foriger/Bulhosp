package com.bulhosp.client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.bulhosp.model.Patient;
import com.bulhosp.model.Results;

@Model
public class UpdateController {
	private Patient patientForUpdate;
	private ArrayList<Patient> allAdmittedPatients;
	
	@PostConstruct
	private void initAllPatientData(){
		allAdmittedPatients = (ArrayList<Patient>)getAllAdmittedPatientsFromServer();
	}
	
	private List<Patient> getAllAdmittedPatientsFromServer(){
		List<Patient> allPersons = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			String uri = "https://api.parse.com/1/classes/Patient/?where=%7B%22status%22%3A%22ADMITTED%22%7D";
			HttpGet getRequest = new HttpGet(uri);
			getRequest.setHeader("X-Parse-Application-Id",
					"4wky9qF2hjZmOM6zU4WOSI6t6fkSPDFPxN0yY1f4");
			getRequest.setHeader("X-Parse-REST-API-Key",
					"ktB7WNVrbhwFIElE6a8Jq74daE1HqcDqyxtcHnEP");

			HttpResponse response = httpClient.execute(getRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				System.out.println("Failed with HTTP error code : "
						+ statusCode);
			} else {
				System.out.println(response.toString());
			}

			HttpEntity httpEntity = response.getEntity();

			ObjectMapper om = new ObjectMapper();
			Results persons = om.readValue(EntityUtils
					.toByteArray(httpEntity), Results.class);
			allPersons =  persons.getResults();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return allPersons;
	}
	
	
	
	public void updatePatientForPopup(Patient patient){
		patientForUpdate = patient;
	}
	
	public void updatePatientInServer(){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			String patientID = patientForUpdate.getObjectId();
			String uri = "https://api.parse.com/1/classes/Patient/"+patientID;
			HttpPut putRequest = new HttpPut(uri);
			putRequest.setHeader("X-Parse-Application-Id",
					"4wky9qF2hjZmOM6zU4WOSI6t6fkSPDFPxN0yY1f4");
			putRequest.setHeader("X-Parse-REST-API-Key",
					"ktB7WNVrbhwFIElE6a8Jq74daE1HqcDqyxtcHnEP");
			putRequest.setHeader("Content-Type", "application/json");
			StringEntity input = new StringEntity("{\"ward_for_hospital_stay\":\"abcd\"}");
			putRequest.setEntity(input);

			HttpResponse response = httpClient.execute(putRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				System.out.println("Failed with HTTP error code : "
						+ statusCode);
			} else {
				System.out.println(response.toString());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
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

	
	
}
