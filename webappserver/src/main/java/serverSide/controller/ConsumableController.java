package serverSide.controller;

import serverSide.control.Manager;
import serverSide.model.Consumable;
import serverSide.model.Drinkables;
import serverSide.model.Eatables;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ConsumableController {

    Manager manager = new Manager();

    /**
     * outputs a msg to show the server is up and running.
     *
     */
    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public String GetPing(){
        return "System is up!";
    }

    /**
     * a getter for the webServer to return the list of all consumables.
     *
     * @return a Consumable arraylist
     */
    @GetMapping("/listAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Consumable> getAll() {
        List<Consumable> foods = manager.getFoods();
        return manager.getFoods();
    }

    /**
     * a getter for the webServer to return the list of consumables that are expired.
     *
     * @return a Consumable arraylist
     */
    @GetMapping("/listExpired")
    @ResponseStatus(HttpStatus.OK)
    public List<Consumable> getExpired() {
        List<Consumable> items = manager.getexpired();
        return items;
    }

    /**
     * a getter for the webServer to return the list of consumables that are not expired.
     *
     * @return a Consumable arraylist
     */
    @GetMapping("/listNonExpired")
    @ResponseStatus(HttpStatus.OK)
    public List<Consumable> getNonExpired() {
        List<Consumable> items = manager.getNotExpired();
        return items;
    }

    /**
     * a getter for the webServer to return the list of consumables that expire in 7 days.
     *
     * @return a Consumable arraylist
     */
    @GetMapping("/listExpiringIn7Days")
    @ResponseStatus(HttpStatus.OK)
    public List<Consumable> getExpiringIn7() {
        List<Consumable> items = manager.getExpireIn7();
        return items;
    }

    /**
     * adds the parameter food to the consumables list.
     *
     * @parameters a Consumable
     * @return a Consumable arraylist
     */
    @PostMapping("/addFood")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Consumable> addFood(@RequestBody Eatables aConsumable){
        manager.add(aConsumable);
        return manager.getFoods();
    }

    /**
     * adds the parameter drink to the consumables list.
     *
     * @parameters a Consumable
     * @return a Consumable arraylist
     */
    @PostMapping("/addDrink")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Consumable> addDrink(@RequestBody Drinkables aConsumable){
        manager.add(aConsumable);
        return manager.getFoods();
    }

    /**
     * removes the consumable at the parameter index.
     *
     * @parameters an int index
     * @return a Consumable arraylist
     */
    @PostMapping("/removeItem/{index}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Consumable> removeItem(@PathVariable("index") int index){
        manager.remove(index);
        return manager.getFoods();
    }

    /**
     * saves the consumables list to JSON.
     *
     */
    @GetMapping("/exit")
    @ResponseStatus(HttpStatus.OK)
    public void saveToJson(){
        manager.save();
    }
}
