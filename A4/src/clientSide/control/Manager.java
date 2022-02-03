package clientSide.control;

import clientSide.model.Consumable;
import clientSide.model.ConsumableFactory;
import com.google.gson.*;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.*;

/**
 * A class to manage the underlying consumable arraylist.
 *
 * @author Hammad
 */
public class Manager {
    private LocalDateTime curenntDate;

    private static ArrayList<Consumable> consumables = new ArrayList<>();
    private static ArrayList<Consumable> expired = new ArrayList<>();
    private static ArrayList<Consumable> nonExpired = new ArrayList<>();
    private static ArrayList<Consumable> expiryIn7 = new ArrayList<>();

    /**
     * A constructor for the class that builds the arraylist by loading all items.
     */
    public Manager() {
        getFoods();
    }

    private void getJsonItems(String request, ArrayList<Consumable> consumable) throws IOException {
        URL url = new URL(request);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setRequestMethod("GET");
        urlCon.connect();
        StringBuilder inline = new StringBuilder();
        Scanner scan = new Scanner(url.openStream());

        while (scan.hasNext()) {
            inline.append(scan.nextLine());
        }
        scan.close();
        JsonElement fileElement = JsonParser.parseString(inline.toString());
        writeFromJson(fileElement, consumable);
    }

    /**
     * A method to write the arraylist from a json file.
     */
    private void writeFromJson(JsonElement fileElement, ArrayList<Consumable> consumable) {

        JsonArray jsonArray = fileElement.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject Object = jsonArray.get(i).getAsJsonObject();
            String expiryDate = Object.get("expiry").getAsString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(expiryDate, formatter);
            consumable.add(ConsumableFactory.getInstance(Object.get("type").getAsInt(), Object.get("name").getAsString(), Object.get("notes").getAsString(), Object.get("price").getAsDouble(), Object.get("weight").getAsDouble(), localDateTime));
        }
    }

    /**
     * a getter to return the list of consumables.
     *
     * @return a Consumable arraylist
     */
    public List<Consumable> getFoods() {
        consumables.clear();
        try {
            getJsonItems("http://localhost:8080/listAll", consumables);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return consumables;
    }

    /**
     * a getter to return the list of expired consumables.
     *
     * @return a Consumable arraylist
     */
    public List<Consumable> getexpired() {
        expired.clear();
        try {
            getJsonItems("http://localhost:8080/listExpired", expired);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expired;
    }

    /**
     * a getter to return the list of non-expired consumables.
     *
     * @return a Consumable arraylist
     */
    public List<Consumable> getNotExpired() {
        nonExpired.clear();
        try {
            getJsonItems("http://localhost:8080/listNonExpired", nonExpired);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nonExpired;
    }

    /**
     * a getter to return the list of consumables that expire in 7 days.
     *
     * @return a Consumable arraylist
     */
    public List<Consumable> getExpireIn7() {
        expiryIn7.clear();
        try {
            getJsonItems("http://localhost:8080/listExpiringIn7Days", expiryIn7);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expiryIn7;
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
        String consumableString = null;
        URL url = null;

        if (consumable != null) {
            consumableString = new JSONObject().put("name", consumable.getName()).put("notes", consumable.getNotes()).put("price", consumable.getPrice()).put("weight", consumable.getWeight()).put("type", consumable.getType()).put("expirydate", consumable.getExpiry()).toString();
        }

        int type = consumable.getType();
        if (type == 1) {
            try {
                url = new URL("http://localhost:8080/addFood");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                url = new URL("http://localhost:8080/addDrink");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(String.valueOf(url))).header("content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(consumableString)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * removes the consumable at the parameter index.
     *
     * @parameters an int index
     */
    public void remove(int index) throws IOException {
        index = index;
        URL url = new URL("http://localhost:8080/removeItem/" + index);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setRequestMethod("POST");
        urlCon.connect();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(String.valueOf(url))).header("content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(String.valueOf(index))).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * removes the consumable at the parameter index.
     *
     * @parameters an int index
     */
    public void exit() throws IOException {
        URL url = new URL("http://localhost:8080/exit");
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setRequestMethod("GET");
        urlCon.connect();

        int responseCode = urlCon.getResponseCode();
    }
}
