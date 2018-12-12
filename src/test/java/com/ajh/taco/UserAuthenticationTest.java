package com.ajh.taco;

import static org.assertj.core.api.Assertions.assertThat;

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
//	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserAuthenticationTest.class);

	@Autowired
	private CsrfTokenRepository csrfTokenRepo;

	@Autowired
	private SecurityConfigProps scp;

	@Autowired
	private TestRestTemplate rest;

	/*
	 * CSRF token works in this case
	 * */
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

//		log.info("Body: " + response.getBody()); // Should be null
//		log.info("Location: " + response.getHeaders().getLocation());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND); // 302 FOUND
		assertThat(response.getHeaders().getContentLength()).isEqualTo(0);
		assertThat(response.getHeaders().getLocation().toString().matches("http://localhost:[0-9]+/design")).isEqualTo(true);

//		ResponseEntity<String> result = rest.withBasicAuth("andy", "1234")
//				.getForEntity("/design", String.class);
//		System.out.println(result.getBody());
//		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
