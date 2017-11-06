/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hub;

/**
 *
 * @author andreadisst
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.MessageFromVA;
import json.MessageToVA;

public class Requester{
    Socket soc;
    DataInputStream din;
    DataOutputStream dout;
    Requester(){}
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    String over = "Msg from VA received";
    
    void run()
    {
        Instant startTimeUTC = Instant.now();
        String URL = "http://object-store-app.eu-gb.mybluemix.net/objectStorage?file=flood.mp4";
        MessageToVA newMessageToVA = new MessageToVA(startTimeUTC.toString(), URL);
        String request = gson.toJson(newMessageToVA);
        
        try{      
            
            soc = new Socket(Configuration.IP, Configuration.port);  
            
            din = new DataInputStream(soc.getInputStream());
            dout = new DataOutputStream(soc.getOutputStream());
            
            sendMessage(request);

            readMessage();

            String response = readMessage();
            MessageFromVA messageFromVA = gson.fromJson(response, MessageFromVA.class);
            System.out.println(messageFromVA.getVidAnalyzed());
            System.out.println(messageFromVA.getVidAnalysis());
            
            sendMessage(over);
            
        }catch(IOException e){
            System.out.println("Error: " + e);
        }finally{
            try{
                din.close();
                dout.close();
                soc.close();
            }catch(IOException e){
                System.out.println("Error: " + e);
            }
        }
    }
    
    void sendMessage(String msg)
    {
        try{
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            dout.write(bytes);
            dout.flush();
            System.out.println("client> " + msg);
        }
        catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
    
    String readMessage()
    {
        String response = "";
        try {
            byte b[] = new byte[1024];
            din.read(b);
            response = new String(b, StandardCharsets.UTF_8);
            response = response.replaceAll("\u0000.*", "");
            System.out.println("server> " + response); 
        } catch (IOException ex) {
            Logger.getLogger(Requester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
}