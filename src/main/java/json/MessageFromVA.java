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
public class MessageFromVA {
    
    MessageFromVAContent message;
    
    public MessageFromVA(){
        
    }
    
    public MessageFromVA(String vid_analyzed, String vid_analysis){
        this.message = new MessageFromVAContent(vid_analyzed, vid_analysis);
    }
    
    public String getVidAnalyzed(){
        return message.getVidAnalyzed();
    }
    
    public String getVidAnalysis(){
        return message.getVidAnalysis();
    }
    
}
