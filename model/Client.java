/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.model;

import Practica1.*;
import chat.view.ChatView;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.MatchResult;

/**
 *
 * @author AliciaBel
 */
public class Client {
    
    MySocket s;
    String liniar, liniaw;
    ChatView view;
    
    public Client(String nick, int port, ChatView cv){
        this.s = new MySocket(nick, port);
        this.view = cv;
    }
    
    public void clientWorking(){
        new Read(view).start();
        s.write_str(s.getNick());
    }
    
    public void write(String message){
        s.write_str(message);
    }
    
    public void close(){
        s.close();
    }
    
    class Read extends Thread {

        Scanner scanner;
        String line;
        String sender;
        ChatView view;
        
        public Read(ChatView cv) {
            this.view = cv;
        }

        public void run() {
            while (!s.isClosed()) {
                while ((liniar = s.read_str()) != null) {
                    scanner = new Scanner(liniar);
                    scanner.useDelimiter(":");
                    sender = scanner.next();
                    if(sender.equalsIgnoreCase("server")){
                        if(scanner.next().equalsIgnoreCase("new")){
                            view.updateClients(scanner.next(), true);
                        } else {
                            view.updateClients(scanner.next(), false);
                        }
                    } else{
                        view.addMessage(scanner.next(), sender);
                    } 
                }
            }
        }
    }
    
}

