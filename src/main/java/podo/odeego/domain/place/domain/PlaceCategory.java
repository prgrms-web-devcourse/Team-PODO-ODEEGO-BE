package podo.odeego.domain.place.domain;

import java.util.Arrays;

import podo.odeego.global.error.exception.InvalidValueException;

public enum PlaceCategory {

	CAFE,
	RESTAURANT;

	public static PlaceCategory of(String category) {
		return Arrays.stream(PlaceCategory.values())
			.filter(value -> value.toString().equals(category))
			.findAny()
			.orElseThrow(() -> {
				throw new InvalidValueException(
					"Enum Constant mismatched. PlaceCategory: %s. But input was '%s'.".formatted(
						Arrays.toString(PlaceCategory.values()), category
					)
				);
			});
	}
}
