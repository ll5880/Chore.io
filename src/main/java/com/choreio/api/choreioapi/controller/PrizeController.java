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

import com.choreio.api.choreioapi.persistence.PrizeDAO;
import com.choreio.api.choreioapi.model.Prize;


@RestController
@RequestMapping("prizes")
public class PrizeController {
    private static final Logger LOG = Logger.getLogger(PrizeController.class.getName());
    private PrizeDAO prizeDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param prizeDAO The {@link prizeDAO prizeDAO Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public PrizeController(PrizeDAO prizeDAO) {
        this.prizeDAO = prizeDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Prize prize} for the given id
     * 
     * @param id The id used to locate the {@link Prize prize}
     * 
     * @return ResponseEntity with {@link Prize prize} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prize> getPrize(@PathVariable int id) {
        LOG.info("GET /prizes/" + id);
        try {
            Prize prize = prizeDAO.getPrize(id);
            if (prize != null)
                return new ResponseEntity<Prize>(prize, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Prize prize}
     * 
     * @return ResponseEntity with array of {@link Prize prize} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Prize[]> getPrizes() {
        LOG.info("GET /prizes");
        try {
            Prize[] prizes = prizeDAO.getPrizes();
            return new ResponseEntity<Prize[]>(prizes, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Prize prizes} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Prize prizes}
     * 
     * @return ResponseEntity with array of {@link Prize prizes} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all heroes that contain the text "ma"
     * GET http://localhost:8080/heroes/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Prize[]> searchPrizes(@RequestParam String name) {
        LOG.info("GET /prizes/?name="+name);
        try {
            Prize[] prizes = prizeDAO.findPrizes(name);
            return new ResponseEntity<Prize[]>(prizes, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Prize prize} with the provided prize object
     * 
     * @param hero - The {@link Prize prize} to create
     * 
     * @return ResponseEntity with created {@link Prize prize} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Prize prize} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Prize> createPrize(@RequestBody Prize prize) {
        LOG.info("POST /prizes " + prize);
        try {
            Prize[] prizes = prizeDAO.getPrizes();
            for (Prize i : prizes) {
                if (i.getPrizeName().equals(prize.getPrizeName())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            prizeDAO.createPrize(prize);
            return new ResponseEntity<Prize>(prize, HttpStatus.CREATED);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        /**
     * Updates the {@linkplain Prize prize} with the provided {@linkplain Prize prize} object, if it exists
     * 
     * @param prize The {@link Prize prize} to update
     * 
     * @return ResponseEntity with updated {@link Prize prize} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Prize> updatePrize(@RequestBody Prize prize) {
        LOG.info("PUT /prizes " + prize);
        try {
            Prize newPrize = prizeDAO.updatePrize(prize);
            if (newPrize == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Prize>(prize, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Prize prize} with the given id
     * 
     * @param id The id of the {@link Prize prize} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Prize> deletePrize(@PathVariable int id) {
        LOG.info("DELETE /prizes/" + id);
        try {
            if (prizeDAO.deletePrize(id)) {
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
