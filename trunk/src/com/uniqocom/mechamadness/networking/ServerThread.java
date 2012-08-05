/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.uniqocom.mechamadness.networking;

import java.net.Socket;

/**
 *
 * @author pwallington
 */
class ServerThread implements Runnable {

    private Socket sock = null;
    private int threadIndex = -1;

    public ServerThread(Socket accept) {
        this.sock = accept;
    }

    public void setId(int id) {
        this.threadIndex = id;
    }
    public int getId() {
        return this.threadIndex;
    }
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean sendStr(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean sendMsg(Message msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
