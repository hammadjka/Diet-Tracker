package serverSide.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * A class to store info about a Food.
 *
 * @author Hammad
 */
public class Eatables extends Consumable {

    /**
     * A constructor for the class if given inputs.
     *
     * @parameters fields of the class
     */
    public Eatables(String name, String notes, double price, double weight, int type,  LocalDateTime localDateTime) {
        super(name, notes, price, weight, localDateTime);
        setType(type);
    }
    public Eatables(@JsonProperty("name")String name, @JsonProperty("notes") String notes, @JsonProperty("price") double price, @JsonProperty("weight") double weight,
                    @JsonProperty("type") int type, @JsonProperty("expirydate") String localDateTime) {

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
            output = "<html> Name: " + super.getName() + "<br>" + "Notes: " + super.getNotes() + "<br>" + "Price: " + strDouble + "<br>" + "Weight: " + super.getWeight() + "<br>" + "Expiry date: " + dateTimeString + "<br>" + "This food item is expired for " + daysBetween + " day(s)." + "<br>" + "<html>";
        } else {
            long daysBetween = DAYS.between(super.getCurrentDate(), super.getExpiry());
            output = "<html> Name: " + super.getName() + "<br>" + "Notes: " + super.getNotes() + "<br>" + "Price: " + strDouble + "<br>" + "Weight: " + super.getWeight() + "<br>" + "Expiry date: " + dateTimeString + "<br>" + "This food item will expire in " + daysBetween + " day(s)." + "<br>" + "<html>";
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
}