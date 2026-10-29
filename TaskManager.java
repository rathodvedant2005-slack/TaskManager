import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Stores the details of a task.
class Task {

    private static int nextId = 1;

    private final int id;
    private String title;
    private String description;
    private String status;

    public Task(String title, String description) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.status = "Pending";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Shows all the task details on one line.
    @Override
    public String toString() {
        return "ID: " + id
                + " | Title: " + title
                + " | Description: " + description
                + " | Status: " + status;
    }
}

// Handles the task manager menu and its operations.
public class TaskManager {

    private static final List<Task> tasks = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        // Repeat the menu until the user decides to exit.
        do {
            showMenu();
            choice = readNumber("Choose an option: ");

            switch (choice) {
                case 1:
                    createTask();
                    break;

                case 2:
                    viewTasks();
                    break;

                case 3:
                    updateTask();
                    break;

                case 4:
                    deleteTask();
                    break;

                case 5:
                    System.out.println("Thanks for using Task Manager. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 5.");
            }

        } while (choice != 5);

        scanner.close();
    }

    // Displays the main menu.
    private static void showMenu() {
        System.out.println("\n========== TASK MANAGER ==========");
        System.out.println("1. Create a new task");
        System.out.println("2. View all tasks");
        System.out.println("3. Update a task");
        System.out.println("4. Delete a task");
        System.out.println("5. Exit");
        System.out.println("==================================");
    }

    // Takes the details and adds a new task.
    private static void createTask() {

        String title = readRequiredText("Enter task title: ");

        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();

        Task task = new Task(title, description);
        tasks.add(task);

        System.out.println("Task added successfully! Your task ID is " + task.getId());
    }

    // Shows all the tasks currently saved in the list.
    private static void viewTasks() {

        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty.");
            return;
        }

        System.out.println("\n---------- YOUR TASKS ----------");

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    // Lets the user change an existing task.
    private static void updateTask() {

        if (tasks.isEmpty()) {
            System.out.println("There are no tasks available to update.");
            return;
        }

        int id = readNumber("Enter the ID of the task to update: ");
        Task task = findTask(id);

        if (task == null) {
            System.out.println("Sorry, no task exists with that ID.");
            return;
        }

        System.out.println("\nCurrent task details:");
        System.out.println(task);

        System.out.println("\nLeave a field empty if you don't want to change it.");

        System.out.print("Enter new title: ");
        String newTitle = scanner.nextLine().trim();

        if (!newTitle.isEmpty()) {
            task.setTitle(newTitle);
        }

        System.out.print("Enter new description: ");
        String newDescription = scanner.nextLine().trim();

        if (!newDescription.isEmpty()) {
            task.setDescription(newDescription);
        }

        System.out.println("\nChoose the new status:");
        System.out.println("1. Pending");
        System.out.println("2. In Progress");
        System.out.println("3. Completed");
        System.out.print("Enter your choice (or press Enter to skip): ");

        String statusChoice = scanner.nextLine().trim();

        switch (statusChoice) {
            case "1":
                task.setStatus("Pending");
                break;

            case "2":
                task.setStatus("In Progress");
                break;

            case "3":
                task.setStatus("Completed");
                break;

            case "":
                // No change to the current status.
                break;

            default:
                System.out.println("Invalid status choice. Keeping the old status.");
        }

        System.out.println("Task details updated successfully!");
    }

    // Removes a task using its ID.
    private static void deleteTask() {

        if (tasks.isEmpty()) {
            System.out.println("There are no tasks available to delete.");
            return;
        }

        int id = readNumber("Enter the ID of the task to delete: ");
        Task task = findTask(id);

        if (task == null) {
            System.out.println("Sorry, no task exists with that ID.");
            return;
        }

        tasks.remove(task);
        System.out.println("Task deleted successfully!");
    }

    // Searches the list for a task with the given ID.
    private static Task findTask(int id) {

        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }

        return null;
    }

    // Keeps asking until a valid whole number is entered.
    private static int readNumber(String message) {

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    // Makes sure the user enters a task title.
    private static String readRequiredText(String message) {

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Task title cannot be empty. Please try again.");
        }
    }
}