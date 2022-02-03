package serverSide.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class to store info about a Consumable.
 *
 * @author Hammad
 */
public abstract class Consumable implements Comparable<Consumable> {

    private String name;
    private String notes;
    private double price;
    private double weight;
    private int type;

    private LocalDateTime expiryDate;
    private LocalDateTime currentDate;

    /**
     * A constructor for the class if given inputs.
     *
     * @parameters fields of the class
     */
    public Consumable(String name, String notes, double price, double weight, LocalDateTime localDateTime) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.expiryDate = localDateTime;
        currentDate = currentDate.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 23, 59);
    }
    public Consumable(String name, String notes, double price, double weight, String localDateTime2) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(localDateTime2, formatter);
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.expiryDate = localDateTime;
        currentDate = currentDate.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 23, 59);
    }
    public Consumable(){}

    /**
     * A setter for the type field of the class.
     * .
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * A getter for the type field of the class.
     *
     * @return type.
     */
    public int getType() {
        return type;
    }

    /**
     * A getter for the name field of the class.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * A getter for the expiryDate field of the class.
     *
     * @return expiryDate.
     */
    public LocalDateTime getExpiry() {
        return expiryDate;
    }

    /**
     * A getter for the currentDate field of the class.
     *
     * @return currentDate.
     */
    public LocalDateTime getCurrentDate() {
        return currentDate;
    }

    /**
     * A getter for the price field of the class.
     *
     * @return price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * A getter for the notes field of the class.
     *
     * @return notes.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * A getter for the weight field of the class.
     *
     * @return expiryDate.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * checks if object is expired.
     *
     * @return check.
     */
    public boolean isExpired() {
        return currentDate.compareTo(expiryDate) > 0;
    }
}

