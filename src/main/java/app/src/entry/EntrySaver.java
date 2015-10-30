package app.src.entry;

import app.src.forms.EntryForm;
import app.models.DailyEntry;
import app.src.repositories.EntryRepository;
import app.src.alcohol.AlcoholHelper;

/**
 * Created by Mihkel on 17.10.2015.
 */
public class EntrySaver
{
    private EntryRepository entryRepository;

    public DailyEntry save(EntryForm form, Integer userId) {

        Double relativePercentage = form.getRelativePercentage();
        Integer amount = form.getAmount();

        Double absolutePercentage = AlcoholHelper.calculateAbsoluteAlcohol(relativePercentage, amount);

        DailyEntry model = new DailyEntry(userId, form.getRelativePercentage(), form.getAmount(), absolutePercentage, form.getDate());

        return this.entryRepository.save(model);
    }


    public EntrySaver setEntryRepository(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
        return this;
    }
}
