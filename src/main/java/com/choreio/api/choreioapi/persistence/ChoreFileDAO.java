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

import com.choreio.api.choreioapi.model.Chore;
/**
 * Implements the functionality for JSON file-based peristance for Chores
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty
 */
@Component
public class ChoreFileDAO implements ChoreDAO {
    private static final Logger LOG = Logger.getLogger(ChoreFileDAO.class.getName());
    Map<Integer,Chore> chores;   // Provides a local cache of the chore objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Chore
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new chore
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Chore File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ChoreFileDAO(@Value("${chores.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the chores from the file
    }

    /**
     * Generates the next id for a new {@linkplain Chore chore}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Chore chores} from the tree map
     * 
     * @return  The array of {@link Chore chores}, may be empty
     */
    private Chore[] getChoresArray() {
        return getChoresArray(null);
    }

    /**
     * Generates an array of {@linkplain Chore chores} from the tree map for any
     * {@linkplain Chore chores} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Chore chores}
     * in the tree map
     * 
     * @return  The array of {@link Chore chores}, may be empty
     */
    private Chore[] getChoresArray(String containsText) { // if containsText == null, no filter
        ArrayList<Chore> choreArrayList = new ArrayList<>();

        for (Chore chore : chores.values()) {
            if (containsText == null || chore.getChoreName().contains(containsText)) {
                choreArrayList.add(chore);
            }
        }

        Chore[] choreArray = new Chore[choreArrayList.size()];
        choreArrayList.toArray(choreArray);
        return choreArray;
    }

    /**
     * Saves the {@linkplain Chore chores} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Chore chores} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Chore[] choreArray = getChoresArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),choreArray);
        return true;
    }

    /**
     * Loads {@linkplain Chore chores} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        chores = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of chores
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Chore[] choreArray = objectMapper.readValue(new File(filename),Chore[].class);

        // Add each chore to the tree map and keep track of the greatest id
        for (Chore chore : choreArray) {
            chores.put(chore.getId(),chore);
            if (chore.getId() > nextId)
                nextId = chore.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Chore[] getChores() {
        synchronized(chores) {
            return getChoresArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Chore[] findChores(String containsText) {
        synchronized(chores) {
            return getChoresArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Chore getChore(int id) {
        synchronized(chores) {
            if (chores.containsKey(id)) {
                return chores.get(id);
            } else {
                return null;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Chore createChore(Chore chore) throws IOException {
        synchronized(chores) {
            // We create a new chore object because the id field is immutable
            // and we need to assign the next unique id
            Chore newChore = new Chore(nextId(),chore.getChoreName(), chore.getChoreDesc(), chore.getPoints());
            chores.put(newChore.getId(),newChore);
            save(); // may throw an IOException
            return newChore;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Chore updateChore(Chore chore) throws IOException {
        synchronized(chores) {
            if (chores.containsKey(chore.getId()) == false)
                return null;  // chore does not exist

            chores.put(chore.getId(),chore);
            save(); // may throw an IOException
            return chore;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteChore(int id) throws IOException {
        synchronized(chores) {
            if (chores.containsKey(id)) {
                chores.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
