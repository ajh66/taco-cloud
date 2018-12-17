package com.ajh.taco;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.ajh.taco.common.Ingredient.Type;
import com.ajh.taco.common.IngredientUDT;
import com.ajh.taco.config.TacoConfigProps;
import com.ajh.taco.controller.RestTacoController;
import com.ajh.taco.dao.abst.TacoRepository;
import com.ajh.taco.domainobject.Taco;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class RestTacoControllerTest {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RestTacoControllerTest.class);

//	@Autowired
	private WebTestClient wtclient;

//	@Autowired
	private TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);

	@Autowired
	private TacoConfigProps tacoConfig;

	@Test
	public void createNewTacos() throws Exception {
		Taco taco = new Taco();
		taco.setName("taco1");
		taco.setIngredients(Arrays.asList(
				new IngredientUDT("Flour Tortilla", Type.WRAP),
				new IngredientUDT("Carnitas"      , Type.PROTEIN),
				new IngredientUDT("Lettuce"       , Type.VEGGIES),
				new IngredientUDT("Monterrey Jack", Type.CHEESE),
				new IngredientUDT("Sour Cream"    , Type.SAUCE)
			)
		);
		Mono<Taco> tacoMono = Mono.just(taco);

		when(tacoRepo.save(any())).thenReturn(tacoMono);

		wtclient = WebTestClient.bindToController(new RestTacoController(tacoRepo, tacoConfig)).build();

		wtclient.post()
			.uri("/design/taco")
			.contentType(MediaType.APPLICATION_JSON)
			.body(tacoMono, Taco.class)
			.exchange()
			.expectStatus().isCreated()
			.expectBody(Taco.class).isEqualTo(taco) // Implement hashCode() and equals(Object) recursively.
		;
	}

	@Test
	public void getRecentTacos() throws Exception {
		Taco[] tacos = new Taco[tacoConfig.getPageSize()+1]; // Only create references not instances
		int i=0;
		for (Taco taco : tacos) {
			taco = tacos[i] = new Taco(); // Not working with "taco = new Taco();"
			taco.setId(UUID.randomUUID());
			taco.setName(new String("taco" + i));
			taco.setIngredients(Arrays.asList(
				new IngredientUDT("Flour Tortilla", Type.WRAP),
				new IngredientUDT("Carnitas"      , Type.PROTEIN),
				new IngredientUDT("Lettuce"       , Type.VEGGIES),
				new IngredientUDT("Monterrey Jack", Type.CHEESE),
				new IngredientUDT("Sour Cream"    , Type.SAUCE)
				)
			);
			i++;
		}
		Flux<Taco> tacoFlux = Flux.just(tacos);

//		tacoRepo = Mockito.mock(TacoRepository.class);
		when(tacoRepo.findAll()).thenReturn(tacoFlux); // Mocks TacoRepository

		wtclient = WebTestClient.bindToController(new RestTacoController(tacoRepo, tacoConfig)).build();
		wtclient.get().uri("/design/recent")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$").isNotEmpty()
				.jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
				.jsonPath("$[0].name").isEqualTo(tacos[0].getName())
				.jsonPath("$[0].ingredients").isArray()
				.jsonPath("$[0].ingredients").isNotEmpty()
				.jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
				.jsonPath("$[1].name").isEqualTo(tacos[1].getName())
				.jsonPath("$["+tacoConfig.getPageSize()+"]").doesNotExist()
			;
	}
}
