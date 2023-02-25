package com.heroes.api.heroesapi.controller;

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

import com.heroes.api.heroesapi.persistence.CreaterDoerDAO;
import com.heroes.api.heroesapi.model.CreatorDoer;


@RestController
@RequestMapping("creator-doer")
public class CreatorDoerController {
    private static final Logger LOG = Logger.getLogger(CreaterDoerController.class.getName());
    private CreatorDoerDAO createrDoerDao;

        /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param buyerDao The {@link BuyerDao Buyer Data Access Object} to perform operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public CreatorDoerController(CreatorDoerDAO createrDoerDao) {
        this.createrDoerDao = createrDoerDao;
    }

    
}
