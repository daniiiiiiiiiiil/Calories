import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class date_of_calories {
    public static void viewCaloriesByDate(Connection connection, String date) {
        String sql = "SELECT SUM(calories_consumed) AS total_calories FROM calorie_log WHERE date = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int totalCalories = resultSet.getInt("total_calories");
                    System.out.println("Калории за " + date + ": " + totalCalories);
                } else {
                    System.out.println("Нет данных за указанную дату.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения калорий за дату: " + e.getMessage());
        }
    }
}