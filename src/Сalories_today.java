import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Сalories_today {
    public static void viewCaloriesToday(Connection connection) {
        String sql = "SELECT SUM(calories_consumed) AS total_calories FROM calorie_log WHERE date = CURRENT_DATE;";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int totalCalories = resultSet.getInt("total_calories");
                System.out.println("Калории за сегодня: " + totalCalories);
            } else {
                System.out.println("Нет данных за сегодня.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения калорий за сегодня: " + e.getMessage());
        }
    }
}