/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.List;

/**
 *
 * @author andreadisst
 */
public class MessageFromDA {
    
    MessageFromDAContent message;
    
    public MessageFromDA(){
        
    }
    
    public MessageFromDA(String media_analyzed, String media_analysis, boolean incident_detected, float latitude, float longitude){
        this.message = new MessageFromDAContent(media_analyzed, media_analysis, incident_detected, latitude, longitude);
    }
    
    public String getMediaAnalyzed(){
        return message.getMediaAnalyzed();
    }
    
    public String getMediaAnalysis(){
        return message.getMediaAnalysis();
    }
    
    public boolean getIncidentDetected(){
        return message.getIncidentDetected();
    }
    
    public float getLatitude(){
        return message.getLatitude();
    }
    
    public float getLongitude(){
        return message.getLongitude();
    }

}
