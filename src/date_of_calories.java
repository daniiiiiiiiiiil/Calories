import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class date_of_calories {
    public static void viewCaloriesToday(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Ожидаемый формат даты

        System.out.println("Введите дату (формат ГГГГ-MM-ДД):");
        String dateString = scanner.nextLine();

        try {
            Date date = dateFormat.parse(dateString); // Преобразуем строку в объект Date
            java.sql.Date sqlDate = new java.sql.Date(date.getTime()); // Преобразуем в java.sql.Date


            String sql = "SELECT " +
                    "SUM(CASE WHEN meat_time = 1 THEN calories_consumed ELSE 0 END) AS breakfast_calories," +
                    "SUM(CASE WHEN meat_time = 2 THEN calories_consumed ELSE 0 END) AS lunch_calories," +
                    "SUM(CASE WHEN meat_time = 3 THEN calories_consumed ELSE 0 END) AS dinner_calories," +
                    "SUM(CASE WHEN meat_time = 4 THEN calories_consumed ELSE 0 END) AS other_calories," +
                    "SUM(calories_consumed) AS total_calories " +
                    "FROM calorie_log " +
                    "WHERE date = ?;";


            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDate(1, sqlDate);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int breakfastCalories = resultSet.getInt("breakfast_calories");
                        int lunchCalories = resultSet.getInt("lunch_calories");
                        int dinnerCalories = resultSet.getInt("dinner_calories");
                        int otherCalories = resultSet.getInt("other_calories");
                        int totalCalories = resultSet.getInt("total_calories");

                        System.out.println("Калории за " + dateString + ":");
                        System.out.println("Завтрак: " + breakfastCalories);
                        System.out.println("Обед: " + lunchCalories);
                        System.out.println("Ужин: " + dinnerCalories);
                        System.out.println("Прочее: " + otherCalories);
                        System.out.println("Итого: " + totalCalories);
                    } else {
                        System.out.println("Нет данных за " + dateString + ".");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка получения калорий за дату: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Неверный формат даты. Используйте YYYY-MM-DD");
        }
    }
}
