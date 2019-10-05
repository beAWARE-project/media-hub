package hub;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;

import json.*;
import mykafka.BusReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import utils.CDR;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andreadisst
 */
public class ControlRoom {
    
    private static Gson gson = new Gson();
    //private static String logFilename = "media-hub-log.txt";
    
    public static void main(String[] args)  {
        
        BusReader busReader = new BusReader();
        KafkaConsumer<String, String> kafkaConsumer = busReader.getKafkaConsumer();
        kafkaConsumer.subscribe(Arrays.asList(Configuration.incident_report_topic,Configuration.uavp_message_topic));

        Hashtable<String, SimplePosition> evacuationMissionsMap = new Hashtable<String, SimplePosition>();
        evacuationMissionsMap.clear();
        Hashtable<String, ArrayList<DronesRequester>> threadMissionsMap = new Hashtable<String,ArrayList<DronesRequester>>();
        threadMissionsMap.clear();

        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                for (ConsumerRecord<String, String> record : records)
                {
                    String message = record.value();

                    //BufferedWriter writer = new BufferedWriter(new FileWriter(logFilename));
                    //writer.write("Message consumed: " + message + "\n");
                    System.out.println("Message consumed: " + message);


                    Type header = new TypeToken<IncidentReportHeader>() {}.getType();

                    try{
                        //
                        IncidentReportHeader incidentReportHeader = gson.fromJson(message, header);

                        if( incidentReportHeader.getHeader().getTopicName().equals(Configuration.incident_report_topic) ){
                            if( incidentReportHeader.getHeader().getSender().equals("FRAPP") || incidentReportHeader.getHeader().getSender().equals("SCAPP") || incidentReportHeader.getHeader().getSender().contains("CCTV") ){
                                //writer.write("Sender is APP\n");
                                Type type = new TypeToken<IncidentReport>() {}.getType();
                                IncidentReport incidentReport = gson.fromJson(message, type);
                                for(Attachment attachment : incidentReport.getBody().getAttachments()){
                                    //writer.write("Has attachment\n");
                                    switch(attachment.getAttachmentType()){
                                        case "image":
                                            //writer.write("Attachment is an image\n");
                                            ImageRequester imageRequester = new ImageRequester(incidentReport, attachment);
                                            imageRequester.start();
                                            break;
                                        case "video":
                                            VideoRequester videoRequester = new VideoRequester(incidentReport, attachment);
                                            videoRequester.start();
                                            break;
                                        case "audio":
                                            AudioRequester audioRequester = new AudioRequester(incidentReport, attachment);
                                            audioRequester.start();
                                            break;
                                    }
                                }
                            }
                        }else if( incidentReportHeader.getHeader().getTopicName().equals(Configuration.uavp_message_topic) ){
                            Type type = new TypeToken<IncidentReportList>() {}.getType();
                            IncidentReportList incidentReport = gson.fromJson(message, type);
                            IncidentReportBodyList incidentReportBody = incidentReport.getBody();
                            String analTask = incidentReportBody.getAnalysisTasks().get(0);

                            if(analTask.equals("Evacuation")){
                                SimplePosition sp;
                                if((evacuationMissionsMap.get(incidentReportBody.getIncidentID()) ==  null) && ( incidentReportBody.getEvacuationStop() == false)) {
                                    sp = new SimplePosition(incidentReport.getBody().getPosition().getLatitude().get(0), incidentReport.getBody().getPosition().getLongitude().get(0));
                                    evacuationMissionsMap.put(incidentReportBody.getIncidentID(), sp);
                                }
                                if((evacuationMissionsMap.get(incidentReportBody.getIncidentID()) !=  null) && ( incidentReportBody.getEvacuationStop() == true)){
                                    for (Attachment attachment : incidentReportBody.getAttachments()) {
                                        DronesRequesterFinal dronesRequesterFinal = new DronesRequesterFinal(incidentReport, attachment, evacuationMissionsMap, threadMissionsMap);
                                        dronesRequesterFinal.start();
                                    }
                                }
                                else {
                                    for (Attachment attachment : incidentReportBody.getAttachments()) {
                                        DronesRequester dronesRequester = new DronesRequester(incidentReport, attachment, evacuationMissionsMap);
                                        ArrayList<DronesRequester> arrThreads;
                                        if (threadMissionsMap.get(incidentReportBody.getIncidentID()) == null) {
                                            arrThreads = new ArrayList<DronesRequester>();
                                            threadMissionsMap.put(incidentReportBody.getIncidentID(), arrThreads);
                                            arrThreads.add(dronesRequester);
                                        } else {
                                            arrThreads = threadMissionsMap.get(incidentReportBody.getIncidentID());
                                            arrThreads.add(dronesRequester);
                                        }
                                        dronesRequester.start();

                                    }
                                }
                            }
                            else{
                                for(Attachment attachment : incidentReport.getBody().getAttachments()){
                                    DronesRequester dronesRequester = new DronesRequester(incidentReport, attachment, evacuationMissionsMap);
                                    dronesRequester.start();
                                }
                            }


                        }
                        
                        //writer.close();
                        //CDR.storeFile(logFilename, logFilename);
                        
                    }catch(JsonSyntaxException e){
                        System.out.println(e);
                    }
                }
            }
        } //catch (IOException ex) { System.out.println(ex); }
        finally {
          kafkaConsumer.close(); 
        }

    }
    
}
