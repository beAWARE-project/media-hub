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
public class ImageAnalyzed {
    
    Header header;
    ImageAnalyzedBody body;
    
    public ImageAnalyzed(){
        
    }
    
    public ImageAnalyzed(Header header, ImageAnalyzedBody body){
        this.header = header;
        this.body = body;
    }
    
}
