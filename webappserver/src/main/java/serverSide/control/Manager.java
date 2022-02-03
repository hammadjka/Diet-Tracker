package serverSide.control;

import serverSide.model.ConsumableFactory;
import serverSide.model.Consumable;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * A class to manage the underlying consumable arraylist.
 *
 * @author Hammad
 */
public class Manager {
    private LocalDateTime curenntDate;

    private static ArrayList<Consumable> consumables = new ArrayList<>();

    /**
     * A constructor for the class that builds the arraylist by calling to write from json.
     */
    public Manager() {
        writeFromJson();
    }

    /**
     * saves the list of consumables to json.
     */
    public static void saveToJson() {
        Collections.sort(consumables);
        Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {

            @Override
            public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
                jsonWriter.value(localDateTime.toString());
            }

            @Override
            public LocalDateTime read(JsonReader jsonReader) throws IOException {
                return LocalDateTime.parse(jsonReader.nextString());
            }
        }).create();

        try {
            Writer FileWrite = new FileWriter("./foodItems.json");
            myGson.toJson(consumables, FileWrite);
            FileWrite.flush();
            FileWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * a public function to sort and save the list of consumables to json.
     */
    public void save() {
        Collections.sort(consumables);
        saveToJson();
    }

    /**
     * a getter to return the list of consumables.
     *
     * @return a Consumable arraylist
     */
    public List<Consumable> getFoods() {
        return consumables;
    }

    /**
     * a getter to return the list of expired consumables.
     *
     * @return a Consumable arraylist
     */
    public List<Consumable> getexpired() {
        List<Consumable> ExpiredFoods = new ArrayList<>();
        for (int i = 0; i < consumables.size(); i++) {
            if (consumables.get(i).isExpired()) {
                ExpiredFoods.add(consumables.get(i));
            }
        }
        Collections.sort(ExpiredFoods);
        return ExpiredFoods;
    }

    /**
     * a getter to return the list of non-expired consumables.
     *
     * @return a Consumable arraylist
     */
    public List<Consumable> getNotExpired() {
        List<Consumable> NotExpired = new ArrayList<>();
        for (int i = 0; i < consumables.size(); i++) {
            if (!consumables.get(i).isExpired()) {
                NotExpired.add(consumables.get(i));
            }
        }
        Collections.sort(NotExpired);
        return NotExpired;
    }

    /**
     * a getter to return the list of consumables that expire in 7 days.
     *
     * @return a Consumable arraylist
     */
    public List<Consumable> getExpireIn7() {
        List<Consumable> ExpireIn7 = new ArrayList<>();
        List<Consumable> NotExpired = getNotExpired();

        curenntDate = curenntDate.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 23, 59);
        for (int i = 0; i < NotExpired.size(); i++) {
            long daysBetween = DAYS.between(curenntDate, NotExpired.get(i).getExpiry());
            if (daysBetween <= 7) {
                ExpireIn7.add(NotExpired.get(i));
            }
        }
        Collections.sort(ExpireIn7);
        return ExpireIn7;
    }

    /**
     * A method to write the arraylist from a json file.
     */
    public static void writeFromJson() {

        File file = new File("./foodItems.json");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));

            JsonArray jsonArray = fileElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject Object = jsonArray.get(i).getAsJsonObject();
                String expiryDate = Object.get("expiryDate").getAsString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime localDateTime = LocalDateTime.parse(expiryDate, formatter);
                consumables.add(ConsumableFactory.getInstance(Object.get("type").getAsInt(), Object.get("name").getAsString(), Object.get("notes").getAsString(), Object.get("price").getAsDouble(), Object.get("weight").getAsDouble(), localDateTime));
            }

        } catch (FileNotFoundException e) {
        }

    }

    /**
     * checks if the parameter index is in bounds of the consumables list.
     *
     * @return check.
     * @parameters an integer index
     */
    public boolean isValid(int index) {
        return index <= consumables.size() && index > 0;
    }

    /**
     * adds the parameter consumable to the consumables list.
     *
     * @parameters a Consumable
     */
    public void add(Consumable consumable) {
        consumables.add(consumable);
        Collections.sort(consumables);
    }

    /**
     * removes the consumable at the parameter index.
     *
     * @parameters an int index
     */
    public void remove(int index) {
        consumables.remove(index - 1);
        Collections.sort(consumables);
    }

}
