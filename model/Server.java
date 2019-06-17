/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.model;

import java.io.IOException;
import java.util.HashMap;
import Practica1.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author AliciaBel
 */
public class Server {

    int port;
    HashMap<String, MySocket> clients;
    MyServerSocket ss;
    String message = new String();
    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    Lock r = rwl.readLock();
    Lock w = rwl.writeLock();

    public Server(int port) {
        this.port = port;
        clients = new HashMap<String, MySocket>();
        ss = new MyServerSocket(this.port);
    }

    public void newClient() {
        MySocket sc = ss.accept();
        new ThreadServei(sc, this).start();
        
    }

    public void listen() {
        while (true) {
            this.newClient();
        }
    }

    public void writeInClients(String line, String sender) {
        r.lock();
        try {
            Iterator<String> i = clients.keySet().iterator();
            while (i.hasNext()) {
                String receiver = i.next();
                if (receiver.compareTo(sender) != 0) {
                    clients.get(receiver).write_str(sender + ":" + line);
                }
            }
        } finally {
            r.unlock();
        }
    }
    
    public void sendList(String nick){
        r.lock();
        try {
            Iterator<String> i = clients.keySet().iterator();
            while (i.hasNext()) {
                String client = i.next();
                if (client.compareTo(nick) != 0) {
                    clients.get(nick).write_str("server:new:" + client);
                }
            }
        } finally {
            r.unlock();
        }
    }

    class ThreadServei extends Thread {

        MySocket s;
        BufferedReader in;
        PrintWriter out;
        Server server;

        ThreadServei(MySocket s, Server server) {
            this.s = s;
            this.server = server;
            this.in = s.getDataInputStream();
            this.out = s.getDataOutputStream();
        }

        public void run() {
            while (true) {
                w.lock();
                try {
                    String nick = in.readLine();
                    clients.put(nick, s);
                    s.setNick(nick);
                    server.writeInClients("new:" + nick, "server");
                    server.sendList(nick);
                    System.out.println(nick);
                } catch (IOException e) {

                } finally {
                    w.unlock();
                    break;
                }
            }
            while (true) {
                try {
                    String line = in.readLine();
                    if ("EOF".equals(line)) {
                        s.close();
                    } else {
                        r.lock();
                        server.writeInClients(line, s.getNick());
                        r.unlock();
                    }
                } catch (IOException e) {

                }
            }
        }

    }

}
