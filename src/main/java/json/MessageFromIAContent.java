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
public class MessageFromIAContent {
    
    String img_analyzed;
    String img_analysis;
    
    public MessageFromIAContent(){
        
    }
    
    public MessageFromIAContent(String img_analyzed, String img_analysis){
        this.img_analyzed = img_analyzed;
        this.img_analysis = img_analysis;
    }
    
    public String getImgAnalyzed(){
        return img_analyzed;
    }
    
    public String getImgAnalysis(){
        return img_analysis;
    }
    
}
