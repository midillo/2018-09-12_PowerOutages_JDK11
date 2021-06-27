package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Adiacenze;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutagesDAO {

	public void loadAllNercs(Map<Integer, Nerc> idMap) {

		String sql = "SELECT id, value FROM nerc";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(!idMap.containsKey(res.getInt("id"))) {
					Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
					idMap.put(res.getInt("id"), n);
				}

			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return;
	}

	public List<Adiacenze> getAdiacenze() {

		String sql = "SELECT p1.nerc_id AS n1, p2.nerc_id AS n2, COUNT(distinct MONTH(p1.date_event_began)) AS peso "
				+ "FROM nercrelations n, poweroutages p1, poweroutages p2 "
				+ "WHERE MONTH(p1.date_event_began) = MONTH(p2.date_event_began) "
				+ "AND YEAR(p1.date_event_began) = YEAR(p2.date_event_began) "
				+ "AND p1.nerc_id = n.nerc_one AND p2.nerc_id = n.nerc_two "
				+ "GROUP BY p1.nerc_id, p2.nerc_id ";
		List<Adiacenze> result = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Adiacenze(res.getInt("n1"), res.getInt("n2"), res.getDouble("peso")));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return result;
	}
	
	public List<Adiacenze> getConfinanti() {

		String sql = "SELECT nerc_one, nerc_two FROM nercrelations ";
		List<Adiacenze> result = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Adiacenze(res.getInt("nerc_one"), res.getInt("nerc_two"), null));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return result;
	}
}
