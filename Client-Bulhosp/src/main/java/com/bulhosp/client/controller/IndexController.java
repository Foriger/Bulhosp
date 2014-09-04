package com.bulhosp.client.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.bulhosp.model.Patient;
import com.bulhosp.model.Results;
import org.richfaces.model.Filter;

@Model
public class IndexController {

	private ArrayList<Patient> allPatients;
	private String nameFilter;
	private String surnameFilter;
	private String familyFilter;



	public Filter<?> getFilterName() { 
        return new Filter<Patient>() {
            public boolean accept(Patient patient) {
                String name = getNameFilter();
                if (name  == null || name.length() == 0 || name.equals(patient.getFirstname())) {
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
                if (name  == null || name.length() == 0 || name.equals(patient.getSurname())) {
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
                if (name  == null || name.length() == 0 || name.equals(patient.getFamily())) {
                    return true;
                }
                return false;
            }
        };
    }
    
	
	@PostConstruct
	private void initAllPatientData() {
		allPatients = (ArrayList<Patient>) getAllAdmittedPatientsFromServer();
	}

	
	private List<Patient> getAllAdmittedPatientsFromServer() {
		List<Patient> allPersons = null;

		try {
			URI uri = new URIBuilder("https://api.parse.com/1/classes/Patient/")
					.build();
			HttpGet getRequest = new HttpGet(uri);
			getRequest.setHeader("X-Parse-Application-Id",
					"4wky9qF2hjZmOM6zU4WOSI6t6fkSPDFPxN0yY1f4");
			getRequest.setHeader("X-Parse-REST-API-Key",
					"ktB7WNVrbhwFIElE6a8Jq74daE1HqcDqyxtcHnEP");
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(getRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				System.out.println("Failed with HTTP error code : "
						+ statusCode);
			} else {
				System.out.println(response.toString());
				HttpEntity httpEntity = response.getEntity();

				ObjectMapper om = new ObjectMapper();
				Results persons = om.readValue(EntityUtils.toByteArray(httpEntity),
						Results.class);
				allPersons = persons.getResults();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return allPersons;
	}

	public ArrayList<Patient> getAllPatients() {
		return allPatients;
	}

	public void setAllPatients(ArrayList<Patient> allPatients) {
		this.allPatients = allPatients;
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


}

