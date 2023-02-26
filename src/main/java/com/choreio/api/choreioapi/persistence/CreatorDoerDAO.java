package com.choreio.api.choreioapi.persistence;
import com.choreio.api.choreioapi.model.CreatorDoer;
import com.choreio.api.choreioapi.model.Prize;
import com.choreio.api.choreioapi.model.Chore;

import java.io.IOException;

public interface CreatorDoerDAO {


    CreatorDoer login( String username) throws IOException;

    CreatorDoer getCreatorDoer( String username ) throws IOException;

    CreatorDoer[] getCreatorDoers() throws IOException;

    /**
     * Creates a new user as a creater of chores or completer of chorse {@linkplain CreatorDoer creatordoer} 
     * with the given username
     * 
     * @return the new {@link CreatorDoer creatorDoer} after creating user
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    CreatorDoer createCreatorDoer( String username ) throws IOException;
    
    /**
     * Completes a chore and puts it into the completedchorse list of {@linkplain CreatorDoer creatordoer} with the given username
     * 
     * @return the new {@link CreatorDoer creatorDoer} completedChore list after adding a completed chore
     * 
     * @throws IOException if underlying storage cannot be accessed
     */

     //add to chorelist of createrdoer & add corresponding points 
     CreatorDoer completeChore( String username, Chore chore ) throws IOException;

    /**
     * Claims prize and puts it into the claimedprizes list of {@linkplain CreatorDoer creatordoer} with the given username
     *
     * @return the new {@link CreatorDoer creatorDoer } claimedPrizes list after claiming a prize
     * 
     * @throws IOException if underlying storage cannot be accessed
     */

    //add to claimlist of createrdoer  
    //subtracts points of the createrdoer by the same number of points of the prize
    CreatorDoer claimPrize( String username, Prize prize ) throws IOException;

    /**
     * Removes a claimed prize from the list of claimedPrizes of the {@linkplain CreatorDoer creatordoer} with the given username
     * 
     * @return the new {@link Prize prize} after redeeming a prize
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    //remove the prize from the claimprize list of createrdoer
     CreatorDoer redeemPrize( String username, int prizeID ) throws IOException;
}