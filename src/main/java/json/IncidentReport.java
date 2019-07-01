/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import hub.Configuration;

/**
 *
 * @author andreadisst
 */
public class IncidentReport {
    
    Header header;
    IncidentReportBody body;

    public IncidentReport(){
        
    }
    public IncidentReportBody  getBody(){
            return body;

    }
    
    public Header getHeader(){
        return header;
    }
    
}
