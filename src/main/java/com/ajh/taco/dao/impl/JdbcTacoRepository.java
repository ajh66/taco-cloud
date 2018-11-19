package com.ajh.taco.dao.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.ajh.taco.common.Ingredient;
import com.ajh.taco.dao.abst.TacoRepository;
import com.ajh.taco.domainobject.Taco;

public class JdbcTacoRepository implements TacoRepository {
	private JdbcTemplate jdbc;

	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		for (Ingredient ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}
		return taco;
	}

	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
			"insert into Taco(name, createdAt) values(?, ?)",
			Types.VARCHAR, Types.TIMESTAMP
		).newPreparedStatementCreator(
			Arrays.asList(
				taco.getName(),
				new Timestamp(taco.getCreatedAt().getTime())
			)
		);
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(psc, kh);
		return kh.getKey().longValue();
	}
	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update(
			"insert into Taco_Ingredients(taco, ingredient) values(?,?)",
			tacoId,
			ingredient.getId());
	}
}
