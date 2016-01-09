package app.src.user;

import app.models.DailyEntry;
import app.models.User;
import app.src.entry.EntryAnalyzer;
import app.src.entry.MonthlyFeedback;
import app.src.entry.UserEntryAnalyzer;
import app.src.helpers.DateTimeHelper;
import app.src.repositories.EntryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mihkel on 14/12/2015.
 */
public class Profile {
    private User user;

    private EntryRepository entryRepository;

    public Profile(User user, EntryRepository entryRepository) {
        this.user = user;
        this.entryRepository = entryRepository;
    }

    public Map<String, Object> getProfileData()
    {
        UserAnalyzer userAnalyzer = new UserAnalyzer(user);
        UserEntryAnalyzer userEntryAnalyzer = new UserEntryAnalyzer(userAnalyzer, entryRepository);
        EntryAnalyzer entryAnalyzer = new EntryAnalyzer(entryRepository);
        long consumption = 0;
        if (userEntryAnalyzer.getUserEntryFormatter().getLastEntries(1).size() != 0) {
             consumption = entryRepository.findLastXDaysAbsoluteAlcoholSum(user.getId(), LocalDate.now().minusDays(30).toString());
        }
        MonthlyFeedback userMonthlyFeedback = new MonthlyFeedback();

        Map<String, Object> data = new HashMap<>();

        data.put("feedbackMessage", userMonthlyFeedback.toString(consumption));
        data.put("firstDate", userEntryAnalyzer.getFirstEntryDate().toString());
        data.put("filledDates", userEntryAnalyzer.getFilledDates());
        data.put("amounts", userEntryAnalyzer.getUserEntryFormatter().getAbsoluteAlcoholEntries());
        data.put("entries", userEntryAnalyzer.getUserEntryFormatter().getLastEntries(10));
        data.put("dates", userEntryAnalyzer.getUserEntryFormatter().getDates());
        data.put("globalAmounts", entryAnalyzer.getField(EntryAnalyzer.ABS_VALUE));
        data.put("globalDates", entryAnalyzer.getField(EntryAnalyzer.DATE));
        data.put("missingEntryDays", userEntryAnalyzer.getMissingEntryDays());
        data.put("isYesterdayFilled", userEntryAnalyzer.isDayFilled(DateTimeHelper.yesterday(LocalDateTime.now())));

        return data;
    }
}
