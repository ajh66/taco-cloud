package com.ajh.taco.dao.abst;

import com.ajh.taco.domainobject.Order;

public interface OrderRepository {
	Order save(Order order);
}
