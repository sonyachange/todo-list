package functions;

import model.Task;

public class PrintFunction {
    public static void printMenu() {
        System.out.println("\n Введите нужный пункт для начала работы ");
        System.out.println("1. Добавить новую задачу ");
        System.out.println("2. Вывести все задачи ");
        System.out.println("3. Вывести задачи по введенному вами имени ");
        System.out.println("4. Удаление задачи по введенному вами имени ");
        System.out.println("5. Изменение статуса задачи ");
        System.out.println("6. Укажите тему категории ");
        System.out.println("7. Показать сгоревшие задачи \n");
    }

    public static void printTask(Task task) {
        System.out.println("----------------------------------------");
        System.out.println("Название: " + task.getName());
        System.out.println("Категория: " + task.getCategory());
        System.out.println("Приоритет: " + task.getPriority());
        System.out.println("Дата: " + task.getCreatedAt());
        System.out.println("Окончание срока выполнения задачи: " + task.getDeadline());
        System.out.println("Статус: " + task.getStatus());
    }
}
