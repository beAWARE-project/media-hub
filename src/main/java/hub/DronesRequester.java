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
import json.TOP019UAVMediaAnalyzed;
import json.TOP019UAVMediaAnalyzedBody;
import json.IncidentReport;
import json.MessageFromDA;
import json.MessageToDA;
import mykafka.Bus;

public class DronesRequester extends Thread{
    Socket soc;
    DataInputStream din;
    DataOutputStream dout;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    String over = "Msg from DA received";
    IncidentReport incidentReport;
    Attachment attachment;
    Bus bus = new Bus();
    BufferedWriter writer;
    
    public DronesRequester(IncidentReport incidentReport, Attachment attachment){
        this.incidentReport = incidentReport;
        this.attachment = attachment;
    }
    
    @Override
    public void run()
    {
        System.out.println("really IN");
        MessageToDA newMessageToDA = new MessageToDA(incidentReport.getBody().getAnalysisTasks(), incidentReport.getBody().getPosition().getLatitude(), incidentReport.getBody().getPosition().getLongitude(),
                incidentReport.getBody().getPosition().getAltitude(), incidentReport.getBody().getPosition().getHeading(), incidentReport.getBody().getPosition().getGimbalPitch(),
                incidentReport.getBody().getPosition().getSpeed(), attachment.getAttachmentName(), attachment.getAttachmentType(), attachment.getAttachmentFormat(),
                attachment.getAttachmentWidth(), attachment.getAttachmentHeight(), attachment.getAttachmentFrameRateFPS(), attachment.getAttachmentURL(), attachment.getAttachmentTimeStampUTC());
        String request = gson.toJson(newMessageToDA);
        System.out.println(request);
        try{      
            
            soc = new Socket(Configuration.drones_IP, Configuration.drones_port);  
            
            din = new DataInputStream(soc.getInputStream());
            dout = new DataOutputStream(soc.getOutputStream());
            
            sendMessage(request);

            readMessage();

            String response = readMessage();
            MessageFromDA messageFromDA = gson.fromJson(response, MessageFromDA.class);
            System.out.println("response");
            sendMessage(over);
            
            TOP019UAVMediaAnalyzedBody mediaAnalyzedBody = new TOP019UAVMediaAnalyzedBody(attachment.getAttachmentTimeStampUTC() , incidentReport.getBody().getPosition(), incidentReport.getBody().getIncidentID(), attachment.getAttachmentURL(), messageFromDA.getMediaAnalyzed(), messageFromDA.getMediaAnalysis());
            Header header = incidentReport.getHeader();
            header.setTopicName(Configuration.media_analyzed_topic);
            TOP019UAVMediaAnalyzed mediaAnalyzed = new TOP019UAVMediaAnalyzed(header, mediaAnalyzedBody);
            String message = gson.toJson(mediaAnalyzed);
            System.out.println(message);
            bus.post(Configuration.media_analyzed_topic, message);
            
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException ex) {
            
        }finally{
            try{
                din.close();
                dout.close();
                soc.close();
            }catch(IOException e){
                
            }
        }
    }
    
    private void sendMessage(String msg)
    {
        try{
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            dout.write(bytes);
            dout.flush();
        }
        catch(IOException e){
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
        } catch (IOException ex) {
        }
        return response;
    }
    
}