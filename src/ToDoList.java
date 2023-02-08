import model.Constant;
import model.Task;
import model.TaskStatus;
import functions.PrintFunction;
import functions.ValidateFunction;
import util.file.FileUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static functions.PrintFunction.printTask;
import static model.Constant.*;

public class ToDoList {
    static List<Task> taskList = new ArrayList<>();
    static List<Task> tasksRemove = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    /**
     * model.Model.Task - {
     *     name - String,
     *     priority - Integer,
     *     createdAt - LocalDateTime,
     *     deadline - LocalDateTime,
     *     status - DONE, IN_PROGRESS, READY_FOR_WORK, CANCELED
     * }
     * ToDo list
     * 1) Добавлять новое дело +
     * 2) Удалять дело по какому-то параметру +
     * 3) Получать все дела +
     * 4) Получать дела по названию +
     * 5) Блок меню +
     * 6) Переводить задачу по статусной модели +
     * 7) Сохранение задач в файл +
     * 8) Вычитывание из файла при запуске программы +
     * 9) Поиск задач по категории
     * 10) сгорание задач по окончании времени
     */

    public static void main(String[] args) {
        menu();
    }

    /**
     * метод для создания меню
     */
    private static void menu() {
        FileUtil.readFile(taskList, "./active_task.dat");
        FileUtil.readFile(tasksRemove, "./archival_task.dat");

        int choice;

        while (true){
            PrintFunction.printMenu();
            choice = scanner.nextInt();
            switch (choice){
                case 1: {
                    addTask();
                    scanner.nextLine();
                } break;
                case 2: {
                    taskList.forEach(PrintFunction::printTask);
                    scanner.nextLine();
                } break;
                case 3: {
                    getByName();
                    scanner.nextLine();
                } break;
                case 4: {
                    removeTask();
                    scanner.nextLine();
                } break;
                case 5: {
                    changeStatus();
                    scanner.nextLine();
                } break;
                case 6: {
                    searchTasksByCategory();
                    scanner.nextLine();
                } break;
                case 7: {
                    endingTasks();
                    scanner.nextLine();
                } break;

                default:
                    System.out.println(Constant.FUNCTION_LIMITATION);
            }

        }

    }

    /**
     * метод для добавления задачи
     */
    private static void addTask() {
        try {
            System.out.println(CREATING_A_NAME);
            scanner.nextLine();
            String name = scanner.nextLine();

            System.out.println(CREATING_A_CATEGORY);
            String category = scanner.nextLine();

            System.out.println(CREATING_A_PRIORITY);
            int priority = scanner.nextInt();

            System.out.println(CREATING_A_DAY);
            int day = scanner.nextInt();

            System.out.println(CREATING_A_HOURS);
            int hour = scanner.nextInt();

            System.out.println(CREATING_A_MINUTE);
            int minute = scanner.nextInt();

            if (!ValidateFunction.isValidInputForAddTask(name,category,priority,day,hour,minute)){
                System.out.println(INCORRECT_DATA);
                return;
            }

            LocalDateTime createdAt = LocalDateTime.now();
            LocalDateTime deadLine = createdAt.plusDays(day).plusHours(hour).plusMinutes(minute);

            Task task = new Task(name,category,priority,createdAt, deadLine, TaskStatus.READY_FOR_WORK);
            taskList.add(task);

            FileUtil.writeFile(task, "./active_task.dat");

        } catch (Exception e){
            System.out.println("Вы ввели неккоректные данные ");
        }
    }

    /**
     * метод для поиска задачи по имени
     */
    private static void getByName() {
        System.out.println(Constant.ENTER_NAME_TO_SEARCH);
        scanner.nextLine();
        String name = scanner.nextLine();

        taskList.stream().filter((task) -> task.getName().equalsIgnoreCase(name))
                .forEach(PrintFunction::printTask);
    }

    /**
     * метод для удаления задачи по параметру (имя)
     */
    private static void removeTask() {
        System.out.println(Constant.ENTER_NAME_TO_REMOVE);
        scanner.nextLine();
        String name = scanner.nextLine();

        List<Task> tasksForDeleteInFile = new ArrayList<>();
        if (taskList.stream().anyMatch((tasks) -> tasks.getName().equalsIgnoreCase(name))) {
            for (Task task : taskList) {
                if (task.getName().equalsIgnoreCase(name)) {
                    tasksForDeleteInFile.add(task);
                }
            }

            taskList.removeAll(tasksForDeleteInFile);
            System.out.printf(Constant.TASK_REMOVED,
                    tasksForDeleteInFile.size());

            return;
        }

        System.out.println(Constant.TASK_NOT_FOUND);
    }

    /**
     * метод для изменения статуса в ходе сохдания задачи
     */
    private static void changeStatus() {
        System.out.println(Constant.NAME_TASK);
        scanner.nextLine();
        String name = scanner.nextLine();

        taskList.forEach(task -> {
            if (task.getName().equalsIgnoreCase(name)) {
                System.out.printf(Constant.CHOOSE_STATUS,
                        TaskStatus.READY_FOR_WORK,
                        TaskStatus.IN_PROGRESS,
                        TaskStatus.DONE,
                        TaskStatus.CANCELED);

                String status = scanner.nextLine();

                TaskStatus newStatus = TaskStatus.valueOf(status);
                task.setStatus(newStatus);

                System.out.println(Constant.CHANGED_STATUS.concat(newStatus.toString()));
            }
        });
    }

    /**
     * поиск задач по заданной категории
     */
    private static void searchTasksByCategory(){
        System.out.println(Constant.SELECT_A_CATEGORY);
        scanner.nextLine();
        String category = scanner.nextLine();

        taskList.forEach(task -> {
            if (task.getCategory().equalsIgnoreCase(category)){
                printTask(task);
            }
        });
    }

    /**
     * сгорание задач по окончании их дедлайна
     */
    private static void endingTasks(){
        Task potentialToRemoveTask = null;

        for (Task task : taskList) {
            if (task.getDeadline().isBefore(LocalDateTime.now())){
                tasksRemove.add(task);
                potentialToRemoveTask = task;
                FileUtil.writeFile(potentialToRemoveTask,"./archival_task.dat");
            }
        }

        if (potentialToRemoveTask != null) {
            taskList.remove(potentialToRemoveTask);
        }

        System.out.println(tasksRemove);

    }
}
// Дописать функционал по сгорающим задачам в ToDoList
// Почитать про блок Try/catch и исключения +
// Добавить обработку исключения при некорректном вводе (например: пользователь вводит букву s на строке с nextInt())
// - необходимо обработать это исключение и не прекращать работу программы +
// Добавить удаление из файла при удалении задачи, так же удаление из файла при удаление истекшей задачи
