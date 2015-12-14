package app.src.entry;

import app.models.DailyEntry;
import app.src.repositories.EntryRepository;
import app.src.user.UserAnalyzer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Mihkel on 08/12/2015.
 */
public class UserEntryAnalyzer {

    private UserAnalyzer userAnalyzer;

    private EntryRepository entryRepository;

    private UserEntryFormatter userEntryFormatter;

    public UserEntryAnalyzer(UserAnalyzer userAnalyzer, EntryRepository entryRepository) {
        this.userAnalyzer = userAnalyzer;
        this.entryRepository = entryRepository;
    }

    public long getMissingEntryDays() {
        long days = userAnalyzer.getAccountAgeInDays();

        long applicableDays = days < 7 ? days : 7;
        if (days > 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = LocalDate.now().minusDays(applicableDays).format(formatter);
            long enteredEntries = entryRepository.countByUserId(userAnalyzer.getUser().getId(), date); // all must be unique
            applicableDays -= enteredEntries;
        }

        return applicableDays;
    }

    public boolean isDayFilled(String date) {
        return entryRepository.findByUserIdAndDate(userAnalyzer.getUser().getId(), date) != null;
    }

    public UserEntryFormatter getUserEntryFormatter() {
        if (userEntryFormatter == null) {
            userEntryFormatter = new UserEntryFormatter(entryRepository, userAnalyzer.getUser().getId());
        }
        return userEntryFormatter;
    }

    public LocalDate getFirstEntryDate() {
        LocalDate registerDate = userAnalyzer.getUserCreatedDate();
        LocalDate weekAgo = LocalDate.now().minusDays(7);
        return registerDate.isAfter(weekAgo) ? registerDate : weekAgo;
    }

    public String[] getFilledDates()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<DailyEntry> filledDates = entryRepository.findByUserIdAndDateGreaterThanEqual(userAnalyzer.getUser().getId(), getFirstEntryDate().format(formatter));
        String[] returnDates = new String[filledDates.size()];
        int iterator = 0;
        for (DailyEntry date : filledDates) {
            returnDates[iterator] = date.getDate();
            iterator++;
        }
        return returnDates;
    }
}
