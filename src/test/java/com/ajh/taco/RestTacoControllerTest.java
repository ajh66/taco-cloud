package com.ajh.taco;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ajh.taco.common.Ingredient;
import com.ajh.taco.common.Ingredient.Type;
import com.ajh.taco.dao.abst.TacoRepository;
import com.ajh.taco.domainobject.Taco;
import com.ajh.taco.security.SecurityConfigProps;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class RestTacoControllerTest {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RestTacoControllerTest.class);

	@Autowired
	private CsrfTokenRepository csrfTokenRepo;

	@Autowired
	private SecurityConfigProps scp;

	@Autowired
	private TestRestTemplate rest;

	private String jsessionid = null;
	private String xsrftoken = null;

	@Autowired
	private TacoRepository tacoRepo;

	@Before
	public void login() {
		CsrfToken csrfToken = csrfTokenRepo.generateToken(null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Cookie", "XSRF-TOKEN=" + csrfToken.getToken()); // In addition to _csrf field in body
		log.info("Cookie: " + headers.get("Cookie"));
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("username", scp.getSampleUserName());
		map.add("password", scp.getSamplePassword());
		map.add("_csrf", csrfToken.getToken());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = rest
//				.withBasicAuth("andy", "1234")
				.postForEntity("/login", request, String.class);
		List<String> cookies = response.getHeaders().get("Set-Cookie");
		for (String cookie : cookies) {
			String[] fields = cookie.split(";");
			for (String field: fields) {
				if (field.matches("JSESSIONID=[A-Z0-9]+")) {
					this.jsessionid = field;
					log.info("Session: " + this.jsessionid);
				} else if(field.matches("XSRF-TOKEN=[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}")) {
					// XSRF-TOKEN=a1ae228e-7322-4966-aa34-25229701081b
					this.xsrftoken = field;
					log.info("Token: " + this.xsrftoken);
				}
			}
		}
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND); // 302 FOUND
		assertThat(response.getHeaders().getContentLength()).isEqualTo(0);
	}

	/*
	 * Note:
	 * Setting of jsessionid works in this case.
	 * Setting of csrf token in this case doesn't work when enabling csrf checking
	 * Why???
	 * 
	 * */
	@Test
	public void createNewTacos() throws Exception {
		String strXsrfToken = "";
		if (this.xsrftoken != null) {
			strXsrfToken += this.xsrftoken;
		} else {
			CsrfToken csrfToken = csrfTokenRepo.generateToken(null);
			strXsrfToken += "XSRF-TOKEN=" + csrfToken.getToken();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (this.jsessionid != null) {
			headers.add("Cookie", strXsrfToken + ";" + this.jsessionid); // In addition to _csrf field in body
		} else {
			headers.add("Cookie", strXsrfToken); // In addition to _csrf field in body
		}

		log.info("Cookie: " + headers.get("Cookie"));

//		repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
//		repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
//		repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
//		repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
//		repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
//		repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
//		repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
//		repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
//		repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
//		repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

		JSONObject taco = new JSONObject();
		taco.put("name", "taco1");
		taco.put("ingredients", new JSONArray() {{
			put(new JSONObject() {{put("id", "FLTO");}});
			put(new JSONObject() {{put("id", "GRBF");}});
			put(new JSONObject() {{put("id", "TMTO");}});
			put(new JSONObject() {{put("id", "JACK");}});
//			put("FLTO");
//			put("GRBF");
//			put("TMTO");
//			put("JACK");
		}});
		taco.put("_csrf", strXsrfToken.split("=")[1]);
		log.info("taco: " + taco.toString());

		HttpEntity<String> request = new HttpEntity<String>(taco.toString(), headers);
		ResponseEntity<String> response = rest
//				.withBasicAuth("andy", "1234")
				.postForEntity("/design/taco", request, String.class);
		log.info("Body: " + response.getBody());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

	}

	@Test
	public void getRecentTacos() throws Exception {
		Taco[] tacos = new Taco[13]; // Only create 13 references not instances
		int i=0;
		for (Taco taco : tacos) {
			taco = tacos[i] = new Taco(); // Not working with "taco = new Taco();"
			taco.setName(new String("taco" + i));
			taco.setIngredients(new ArrayList<Ingredient>() {
				private static final long serialVersionUID = 1L; // For anonymous inner class
				{
					add(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
					add(new Ingredient("CARN", "Carnitas"      , Type.PROTEIN));
					add(new Ingredient("LETC", "Lettuce"       , Type.VEGGIES));
					add(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
					add(new Ingredient("SRCR", "Sour Cream"    , Type.SAUCE));
				}
			});
			i++;
		}
		tacoRepo.saveAll(Arrays.asList(tacos)); // Save to database

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (this.jsessionid != null) {
			headers.add("Cookie", this.jsessionid);
		}
		HttpEntity<String> req = new HttpEntity<String>("", headers);
		ResponseEntity<String> rsp = rest.exchange("/design/recent", HttpMethod.GET, req, String.class);
//		log.info("Body: " + rsp.getBody());

		assertThat(rsp.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(rsp.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);

		JSONObject json = new JSONObject(rsp.getBody());
		assertEquals(((JSONObject)json.get("_embedded")).getJSONArray("tacos").length(), 12);

	}
}
