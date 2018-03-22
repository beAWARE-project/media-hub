package hub;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import mykafka.BusReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import json.Attachment;
import json.IncidentReport;
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
    private static String logFilename = "media-hub-log.txt";
    
    public static void main(String[] args)  {
        
        BusReader busReader = new BusReader();
        KafkaConsumer<String, String> kafkaConsumer = busReader.getKafkaConsumer();
        kafkaConsumer.subscribe(Arrays.asList(Configuration.incident_report_topic));
        
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                for (ConsumerRecord<String, String> record : records)
                {
                    String message = record.value();
                    
                    BufferedWriter writer = new BufferedWriter(new FileWriter(logFilename));
                    writer.write("Message consumed: " + message + "\n");
                    //System.out.println("Message consumed: " + message);
                    
                    Type type = new TypeToken<IncidentReport>() {}.getType();
                    
                    try{
                        IncidentReport incidentReport = gson.fromJson(message, type);

                        if( incidentReport.getHeader().getSender().equals("FRAPP") || incidentReport.getHeader().getSender().equals("SCAPP") ){
                            writer.write("Sender is APP\n");
                            for(Attachment attachment : incidentReport.getBody().getAttachments()){
                                writer.write("Has attachment\n");
                                switch(attachment.getAttachmentType()){
                                    case "image":
                                        writer.write("Attachment is an image\n");
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
                        
                        writer.close();
                        CDR.storeFile(logFilename, logFilename);
                        
                    }catch(JsonSyntaxException e){
                        System.out.println(e);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
          kafkaConsumer.close(); 
        }

    }
    
}
