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
			List<MediaModel> mediaList = getRecord(rs);
			return mediaList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return new MediaModel();
		}
	}

	// 媒体情報を全件取得
	public List<MediaModel> allList() {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_media_mst";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return getRecord(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<MediaModel>();
		}
	}

	// -----------------------------
	// 内部処理メソッド
	// -----------------------------

	// 会員情報レコード取得
	private List<MediaModel> getRecord(ResultSet rs) throws SQLException {
		List<MediaModel> mediaList = new ArrayList<MediaModel>();
		while (rs.next()) {
			MediaModel mModel = new MediaModel();
			mModel.setMedia_id(rs.getInt("media_id"));
			mModel.setMedia_name(rs.getString("media_name"));
			mModel.setMedia_color(rs.getString("media_color"));
			mediaList.add(mModel);
		}
		return mediaList;
	}
}
