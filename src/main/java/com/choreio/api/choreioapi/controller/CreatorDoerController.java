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
import com.choreio.api.choreioapi.persistence.PrizeDAO;
import com.choreio.api.choreioapi.persistence.CreatorDoerDAO;

@RestController
@RequestMapping("creatordoers")
public class CreatorDoerController {
    private static final Logger LOG = Logger.getLogger(CreatorDoerController.class.getName());
    private ChoreDAO choreDao;
    private PrizeDAO prizeDao;
    
    

}
