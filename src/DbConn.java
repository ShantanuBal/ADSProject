import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConn {

	public static void main(String[] argv) {

		System.out.println("-------- Oracle JDBC Connection Testing ------");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("Oracle JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:ORA11G", "system",
					"scuderia800");
			insertRecordIntoDbUserTable(connection);
			selectRecordsFromTable(connection);
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	private static void insertRecordIntoDbUserTable(Connection dbConnection) throws SQLException {

		Statement statement = null;

		String insertTableSQL = "INSERT INTO test_ads values (3, 'beeble')";

		try {
			statement = dbConnection.createStatement();
			// execute insert SQL stetement
			statement.executeUpdate(insertTableSQL);
			System.out.println("Record is inserted into table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
	}
	
	private static void selectRecordsFromTable(Connection dbConnection) throws SQLException {

		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT id, name FROM test_ads";

		try {
			preparedStatement = dbConnection.prepareStatement(selectSQL);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String userid = rs.getString("id");
				String username = rs.getString("name");
				System.out.println("userid : " + userid);
				System.out.println("username : " + username);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
