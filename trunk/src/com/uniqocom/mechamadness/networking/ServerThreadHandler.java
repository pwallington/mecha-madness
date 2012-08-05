/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.uniqocom.mechamadness.networking;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pwallington
 */
public class ServerThreadHandler implements Runnable {

    static ServerThreadHandler getSingleton() {
        if (singleton != null) {
            return singleton;
        }
        else
        {
            singleton = new ServerThreadHandler();
            return singleton;
        }
    }

    private ServerThreadHandler() {
        super();
    }

    static ServerThreadHandler singleton = null;
    static LinkedList<ServerThread> threadList = new LinkedList<ServerThread>();

    static final int port = 4799;

    //FIXME
    static int maxConnections = 100;
    private ServerSocket serverSocket = null;


    public void run() {

        int numConnections = 0;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            return;
        }

        while (true) {
            ServerThread newThread = null;
            try {
                newThread = new ServerThread(serverSocket.accept());
            } catch (IOException ex) {
                Logger.getLogger(ServerThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }
            numConnections++;
            newThread.setId(numConnections);
            threadList.addLast(newThread);
            newThread.run();

            if (numConnections > maxConnections) {
                newThread.sendStr("server full - you are in the queue");
            }
            // TODO notify server logic of new client

        }
        
    }

    public void broadcast(Message msg) {
        for (ServerThread thread : threadList) {
            thread.sendMsg(msg);
        }
    }

    public void stopListening() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
