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
public class AudioAnalyzedBody {
    
    String transcription_ID;
    String timestamp;
    String incidentID;
    String language;
    
    public AudioAnalyzedBody(){
        
    }
    
    public AudioAnalyzedBody(String transcription_ID, String timestamp, String incidentID, String language){
        this.transcription_ID = transcription_ID;
        this.timestamp = timestamp;
        this.incidentID = incidentID;
        this.language = language;
    }
    
}
