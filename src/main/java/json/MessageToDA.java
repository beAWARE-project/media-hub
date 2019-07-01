/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.List;

/**
 *
 * @author andreadisst
 */
public class MessageToDA {
    
    MessageToDAContent message;
    
    public MessageToDA(){
        
    }
    
    public MessageToDA(List<String> analysisTasks, List<Float>  latitude, List<Float>  longitude, List<Float>  altitude, List<Integer>  heading,List<Integer> gimbalPitch, double speed, String attachmentName,
                              String attachmentType, String attachmentFormat, int attachmentWidth, int attachmentHeight, int attachmentFrameRateFPS, String attachmentURL,
                              String attachmentTimeStampUTC){
        this.message = new MessageToDAContent(analysisTasks, latitude, longitude, altitude, heading, gimbalPitch, speed, attachmentName,
                              attachmentType, attachmentFormat, attachmentWidth, attachmentHeight, attachmentFrameRateFPS, attachmentURL,
                              attachmentTimeStampUTC);
    }
    
}
