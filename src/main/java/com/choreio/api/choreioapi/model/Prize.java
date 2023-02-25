package com.choreio.api.choreioapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Prize {
    @JsonProperty("prizeName") private String prizeName;
    @JsonProperty("prizeDesc") private String prizeDesc;
    @JsonProperty("pointCost") private int pointCost;

    public Prize(@JsonProperty("prizeName") String prizeName, 
            @JsonProperty("prizeDesc") String prizeDesc, @JsonProperty("pointCost") int pointCost){
        this.prizeName = prizeName;
        this.prizeDesc = prizeDesc;
        this.pointCost = pointCost;
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
