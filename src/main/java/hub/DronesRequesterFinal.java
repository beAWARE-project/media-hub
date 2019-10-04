/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import jdk.nashorn.internal.parser.JSONParser;
import json.*;
import mykafka.Bus;

public class DronesRequesterFinal extends Thread{

    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    String over = "Msg from DA received";
    IncidentReportList incidentReport;
    Attachment attachment;
    Hashtable<String, SimplePosition> evacuationMissionMap;
    Hashtable<String, ArrayList<DronesRequester>> threadMissionsMap;
    Bus bus = new Bus();

    public DronesRequesterFinal(IncidentReportList incidentReport, Attachment attachment, Hashtable<String, SimplePosition> evacuationMissionMap, Hashtable<String, ArrayList<DronesRequester>> threadMissionsMap){
        this.incidentReport = incidentReport;
        this.attachment = attachment;
        this.evacuationMissionMap = evacuationMissionMap;
        this.threadMissionsMap = threadMissionsMap;
    }

    @Override
    public void run()
    {
        MessageToDA newMessageToDA = new MessageToDA(incidentReport.getBody().getAnalysisTasks(), incidentReport.getBody().getPosition().getLatitude(), incidentReport.getBody().getPosition().getLongitude(),
                incidentReport.getBody().getPosition().getAltitude(), incidentReport.getBody().getPosition().getHeading(), incidentReport.getBody().getPosition().getGimbalPitch(),
                incidentReport.getBody().getPosition().getSpeed(), attachment.getAttachmentName(), attachment.getAttachmentType(), attachment.getAttachmentFormat(),
                attachment.getAttachmentWidth(), attachment.getAttachmentHeight(), attachment.getAttachmentFrameRateFPS(), attachment.getAttachmentURL(), attachment.getAttachmentTimeStampUTC());
        String request = gson.toJson(newMessageToDA);

        try {
            SimplePosition sp;
            String message;

            for (int i = 0; i < threadMissionsMap.get(incidentReport.getBody().getIncidentID()).size(); i++)
            {
                threadMissionsMap.get(incidentReport.getBody().getIncidentID()).get(i).join();
            }
            threadMissionsMap.get(incidentReport.getBody().getIncidentID()).clear();

            if (incidentReport.getBody().getEvacuationStop() == true) {
                sp = evacuationMissionMap.get(incidentReport.getBody().getIncidentID());
                if (sp != null) {
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
        }catch(IOException | InterruptedException | ExecutionException | TimeoutException ex){
        }
    }
}