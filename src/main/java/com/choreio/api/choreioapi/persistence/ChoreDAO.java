package com.choreio.api.choreioapi.persistence;

import java.io.IOException;
import com.choreio.api.choreioapi.model.Chore;

/**
 * Defines the interface for Chore object persistence
 * 
 * @author SWEN Faculty
 */
public interface ChoreDAO {
    /**
     * Retrieves all {@linkplain Chore chore}
     * 
     * @return An array of {@link Chore chore} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Chore[] getChores() throws IOException;

    /**
     * Finds all {@linkplain Chore chore} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Chore chore} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Chore[] findChores(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Chore chore} with the given id
     * 
     * @param id The id of the {@link Chore chore} to get
     * 
     * @return a {@link Chore chore} object with the matching id
     * <br>
     * null if no {@link Chore chore} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Chore getChore(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Chore chore}
     * 
     * @param chore {@linkplain Chore chore} object to be created and saved
     * <br>
     * The id of the chore object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Chore chore} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Chore createChore(Chore chore) throws IOException;

    /**
     * Updates and saves a {@linkplain Chore chore}
     * 
     * @param {@link Chore chore} object to be updated and saved
     * 
     * @return updated {@link Chore chore} if successful, null if
     * {@link Chore chore} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Chore updateChore(Chore chore) throws IOException;

    /**
     * Deletes a {@linkplain Chore chore} with the given id
     * 
     * @param id The id of the {@link Chore chore}
     * 
     * @return true if the {@link Chore chore} was deleted
     * <br>
     * false if chore with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteChore(int id) throws IOException;
}
