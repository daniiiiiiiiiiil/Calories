
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AddCaloriesDay {

    static void addCaloriesDay(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        LocalDate selectedDate = null;
        while (selectedDate == null) {
            System.out.print("Введите дату (ГГГГ-ММ-ДД): ");
            String dateString = scanner.nextLine();
            try {
                selectedDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты. Попробуйте снова.");
            }
        }

        System.out.println("Если в списке нету продукта, то вы добавите его в список");
        String foodName = getFoodName(scanner);
        int calories = getIntegerInput(scanner, "Калории ");
        int protein = getIntegerInput(scanner, "Белки ");
        int fat = getIntegerInput(scanner, "Жиры ");
        int carbohydrate = getIntegerInput(scanner, "Углеводы ");
        int mealTime = getMealTimeChoice(scanner);


        try {
            int foodId = getOrCreateFoodId(connection, foodName, calories, protein, fat, carbohydrate);

            insertIntoCalorieLog(connection, foodId, calories, mealTime, selectedDate);
            System.out.println("Калории добавлены успешно!");

        } catch (SQLException e) {
            System.err.println("Ошибка добавления калорий: " + e.getMessage());
        }
    }

    private static int getOrCreateFoodId(Connection connection, String foodName, int calories, int protein, int fat, int carbohydrate) throws SQLException {
        String checkFoodExistsSql = "SELECT id FROM food WHERE name = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkFoodExistsSql)) {
            checkStatement.setString(1, foodName);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    // Insert new food item
                    String insertFoodSql = "INSERT INTO food (name, calories, protein, fat, carbohydrate) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement foodStatement = connection.prepareStatement(insertFoodSql)) {
                        foodStatement.setString(1, foodName);
                        foodStatement.setInt(2, calories);
                        foodStatement.setInt(3, protein);
                        foodStatement.setInt(4, fat);
                        foodStatement.setInt(5, carbohydrate);
                        foodStatement.executeUpdate();
                    }

                    String getFoodIdSql = "SELECT LAST_INSERT_ID()";
                    try (PreparedStatement getIdStatement = connection.prepareStatement(getFoodIdSql);
                         ResultSet idResultSet = getIdStatement.executeQuery()) {
                        if (idResultSet.next()) {
                            return idResultSet.getInt(1);
                        } else {
                            throw new SQLException("Failed to retrieve food ID after insertion.");
                        }
                    }
                }
            }
        }
    }

    //Helper function to insert data into calorie_log
    private static void insertIntoCalorieLog(Connection connection, int foodId, int calories, int mealTime, LocalDate selectedDate) throws SQLException {
        String insertCaloriesSql = "INSERT INTO calorie_log (user_id, food_id, calories_consumed, meat_time, date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement caloriesStatement = connection.prepareStatement(insertCaloriesSql)) {
            caloriesStatement.setInt(1, 1);
            caloriesStatement.setInt(2, foodId);
            caloriesStatement.setInt(3, calories);
            caloriesStatement.setInt(4, mealTime);
            caloriesStatement.setDate(5, Date.valueOf(selectedDate));
            caloriesStatement.executeUpdate();
        }
    }


    private static String getFoodName(Scanner scanner) {
        System.out.print("Введите название продукта: ");
        return scanner.nextLine();
    }

    private static int getIntegerInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Неверный ввод. Попробуйте снова.");
                scanner.next();
            }
        }
    }


    private static int getMealTimeChoice(Scanner scanner) {

        while (true) {
            System.out.println("В какое время дня был прием пищи:");
            System.out.println("1. Завтрак");
            System.out.println("2. Обед");
            System.out.println("3. Ужин");
            System.out.println("4. Другое");
            System.out.print("Выберите (1-4): ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Неверный ввод. Попробуйте снова.");
                scanner.next();
            }
        }
    }
}
