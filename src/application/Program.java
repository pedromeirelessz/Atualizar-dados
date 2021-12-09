package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DB.getConnection();
			ps = conn.prepareStatement("UPDATE seller SET BaseSalary = BaseSalary + ?"
					// Nunca faça uma atualização sem colocar o "WHERE" se não você irá
					// atualizar todos os dados
					+ "WHERE DepartmentId = ?;", 
					Statement.RETURN_GENERATED_KEYS);

			ps.setDouble(1, 200.0);
			ps.setInt(2, 2);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id" + id);
				}
			} else {
				System.out.println("No have rows affected");
			}

		} catch (SQLException SQL) {
			System.out.println(SQL.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeConnection();
		}
	}
}
