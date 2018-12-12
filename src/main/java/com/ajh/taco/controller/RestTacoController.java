package com.ajh.taco.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ajh.taco.dao.abst.TacoRepository;
import com.ajh.taco.domainobject.Taco;
import com.ajh.taco.hateoas.TacoResource;
import com.ajh.taco.hateoas.TacoResourceAssembler;

@RestController
@RequestMapping(path="/design", produces="application/json")
@CrossOrigin(origins="*") // Allow cross origin requests
public class RestTacoController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);

	private TacoRepository tacoRepo;

	public RestTacoController(TacoRepository tacoRepo) {
		super();
		this.tacoRepo = tacoRepo;
	}

	@GetMapping("/recent") // REST API /design/recent
	public Resources<TacoResource> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		List<Taco> tacos = tacoRepo.findAll(page).getContent(); // JSON
		List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
		Resources<TacoResource> recentResources = new Resources<TacoResource>(tacoResources);
//		recentResources.add(ControllerLinkBuilder.linkTo(RestTacoController.class).slash("recent").withRel("recents"));
		recentResources.add(
			ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(RestTacoController.class).recentTacos())
				.withRel("recents"));
		return recentResources;
	}

	@GetMapping("/byid/{id}") // REST API /design/byid/{id}
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		java.util.Optional<Taco> taco = tacoRepo.findById(id);
		if (taco.isPresent()) {
			return new ResponseEntity<>(taco.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	// 403 Forbidden due to CSRF protection. How to fix?
	@PostMapping(path="/taco", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco) { // Annotation @RequestBody ensures that JSON in the request body is bound to Taco object.
		log.info("POST /design/taco");
		log.info("Taco: " + taco);
		return tacoRepo.save(taco);
	}
}
