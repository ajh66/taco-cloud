package com.ajh.taco;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ajh.taco.security.SecurityConfigProps;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class UserAuthenticationTest {
	@Autowired
	private CsrfTokenRepository csrfTokenRepo;

	@Autowired
	private SecurityConfigProps scp;

	@Autowired
	private TestRestTemplate rest;

	@SuppressWarnings("serial")
	@Test
	public void testLogin() {
		CsrfToken csrfToken = csrfTokenRepo.generateToken(null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Cookie", "XSRF-TOKEN=" + csrfToken.getToken()); // In addition to _csrf field in body

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("username", scp.getSampleUserName());
		map.add("password", scp.getSamplePassword());
		map.add("_csrf", csrfToken.getToken());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = rest
//				.withBasicAuth("andy", "1234")
				.postForEntity("/login", request, String.class);

//		System.out.println("Body: " + response.getBody()); // Should be null
//		System.out.println("Location: " + response.getHeaders().get("Location"));
//		System.out.println("Location: " + response.getHeaders().get("Content-Length"));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND); // 302 FOUND
		assertThat(response.getHeaders().get("Content-Length")).isEqualTo(
				new ArrayList<String>() {{
					add("0"); // Length 0 of null body
				}}
		);

//		ResponseEntity<String> result = rest.withBasicAuth("andy", "1234")
//				.getForEntity("/design", String.class);
//		System.out.println(result.getBody());
//		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
