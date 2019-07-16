package json;

import hub.Configuration;

public class IncidentReportList {
    Header header;
    IncidentReportBodyList body;

    public IncidentReportList(){

    }
    public IncidentReportBodyList  getBody(){
        return body;

    }

    public Header getHeader(){
        return header;
    }
}
