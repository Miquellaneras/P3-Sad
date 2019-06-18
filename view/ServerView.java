/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.view;

import chat.controller.ServerController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miquel_llaneras
 */
public class ServerView {
    ServerController controller;
    
    public ServerView(){
        this.controller = new ServerController();
    }
            
    public static void main(String[] args){
        ServerView sv = new ServerView();
        sv.controller.initServer(args[0]);         
    }   
}
