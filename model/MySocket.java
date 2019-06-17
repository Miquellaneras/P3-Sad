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


public class MySocket 
{
	public Socket socket;
	BufferedReader in;
	PrintWriter out;
        String nick;

	public MySocket(String nick, int port){
		// Construye y conecta un socket con el servidor indicado
	    try {
	    	socket = new Socket("localhost", port);
	    	init_streams();
                this.nick = nick;
	    } catch (IOException e) {
	    }
	}

	MySocket(Socket s)
	{
	    socket = s;
	    init_streams();
	}

	private void init_streams()
	{
	    try {
	    	in = new BufferedReader(new InputStreamReader(
	    		socket.getInputStream()
	    	));
	    	out = new PrintWriter(socket.getOutputStream(),true);
	    } catch (IOException e) {
	    }
	}
        
        public void setNick(String nick){
            this.nick=nick;
        }
        
        public String getNick(){
            return this.nick;
        }
        
        public BufferedReader getDataInputStream() {
            return this.in;
        }
        
        public PrintWriter getDataOutputStream() {
            return this.out;
        }

	public String read_str()
		// Lee el siguiente 'string'.
		// Si se ha cerrado el otro extremo, obtiene 'null'.
	{
	    try {
	    	return in.readLine();
	    } catch (IOException e) {
	    	//return null
	    	return null;
	    }
	}
        
        public void write_str(String s)
		// Escribe 's' en el socket.
	{
	    out.println(s);
	}

	public void close()
		// Cierra el socket.
	{
	    try {
	    	in.close();
	    	out.close();
	    	socket.close();
	    } catch (IOException e) {
	    }
	}
        
        public boolean isClosed(){
            return socket.isClosed();
        }
}

