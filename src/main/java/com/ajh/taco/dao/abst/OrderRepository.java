package com.ajh.taco.dao.abst;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ajh.taco.domainobject.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findByZip(String zip);
}
