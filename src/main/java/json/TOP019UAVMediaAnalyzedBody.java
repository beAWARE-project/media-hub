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
public class TOP019UAVMediaAnalyzedBody {
    
    boolean incident_detected;
    String media_timestamp;
    SimplePosition location;
    String incidentID;
    String language;
    //String media_original;
    String media_analyzed;
    String media_analysis;
    List<String> analysisTasks;
    boolean EvacuationStop;
    
    public TOP019UAVMediaAnalyzedBody(){
        
    }
    
    public TOP019UAVMediaAnalyzedBody(boolean incident_detected, String media_timestamp,List<String> analysisTasks,boolean EvacuationStop, SimplePosition location, String incidentID, String language,
                                /*String media_original, */String media_analyzed, String media_analysis){
        this.incident_detected = incident_detected;
        this.media_timestamp = media_timestamp;
        this.analysisTasks = analysisTasks;
        this.EvacuationStop = EvacuationStop;
        this.location = location;
        this.incidentID = incidentID;
        this.language = language;
        //this.media_original = media_original;
        this.media_analyzed = media_analyzed;
        this.media_analysis = media_analysis;
    }
    public void setPosition(SimplePosition location){this.location = location;}
    public void setIncidentID(String incidentID){this.incidentID = incidentID;}
    public void setLanguage(String language){this.language = language;}
    public void setTimestamp(String media_timestamp){this.media_timestamp = media_timestamp;}
}
