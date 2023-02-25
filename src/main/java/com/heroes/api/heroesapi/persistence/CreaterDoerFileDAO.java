package com.heroes.api.heroesapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.heroes.api.heroesapi.model.CreatorDoer;

@Component
public class CreaterDoerFileDAO implements CreaterDoerDAO{
    private ObjectMapper objectMapper;

    /**
     * Creates a Buyer File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CreaterDoerFileDAO(@Value("${createrdoers.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the buyers from the file
    }

    private CreaterDoer[] getCreaterDoersArray() { 
        ArrayList<CreaterDoer> buyerArrayList = new ArrayList<>();

        for (CreaterDoer createrDoer : buyers.values()) {   //last fix
            buyerArrayList.add(buyer);
        }

        Buyer[] buyerArray = new Buyer[buyerArrayList.size()];
        buyerArrayList.toArray(buyerArray);
        return buyerArray;
    }

    /**
     * Saves the {@linkplain Buyer buyer} from the set into the file as an array of JSON objects
     * 
     * @return true if the {@link Buyer buyer} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Buyer[] buyerArray = getBuyersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), buyerArray);
        return true;
    }

    /**
     * Loads {@linkplain Buyer buyers} from the JSON file into the set
     * <br>
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        buyers = new HashMap<>();

        // Deserializes the JSON objects from the file into an array of buyers
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Buyer[] buyerArray = objectMapper.readValue(new File(filename),Buyer[].class);

        // Add each Buyer to the hash set
        for (Buyer buyer : buyerArray) {
            buyers.put(buyer.getUsername(), buyer);
        }
        
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Buyer login(String username) {
        synchronized(buyers) {
            if (buyers.containsKey(username)) {
                return buyers.get(username);
            } else {
                return null;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Buyer createBuyer(String username) throws IOException {
        synchronized(buyers) {
            if (buyers.containsKey(username)) {
                return null;
            }
            else {
                Buyer buyer = new Buyer(username);
                buyers.put(buyer.getUsername(), buyer);
                save(); // may throw an IOException
                return buyer;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteBuyer(String username) throws IOException {
        synchronized(buyers) {
            if (username.equals("admin")) {
                return false;
            }
            Buyer buyer = new Buyer(username);
            if (buyers.containsKey(buyer.getUsername())) {
                buyers.remove(buyer.getUsername());
                return save();
            } else {
                return false;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Buyer addToCart(String username, int snackID) throws IOException {
        synchronized(buyers) {
            Buyer buyer = buyers.get(username);

            // Buyer is not found
            if (buyers.containsKey(username) == false)
                return null; 
            else {
                // Add to cart
                buyer.addToCart(snackID);

                // Update buyers list with new cart
                buyers.put(buyer.getUsername(),buyer);
                save(); 
                return buyer;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Buyer deleteFromCart(String username, int snackID) throws IOException {
        synchronized(buyers) {
            Buyer buyer = buyers.get(username);

            // Buyer is not found
            if (buyers.containsKey(username) == false)
                return null; 
            else {
                // Delete item from cart
                buyer.deleteFromCart(snackID);

                // Update buyers list with new cart
                buyers.put(buyer.getUsername(),buyer);
                save(); 
                return buyer;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Buyer clearCart(String username) throws IOException {
        synchronized(buyers) {
            Buyer buyer = buyers.get(username);

            // Buyer is not found
            if (buyers.containsKey(username) == false)
                return null; 
            else {
                // Clear buyer's cart
                buyer.clearCart();

                // Update buyers list with new cart
                buyers.put(buyer.getUsername(),buyer);
                save(); 
                return buyer;
            }
        }
    }

}
