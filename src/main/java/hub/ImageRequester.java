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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import json.Attachment;
import json.Header;
import json.TOP018ImageAnalyzed;
import json.TOP018ImageAnalyzedBody;
import json.IncidentReport;
import json.MessageFromIA;
import json.MessageToIA;
import json.SimplePosition;
import mykafka.Bus;
//import utils.CDR;

public class ImageRequester extends Thread{
    Socket soc;
    DataInputStream din;
    DataOutputStream dout;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    String over = "Msg from IA received";
    IncidentReport incidentReport;
    Attachment attachment;
    Bus bus = new Bus();
    //String logFilename = "media-hub-image-log.txt";
    BufferedWriter writer;
    
    public ImageRequester(IncidentReport incidentReport, Attachment attachment){
        this.incidentReport = incidentReport;
        this.attachment = attachment;
    }
    
    @Override
    public void run()
    {
        //log("ImageRequester started\n");
        //log(attachment.getAttachmentURL() + "\n");
        MessageToIA newMessageToIA = new MessageToIA(attachment.getAttachmentURL(), incidentReport.getBody().getIncidentType(), attachment.getAttachmentTimeStampUTC());
        String request = gson.toJson(newMessageToIA);
        
        try{      
            
            soc = new Socket(Configuration.image_IP, Configuration.image_port);  
            
            din = new DataInputStream(soc.getInputStream());
            dout = new DataOutputStream(soc.getOutputStream());
            
            sendMessage(request);

            readMessage();

            String response = readMessage();
            MessageFromIA messageFromIA = gson.fromJson(response, MessageFromIA.class);
            
            sendMessage(over);
            
            TOP018ImageAnalyzedBody imageAnalyzedBody = new TOP018ImageAnalyzedBody(attachment.getAttachmentTimeStampUTC() , new SimplePosition(incidentReport.getBody().getPosition().getLatitude(),incidentReport.getBody().getPosition().getLongitude()), incidentReport.getBody().getIncidentID(), attachment.getAttachmentURL(), messageFromIA.getImgAnalyzed(), messageFromIA.getImgAnalysis());
            Header header = incidentReport.getHeader();
            header.setTopicName(Configuration.image_analyzed_topic);
            TOP018ImageAnalyzed imageAnalyzed = new TOP018ImageAnalyzed(header, imageAnalyzedBody);
            String message = gson.toJson(imageAnalyzed);
            bus.post(Configuration.image_analyzed_topic, message);
            
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException ex) {
            //log(ex.toString());
        }finally{
            try{
                din.close();
                dout.close();
                soc.close();
            }catch(IOException e){
                //log("Error: " + e);
            }
        }
    }
    
    private void sendMessage(String msg)
    {
        try{
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            dout.write(bytes);
            dout.flush();
            //log("client> " + msg + "\n");
        }
        catch(IOException e){
            //log("Error: " + e);
        }
    }
    
    private String readMessage()
    {
        String response = "";
        try {
            byte b[] = new byte[1024];
            din.read(b);
            response = new String(b, StandardCharsets.UTF_8);
            response = response.replaceAll("\u0000.*", "");
            //log("server> " + response + "\n"); 
        } catch (IOException ex) {
            //log(ex.toString());
        }
        return response;
    }
    
    /*private void log(String info){
        
        try {
            writer = new BufferedWriter(new FileWriter(logFilename));
            writer.write(info);
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        CDR.storeFile(logFilename, logFilename);
        
    }*/
}