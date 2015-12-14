package app.src.user;

import app.models.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by Mihkel on 08/12/2015.
 */
public class UserAnalyzer {
    private User user;

    public UserAnalyzer (User user) {
        this.user = user;
    }

    public long getAccountAgeInDays()
    {
        return ChronoUnit.DAYS.between(getUserCreatedDate(), LocalDate.now());
    }

    public LocalDate getUserCreatedDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDate.parse(this.user.getCreatedAt(), formatter);
    }

    public User getUser(){
        return user;
    }
}
