
package element.memory.game;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    // Bağlantı dizesi (Kendi bilgisayarına göre düzenlemelisin)
    private static final String connectionUrl = 
        "jdbc:sqlserver://localhost:1433;databaseName=YourDatabaseName;user=YourUsername;password=YourPassword;encrypt=false;";

    // Skoru Kaydetme (INSERT)
    public static void saveScore(String playerName, int moves) {
        String sql = "INSERT INTO GameScores (PlayerName, Moves) VALUES (?, ?)";
        
        try (Connection con = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, playerName);
            pstmt.setInt(2, moves);
            pstmt.executeUpdate();
            System.out.println("Score saved to MS SQL!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // En İyi Skorları Çekme (SELECT)
    public static String getLeaderboard() {
        StringBuilder sb = new StringBuilder("--- TOP SCORES ---\n");
        String sql = "SELECT TOP 5 PlayerName, Moves FROM GameScores ORDER BY Moves ASC";

        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sb.append(rs.getString("PlayerName"))
                  .append(": ")
                  .append(rs.getInt("Moves"))
                  .append(" moves\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
