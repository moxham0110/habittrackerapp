import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public abstract class Habit implements HabitStatistics{

    static int idCount = 0;
    private final int id;
    private String name;
    private ArrayList<HistoryEntry> habitHistory;
    private Category category;    //todo: make category into enum, HEALTH, EDUCATION, CLEANING, OTHER

    public Habit(String name, LocalDate startDate, Category category) {
        this.id = Habit.idCount++;
        this.name = name;
        this.habitHistory = new ArrayList<>();
        this.category = category;
    }

    public void displayHabitHistory() {
        String currentMonthYear = "";
        int daysInMonth = 0;
        int firstDayOfWeek = 0;

        for (HistoryEntry entry : this.habitHistory) {
            LocalDate date = entry.getEntryDate();
            String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();

            // Check if we need to start a new month
            if (!monthYear.equals(currentMonthYear)) {
                if (!currentMonthYear.isEmpty()) {
                    System.out.println("\n");
                }
                currentMonthYear = monthYear;
                daysInMonth = date.lengthOfMonth();
                firstDayOfWeek = date.withDayOfMonth(1).getDayOfWeek().getValue() % 7;

                // Print the month header and calendar header
                System.out.println("===== " + currentMonthYear + " =====");
                System.out.println("Su Mo Tu We Th Fr Sa");

                // Print leading spaces for the first week
                for (int i = 0; i < firstDayOfWeek; i++) {
                    System.out.print("   ");
                }
            }

            // Print the day's date and status
            int day = date.getDayOfMonth();
            System.out.printf("%2d%s ", day, entry.isStatusComplete() ? "✔" : "✘");

            // Move to a new line after Saturday
            if ((firstDayOfWeek + day) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<HistoryEntry> getHabitHistory() {
        return habitHistory;
    }

    public void setHabitHistory(ArrayList<HistoryEntry> habitHistory) {
        this.habitHistory = habitHistory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
