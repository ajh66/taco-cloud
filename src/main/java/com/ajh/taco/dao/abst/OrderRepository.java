package com.ajh.taco.dao.abst;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import com.ajh.taco.domainobject.Order;

import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCassandraRepository<Order, UUID> {
	Flux<Order> findByZip(String zip);
}
