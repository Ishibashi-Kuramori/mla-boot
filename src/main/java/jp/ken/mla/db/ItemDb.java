package jp.ken.mla.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.ken.mla.model.ItemModel;

@Repository
public class ItemDb {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private MediaDb mediaDb;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	// ID から商品情報を1件取得
	public ItemModel getById(int id) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_item_tbl WHERE item_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			List<ItemModel> iList = getRecord(rs);
			return iList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return new ItemModel();
		}
	}

	// 商品情報を全件取得
	public List<ItemModel> allList() {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_item_tbl";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return getRecord(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	// 新作/旧作の上位5作品を取得
	public List<ItemModel> top5List(int new_old) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_item_tbl WHERE new_old = ? ORDER BY can_rental_date DESC LIMIT 5";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, new_old);
			ResultSet rs = ps.executeQuery();
			return getRecord(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	// 検索結果を取得
	public List<ItemModel> searchList(String word) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_item_tbl WHERE item_name LIKE ? OR author_name LIKE ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, "%" + word + "%");
			ps.setString(2, "%" + word + "%");
			ResultSet rs = ps.executeQuery();
			return getRecord(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	// 商品情報を編集
	public boolean updateItemData(ItemModel iModel) {
		int numRow =0;
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "UPDATE mla_item_tbl SET ";
			sql        += "item_name = ?, author_name = ?, media_id = ?, stock_cnt = ?, order_cnt = ?, ";
			sql        += "new_old = ?, can_rental_date = ?, add_point = ?, update_date = NOW() ";
			sql        += "WHERE item_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, iModel.getItem_name());
			ps.setString(2, iModel.getAuthor_name());
			ps.setInt(3, iModel.getMedia_id());
			ps.setInt(4, iModel.getStock_cnt());
			ps.setInt(5, iModel.getOrder_cnt());
			ps.setInt(6, iModel.getNew_old());
			ps.setDate(7, new java.sql.Date(iModel.getCan_rental_date().getTime()));
			ps.setInt(8, iModel.getAdd_point());
			ps.setInt(9, iModel.getItem_id());

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

	// 商品情報レコード取得
	private List<ItemModel> getRecord(ResultSet rs) throws SQLException {
		List<ItemModel> iList = new ArrayList<>();
		while (rs.next()) {
			ItemModel iModel = new ItemModel();
			iModel.setItem_id(rs.getInt("item_id"));
			iModel.setItem_name(rs.getString("item_name"));
			iModel.setAuthor_name(rs.getString("author_name"));
			iModel.setMedia_id(rs.getInt("media_id"));
			iModel.setStock_cnt(rs.getInt("stock_cnt"));
			iModel.setOrder_cnt(rs.getInt("order_cnt"));
			iModel.setNew_old(rs.getInt("new_old"));
			iModel.setCan_rental_date(rs.getDate("can_rental_date"));
			iModel.setAdd_point(rs.getInt("add_point"));
			iModel.setMake_date(rs.getDate("make_date"));
			iModel.setUpdate_date(rs.getDate("update_date"));
			iModel.setMedia(mediaDb.getById(iModel.getMedia_id()));
			iList.add(iModel);
		}
		return iList;
	}
}
