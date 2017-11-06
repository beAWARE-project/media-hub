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
public class MessageToVAContent {
    
    String startTimeUTC;
    String URL;
    
    public MessageToVAContent(){
        
    }
    
    public MessageToVAContent(String startTimeUTC, String URL){
        this.startTimeUTC = startTimeUTC;
        this.URL = URL;
    }
    
}
