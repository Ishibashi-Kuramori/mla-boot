package jp.ken.mla.model;

import java.io.Serializable;
import java.util.Date;

public class ItemModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private int item_id;
	private String item_name;
	private String author_name;
	private int media_id;
	private int stock_cnt;
	private int order_cnt;
	private int new_old;
	private Date can_rental_date;
	private int add_point;
	private Date make_date;
	private Date update_date;
	private MediaModel media;

	public MediaModel getMedia() {
		return media;
	}
	public void setMedia(MediaModel media) {
		this.media = media;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public int getMedia_id() {
		return media_id;
	}
	public void setMedia_id(int media_id) {
		this.media_id = media_id;
	}
	public int getStock_cnt() {
		return stock_cnt;
	}
	public void setStock_cnt(int stock_cnt) {
		this.stock_cnt = stock_cnt;
	}
	public int getOrder_cnt() {
		return order_cnt;
	}
	public void setOrder_cnt(int order_cnt) {
		this.order_cnt = order_cnt;
	}
	public int getNew_old() {
		return new_old;
	}
	public void setNew_old(int new_old) {
		this.new_old = new_old;
	}
	public Date getCan_rental_date() {
		return can_rental_date;
	}
	public void setCan_rental_date(Date can_rental_date) {
		this.can_rental_date = can_rental_date;
	}
	public int getAdd_point() {
		return add_point;
	}
	public void setAdd_point(int add_point) {
		this.add_point = add_point;
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
