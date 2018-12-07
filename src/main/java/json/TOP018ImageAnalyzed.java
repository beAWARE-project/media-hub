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
public class TOP018ImageAnalyzed {
    
    Header header;
    TOP018ImageAnalyzedBody body;
    
    public TOP018ImageAnalyzed(){
        
    }
    
    public TOP018ImageAnalyzed(Header header, TOP018ImageAnalyzedBody body){
        this.header = header;
        this.body = body;
    }
    
}
