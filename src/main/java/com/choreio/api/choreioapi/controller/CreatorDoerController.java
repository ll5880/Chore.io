package com.choreio.api.choreioapi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.choreio.api.choreioapi.model.Chore;
import com.choreio.api.choreioapi.model.CreatorDoer;
import com.choreio.api.choreioapi.model.Prize;
import com.choreio.api.choreioapi.persistence.ChoreDAO;
import com.choreio.api.choreioapi.persistence.CreatorDoerDAO;
import com.choreio.api.choreioapi.persistence.PrizeDAO;

@RestController
@RequestMapping("creatordoers")
public class CreatorDoerController {
    private static final Logger LOG = Logger.getLogger(CreatorDoerController.class.getName());
    private ChoreDAO choreDao;
    private PrizeDAO prizeDao;
    private CreatorDoerDAO creatorDoerDAO;
    
    public CreatorDoerController(CreatorDoerDAO creatordoerDao, ChoreDAO choreDao, PrizeDAO prizeDao) {
        this.creatorDoerDAO = creatordoerDao;
        this.choreDao = choreDao;
        this.prizeDao = prizeDao;
    }

    @GetMapping("/{username}")
    public ResponseEntity<CreatorDoer> login(@PathVariable String username) {
        LOG.info("GET creatordoers/" + username);

        try{
            CreatorDoer creatorDoer = creatorDoerDAO.login(username);
            if (creatorDoer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<CreatorDoer>(creatorDoer, HttpStatus.OK);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }

    @PostMapping("/{username}")
    public ResponseEntity<CreatorDoer> createCreatorDoer(@PathVariable String username) {
        LOG.info("POST /creatordoers " + username);

        try{            
            CreatorDoer creatordoer = creatorDoerDAO.createCreatorDoer(username);
            if (creatordoer != null) {
                return new ResponseEntity<CreatorDoer>(creatordoer, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<CreatorDoer>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    @PutMapping("/{username}/{chore}")
    public ResponseEntity<CreatorDoer> completeChore(@PathVariable String username, @PathVariable Chore chore) {
        LOG.info("PUT / " + username + "/" + chore);

        try{            
            if (chore != null) {
                //if exists put it in the completed chore list
                CreatorDoer creatordoer = creatorDoerDAO.completeChore(username, chore);

                //deletes a chore from the main public list
                choreDao.deleteChore(chore.getId());

                return new ResponseEntity<CreatorDoer>(creatordoer, HttpStatus.OK);
            } else {
                return new ResponseEntity<CreatorDoer>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   

    }

    @PutMapping("/a/{username}/{prize}")
    public ResponseEntity<CreatorDoer> claimPrize(String username, Prize prize) throws IOException {
        LOG.info("PUT / " + username + "/" + prize);

        try{            
            if (prize != null) {
                //puts a prize into the claimed prized list
                CreatorDoer creatordoer = creatorDoerDAO.claimPrize(username, prize);

                //remove the prize list
                prizeDao.deletePrize(prize.getId());

                return new ResponseEntity<CreatorDoer>(creatordoer, HttpStatus.OK);
            } else {
                return new ResponseEntity<CreatorDoer>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    @DeleteMapping("/a/{username}/{prize}")
    public ResponseEntity<CreatorDoer> redeemPrize(String username, Prize prize) throws IOException {
        LOG.info("DELETE / " + username + "/" + prize);

        try{            
            if (prize != null) {
                //puts a prize into the claimed prized list
                CreatorDoer creatordoer = creatorDoerDAO.redeemPrize(username, prize);

                return new ResponseEntity<CreatorDoer>(creatordoer, HttpStatus.OK);
            } else {
                return new ResponseEntity<CreatorDoer>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

}