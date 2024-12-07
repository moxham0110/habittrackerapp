import java.time.LocalDate;

public class HistoryEntry {
    private LocalDate entryDate;
    private boolean completionStatus;

    public HistoryEntry(boolean status, LocalDate entryDate) {
        this.completionStatus = status;
        this.entryDate = entryDate;
    }

    public void switchStatus() {
        this.completionStatus = !completionStatus;
    }

    public boolean isStatusComplete() {
        return completionStatus;
    }

    public void setStatus(boolean status) {
        this.completionStatus = status;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }
}
