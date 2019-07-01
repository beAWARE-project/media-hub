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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import json.Attachment;
import json.TOP010AudioAnalyzed;
import json.TOP010AudioAnalyzedBody;
import json.Header;
import json.IncidentReport;
import json.MessageFromASR;
import json.MessageToASR;
import json.SimplePosition;
import mykafka.Bus;

public class AudioRequester extends Thread{
    Socket soc;
    ObjectInputStream din;
    ObjectOutputStream dout;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    IncidentReport incidentReport;
    Attachment attachment;
    Bus bus = new Bus();
    
    String start = "Connection successful";
    String ongoing = "Msg from Hub received. File is being processed..";
    String over = "bye";
    
    public AudioRequester(){
        
    }
    
    public AudioRequester(IncidentReport incidentReport, Attachment attachment){
        this.incidentReport = incidentReport;
        this.attachment = attachment;
    }
    
    @Override
    public void run()
    {
        MessageToASR newMessageToASR = new MessageToASR(attachment.getAttachmentURL(), attachment.getAttachmentTimeStampUTC(), incidentReport.getBody().getLanguage(), incidentReport.getBody().getIncidentID());
        String request = gson.toJson(newMessageToASR);
       
        try{      
            
            soc = new Socket(Configuration.audio_IP, Configuration.audio_port);  
            
            dout = new ObjectOutputStream(soc.getOutputStream());
            din = new ObjectInputStream(soc.getInputStream());
            
            String message;
            do{
                message = readMessage();

                boolean terminate = true;

                if (message.equals(start)){
                    sendMessage(request);
                    terminate = false;
                }       

                if (message.equals(ongoing)){
                    terminate = false;
                }

                if ( terminate == true && !message.equals(over) ) {
                    
                    MessageFromASR messageFromASR = gson.fromJson(message, MessageFromASR.class);

                    Header header = incidentReport.getHeader();
                    String date = new java.text.SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss'Z'").format(new java.util.Date(System.currentTimeMillis()));
                    TOP010AudioAnalyzedBody audioAnalyzedBody = new TOP010AudioAnalyzedBody(header.getSender(), incidentReport.getBody().getIncidentID(), messageFromASR.getLanguage(), date, messageFromASR.getTranscription(), new SimplePosition(incidentReport.getBody().getPosition().getLatitude().get(0),incidentReport.getBody().getPosition().getLongitude().get(0)));
                    header.setTopicName(Configuration.audio_analyzed_topic);
                    header.setSender("ASR");
                    TOP010AudioAnalyzed audioAnalyzed = new TOP010AudioAnalyzed(header, audioAnalyzedBody);
                    String topic_message = gson.toJson(audioAnalyzed);
                    bus.post(Configuration.audio_analyzed_topic, topic_message);
                    
                    sendMessage(over);
                }
                
            }while(!message.equals(over));
            
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException ex) {
            Logger.getLogger(AudioRequester.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void sendMessage(String msg)
    {
        try{
            dout.writeObject(msg);
            dout.flush();
            System.out.println("client> " + msg);
        }
        catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
    
    private String readMessage()
    {
        String response = "";
        try {
            response = (String) din.readObject();
            System.out.println("server> " + response); 
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(AudioRequester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
}