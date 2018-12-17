package com.ajh.taco;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ajh.taco.common.Ingredient;
import com.ajh.taco.common.Ingredient.Type;
import com.ajh.taco.common.IngredientUDT;
import com.ajh.taco.dao.abst.IngredientRepository;
import com.ajh.taco.dao.abst.TacoRepository;
import com.ajh.taco.domainobject.Taco;


@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo, TacoRepository tacoRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				IngredientUDT ingreFlto = addAnIngredient("FLTO", "Flour Tortilla", Type.WRAP);
				IngredientUDT ingreCoto = addAnIngredient("COTO", "Corn Tortilla", Type.WRAP);
				IngredientUDT ingreGrbf = addAnIngredient("GRBF", "Ground Beef", Type.PROTEIN);
				IngredientUDT ingreCarn = addAnIngredient("CARN", "Carnitas", Type.PROTEIN);
				IngredientUDT ingreTmto = addAnIngredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
				IngredientUDT ingreLetc = addAnIngredient("LETC", "Lettuce", Type.VEGGIES);
				IngredientUDT ingreChed = addAnIngredient("CHED", "Cheddar", Type.CHEESE);
				IngredientUDT ingreJack = addAnIngredient("JACK", "Monterrey Jack", Type.CHEESE);
				IngredientUDT ingreSlsa = addAnIngredient("SLSA", "Salsa", Type.SAUCE);
				IngredientUDT ingreSrcr = addAnIngredient("SRCR", "Sour Cream", Type.SAUCE);

				Taco taco1 = new Taco();
				taco1.setName("taco1");
				taco1.setIngredients(Arrays.asList(ingreFlto, ingreGrbf, ingreTmto, ingreChed, ingreSlsa));
				tacoRepo.save(taco1).subscribe();

				Taco taco2 = new Taco();
				taco2.setName("taco2");
				taco2.setIngredients(Arrays.asList(ingreCoto, ingreCarn, ingreLetc, ingreJack, ingreSrcr));
				tacoRepo.save(taco2).subscribe();
			}

			private IngredientUDT addAnIngredient(String id, String name, Type type) {
				repo.save(new Ingredient(id, name, type)).subscribe();
				return new IngredientUDT(name, type);
			}
		};
	}
}
