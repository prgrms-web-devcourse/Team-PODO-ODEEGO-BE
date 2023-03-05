package podo.odeego.domain.place.entity;

import java.util.Arrays;

import podo.odeego.global.error.exception.InvalidValueException;

public enum PlaceCategory {

	ALL,
	CAFE,
	RESTAURANT;

	public static PlaceCategory of(String category) {
		if (category == null) {
			return PlaceCategory.ALL;
		}

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

	public boolean isAll() {
		return this == ALL;
	}
}
