/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andreadisst
 */
public class MessageFromDAContent {
    
    String media_analyzed;
    String media_analysis;
    boolean incident_detected;
    float latitude;
    float longitude;
    
    public MessageFromDAContent(){
        
    }
    
    public MessageFromDAContent(String media_analyzed, String media_analysis, boolean incident_detected, float latitude, float longitude){
        this.media_analyzed = media_analyzed;
        this.media_analysis = media_analysis;
        this.incident_detected  = incident_detected;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getMediaAnalyzed(){
        return media_analyzed;
    }
    
    public String getMediaAnalysis(){
        return media_analysis;
    }
    
    public boolean getIncidentDetected(){
        return incident_detected;
    }
    
    public float getLatitude(){
        return latitude;
    }
    
    public float getLongitude(){
        return longitude;
    }
}
