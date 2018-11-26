package com.ajh.taco.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	public Iterable<Taco> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		return tacoRepo.findAll(page).getContent(); // JSON
	}

	@GetMapping("/byid/{id}") // REST API /design/{id}
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
	public Taco postTaco(@RequestBody Taco taco) {
		log.info("POST /design/taco");
		return tacoRepo.save(taco);
	}
}
