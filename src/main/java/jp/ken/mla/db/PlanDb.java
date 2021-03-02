package jp.ken.mla.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import jp.ken.mla.model.PlanModel;

public class PlanDb {

	private DataSource dataSource;

	public PlanDb(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public PlanModel getById(int id) {
		PlanModel pModel = new PlanModel();
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_plan_mst WHERE plan_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pModel.setPlan_id(rs.getInt("plan_id"));
				pModel.setPlan_name(rs.getString("plan_name"));
				pModel.setPlan_color(rs.getString("plan_color"));
				pModel.setMonthly(rs.getInt("monthly"));
				pModel.setLimit_cnt(rs.getInt("limit_cnt"));
			}
			return pModel;
		} catch (Exception e) {
			e.printStackTrace();
			return pModel;
		}
	}

}
