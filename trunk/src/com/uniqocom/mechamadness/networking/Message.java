/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.uniqocom.mechamadness.networking;

import com.uniqocom.mechamadness.gameLogic.*;
import java.io.*;

/**
 *
 * @author pwallington
 */
public class Message implements Serializable {

    int msgSeqNum;
    int clientId;

    String playerName;
    int playerNumber;

    MessageType messageType;

    /**
     * These fields are each optional depending on the message type
     * obviously the appropriate fields need to be present.
     */
    Card[] cardHand = null;
    Card[] cardChoices = null;
    BoardLayout boardLayout = null;
    

    enum MessageType {
        GameSetup,
        TurnStatus,
        CardHand,
        CardChoices,
        Synchronisation,
        Chat
    }
    
}
