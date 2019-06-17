/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.controller;

import chat.model.Client;
import chat.view.ChatView;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 *
 * @author miquel_llaneras
 */
public class ClientController {
    Client c;
    
    public ClientController(){
        
    }
    
    public void initClient(String[] line, ChatView cv){
        int port = Integer.parseInt(line[1]);
        String nick = line[0];
        c = new Client(nick, port, cv);
        c.clientWorking();
    }
    
    public void write(String message){
        c.write(message);
    }
    
    public void close(){
        c.close();
    }
    
}
