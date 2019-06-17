/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.controller;

import chat.model.Server;

/**
 *
 * @author miquel_llaneras
 */
public class ServerController {
    
    public ServerController(){
        
    }

    public void initServer(String line){
        int port = Integer.parseInt(line,10);
        Server s = new Server(port);
        s.listen();
    }
}
