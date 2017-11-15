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
public class VideoAnalyzed {
    
    Header header;
    VideoAnalyzedBody body;
    
    public VideoAnalyzed(){
        
    }
    
    public VideoAnalyzed(Header header, VideoAnalyzedBody body){
        this.header = header;
        this.body = body;
    }
    
}
