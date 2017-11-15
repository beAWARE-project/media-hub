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
import java.util.logging.Level;
import java.util.logging.Logger;
import json.Attachment;
import json.Header;
import json.ImageAnalyzed;
import json.ImageAnalyzedBody;
import json.IncidentReport;
import json.MessageFromIA;
import json.MessageFromVA;
import json.MessageToIA;
import json.MessageToVA;
import json.VideoAnalyzed;
import json.VideoAnalyzedBody;
import mykafka.Bus;

public class ImageRequester extends Thread{
    Socket soc;
    DataInputStream din;
    DataOutputStream dout;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    String over = "Msg from IA received";
    IncidentReport incidentReport;
    Attachment attachment;
    Bus bus = new Bus();
    
    public ImageRequester(IncidentReport incidentReport, Attachment attachment){
        this.incidentReport = incidentReport;
        this.attachment = attachment;
    }
    
    @Override
    public void run()
    {
        MessageToIA newMessageToIA = new MessageToIA(attachment.getAttachmentURL());
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
            
            ImageAnalyzedBody imageAnalyzedBody = new ImageAnalyzedBody(attachment.getAttachmentTimeStampUTC() , incidentReport.getBody().getPosition(), attachment.getAttachmentURL(), messageFromIA.getImgAnalyzed(), messageFromIA.getImgAnalysis());
            Header header = incidentReport.getHeader();
            header.setTopicName(Configuration.image_analyzed);
            ImageAnalyzed imageAnalyzed = new ImageAnalyzed(header, imageAnalyzedBody);
            String message = gson.toJson(imageAnalyzed);
            bus.post(Configuration.image_analyzed, message);
            
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException ex) {
            Logger.getLogger(ImageRequester.class.getName()).log(Level.SEVERE, null, ex);
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
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            dout.write(bytes);
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
            byte b[] = new byte[1024];
            din.read(b);
            response = new String(b, StandardCharsets.UTF_8);
            response = response.replaceAll("\u0000.*", "");
            System.out.println("server> " + response); 
        } catch (IOException ex) {
            Logger.getLogger(ImageRequester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
}