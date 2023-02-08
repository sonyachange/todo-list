package functions;

public class ValidateFunction {
    public static boolean isValidInputForAddTask(String name, String category, int priority,
                                                 int day, int hour, int minute) {
        if (!name.isEmpty() && !category.isEmpty() && (priority > 0 && priority<= 10)){
            if ((day >= 0 && day<= 31) && (hour >= 0 && hour <= 24) && (minute >= 0 && minute < 60)){
                return day != 0 || hour != 0 || minute != 0;
            }
        }
        return false;
    }
}
