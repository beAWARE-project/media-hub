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
public class MessageFromDAContent {
    
    String media_analyzed;
    String media_analysis;
    
    public MessageFromDAContent(){
        
    }
    
    public MessageFromDAContent(String media_analyzed, String media_analysis){
        this.media_analyzed = media_analyzed;
        this.media_analysis = media_analysis;
    }
    
    public String getMediaAnalyzed(){
        return media_analyzed;
    }
    
    public String getMediaAnalysis(){
        return media_analysis;
    }
    
}
