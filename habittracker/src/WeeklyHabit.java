import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeeklyHabit extends Habit{

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public WeeklyHabit(String name, LocalDate startDate, Category category) {
        super(name, startDate, category);

        LocalDate currentDate = LocalDate.now();
        List<HistoryEntry> historyEntries = this.getHabitHistory();

        while(!startDate.isAfter(currentDate)){
            historyEntries.add(new HistoryEntry(false, startDate));
            startDate = startDate.plusDays(7);
        }
    }

    public WeeklyHabit(String name, LocalDate startDate, Category category, boolean defaultEntriesStatus) {
        super(name, startDate, category);

        LocalDate currentDate = LocalDate.now();
        List<HistoryEntry> historyEntries = this.getHabitHistory();

        while(!startDate.isAfter(currentDate)){
            historyEntries.add(new HistoryEntry(defaultEntriesStatus, startDate));
            startDate = startDate.plusDays(7);
        }
    }

    @Override
    public int calculateCurrentStreak() {
        int streak = 0;
        var historyEntries = getHabitHistory();
        for (int i = historyEntries.size() - 1; i >= 0; i--) {
            HistoryEntry entry = historyEntries.get(i);

            if (entry.isStatusComplete()) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }

    @Override
    public void displayHabitHistory() {
        String currentMonthYear = "";
        int weekCount = 1;

        System.out.println("===== Weekly Habit History =====");

        for (HistoryEntry entry : getHabitHistory()) {
            LocalDate date = entry.getEntryDate();
            String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();

            // Checks month to print
            if (!monthYear.equals(currentMonthYear)) {
                if (!currentMonthYear.isEmpty()) {
                    System.out.println();
                }
                currentMonthYear = monthYear;
                weekCount = 1;

                System.out.println("\n===== " + currentMonthYear + " =====");
            }

            // Print the week's habit status
            System.out.printf("Week %d (%s): %s%n", weekCount, date.format(dateFormatter), entry.isStatusComplete() ? "✔ Completed" : "✘ Missed");

            // Increment the week count
            weekCount++;
        }
        System.out.println();
    }
}
