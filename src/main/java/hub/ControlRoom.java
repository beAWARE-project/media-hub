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
        
        String message = "{\n" +
"  \"header\": {\n" +
"    \"topicName\": \"TOP021_INCIDENT_REPORT\",\n" +
"    \"topicMajorVersion\": 1,\n" +
"    \"topicMinorVersion\": 0,\n" +
"    \"sender\": \"SCAPP\",\n" +
"    \"msgIdentifier\": 542854,\n" +
"    \"sentUTC\": \"2018-01-01T12:00:00Z\",\n" +
"    \"status\": \"Actual\",\n" +
"    \"actionType\": \"Update\",\n" +
"    \"specificSender\": \"citizen\",\n" +
"    \"scope\": \"Restricted\",\n" +
"    \"district\": \"Thessaloniki\",\n" +
"    \"recipients\": \"\",\n" +
"    \"code\": 0,\n" +
"    \"note\": \"\",\n" +
"    \"references\": \"\"\n" +
"  },\n" +
"  \"body\": {\n" +
"    \"incidentOriginator\": \"SCAPP\",\n" +
"    \"incidentID\": 542853,\n" +
"    \"language\": \"en-US\",\n" +
"    \"incidentCategory\": \"Other\",\n" +
"    \"incidentType\": \"Other\",\n" +
"    \"priority\": \"undefined\",\n" +
"    \"severity\": \"Unknown\",\n" +
"    \"certainty\": \"Observed\",\n" +
"    \"startTimeUTC\": \"2018-01-01T12:00:00Z\",\n" +
"    \"expirationTimeUTC\": \"2018-01-01T12:00:00Z\",\n" +
"    \"title\": \"\",\n" +
"    \"description\": \"The plaza is flooded. (If the citizen only provides some media files without any description, then this field will be omitted.)\",\n" +
"    \"position\": {\n" +
"      \"latitude\": 45.43417,\n" +
"      \"longitude\": 12.33847\n" +
"    },\n" +
"    \"attachments\": [{\n" +
"      \"attachmentName\": \"Floods.2016.CsQfCsGWIAAhe0c.jpg\",\n" +
"      \"attachmentType\": \"image\",\n" +
"      \"attachmentTimeStampUTC\": \"2018-01-01T12:00:00Z\",\n" +
"      \"attachmentURL\": \"http://object-store-app.eu-gb.mybluemix.net/objectStorage?file=Floods.2016.CsQfCsGWIAAhe0c.jpg\"\n" +
"    }]\n" +
"  }\n" +
"}";
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
                                        //AudioRequester audioRequester = new AudioRequester(incidentReport, attachment);
                                        //audioRequester.start();
                                        break;
                                }
                            }
                            
                        }
                    }catch(JsonSyntaxException e){
                        System.out.println(e);
                    }
        
        /*BusReader busReader = new BusReader();
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
                                        //AudioRequester audioRequester = new AudioRequester(incidentReport, attachment);
                                        //audioRequester.start();
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
        }*/
    }
    
}
