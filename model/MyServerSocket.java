/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.model;

/**
 *
 * @author AliciaBel
 */
import java.net.*;
import java.io.*;


public class MyServerSocket
{
	ServerSocket socket;

	public MyServerSocket(int port)
	{
	    try {
	    	socket = new ServerSocket(port);
	    } catch (IOException e) {
	    }
	}

	public MySocket accept()
		// Espera y obtiene un socket connectado con un nuevo cliente
	{
	    try {
	    	Socket cs = socket.accept();
	    	return new MySocket(cs);
	    } catch (IOException e) {
	    	return null;
	    }
	}
        
}
