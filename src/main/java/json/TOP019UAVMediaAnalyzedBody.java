/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

/**
 *
 * @author andreadisst
 */
public class TOP019UAVMediaAnalyzedBody {
    
    String media_timestamp;
    SimplePosition location;
    String incidentID;
    String media_original;
    String media_analyzed;
    String media_analysis;
    
    public TOP019UAVMediaAnalyzedBody(){
        
    }
    
    public TOP019UAVMediaAnalyzedBody(String media_timestamp, SimplePosition location, String incidentID,
                                String media_original, String media_analyzed, String media_analysis){
        this.media_timestamp = media_timestamp;
        this.location = location;
        this.incidentID = incidentID;
        this.media_original = media_original;
        this.media_analyzed = media_analyzed;
        this.media_analysis = media_analysis;
    }
    
}
