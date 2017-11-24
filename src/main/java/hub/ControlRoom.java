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
import json.Attachment;
import json.IncidentReport;

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
                    System.out.println("Message consumed: " + message);
                    
                    Type type = new TypeToken<IncidentReport>() {}.getType();
                    
                    try{
                        IncidentReport incidentReport = gson.fromJson(message, type);

                        if( incidentReport.getHeader().getSender().equals("FRAPP") || incidentReport.getHeader().getSender().equals("SCAPP") ){
                            
                            for(Attachment attachment : incidentReport.getBody().getAttachments()){
                                
                                switch(attachment.getAttachmentType()){
                                    case "image":
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
                    }catch(JsonSyntaxException e){
                        System.out.println(e);
                    }
                }
            }
        } finally {
          kafkaConsumer.close(); 
        }
    }
    
}
