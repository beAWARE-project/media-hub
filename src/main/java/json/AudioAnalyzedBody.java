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
    String audio_link;
    String timestamp;
    
    public AudioAnalyzedBody(){
        
    }
    
    public AudioAnalyzedBody(String transcription_ID, String audio_link, String timestamp){
        this.transcription_ID = transcription_ID;
        this.audio_link = audio_link;
        this.timestamp = timestamp;
    }
    
}
