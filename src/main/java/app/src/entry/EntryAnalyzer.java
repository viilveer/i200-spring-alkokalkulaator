package app.src.entry;

import app.src.repositories.EntryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mihkel on 09/12/2015.
 */
public class EntryAnalyzer {
    public static final int DATE = 0;
    public static final int ABS_VALUE = 1;


    private EntryRepository entryRepository;
    private List<Object[]> data = new ArrayList<>();

    public EntryAnalyzer (EntryRepository entryRepository) {

        this.entryRepository = entryRepository;
    }

    private void populateData()
    {
        if (this.data.isEmpty()) {
            this.data = entryRepository.findAmountPerDate();
        }
    }

    public String[] getField(int fieldNr)
    {
        populateData();
        String[] returnArray = new String[data.size()];
        int iterator = 0;
        for (Object[] row : data) {
            returnArray[iterator] = row[fieldNr].toString();
            iterator++;
        }

        return returnArray;
    }
}
