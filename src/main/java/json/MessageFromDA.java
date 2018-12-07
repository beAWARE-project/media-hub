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
public class MessageFromDA {
    
    MessageFromDAContent message;
    
    public MessageFromDA(){
        
    }
    
    public MessageFromDA(String media_analyzed, String media_analysis){
        this.message = new MessageFromDAContent(media_analyzed, media_analysis);
    }
    
    public String getMediaAnalyzed(){
        return message.getMediaAnalyzed();
    }
    
    public String getMediaAnalysis(){
        return message.getMediaAnalysis();
    }
    
}
