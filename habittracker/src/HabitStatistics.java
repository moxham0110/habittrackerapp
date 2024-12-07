import java.util.List;

public interface HabitStatistics {

    int calculateCurrentStreak();

    default double calculateCompletionPercentage(List<HistoryEntry> historyEntries){
        //default, private interface methods, Local Variable Type Inference

        var completeStatusCount = 0;
        for (var entry : historyEntries){
            if (entry.isStatusComplete()){
                completeStatusCount++;
            }
        }
        return calculatePCT(historyEntries.size(), completeStatusCount);
    }

    private double calculatePCT(int entriesCount, int completeStatusCount){
        if (entriesCount == 0) {
            return 0.0;
        }
        return completeStatusCount * (100.0 / entriesCount);
    }

}
