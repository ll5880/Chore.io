package com.choreio.api.choreioapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.choreio.api.choreioapi.model.Chore;
import com.choreio.api.choreioapi.model.CreatorDoer;
import com.choreio.api.choreioapi.model.Prize;

@Component
public class CreatorDoerFileDAO implements CreatorDoerDAO{
    private static final Logger LOG = Logger.getLogger(PrizeFileDAO.class.getName());
    Map<String,CreatorDoer> createrDoers;   // Provides a local cache 
    private ObjectMapper objectMapper;  // Provides conversion to JSON
    private String filename;    // Filename to read from and write to

    /**
     * Creates a CreatorDoer File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CreatorDoerFileDAO(@Value("${creatordoers.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private CreatorDoer[] getCreatorDoersArray() { 
        ArrayList<CreatorDoer> creatorDoerArrayList = new ArrayList<>();

        for (CreatorDoer creatorDoer : createrDoers.values()) {
            creatorDoerArrayList.add(creatorDoer);
        }

        CreatorDoer[] creatorArray = new CreatorDoer[creatorDoerArrayList.size()];
        creatorDoerArrayList.toArray(creatorArray);
        return creatorArray;
    }

    /**
     * Saves the {@linkplain Buyer buyer} from the set into the file as an array of JSON objects
     * 
     * @return true if the {@link Buyer buyer} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        CreatorDoer[] buyerArray = getCreatorDoersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), buyerArray);
        return true;
    }

    private boolean load() throws IOException {
        createrDoers = new HashMap<>();

        // Deserializes the JSON objects
        CreatorDoer[] creatorDoersArray = objectMapper.readValue(new File(filename),CreatorDoer[].class);

        for (CreatorDoer creatorDoer : creatorDoersArray) {
            createrDoers.put(creatorDoer.getUserName(), creatorDoer);
        }
        return true;
    }

    @Override
    public CreatorDoer login(String username) throws IOException {
        synchronized(createrDoers) {
            if (createrDoers.containsKey(username)) {
                return createrDoers.get(username);
            } else {
                return null;
            }
        }
    }

    @Override
    public CreatorDoer createCreatorDoer(String username) throws IOException {
        synchronized(createrDoers) {
        if (createrDoers.containsKey(username)) {
            return null;
        }
        else {
            CreatorDoer creatorDoer = new CreatorDoer(username);
            createrDoers.put(creatorDoer.getUserName(), creatorDoer);
            save(); // may throw an IOException
            return creatorDoer;
        }
    }
    }

    @Override
    public CreatorDoer completeChore(String username, Chore chore) throws IOException {
        synchronized(createrDoers) {
            CreatorDoer creatorDoer = createrDoers.get(username);

            // CreatorDoer is not found
            if (createrDoers.containsKey(username) == false)
                return null; 
            else {
                // Complete Chore
                creatorDoer.completeChore(chore);

                // Update CreatorDoer list with edited CreatorDoer
                createrDoers.put(creatorDoer.getUserName(), creatorDoer);
                save(); 
                return creatorDoer;
            }
        }
    }

    @Override
    public CreatorDoer claimPrize(String username, Prize prize) throws IOException {
        synchronized(createrDoers) {
            CreatorDoer creatorDoer = createrDoers.get(username);

            // CreatorDoer is not found
            if (createrDoers.containsKey(username) == false)
                return null; 
            else {
                // Claims prize
                if (!creatorDoer.claimPrize(prize)) {
                    return null;
                }

                // Update CreatorDoer list with edited CreatorDoer
                createrDoers.put(creatorDoer.getUserName(), creatorDoer);
                save(); 
                return creatorDoer;
            }
        }
        
    }

    @Override
    public CreatorDoer redeemPrize(String username, int prizeID) throws IOException {
        synchronized(createrDoers) {
            CreatorDoer creatorDoer = createrDoers.get(username);

            // CreatorDoer is not found
            if (createrDoers.containsKey(username) == false)
                return null; 
            else {
                // Redeems prize
                creatorDoer.redeemPrize(prizeID);

                // Update CreatorDoer list with edited CreatorDoer
                createrDoers.put(creatorDoer.getUserName(), creatorDoer);
                save(); 
                return creatorDoer;
            }
        }
    }

    @Override
    public CreatorDoer getCreatorDoer(String username) throws IOException {
        synchronized(createrDoers) {
            if (!createrDoers.containsKey(username)) {
                return null;
            } else {
                CreatorDoer creatorDoer = createrDoers.get(username);
                return creatorDoer;
            }
        }
    }

    @Override
    public CreatorDoer[] getCreatorDoers() {
        synchronized(createrDoers) {
            return getCreatorDoersArray();
        }
    }
}