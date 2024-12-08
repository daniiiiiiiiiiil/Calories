import java.sql.*;

public class All_calories {
    public static void viewAllCalories(Connection connection) {
        String sql = "SELECT date, SUM(calories_consumed) AS total_calories FROM calorie_log GROUP BY date ORDER BY date;";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {//Выполняет SQL-запрос в этом объекте PreparedStatement и возвращает объект ResultSet , созданный запросом.
            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                int totalCalories = resultSet.getInt("total_calories");
                System.out.println("Дата: " + date + ", Калории: " + totalCalories);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения всех калорий: " + e.getMessage());
        }
    }
}