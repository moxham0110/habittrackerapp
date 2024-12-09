import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class HabitAppService {
    public Scanner scanner = new Scanner(System.in);
    public List<Habit> habits = new ArrayList<>();
    public boolean exit = false;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        HabitAppService service = new HabitAppService();

        service.createDemoData();
        service.beginApplication();
    }

    void createDemoData(){
        Habit h1 = new DailyHabit("Brush teeth", LocalDate.now().minusDays(10), Category.HEALTH);
        Habit h2 = new WeeklyHabit("Attend Yoga Class", LocalDate.now().minusWeeks(6), Category.FITNESS, true);

        habits.add(h1);
        habits.add(h2);
    }

    void beginApplication(){
        String menuTemplate = """
        =============== Habit Tracker Menu ===============
        1. Create a habit
        2. Manage your habits
        3. Track progress
        4. Exit
        Enter your choice: 
        """;

        while (!exit) {
            System.out.print(menuTemplate);

            // Capture input and handle it using Pattern Matching for `switch`
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> createHabitOption();
                case "2" -> manageHabitsOption();
                case "3" -> trackProgressOption();
                case "4" -> {
                    System.out.println("Goodbye!");
                    exit = true;
                }
                default -> System.out.println("Invalid input. Please enter a valid choice.");
            }
        }
        scanner.close();
    }

    private void trackProgressOption() {
        String trackProgressMenu = """
        =============== Progress Statistics ===============
        1. Display all current streaks
        2. Display completion percentage by habit or category
        3. Return to main menu
        Enter your choice: 
        """;

        boolean progressMenuExit = false;

        while (!progressMenuExit) {
            System.out.print(trackProgressMenu);

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    displayAllCurrentStreaks();
                }
                case "2" -> {
                    do {
                        System.out.println("""
                                Completion Statistics:
                                1. By Habit
                                2. By Category
                                Enter a valid option!
                                """);
                        input = scanner.nextLine();

                    } while (!input.equals("1") && !input.equals("2"));


                    if (input.equals("1")){
                        displayCompletionStats();
                    }else{
                        System.out.println("Pick a category");

                        var allCategories = Category.values();

                        for(int i = 0; i < allCategories.length; i++){
                            System.out.println(i + ". " + allCategories[i]);
                        }
                        try{
                            input = scanner.nextLine();
                            displayCompletionStats(allCategories[Integer.parseInt(input)]);

                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("No category under this menu option");
                        } catch (Exception e){
                            System.out.println("Invalid input entered. Please enter a menu option number!");
                        }
                    }
                }
                case "3" -> {
                    System.out.println("Returning to main menu!");
                    progressMenuExit = true;
                }
                default -> {
                    System.out.println("Invalid input. Please enter a valid choice.");
                }
            }
        }


    }

    private void displayCompletionStats() {
        System.out.println("Completion Percentages for all habits");

        for (Habit habit : habits) {
            var pct = habit.calculateCompletionPercentage(habit.getHabitHistory());
            System.out.println(habit.getName() + ":  " + pct);
        }
    }

    private void displayCompletionStats(Category category) {
        // Predicate used to filter by a habits category
        Predicate<Habit> isCategory = habit -> habit.getCategory() == category;

        System.out.println("Completion Percentages for habits under - " + category);

        for (Habit habit : habits) {
            if (isCategory.test(habit)) {
                var pct = habit.calculateCompletionPercentage(habit.getHabitHistory());
                System.out.println(habit.getName() + ":  " + pct);
            }
        }
    }

    private void displayAllCurrentStreaks() {
        System.out.println("Current streaks: \n");
        for (Habit habit : habits){
            StringBuilder sb = new StringBuilder();
            sb.append(habit.getName()).append(" ------>  ").append(habit.calculateCurrentStreak());
            System.out.println("\n" + sb.toString());
        }
    }

    private void createHabitOption(){
        try {
            System.out.println("\n\nCreating a habit...........");
            System.out.println("Enter the name of the habit: ");
            String name = scanner.nextLine();

            System.out.print("Enter a habit category: ");
            Category category = Category.valueOf(scanner.nextLine());

            System.out.println("Enter a start date (DD/MM/YYYY): ");
            String dateInput = scanner.nextLine();

            LocalDate startDate = LocalDate.parse(dateInput, formatter);

            System.out.println("Enter habit frequency: \n1. Daily\n2. Weekly");
            int frequency = Integer.parseInt(scanner.nextLine().trim());

            Habit habit;
            if (frequency == 1) {
                habit = new DailyHabit(name, startDate, category);
            } else {
                habit = new WeeklyHabit(name, startDate, category);
            }

            habits.add(habit);
            System.out.println("Habit added: " + name);
        } catch (Exception e) {
            System.out.println("Invalid data entered. Please try again!");
            createHabitOption();
        }
    }

    void manageHabitsOption(){
        String habitManagementMenu = """
        =============== Habit Management ===============
        1. Update habit status
        2. Delete habit
        3. Return to main menu
        Enter your choice: 
        """;

        boolean managementExit = false;

        while (!managementExit) {
            System.out.print(habitManagementMenu);

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    changeHabitStatusOption();
                }
                case "2" -> {
                    deleteHabitOption();
                }
                case "3" -> {
                    System.out.println("Returning to main menu!");
                    managementExit = true;
                }
                default -> {
                    System.out.println("Invalid input. Please enter a valid choice.");
                }
            }
        }
    }

    private void deleteHabitOption() {
        displayListOfHabits();

        try{
            int input = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Confirm deletion of habit: (y/n)" + habits.get(input).getName());

            String confirm = scanner.nextLine();

            if(confirm.equals("y")){
                habits.remove(input);
                System.out.println("Habit deleted!");
            }else{
                System.out.println("Habit delete cancelled!");
            }
        } catch (Exception e) {
            System.out.println("Invalid input! No habits deleted. Try again!");
            deleteHabitOption();
        }
    }

    private void displayListOfHabits() {
        StringBuilder habitsTemplate = new StringBuilder("""
                =============== Your Habits ===============
                """);

        for(int i = 0; i < habits.size(); i++){
            habitsTemplate.append("\n").append(i).append(". ").append(habits.get(i).getName());
        }

        habitsTemplate.append("\nSelect a habit: (-1 to return)");

        System.out.println(habitsTemplate.toString());
    }

    private void changeHabitStatusOption() {
        displayListOfHabits();

        try {
            int input = Integer.parseInt(scanner.nextLine().trim());
            if(input == -1){
                return;
            }
            Habit habit = habits.get(input);
            habit.displayHabitHistory();

            System.out.println("Enter the date for status change (DD/MM/YYYY):");
            String dateForChange = scanner.nextLine();
            boolean datePresent = false;

            for (HistoryEntry entry : habit.getHabitHistory()){
                if (entry.getEntryDate().format(formatter).equals(dateForChange)){
                    entry.switchStatus();
                    datePresent = true;
                    break;
                }
            }

            if (datePresent){
                System.out.println("Date status switched successfully!");
                habit.displayHabitHistory();

            }else{
                System.out.println("No habit entry found at provided date!");
            }

        } catch (Exception e) {
            System.out.println("Incorrect data entered!");
            changeHabitStatusOption();
        }
    }
}


