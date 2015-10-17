package app.src.alcohol;

/**
 * Created by Mihkel on 17.10.2015.
 */
public class AlcoholHelper {
    static public Double calculateAbsoluteAlcohol(Double relativeAlcohol, Integer amount)
    {
        return (relativeAlcohol) * (amount / 100) * 0.789;
    }
}
