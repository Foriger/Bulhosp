package com.bulhosp.client.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.bulhosp.model.Patient;
import com.bulhosp.model.Results;

@Model
public class DischargeController {

	private Patient patientForDischarge;
	private ArrayList<Patient> allAdmittedPatients;

	@PostConstruct
	private void initAllPatientData() {
		allAdmittedPatients = (ArrayList<Patient>) getAllAdmittedPatientsFromServer();
	}

	private List<Patient> getAllAdmittedPatientsFromServer() {
		List<Patient> allPersons = null;
		try {
			URI uri = new URIBuilder("http://bulhosp-pentech.rhcloud.com/rest/patient")
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
			allPersons =  om.readValue(
					EntityUtils.toString(httpEntity),
					om.getTypeFactory().constructCollectionType(List.class,
							Patient.class));
			
			for(Patient person : allPersons){
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

	public void dischargePatient(Patient patient) {

		try {
			Long patientID = patient.getPatient_id();
			String uri = "http://bulhosp-pentech.rhcloud.com/rest/patient/"+ patientID + "/discharge";
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

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public Patient getPatientForDischarge() {
		return patientForDischarge;
	}

	public ArrayList<Patient> getAllAdmittedPatients() {
		return allAdmittedPatients;
	}

	public void setAllAdmittedPatients(ArrayList<Patient> allAdmittedPatients) {
		this.allAdmittedPatients = allAdmittedPatients;
	}

	public void setPatientForDischarge(Patient patientForDischarge) {
		this.patientForDischarge = patientForDischarge;
	}

}
