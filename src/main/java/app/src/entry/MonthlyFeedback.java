package app.src.entry;

/**
 * Created by Mihkel on 09/12/2015.
 */
public class MonthlyFeedback extends AbstractFeedback {

    public MonthlyFeedback() {
        minimumText = "Tubli, Teie alkoholi tarbimine on mõõdukas ning kontrollitud!";
        maximumText = "Teil on alkoholiga probleeme (mitte alkoholil teiega)!";
        averageText = "Võiksite natukene enda alkoholitarbimist piirata";

        averageConsumptionMax = 1000.;
        averageConsumptionMin = 100.;

    }
}
