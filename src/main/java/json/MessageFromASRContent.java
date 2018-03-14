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
public class MessageFromASRContent {
    
    String transcription;
    String language;
    
    public MessageFromASRContent(){
        
    }
    
    public MessageFromASRContent(String transcription, String language){
        this.transcription = transcription;
        this.language = language;
    }
    
    public String getTranscription(){
        return transcription;
    }
    
    public String getLanguage(){
        return language;
    }
    
}
