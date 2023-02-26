package com.choreio.api.choreioapi.model;

// import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Chore
 * 
 * @author Anna Leung
 */

public class Chore {
    @JsonProperty("id") private int id;
    @JsonProperty("choreName") private String choreName;
    @JsonProperty("choreDesc") private String choreDesc;
    @JsonProperty("points") private int points;
    static final String STRING_FORMAT = "Chore [id=%d, name=%s]";

    public Chore(@JsonProperty("id") int id, @JsonProperty("choreName") String choreName, 
        @JsonProperty("choreDesc") String choreDesc, @JsonProperty("points") int points){
        this.choreName = choreName;
        this.choreDesc = choreDesc;
        this.points = points;
        this.id = id;
    }

    /**
     * Retrieves the name of the chore
     * @return The name of the chore
     */
    public String getChoreName(){
        return this.choreName;
    }

    /**
     * Retrieves the name of the chore
     * @return The description of the chore
     */
    public String getChoreDesc(){
        return this.choreDesc;
    }

    /**
     * Retrieves the amount of points from chore
     * @return The points of the chore
     */
    public int getPoints(){
        return this.points;
    }

    /**
     * Sets the name of the chore - necessary for JSON object to Java object deserialization
     * @param name The name of the chore
     */
    public void setChoreName(String choreName){
        this.choreName = choreName;
    }

    /**
     * Sets the description of the chore
     * @param name The description of the chore
     */
    public void setChoreDesc(String choreDesc){
        this.choreDesc = choreDesc;
    }

    /**
     * Sets the point you can get from the chore
     * @param name The points of the chore
     */
    public void setPoints(int points){
        this.points = points;
    }

    /**
     * Retrieves the id of the chore
     * @return The id of the chore
     */
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,choreName);
    }
    
}
