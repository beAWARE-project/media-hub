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
public class MessageFromVAContent {
    
    String vid_analyzed;
    String vid_analysis;
    
    public MessageFromVAContent(){
        
    }
    
    public MessageFromVAContent(String vid_analyzed, String vid_analysis){
        this.vid_analyzed = vid_analyzed;
        this.vid_analysis = vid_analysis;
    }
    
    public String getVidAnalyzed(){
        return vid_analyzed;
    }
    
    public String getVidAnalysis(){
        return vid_analysis;
    }
    
}
