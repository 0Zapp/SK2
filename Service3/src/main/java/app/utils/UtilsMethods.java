package app.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static app.security.SecurityConstants.HEADER_STRING;

public class UtilsMethods {

	public static <T> ResponseEntity<T> sendGet(String url, String token, Class<T> cls) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_STRING, token);

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, cls);

		return response;
	}

	public static <T> ResponseEntity<T> sendPost(String url, Object body, Class<T> cls) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, cls);

		return response;
	}

}
