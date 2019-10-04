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
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import jdk.nashorn.internal.parser.JSONParser;
import json.*;
import mykafka.Bus;

import java.io.FileReader;
import java.io.IOException;

public class DronesRequester extends Thread{
    Socket soc;
    DataInputStream din;
    DataOutputStream dout;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    String over = "Msg from DA received";
    IncidentReportList incidentReport;
    Attachment attachment;
    Hashtable<String, SimplePosition> evacuationMissionMap;
    Bus bus = new Bus();
    BufferedWriter writer;
    
    public DronesRequester(IncidentReportList incidentReport, Attachment attachment, Hashtable<String, SimplePosition> evacuationMissionMap){
        this.incidentReport = incidentReport;
        this.attachment = attachment;
        this.evacuationMissionMap = evacuationMissionMap;
    }
    
    @Override
    public void run()
    {
        MessageToDA newMessageToDA = new MessageToDA(incidentReport.getBody().getAnalysisTasks(), incidentReport.getBody().getPosition().getLatitude(), incidentReport.getBody().getPosition().getLongitude(),
                incidentReport.getBody().getPosition().getAltitude(), incidentReport.getBody().getPosition().getHeading(), incidentReport.getBody().getPosition().getGimbalPitch(),
                incidentReport.getBody().getPosition().getSpeed(), attachment.getAttachmentName(), attachment.getAttachmentType(), attachment.getAttachmentFormat(),
                attachment.getAttachmentWidth(), attachment.getAttachmentHeight(), attachment.getAttachmentFrameRateFPS(), attachment.getAttachmentURL(), attachment.getAttachmentTimeStampUTC());
        String request = gson.toJson(newMessageToDA);
        
        try{
            SimplePosition sp;
            String message;
            if( incidentReport.getBody().getEvacuationStop() == true ) {
                sp = evacuationMissionMap.get(incidentReport.getBody().getIncidentID());
                if(sp != null) {
                    evacuationMissionMap.remove(incidentReport.getBody().getIncidentID());
                    Header header = incidentReport.getHeader();
                    header.setTopicName(Configuration.media_analyzed_topic);
                    String json = "{\"incident_detected\": false,\"media_timestamp\": \"2019-09-24T12:03:27Z\",\"analysisTasks\": [\"Evacuation\"],\"EvacuationStop\": true,\"location\": {\"latitude\": 0,\"longitude\": 0},\"incidentID\": \"INC_UAVP_@sinst-id-12ef3240-ccba-11e9-a234-615883b44fb6\",\"media_analyzed\": \"Unknown\",\"media_analysis\": \"https://beaware-1.eu-de.containers.appdomain.cloud/object-store/Drones/sinst-id-12ef3240-ccba-11e9-a234-615883b44fb6/videoPart00030_output.json\"}";
                    TOP019UAVMediaAnalyzedBody mediaAnalyzedBody = gson.fromJson(json, TOP019UAVMediaAnalyzedBody.class);
                    mediaAnalyzedBody.setIncidentID(incidentReport.getBody().getIncidentID());
                    mediaAnalyzedBody.setPosition(sp);

                    TOP019UAVMediaAnalyzed mediaAnalyzed = new TOP019UAVMediaAnalyzed(header, mediaAnalyzedBody);
                    message = gson.toJson(mediaAnalyzed);
                    bus.post(Configuration.media_analyzed_topic, message);
                }
            }
            else {

                soc = new Socket(Configuration.drones_IP, Configuration.drones_port);

                din = new DataInputStream(soc.getInputStream());
                dout = new DataOutputStream(soc.getOutputStream());

                sendMessage(request);
                readMessage();

                String response = readMessage();
                MessageFromDA messageFromDA = gson.fromJson(response, MessageFromDA.class);
                sendMessage(over);

                if (messageFromDA.getMediaAnalyzed().equals("") && messageFromDA.getMediaAnalysis().equals("") && messageFromDA.getIncidentDetected() == false) {
                    //do nothing
                } else {
                    if(messageFromDA.getIncidentDetected() == true)
                        evacuationMissionMap.replace(incidentReport.getBody().getIncidentID(), new SimplePosition(messageFromDA.getLatitude(), messageFromDA.getLongitude()));
                    TOP019UAVMediaAnalyzedBody mediaAnalyzedBody = new TOP019UAVMediaAnalyzedBody(messageFromDA.getIncidentDetected(), attachment.getAttachmentTimeStampUTC(), incidentReport.getBody().getAnalysisTasks(), incidentReport.getBody().getEvacuationStop(), new SimplePosition(messageFromDA.getLatitude(), messageFromDA.getLongitude()), incidentReport.getBody().getIncidentID(), /*attachment.getAttachmentURL(), */messageFromDA.getMediaAnalyzed(), messageFromDA.getMediaAnalysis());
                    Header header = incidentReport.getHeader();
                    header.setTopicName(Configuration.media_analyzed_topic);
                    TOP019UAVMediaAnalyzed mediaAnalyzed = new TOP019UAVMediaAnalyzed(header, mediaAnalyzedBody);
                    message = gson.toJson(mediaAnalyzed);
                    bus.post(Configuration.media_analyzed_topic, message);
                }
            }
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException  ex) {
            
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