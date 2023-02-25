package com.heroes.api.heroesapi.persistence;

import java.io.IOException;
import com.heroes.api.heroesapi.model.CreatorDoer; //fix this


public interface CreaterDoerDAO {


    CreatorDoer login( String username) throws IOException;

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
     CreatorDoer completeChore( Chore chore ) throws IOException;

    /**
     * Claims prize and puts it into the claimedprizes list of {@linkplain CreatorDoer creatordoer} with the given username
     *
     * @return the new {@link CreatorDoer creatorDoer } claimedPrizes list after claiming a prize
     * 
     * @throws IOException if underlying storage cannot be accessed
     */

    //add to claimlist of createrdoer  
    //subtracts points of the createrdoer by the same number of points of the prize
    CreatorDoer claimPrize( Prize prize ) throws IOException;

    /**
     * Removes a claimed prize from the list of claimedPrizes of the {@linkplain CreatorDoer creatordoer} with the given username
     * 
     * @return the new {@link Prize prize} after redeeming a prize
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    //remove the prize from the claimprize list of createrdoer
     CreatorDoer redeemPrize() throws IOException;
}
