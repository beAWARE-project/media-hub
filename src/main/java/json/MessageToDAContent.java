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
public class MessageToDAContent {
    
    List<String> analysisTasks;
    List<Float> latitude;
    List<Float> longitude;
    List<Float> altitude;
    List<Integer> heading;
    List<Integer> gimbalPitch;
    double speed;
    String attachmentName;
    String attachmentType;
    String attachmentFormat;
    int attachmentWidth;
    int attachmentHeight;
    int attachmentFrameRateFPS;
    String attachmentURL;
    String attachmentTimeStampUTC;
    
    public MessageToDAContent(){
        
    }
    
    public MessageToDAContent(List<String> analysisTasks,  List<Float> latitude, List<Float> longitude,  List<Float> altitude, List<Integer> heading, List<Integer> gimbalPitch, double speed, String attachmentName,
                              String attachmentType, String attachmentFormat, int attachmentWidth, int attachmentHeight, int attachmentFrameRateFPS, String attachmentURL,
                              String attachmentTimeStampUTC){
        this.analysisTasks = analysisTasks;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.heading = heading;
        this.gimbalPitch = gimbalPitch;
        this.speed = speed;
        this.attachmentName = attachmentName;
        this.attachmentType = attachmentType;
        this.attachmentFormat = attachmentFormat;
        this.attachmentWidth = attachmentWidth;
        this.attachmentHeight = attachmentHeight;
        this.attachmentFrameRateFPS = attachmentFrameRateFPS;
        this.attachmentURL = attachmentURL;
        this.attachmentTimeStampUTC = attachmentTimeStampUTC;
    }
    
}
