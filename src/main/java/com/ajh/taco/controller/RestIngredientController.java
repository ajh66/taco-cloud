package com.ajh.taco.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/ingredient", produces="application/json")
@CrossOrigin(origins="*") // Allow cross origin requests
public class RestIngredientController {

}
