package com.ajh.taco.dao.abst;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ajh.taco.domainobject.Taco;

public interface TacoRepository extends ReactiveCrudRepository<Taco, UUID> {
//	Page<Taco> findAll(Pageable pageable);
}
