import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class date_of_calories {
    public static void viewCaloriesByDate(Connection connection, String date) {
        Scanner scanner = new Scanner(System.in);
        int mealTimeInt;
        System.out.println("Введите время приема пищи (1.Завтрак,2.Обед,3.Ужин,4.Другое):");
        mealTimeInt = scanner.nextInt();
        String sql = "SELECT SUM(calories_consumed) AS total_calories FROM calorie_log WHERE date = CAST(? AS date) AND meat_time = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date);
            statement.setInt(2, mealTimeInt);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int totalCalories = resultSet.getInt("total_calories");
                    System.out.println("Калории за " + date + " (" + mealTimeInt + "): " + totalCalories);
                } else {
                    System.out.println("Нет данных за указанную дату и время приема пищи.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения калорий за дату: " + e.getMessage());
        }

    }
}