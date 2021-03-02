package jp.ken.mla.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import jp.ken.mla.model.LoginModel;

public class MemberDb {

	private DataSource dataSource;

	public MemberDb(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public LoginModel getByLogin(LoginModel lModel) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_member_tbl WHERE email = ? AND password = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, lModel.getEmail());
			ps.setString(2, lModel.getPassword());

			ResultSet rs = ps.executeQuery();
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

				PlanDb planDb = new PlanDb(dataSource);
				lModel.setPlan(planDb.getById(lModel.getPlan_id()));
			}
			return lModel;
		} catch (Exception e) {
			e.printStackTrace();
			return lModel;
		}
	}
}
