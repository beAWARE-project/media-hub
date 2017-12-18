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
public class MessageToASR {
    
    MessageToASRContent message;
    
    public MessageToASR(){
        
    }
    
    public MessageToASR(String URL, String timestamp, String language, String incidentID){
        this.message = new MessageToASRContent(URL, timestamp, language, incidentID);
    }
    
}
