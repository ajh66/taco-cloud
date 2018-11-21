package com.ajh.taco.dao.abst;

import org.springframework.data.repository.CrudRepository;

import com.ajh.taco.domainobject.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
