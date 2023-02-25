package com.choreio.api.choreioapi.persistence;
import java.io.IOException;
import com.choreio.api.choreioapi.model.Prize;

/**
 * Defines the interface for Prize object persistence
 * 
 * @author Patricio Solis
 */
public interface PrizeDAO {
    /**
     * Retrieves all {@linkplain Prize prizes}
     * 
     * @return An array of {@link Prize prize} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Prize[] getPrizes() throws IOException;

    /**
     * Finds all {@linkplain Prize prizes} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Prize prizes} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Prize[] findPrizes(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Prize prize} with the given id
     * 
     * @param id The id of the {@link Prize prize} to get
     * 
     * @return a {@link Prize prize} object with the matching id
     * <br>
     * null if no {@link Prize prize} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Prize getPrize(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Prize prize}
     * 
     * @param prize {@linkplain Prize prize} object to be created and saved
     * <br>
     * The id of the prize object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Prize prize} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Prize createPrize(Prize prize) throws IOException;

    /**
     * Updates and saves a {@linkplain Prize prize}
     * 
     * @param {@link Prize prize} object to be updated and saved
     * 
     * @return updated {@link Prize prize} if successful, null if
     * {@link Prize prize} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Prize updatePrize(Prize prize) throws IOException;

    /**
     * Deletes a {@linkplain Prize prize} with the given id
     * 
     * @param id The id of the {@link Prize prize}
     * 
     * @return true if the {@link Prize prize} was deleted
     * <br>
     * false if prize with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deletePrize(int id) throws IOException;
}
