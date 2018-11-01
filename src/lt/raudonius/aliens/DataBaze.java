package lt.raudonius.aliens;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class DataBaze {
	ResultSet rs;
	Statement statement;
	Connection connection = null;
	private String name;
	private int points;

	public DataBaze() {

	}

	public void incertHighScore(String name, int score) throws ClassNotFoundException {
		List<String> names = new ArrayList<>();
		conection();

		try {
			while (rs.next()) {
				// read the result set
				names.add(rs.getString("name"));
			}
			if (!names.contains(name)) {
				PreparedStatement insert = this.connection.prepareStatement("insert into High_Scores values(?, ?)");
				insert.setString(1, name);
				insert.setInt(2, score);
				insert.executeUpdate();
			} else {
				if (getValue(name) < score) {
					PreparedStatement update = this.connection
							.prepareStatement("Update High_Scores set score = ? WHERE name = ?");
					update.setInt(1, score);
					update.setString(2, name);
					update.executeUpdate();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// closeConection();
	}

	private void conection() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Valdas\\Desktop\\database\\Rezults.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			rs = statement.executeQuery("SELECT * FROM High_Scores");
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
	}

	private void closeConection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// connection close failed.
			System.err.println(e);
		}
	}

	/*public void print() throws ClassNotFoundException {
		conection();
		
		System.out.println("name: " + "score");
		try {
			rs = statement.executeQuery("SELECT * FROM High_Scores ORDER BY score DESC LIMIT 5");
			while (rs.next()) {
				// read the result set
				System.out.println(rs.getString("name") + ": " + rs.getInt("score"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConection();
	}
*/
	public int getValue(String name) throws ClassNotFoundException {
		conection();
		int score = 0;
		try {
			while (rs.next()) {
				// read the result set
				if (rs.getString("name").equals(name)) {
					score = rs.getInt("score");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// closeConection();
		return score;
	}
	
	public  DefaultTableModel buildTableModel()
	        throws SQLException {
		  try {
				conection();
				rs = statement.executeQuery("SELECT * FROM High_Scores ORDER BY score DESC LIMIT 20");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    ResultSetMetaData metaData = rs.getMetaData();
	  
	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
