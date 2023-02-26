package com.choreio.api.choreioapi.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Creator and Doer of chores
 * 
 * @author Patricio Solis
 */

 public class CreatorDoer{
    @JsonProperty("userName") private String userName;
    @JsonProperty("points") private int points;
    @JsonProperty("completedChores") private ArrayList<Chore> completedChores;
    @JsonProperty("claimedPrizes") private HashMap<Integer, Prize> claimedPrizes;

    public CreatorDoer(@JsonProperty("userName") String userName){
        this.userName = userName;
        this.points = 0;
        this.completedChores = new ArrayList<>();
        this.claimedPrizes = new HashMap<>();
    }

    public String getUserName(){
        return this.userName;
    }

    public int getPoints(){
        return this.points;
    }

    public ArrayList<Chore> getCompletedChores(){
        return this.completedChores;
    }

    public Object[] getClaimedPrizes(){
        return  this.claimedPrizes.values().toArray();
    }

    public void completeChore(Chore chore){
        this.completedChores.add(chore);
        this.points += chore.getPoints();
    }

    public void claimPrize(Prize prize){
        this.claimedPrizes.put(prize.getId(), prize);
        this.points -= prize.getPointCost();
    }

    public Prize redeemPrize(Prize prize){
        if(claimedPrizes.containsKey(prize.getId())){
            return this.claimedPrizes.remove(prize.getId());
        }
        return null;
    }
 }