package app.src.forms;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * Created by Mihkel on 14.10.2015.
 */
public class EntryForm {

    @NotNull
    @Max(80)
    private Double relativePercentage;

    @NotNull
    @Max(10000)
    private Integer amount;

    @NotNull
    private String date;

    public Double getRelativePercentage() {
        return relativePercentage;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setRelativePercentage(Double relativePercentage) {
        this.relativePercentage = relativePercentage;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
