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
public class ImageAnalyzedBody {
    
    String media_timestamp;
    Position location;
    int incidentID;
    String im_original;
    String im_analyzed;
    String im_analysis;
    
    public ImageAnalyzedBody(){
        
    }
    
    public ImageAnalyzedBody(String media_timestamp, Position location, int incidentID,
                                String im_original, String im_analyzed, String im_analysis){
        this.media_timestamp = media_timestamp;
        this.location = location;
        this.incidentID = incidentID;
        this.im_original = im_original;
        this.im_analyzed = im_analyzed;
        this.im_analysis = im_analysis;
    }
    
}
