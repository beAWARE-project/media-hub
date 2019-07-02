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
public class Attachment {
    
    String attachmentName;
    String attachmentType;
    String attachmentTimeStampUTC;
    String attachmentURL;
    String attachmentFormat;
    int attachmentWidth;
    int attachmentHeight;
    int attachmentFrameRateFPS;
    
    public Attachment(){
        
    }
    
    public String getAttachmentName(){
        return attachmentName;
    }
    
    public String getAttachmentType(){
        return attachmentType;
    }
    
    public String getAttachmentTimeStampUTC(){
        return attachmentTimeStampUTC;
    }
    
    public String getAttachmentURL(){
        return attachmentURL;
    }
    
    public String getAttachmentFormat(){
        return attachmentFormat;
    }
    
    public int getAttachmentWidth(){
        return attachmentWidth;
    }
    
    public int getAttachmentHeight(){
        return attachmentHeight;
    }
    
    public int getAttachmentFrameRateFPS(){
        return attachmentFrameRateFPS;
    }
    
}
