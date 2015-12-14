package app.src.entry;

/**
 * Created by Mihkel on 14/12/2015.
 */
public abstract class AbstractFeedback {
    public String minimumText;
    public String averageText;
    public String maximumText;

    public double averageConsumptionMin = 300.;
    public double averageConsumptionMax = 1000;


    public String toString(double consumption)
    {
        String text;

        if (consumption < averageConsumptionMin) {
            text = minimumText;
        } else if (consumption > averageConsumptionMin && consumption < averageConsumptionMax) {
            text = maximumText;
        } else {
            text = averageText;
        }
        return text;
    }
}
