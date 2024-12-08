import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Сalories_today {
    public static void viewCaloriesToday(Connection connection) {

        String sql = "SELECT SUM(CASE WHEN meat_time = 1 THEN calories_consumed ELSE 0 END) AS breakfast_calories,SUM(CASE WHEN meat_time = 2 THEN calories_consumed ELSE 0 END) AS lunch_calories,SUM(CASE WHEN meat_time = 3 THEN calories_consumed ELSE 0 END) AS dinner_calories,SUM(CASE WHEN meat_time = 4 THEN calories_consumed ELSE 0 END) AS other_calories,SUM(calories_consumed) AS total_calories FROM calorie_log WHERE date = CURRENT_DATE;";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int breakfastCalories = resultSet.getInt("breakfast_calories");
                int lunchCalories = resultSet.getInt("lunch_calories");
                int dinnerCalories = resultSet.getInt("dinner_calories");
                int otherCalories = resultSet.getInt("other_calories");
                int totalCalories = resultSet.getInt("total_calories");

                System.out.println("Калории за сегодня:");
                System.out.println("Завтрак: " + breakfastCalories);
                System.out.println("Обед: " + lunchCalories);
                System.out.println("Ужин: " + dinnerCalories);
                System.out.println("Прочее: " + otherCalories);
                System.out.println("Итого: " + totalCalories);
            } else {
                System.out.println("Нет данных за сегодня.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения калорий за сегодня: " + e.getMessage());
        }
    }
}