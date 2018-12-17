package com.ajh.taco.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ajh.taco.config.TacoConfigProps;
import com.ajh.taco.dao.abst.TacoRepository;
import com.ajh.taco.domainobject.Taco;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path="/design", produces="application/json")
@CrossOrigin(origins="*") // Allow cross origin requests
public class RestTacoController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RestTacoController.class);

	private TacoRepository tacoRepo;
	private TacoConfigProps tacoConfig;

	public RestTacoController(TacoRepository tacoRepo, TacoConfigProps tacoConfig) {
		super();
		this.tacoRepo = tacoRepo;
		this.tacoConfig = tacoConfig;
	}

	@GetMapping("/recent") // REST API /design/recent
	public Flux<Taco> recentTacos() {
		return tacoRepo.findAll().take(tacoConfig.getPageSize());
	}

	@GetMapping("/byid/{id}") // REST API /design/byid/{id}
	public Mono<Taco> tacoById(@PathVariable("id") UUID id) {
		return tacoRepo.findById(id);
	}

	// 403 Forbidden due to CSRF protection. How to fix?
	@PostMapping(path="/taco", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Taco> postTaco(@RequestBody Taco taco) { // Annotation @RequestBody ensures that JSON in the request body is bound to Taco object.
		log.info("POST /design/taco");
		log.info("Taco: " + taco);
		return tacoRepo.save(taco);
	}
}
