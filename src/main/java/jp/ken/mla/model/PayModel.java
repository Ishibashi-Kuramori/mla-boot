package jp.ken.mla.model;

import java.io.Serializable;

public class PayModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int pay_id;
	private String pay_name;

	public int getPay_id() {
		return pay_id;
	}

	public void setPay_id(int pay_id) {
		this.pay_id = pay_id;
	}

	public String getPay_name() {
		return pay_name;
	}

	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
}
