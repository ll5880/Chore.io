package com.choreio.api.choreioapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.choreio.api.choreioapi.persistence.ChoreDAO;
import com.choreio.api.choreioapi.model.Chore;


/**
 * Handles the REST API requests for the Chore resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Patricio Solis
 */
@RestController
@RequestMapping("chores")
public class ChoreController {
    private static final Logger LOG = Logger.getLogger(ChoreController.class.getName());
    private ChoreDAO choreDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param choreDao The {@link choreDAO Chore Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public ChoreController(ChoreDAO choreDAO) {
        this.choreDAO = choreDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Chore chore} for the given id
     * 
     * @param id The id used to locate the {@link Chore chore}
     * 
     * @return ResponseEntity with {@link Chore chore} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Chore> getChore(@PathVariable int id) {
        LOG.info("GET /chores/" + id);
        try {
            Chore chore = choreDAO.getChore(id);
            if (chore != null)
                return new ResponseEntity<Chore>(chore, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Chore chores}
     * 
     * @return ResponseEntity with array of {@link Chore chore} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Chore[]> getChores() {
        LOG.info("GET /chores");
        try {
            Chore[] chores = choreDAO.getChores();
            return new ResponseEntity<Chore[]>(chores, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Chore chore} with the provided chore object
     * 
     * @param chore - The {@link Chore chore} to create
     * 
     * @return ResponseEntity with created {@link Chore chore} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Chore chore} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Chore> createChore(@RequestBody Chore chore) {
        LOG.info("POST /chores " + chore);
        try {
            Chore[] chores = choreDAO.getChores();
            for (Chore i : chores) {
                if (i.getChoreName().equals(chore.getChoreName())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            choreDAO.createChore(chore);
            return new ResponseEntity<Chore>(chore, HttpStatus.CREATED);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Chore chore} with the provided {@linkplain Chore chore} object, if it exists
     * 
     * @param chore The {@link Chore chore} to update
     * 
     * @return ResponseEntity with updated {@link Chore chore} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Chore> updateChore(@RequestBody Chore chore) {
        LOG.info("PUT /chores " + chore);
        try {
            Chore newChore = choreDAO.updateChore(chore);
            if (newChore == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Chore>(newChore, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Chore chore} with the given id
     * 
     * @param id The id of the {@link Chore chore} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Chore> deleteChore(@PathVariable int id) {
        LOG.info("DELETE /chores/" + id);
        try {
            if (choreDAO.deleteChore(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
