package com.bulhosp.client.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import com.bulhosp.model.Patient;

@Model
public class AdmissionController {
	private Patient patientForAdmit;

	@Produces
	@Named
	public Patient getPatientForAdmit() {
		return patientForAdmit;
	}

	public void setPatientForAdmit(Patient patientForAdmit) {
		this.patientForAdmit = patientForAdmit;
	}

	@PostConstruct
	private void initPatientForAdmit() {
		patientForAdmit = new Patient();
	}

	public void admitPatient() {
		patientForAdmit.setStatus("ADMITTED");
		System.out.println("Patient will be admitted");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			ObjectMapper om = new ObjectMapper();
			StringEntity input = new StringEntity(om.writeValueAsString(patientForAdmit));
			
			HttpPost postRequest = new HttpPost(
					"https://api.parse.com/1/classes/Patient");
			postRequest.setHeader("X-Parse-Application-Id",
					"4wky9qF2hjZmOM6zU4WOSI6t6fkSPDFPxN0yY1f4");
			postRequest.setHeader("X-Parse-REST-API-Key",
					"ktB7WNVrbhwFIElE6a8Jq74daE1HqcDqyxtcHnEP");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 201) {
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "Admit failed.Please try again later");
				FacesContext.getCurrentInstance().addMessage(null, m);
				System.out.println(statusCode);
			} else {
				 FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Admitted!", "Admission successful");
				 FacesContext.getCurrentInstance().addMessage(null, m);
				 System.out.println(statusCode);
				 initPatientForAdmit();
			}
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Admit failed.Please try again later");
			FacesContext.getCurrentInstance().addMessage(null, m);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}
}
