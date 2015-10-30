package app.src.repositories;

import app.models.DailyEntry;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Mihkel on 10.10.2015.
 */
@Transactional
public interface EntryRepository extends CrudRepository<DailyEntry, Long> {
}
