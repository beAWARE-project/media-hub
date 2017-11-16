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
    
    String IDRef;
    
    public MessageFromASR(){
        
    }
    
    public MessageFromASR(String IDRef){
        this.IDRef = IDRef;
    }
    
    public String getIDRef(){
        return IDRef;
    }
    
}
