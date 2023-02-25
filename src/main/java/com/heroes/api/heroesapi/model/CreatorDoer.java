package com.heroes.api.heroesapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Creator and Doer of chores
 * 
 * @author Patricio Solis
 */

 public class CreatorDoer {
    @JsonProperty("userName") private String userName;
    @JsonProperty("points") private int points;
    @JsonProperty("completedChores") private ArrayList<Chore> completedChores;
    @JsonProperty("claimedPrizes") private ArrayList<Prize> claimedPrizes;

    public CreatorDoer(@JsonProperty("userName") String userName){
        this.userName = userName;
        this.points = 0;
        this.completedChores = new ArrayList<>();
        this.claimedPrizes = new ArrayList<>();
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

    public ArrayList<Prize> getClaimedPrizes(){
        return this.claimedPrizes;
    }

    public void completeChore(Chore chore){
        this.completedChores.add(chore);
        this.points += chore.getPoints();
    }

    //
    //
    //

    public void claimPrize(Prize prize){
        this.claimedPrizes.add(prize);
        this.points -= prize.getPointCost();
    }

    //takes a specific prize and removes it from the claimed prizes list
    //
    //
    public Prize redeemPrize(int prizeID){
        if(claimedPrizes.size() > 0){
            return this.claimedPrizes.remove(prizeID);
        }
        return null;
    }
 }