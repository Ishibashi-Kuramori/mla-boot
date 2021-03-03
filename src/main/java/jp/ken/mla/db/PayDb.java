package jp.ken.mla.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.ken.mla.model.PayModel;

@Repository
public class PayDb {

	@Autowired
	private DataSource dataSource;

	public List<PayModel> allList() {
		List<PayModel> payList = new ArrayList<PayModel>();
		try (Connection connection = dataSource.getConnection()) {
			String sql = "SELECT * FROM mla_pay_mst";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PayModel pModel = new PayModel();
				pModel.setPay_id(rs.getInt("pay_id"));
				pModel.setPay_name(rs.getString("pay_name"));
				payList.add(pModel);
			}
			return payList;
		} catch (Exception e) {
			e.printStackTrace();
			return payList;
		}
	}

}
