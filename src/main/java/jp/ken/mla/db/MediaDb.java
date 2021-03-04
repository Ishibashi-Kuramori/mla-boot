package jp.ken.mla.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.ken.mla.model.MediaModel;

@Repository
public class MediaDb {

	@Autowired
	private DataSource dataSource;

	// ID から媒体情報を1件取得
	public MediaModel getById(int id) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_media_mst WHERE media_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			return getRecord(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return new MediaModel();
		}
	}

	// -----------------------------
	// 内部処理メソッド
	// -----------------------------

	// 会員情報レコード取得
	private MediaModel getRecord(ResultSet rs) throws SQLException {
		MediaModel mModel = new MediaModel();
		while (rs.next()) {
			mModel.setMedia_id(rs.getInt("media_id"));
			mModel.setMedia_name(rs.getString("media_name"));
			mModel.setMedia_color(rs.getString("media_color"));
		}
		return mModel;
	}
}
