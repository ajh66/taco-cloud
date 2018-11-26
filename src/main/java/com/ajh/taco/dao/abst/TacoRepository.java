package com.ajh.taco.dao.abst;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.ajh.taco.domainobject.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
	Page<Taco> findAll(Pageable pageable);
}
