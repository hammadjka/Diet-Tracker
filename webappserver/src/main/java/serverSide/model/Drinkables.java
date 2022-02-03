package serverSide.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * A class to store info about a Drink.
 *
 * @author Hammad
 */
public class Drinkables extends Consumable {

    /**
     * A constructor for the class if given inputs.
     *
     * @parameters fields of the class
     */
    public Drinkables(String name, String notes, double price, double weight, int type,  LocalDateTime localDateTime) {
        super(name, notes, price, weight, localDateTime);
        setType(type);
    }

    public Drinkables(@JsonProperty("name")String name, @JsonProperty("notes") String notes, @JsonProperty("price")
            double price, @JsonProperty("weight") double weight, @JsonProperty("type") int type, @JsonProperty("expirydate") String localDateTime) {
        super(name, notes, price, weight, localDateTime);
        setType(type);
    }
    /**
     * converts object fields to a string.
     *
     * @return the string.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTimeString = getExpiry().format(formatter);
        String output;
        String strDouble = String.format("%.2f", super.getPrice());

        if (isExpired()) {
            long daysBetween = DAYS.between(super.getExpiry(), super.getCurrentDate());
            output = "<html> Name: " + super.getName() + "<br>" + "Notes: " + super.getNotes() + "<br>" + "Price: " + strDouble + "<br>" + "Volume: " + super.getWeight() + "<br>" + "Expiry date: " + dateTimeString + "<br>" + "This drink item is expired for " + daysBetween + " day(s)." + "<br>" + "<html>";
        } else {
            long daysBetween = DAYS.between(getCurrentDate(), getExpiry());
            output = "<html> Name: " + super.getName() + "<br>" + "Notes: " + super.getNotes() + "<br>" + "Price: " + strDouble + "<br>" + "Volume: " + super.getWeight() + "<br>" + "Expiry date: " + dateTimeString + "<br>" + "This drink item will expire in " + daysBetween + " day(s)." + "<br>" + "<html>";
        }
        return output;
    }

    /**
     * compares parameters expiry to objects expiry, returns 0 if equal, -ve if smaller, +ve if larger.
     *
     * @return the string.
     * @parameters a consumable to be compared
     */
    @Override
    public int compareTo(Consumable o) {
        return getExpiry().compareTo(o.getExpiry());
    }

    /**
     * A setter for the type field of the class.
     */
    @Override
    public void setType(int type) {
        super.setType(type);
    }
}
