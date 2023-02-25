package com.choreio.api.choreioapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Prize {
    @JsonProperty("id") private int id;
    @JsonProperty("prizeName") private String prizeName;
    @JsonProperty("prizeDesc") private String prizeDesc;
    @JsonProperty("pointCost") private int pointCost;

    public Prize(@JsonProperty("id") int id, @JsonProperty("prizeName") String prizeName, 
            @JsonProperty("prizeDesc") String prizeDesc, @JsonProperty("pointCost") int pointCost){
        this.id = id;
        this.prizeName = prizeName;
        this.prizeDesc = prizeDesc;
        this.pointCost = pointCost;
    }

    public int getId(){
        return this.id;
    }

    public String getPrizeName(){
        return this.prizeName;
    }

    public String getPrizeDesc(){
        return this.prizeDesc;
    }

    public int getPointCost(){
        return this.pointCost;
    }

    public void setPrizeName(String prizeName){
        this.prizeName = prizeName;
    }

    public void setPrizeDesc(String prizeDesc){
        this.prizeDesc = prizeDesc;
    }

    public void setPointCost(int pointCost){
        this.pointCost = pointCost;
    }

}
