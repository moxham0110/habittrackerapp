import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeeklyHabit extends Habit{

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
        //todo: display weekly habits differently
        super.displayHabitHistory();
    }
}
