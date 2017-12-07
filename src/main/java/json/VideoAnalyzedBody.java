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
public class VideoAnalyzedBody {
    
    String media_timestamp;
    Position location;
    String incidentID;
    String vid_original;
    String vid_analyzed;
    String vid_analysis;
    
    public VideoAnalyzedBody(){
        
    }
    
    public VideoAnalyzedBody(String media_timestamp, Position location, String incidentID,
                                String vid_original, String vid_analyzed, String vid_analysis){
        this.media_timestamp = media_timestamp;
        this.location = location;
        this.incidentID = incidentID;
        this.vid_original = vid_original;
        this.vid_analyzed = vid_analyzed;
        this.vid_analysis = vid_analysis;
    }
    
}
