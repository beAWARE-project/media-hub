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
public class Header {
    
    String topicName;
    int topicMajorVersion;
    int topicMinorVersion;
    String sender;
    String msgIdentifier;
    String sentUTC;
    String status;
    String actionType;
    String specificSender;
    String scope;
    String district;
    String recipients;
    int code;
    String note;
    String references;
    
    public Header(){
        
    }
    
    public void setTopicName(String topicName){
        this.topicName = topicName;
    }
    
    public void setSender(String sender){
        this.sender = sender;
    }
    
    public String getSender(){
        return sender;
    }
    
    public String getTopicName(){
        return topicName;
    }
    
}
