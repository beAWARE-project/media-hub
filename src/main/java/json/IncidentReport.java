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
public class IncidentReport {
    
    Header header;
    IncidentReportBody body;
    
    public IncidentReport(){
        
    }
    
    public IncidentReport(Header header, IncidentReportBody body){
        this.header = header;
        this.body = body;
    }
    
    public IncidentReportBody getBody(){
        return body;
    }
    
    public Header getHeader(){
        return header;
    }
    
}
