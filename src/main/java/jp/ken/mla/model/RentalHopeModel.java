package jp.ken.mla.model;

import java.io.Serializable;

public class RentalHopeModel  implements Serializable {
	private static final long serialVersionUID = 1L;

	private String rental_hope;

	public String getRental_hope() {
		return rental_hope;
	}

	public void setRental_hope(String rental_hope) {
		this.rental_hope = rental_hope;
	}
}
