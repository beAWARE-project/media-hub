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
public class TOP010AudioAnalyzed {
    
    Header header;
    TOP010AudioAnalyzedBody body;
    
    public TOP010AudioAnalyzed(){
        
    }
    
    public TOP010AudioAnalyzed(Header header, TOP010AudioAnalyzedBody body){
        this.header = header;
        this.body = body;
    }
    
}
