package app.src.repositories;

import app.models.DailyEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Mihkel on 10.10.2015.
 */
@Transactional
public interface EntryRepository extends JpaRepository<DailyEntry, Long> {
    List<DailyEntry> findByUserIdOrderByDateAsc(int userId);
    long countByUserId(int userId);
    DailyEntry findByUserIdAndDate(int userId, String date);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM DailyEntry c WHERE c.userId = ?1 AND c.id = ?2")
    public void deleteByUserIdAndId(int userId, int id);

    List<DailyEntry> findByUserIdAndDateGreaterThanEqual(int userId, String date);

    @Query(value = "select c.date, sum(c.absoluteAmount) from DailyEntry c group by c.date")
    public List<Object[]> findAmountPerDate();

    @Query(value = "select sum(c.absoluteAmount) from DailyEntry c where c.userId =?1 AND c.date >= ?2 group by c.userId")
    public long findLastXDaysAbsoluteAlcoholSum(int userId, String date);
}
