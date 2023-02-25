package com.choreio.api.choreioapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Chore
 * 
 * @author Patricio Solis
 */

public class Chore {
    @JsonProperty("choreName") private String choreName;
    @JsonProperty("choreDesc") private String choreDesc;
    @JsonProperty("points") private int points;

    public Chore(@JsonProperty("choreName") String choreName, 
        @JsonProperty("choreDesc") String choreDesc, @JsonProperty("points") int points){
        this.choreName = choreName;
        this.choreDesc = choreDesc;
        this.points = points;
    }

    public String getChoreName(){
        return this.choreName;
    }

    public String getChoreDesc(){
        return this.choreDesc;
    }

    public int getPoints(){
        return this.points;
    }

    public void setChoreName(String choreName){
        this.choreName = choreName;
    }

    public void setChoreDesc(String choreDesc){
        this.choreDesc = choreDesc;
    }

    public void setPoints(int points){
        this.points = points;
    }

}
