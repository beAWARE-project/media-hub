/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andreadisst
 */
public class MessageToIAContent {
    
    String URL;
    String incidentType;
    String attachmentTimeStampUTC;
    
    public MessageToIAContent(){
        
    }
    
    public MessageToIAContent(String URL, String incidentType, String attachmentTimeStampUTC){
        this.URL = URL;
        this.incidentType = incidentType;
        this.attachmentTimeStampUTC = attachmentTimeStampUTC;
    }
    
}
