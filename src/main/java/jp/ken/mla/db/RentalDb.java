package jp.ken.mla.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.ken.mla.model.RentalModel;

@Repository
public class RentalDb {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ItemDb itemDb;

	// 会員IDに紐付くレンタル情報を全件取得
	public List<RentalModel> getByMemberId(int id) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_rental_tbl WHERE member_id = ? ORDER BY hope_order";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			return getRecord(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	// レンタル情報リストからIDのみを取得
	public List<Integer> getByRentalItemIds(int id) {
		List<RentalModel> rList = getByMemberId(id);
		List<Integer> retList = new ArrayList<Integer>();
		for(int i = 0; i < rList.size(); i++) {
			RentalModel rModel = rList.get(i);
			retList.add(rModel.getItem_id());
		}
		return retList;
	}

	// 希望順新規採番値を取得
	public int getHopeOrder(int id) {
		int ret = 0;
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT MAX(hope_order) FROM mla_rental_tbl WHERE member_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ret = rs.getInt("max");
			}
			ret += 1;
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	// レンタル情報を追加
	public boolean insertRentalData(RentalModel rModel) {
		int numRow =0;
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "INSERT INTO mla_rental_tbl (";
			sql        += "member_id, item_id, order_date, hope_order,";
			sql        += "send_flag, return_flag, make_date, update_date";
			sql        += ") VALUES(?, ?, NOW(), ?, 0, 0, NOW(), NOW())";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, rModel.getMember_id());
			ps.setInt(2, rModel.getItem_id());
			ps.setInt(3, getHopeOrder(rModel.getMember_id()));

			numRow = ps.executeUpdate();
			if(numRow > 0) {
				connection.commit();
				ps.close();
				return true;

			} else {
				connection.rollback();
				ps.close();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// レンタル情報を削除
	public boolean deleteRentalData(RentalModel rModel) {
		int numRow =0;
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "DELETE FROM mla_rental_tbl WHERE member_id = ? AND item_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, rModel.getMember_id());
			ps.setInt(2, rModel.getItem_id());
			numRow = ps.executeUpdate();
			if(numRow > 0) {
				connection.commit();
				ps.close();
				return true;

			} else {
				connection.rollback();
				ps.close();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// レンタルIDに紐付く希望順を更新
	public boolean updateRentalHope(String ids) {
		int numRow =0;
		String[] idsArr = ids.split(",", 0);
		if(idsArr.length < 2) {
			return false; // 2件以下では来ないハズなのでエラー
		}
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "UPDATE mla_rental_tbl SET update_date = NOW(), hope_order = CASE rental_id ";
			for(int i = 0; i < idsArr.length; i++) {
				sql += "WHEN " + idsArr[i] + " THEN " + (i + 1) + " ";
			}
			sql += "END WHERE rental_id IN (" + ids + ")";
			PreparedStatement ps = connection.prepareStatement(sql);
			numRow = ps.executeUpdate();
			if(numRow > 0) {
				connection.commit();
				ps.close();
				return true;

			} else {
				connection.rollback();
				ps.close();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	// -----------------------------
	// 内部処理メソッド
	// -----------------------------

	// レンタル情報レコード取得
	private List<RentalModel> getRecord(ResultSet rs) throws SQLException {
		List<RentalModel> rList = new ArrayList<>();
		while (rs.next()) {
			RentalModel rModel = new RentalModel();
			rModel.setRental_id(rs.getInt("rental_id"));
			rModel.setMember_id(rs.getInt("member_id"));
			rModel.setItem_id(rs.getInt("item_id"));
			rModel.setOrder_date(rs.getDate("order_date"));
			rModel.setHope_order(rs.getInt("hope_order"));
			rModel.setSend_flag(rs.getInt("send_flag"));
			rModel.setReturn_flag(rs.getInt("return_flag"));
			rModel.setMake_date(rs.getDate("make_date"));
			rModel.setUpdate_date(rs.getDate("update_date"));
			rModel.setItem(itemDb.getById(rModel.getItem_id()));
			rList.add(rModel);
		}
		return rList;
	}
}
