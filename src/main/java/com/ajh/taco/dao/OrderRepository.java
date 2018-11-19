package com.ajh.taco.dao;

import com.ajh.taco.domainobject.Order;

public interface OrderRepository {
	Order save(Order order);
}
