/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.view;

import chat.controller.ClientController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 *
 * @author miquel_llaneras
 */
public class ChatView implements ActionListener{

    JFrame chatFrame;
    JPanel chatPanel;
    DefaultListModel usersModel;
    JList users;
    JButton sendButton;
    JTextField textEntry;
    JScrollPane usersScroller, messagesScroller;
    JTextArea messages;
    ClientController controller;
    
    public ChatView(){
        chatFrame = new JFrame("chatSad");
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        chatPanel = new JPanel();
        chatPanel.setLayout(new GridBagLayout());
        this.addWidgets();
        
        chatFrame.add(chatPanel, BorderLayout.CENTER);
        chatFrame.getRootPane().setDefaultButton(sendButton);
        chatFrame.setSize(800, 600);
        chatFrame.setVisible(true);
        
        this.controller = new ClientController();

        chatFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                controller.close();
            }
        });       
    }
    
    private void addWidgets(){
        GridBagConstraints constraints = new GridBagConstraints();
        
        messages = new JTextArea();
        messages.setLineWrap(true);
        messages.setEditable(false);
        messagesScroller = new JScrollPane(messages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setConstraints(messagesScroller, constraints, 0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.BOTH);
        
        textEntry = new JTextField();
        this.setConstraints(textEntry, constraints, 0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.HORIZONTAL);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        this.setConstraints(sendButton, constraints, 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NONE);
        
        usersModel = new DefaultListModel();
        users = new JList(usersModel);
        usersScroller = new JScrollPane(users);
        usersScroller.setBorder(new TitledBorder("Users Online"));
        this.setConstraints(usersScroller, constraints, 2, 0, 1, 2, 0.5, 1.0, GridBagConstraints.BOTH); 
    }
    
    
    public void setConstraints(JComponent component, GridBagConstraints constraints, int column, int row, int width, int height, double weightx, double weighty, int fill){
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.fill = fill;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        chatPanel.add(component, constraints);
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
    }
    
    public void updateClients(String client, boolean add){
        if(add){
            usersModel.addElement(client);
        } else {
            usersModel.removeElement(client);
        }
    }
    
    public void addMessage(String message, String nick){
        if(nick == null){
            messages.append(message + "\n");
        } else {
            messages.append(nick.toUpperCase() + "> " + message + "\n");
        }
        messages.revalidate();
        messages.repaint();
    }
    

    public void actionPerformed(ActionEvent e) {
        String message = textEntry.getText();
        controller.write(message);
        this.addMessage(message, null);
        textEntry.setText("");
    }
    
    private static void createAndShowGUI(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            
        }
        ChatView chat = new ChatView();   
        chat.controller.initClient(args, chat);
    }
    
    
    public static void main(final String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                createAndShowGUI(args);
            }
        });     
    }
}

