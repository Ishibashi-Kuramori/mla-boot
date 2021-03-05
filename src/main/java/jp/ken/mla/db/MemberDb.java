package jp.ken.mla.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.ken.mla.model.LoginModel;

@Repository
public class MemberDb {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PlanDb planDb;

	// ID から会員情報を1件取得
	public LoginModel getById(int id) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_member_tbl WHERE member_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			return getRecord(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return new LoginModel();
		}
	}

	// Mail から会員情報を1件取得
	public LoginModel getByMail(String email) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_member_tbl WHERE email = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			return getRecord(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return new LoginModel();
		}
	}

	// Mail & Password から会員情報を1件取得
	public LoginModel getByLogin(LoginModel lModel) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_member_tbl WHERE email = ? AND password = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, lModel.getEmail());
			ps.setString(2, lModel.getPassword());

			ResultSet rs = ps.executeQuery();
			BeanUtils.copyProperties(getRecord(rs), lModel);
			return lModel;
		} catch (Exception e) {
			e.printStackTrace();
			return lModel;
		}
	}

	// 会員情報を追加
	public boolean insertMemberData(LoginModel lModel) {
		int numRow =0;
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "INSERT INTO mla_member_tbl (";
			sql        += "member_name, email, password, admin, plan_id, pay_id, ";
			sql        += "total_point, icon_idx, join_date, make_date, update_date";
			sql        += ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), NOW())";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, lModel.getMember_name());
			ps.setString(2, lModel.getEmail());
			ps.setString(3, lModel.getPassword());
			ps.setInt(4, 0); // 追加時は会員権限
			ps.setInt(5, 0); // 追加時はお試しプラン
			ps.setInt(6, lModel.getPay_id());
			ps.setInt(7, 0); // 追加時はポイント0
			ps.setInt(8, lModel.getIcon_idx());

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

	// 会員情報を編集
	public boolean updateMemberData(LoginModel lModel) {
		int numRow =0;
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "UPDATE mla_member_tbl SET ";
			sql        += "member_name = ?, email = ?, password = ?, admin = ?, plan_id = ?, ";
			sql        += "pay_id = ?, total_point = ?, icon_idx = ?, update_date = NOW() ";
			sql        += "WHERE member_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, lModel.getMember_name());
			ps.setString(2, lModel.getEmail());
			ps.setString(3, lModel.getPassword());
			ps.setInt(4, lModel.getAdmin());
			ps.setInt(5, lModel.getPlan_id());
			ps.setInt(6, lModel.getPay_id());
			ps.setInt(7, lModel.getTotal_point());
			ps.setInt(8, lModel.getIcon_idx());
			ps.setInt(9, lModel.getMember_id());

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

	// 会員情報を削除
	public boolean deleteMemberData(int id) {
		int numRow =0;
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "DELETE FROM mla_member_tbl WHERE member_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
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

	// 会員情報レコード取得
	private LoginModel getRecord(ResultSet rs) throws SQLException {
		LoginModel lModel = new LoginModel();
		while (rs.next()) {
			lModel.setMember_id(rs.getInt("member_id"));
			lModel.setMember_name(rs.getString("member_name"));
			lModel.setEmail(rs.getString("email"));
			lModel.setPassword(rs.getString("password"));
			lModel.setAdmin(rs.getInt("admin"));
			lModel.setPlan_id(rs.getInt("plan_id"));
			lModel.setPay_id(rs.getInt("pay_id"));
			lModel.setTotal_point(rs.getInt("total_point"));
			lModel.setIcon_idx(rs.getInt("icon_idx"));
			lModel.setJoin_date(rs.getDate("join_date"));
			lModel.setMake_date(rs.getDate("make_date"));
			lModel.setUpdate_date(rs.getDate("update_date"));
			lModel.setPlan(planDb.getById(lModel.getPlan_id()));
		}
		return lModel;
	}

}
