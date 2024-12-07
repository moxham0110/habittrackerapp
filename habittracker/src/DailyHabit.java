import java.time.LocalDate;
import java.util.List;

public class DailyHabit extends Habit {

    public DailyHabit(String name, LocalDate startDate, Category category) {
        super(name, startDate, category);

        LocalDate currentDate = LocalDate.now();
        List<HistoryEntry> historyEntries = this.getHabitHistory();

        while(!startDate.isAfter(currentDate)){
            historyEntries.add(new HistoryEntry(false, startDate));
            startDate = startDate.plusDays(1);
        }
    }

    public DailyHabit(String name, LocalDate startDate, Category category, boolean defaultEntriesStatus) {
        super(name, startDate, category);

        LocalDate currentDate = LocalDate.now();
        List<HistoryEntry> historyEntries = this.getHabitHistory();

        while(!startDate.isAfter(currentDate)){
            historyEntries.add(new HistoryEntry(defaultEntriesStatus, startDate));
            startDate = startDate.plusDays(1);
        }
    }

    @Override
    public int calculateCurrentStreak() {
        int streak = 0;
        List<HistoryEntry> historyEntries = getHabitHistory();
        for (int i = getHabitHistory().size() - 1; i >= 0; i--) {
            HistoryEntry entry = historyEntries.get(i);

            if (entry.isStatusComplete()) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }

}
