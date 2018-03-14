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
public class MessageFromASR {
    
    MessageFromASRContent message;
    
    public MessageFromASR(){
        
    }
    
    public MessageFromASR(String transcription, String language){
        this.message = new MessageFromASRContent(transcription, language);
    }
    
    public String getTranscription(){
        return message.getTranscription();
    }
    
    public String getLanguage(){
        return message.getLanguage();
    }
    
}
