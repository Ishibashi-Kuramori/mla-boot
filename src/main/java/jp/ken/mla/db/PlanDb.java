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

import jp.ken.mla.model.PlanModel;

@Repository
public class PlanDb {

	@Autowired
	private DataSource dataSource;

	public List<PlanModel> allList() {
		List<PlanModel> planList = new ArrayList<PlanModel>();
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_plan_mst";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return getRecord(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return planList;
		}
	}

	public PlanModel getById(int id) {
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_plan_mst WHERE plan_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<PlanModel> planList = getRecord(rs);
			return planList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return new PlanModel();
		}
	}

	// -----------------------------
	// 内部処理メソッド
	// -----------------------------

	// プラン情報レコード取得
	private List<PlanModel> getRecord(ResultSet rs) throws SQLException {
		List<PlanModel> planList = new ArrayList<PlanModel>();
		while (rs.next()) {
			PlanModel pModel = new PlanModel();
			pModel.setPlan_id(rs.getInt("plan_id"));
			pModel.setPlan_name(rs.getString("plan_name"));
			pModel.setPlan_color(rs.getString("plan_color"));
			pModel.setMonthly(rs.getInt("monthly"));
			pModel.setLimit_cnt(rs.getInt("limit_cnt"));
			planList.add(pModel);
		}
		return planList;
	}

}
