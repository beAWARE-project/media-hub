package hub;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
                            boolean createLastFromDrones = false;
                            if(analTask.equals("Evacuation")){
                                SimplePosition sp;
                                if((evacuationMissionsMap.get(incidentReportBody.getIncidentID()) ==  null) && ( incidentReportBody.getEvacuationStop() == false)) {
                                    sp = new SimplePosition(incidentReport.getBody().getPosition().getLatitude().get(0), incidentReport.getBody().getPosition().getLongitude().get(0));
                                    evacuationMissionsMap.put(incidentReportBody.getIncidentID(), sp);
                                }
                                else if((evacuationMissionsMap.get(incidentReportBody.getIncidentID()) ==  null) && ( incidentReportBody.getEvacuationStop() == true)){
                                    createLastFromDrones = true;
                                }
                            }
                            for (Attachment attachment : incidentReportBody.getAttachments()) {
                                DronesRequester dronesRequester = new DronesRequester(incidentReport, attachment, evacuationMissionsMap);
                                dronesRequester.start();
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
