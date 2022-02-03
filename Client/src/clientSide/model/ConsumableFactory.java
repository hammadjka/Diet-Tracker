package clientSide.model;

import java.time.LocalDateTime;

/**
 * A class to get instances of different consumable items.
 *
 * @author Hammad
 */
public class ConsumableFactory {

    /**
     * A constructor for the class.
     */
    public ConsumableFactory() {
    }


    /**
     * gets instance of a consumable depending on the parameter "type" and sets its fields to the respected parameters .
     *
     * @parameters fields of the class and an int to decide the type.
     */
    public static Consumable getInstance(int type, String name, String notes, double price, double weight, LocalDateTime localDateTime) {
        if (type == 1) {
            return new Eatables(type, name, notes, price, weight, localDateTime);
        }

        return new Drinkables(type, name, notes, price, weight, localDateTime);
    }
}

