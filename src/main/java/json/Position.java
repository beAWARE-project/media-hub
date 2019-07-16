/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;
import java.util.List;
/**
 *
 * @author andreadisst
 */
public class Position {

    float latitude;
    float longitude;
    float altitude;
    int heading;
    int gimbalPitch;

//    float latitude;
//    float longitude;
//    float altitude;
//    int heading;
//    int gimbalPitch;
    float speed;

    public Position(){

    }


    public float getLongitude(){
        return longitude;
    }

    public float getLatitude(){
        return latitude;
    }

    public float getAltitude(){
        return altitude;
    }

    public int getHeading(){
        return heading;
    }

    public int getGimbalPitch(){
        return gimbalPitch;
    }

    public float getSpeed(){
        return speed;
    }

}
