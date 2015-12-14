package app.src.entry;

import app.models.DailyEntry;
import app.src.repositories.EntryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mihkel on 22/11/2015.
 */
public class UserEntryFormatter {

    private EntryRepository entryRepository;

    private int userId;

    private List<DailyEntry> data = new ArrayList<>();

    public UserEntryFormatter(EntryRepository entryRepository, int userId) {
        this.entryRepository = entryRepository;
        this.userId = userId;
    }

    private void generateData()
    {
        if (this.data.isEmpty()) {
            this.data = this.entryRepository.findByUserIdOrderByDateAsc(this.userId);
        }
    }


    public String[] getDates()
    {
        this.generateData();
        String[] formattedData = new String[data.size()];
        int i = 0;
        for (DailyEntry row: this.data) {
            formattedData[i] = row.getDate();
            i++;
        }
        return formattedData;
    }

    public double[] getAbsoluteAlcoholEntries()
    {
        this.generateData();
        double[] formattedData = new double[data.size()];
        int i = 0;
        for (DailyEntry row: this.data) {
            formattedData[i] = row.getAbsoluteAmount();
            i++;
        }
        return formattedData;
    }

    public List<DailyEntry> getLastEntries(int count)
    {
        List<DailyEntry> sortedEntries = data;
        Collections.sort(sortedEntries);
        return sortedEntries.subList(Math.max(sortedEntries.size() - count, 0), sortedEntries.size());
    }
}

