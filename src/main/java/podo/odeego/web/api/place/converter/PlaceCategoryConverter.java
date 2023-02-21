package podo.odeego.web.api.place.converter;

import org.springframework.core.convert.converter.Converter;

import podo.odeego.domain.place.domain.PlaceCategory;

public class PlaceCategoryConverter implements Converter<String, PlaceCategory> {

	@Override
	public PlaceCategory convert(String category) {
		return PlaceCategory.of(category);
	}
}
