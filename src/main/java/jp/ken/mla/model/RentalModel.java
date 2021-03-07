package jp.ken.mla.model;

import java.io.Serializable;
import java.util.Date;

public class RentalModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private int rental_id;
	private int member_id;
	private int item_id;
	private Date order_date;
    private int hope_order;
    private int send_flag;
    private int return_flag;
    private Date make_date;
    private Date update_date;
	private ItemModel item;

	public ItemModel getItem() {
		return item;
	}
	public void setItem(ItemModel item) {
		this.item = item;
	}
	public int getRental_id() {
		return rental_id;
	}
	public void setRental_id(int rental_id) {
		this.rental_id = rental_id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public int getHope_order() {
		return hope_order;
	}
	public void setHope_order(int hope_order) {
		this.hope_order = hope_order;
	}
	public int getSend_flag() {
		return send_flag;
	}
	public void setSend_flag(int send_flag) {
		this.send_flag = send_flag;
	}
	public int getReturn_flag() {
		return return_flag;
	}
	public void setReturn_flag(int return_flag) {
		this.return_flag = return_flag;
	}
	public Date getMake_date() {
		return make_date;
	}
	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
}
