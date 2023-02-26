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

    // public Object[] getClaimedPrizes(){
    //     return this.claimedPrizes.values().toArray();
    // }

    public void completeChore(Chore chore){
        this.completedChores.add(chore);
        this.points += chore.getPoints();
    }

    //true for fulfilled
    //false for not 
    public boolean claimPrize(Prize prize){
        if (this.points >= prize.getPointCost()) {
            this.claimedPrizes.put(prize.getId(), prize);
            this.points -= prize.getPointCost();
            return true;
        }
        return false;
    }

    public Prize redeemPrize(int prizeID){
        if(claimedPrizes.containsKey(prizeID)){
            return this.claimedPrizes.remove(prizeID);
        }
        return null;
    }
 }