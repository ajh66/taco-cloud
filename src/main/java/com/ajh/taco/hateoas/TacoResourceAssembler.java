package com.ajh.taco.hateoas;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.ajh.taco.controller.RestTacoController;
import com.ajh.taco.domainobject.Taco;

public class TacoResourceAssembler extends ResourceAssemblerSupport<Taco, TacoResource> {

	public TacoResourceAssembler() {
		super(RestTacoController.class, TacoResource.class);
	}

	// This method would be optional if TacoResource had a default constructor. In this case, however, it doesn't have.
	@Override
	protected TacoResource instantiateResource(Taco taco) {
		return new TacoResource(taco);
	}

	@Override
	public TacoResource toResource(Taco taco) {
		// Creates a new resource with a self link to the given id.
		return createResourceWithId(taco.getId(), taco);
	}

}
