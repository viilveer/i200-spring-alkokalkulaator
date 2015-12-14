package app.src.entry;

/**
 * Created by Mihkel on 09/12/2015.
 */
public class DailyFeedback extends AbstractFeedback {

    public DailyFeedback() {
        minimumText = "Sa olid eile tubli!";
        maximumText = "Kõik on hästi aga katsu tagasi hoida";
        averageText = "Sa läksid eile liiale!";

        averageConsumptionMax = 50.;
        averageConsumptionMin = 120.;

    }
}
