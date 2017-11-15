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
public class MessageFromIA {
    
    MessageFromIAContent message;
    
    public MessageFromIA(){
        
    }
    
    public MessageFromIA(String img_analyzed, String img_analysis){
        this.message = new MessageFromIAContent(img_analyzed, img_analysis);
    }
    
    public String getImgAnalyzed(){
        return message.getImgAnalyzed();
    }
    
    public String getImgAnalysis(){
        return message.getImgAnalysis();
    }
    
}
