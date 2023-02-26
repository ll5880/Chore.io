package com.choreio.api.choreioapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.choreio.api.choreioapi.model.Prize;

/**
 * Implements the functionality for JSON file-based peristance for Prizes
 */
@Component
public class PrizeFileDAO implements PrizeDAO {
    private static final Logger LOG = Logger.getLogger(PrizeFileDAO.class.getName());
    Map<Integer,Prize> prizes;   // Provides a local cache
    private ObjectMapper objectMapper;  // Provides conversion to JSON
    private static int nextId;  // The next Id to assign to a new prize
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Prize File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public PrizeFileDAO(@Value("${prizes.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }
    
    /**
     * Generates the next id for a new {@linkplain Prize prize}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Prize prizes} from the tree map
     * 
     * @return  The array of {@link Prize prizes}, may be empty
     */
    private Prize[] getPrizesArray() {
        return getPrizesArray(null);
    }

    /**
     * Generates an array of {@linkplain Prize prize} from the tree map for any
     * {@linkplain Prize prizes} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Prize prizes}
     * in the tree map
     * 
     * @return  The array of {@link Prize prizes}, may be empty
     */
    private Prize[] getPrizesArray(String containsText) { // if containsText == null, no filter
        ArrayList<Prize> prizeArrayList = new ArrayList<>();

        for (Prize prize : prizes.values()) {
            if (containsText == null || prize.getPrizeName().contains(containsText)) {
                prizeArrayList.add(prize);
            }
        }

        Prize[] prizeArray = new Prize[prizeArrayList.size()];
        prizeArrayList.toArray(prizeArray);
        return prizeArray;
    }    
    
    /**
     * Saves the {@linkplain Prize prize} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Prize prizes} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Prize[] prizeArray = getPrizesArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),prizeArray);
        return true;
    }

    /**
     * Loads {@linkplain Prize prizes} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        prizes = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects
        Prize[] prizeArray = objectMapper.readValue(new File(filename),Prize[].class);

        for (Prize prize : prizeArray) {
            prizes.put(prize.getId(), prize);
            if (prize.getId() > nextId)
                nextId = prize.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Prize[] getPrizes() {
        synchronized(prizes) {
            return getPrizesArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Prize[] findPrizes(String containsText) {
        synchronized(prizes) {
            return getPrizesArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Prize getPrize(int id) {
        synchronized(prizes) {
            if (prizes.containsKey(id))
                return prizes.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
    */
    @Override
    public Prize createPrize(Prize prize) throws IOException {
        synchronized(prizes) {
            // We create a new prize object because the id field is immutable
            // and we need to assign the next unique id
            Prize newPrize = new Prize(nextId(), prize.getPrizeName(), prize.getPrizeDesc(), prize.getPointCost());
            prizes.put(newPrize.getId(), newPrize);
            save(); // may throw an IOException
            return newPrize;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Prize updatePrize(Prize prize) throws IOException {
        synchronized(prizes) {
            if (prizes.containsKey(prize.getId()) == false)
                return null;  // prize does not exist
            prizes.put(prize.getId(), prize);
            save(); // may throw an IOException
            return prize;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deletePrize(int id) throws IOException {
        synchronized(prizes) {
            if (prizes.containsKey(id)) {
                prizes.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
