package jp.ken.mla.model;

import java.io.Serializable;

// 管理者用会員一覧から編集対象者のIDの受け渡しを行う。
// member_idという名称だと なぜかログインセッションを上書きする為、mod_idとする
public class ListMemberModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private int mod_id = 0;

	public int getMod_id() {
		return mod_id;
	}

	public void setMod_id(int mod_id) {
		this.mod_id = mod_id;
	}
}
