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

    List<Float> latitude;
    List<Float> longitude;
    List<Float> altitude;
    List<Integer> heading;
    List<Integer> gimbalPitch;

//    float latitude;
//    float longitude;
//    float altitude;
//    int heading;
//    int gimbalPitch;
    float speed;

    public Position(){

    }


    public List<Float> getLongitude(){
        return longitude;
    }

    public List<Float> getLatitude(){
        return latitude;
    }

    public List<Float> getAltitude(){
        return altitude;
    }

    public List<Integer> getHeading(){
        return heading;
    }

    public List<Integer> getGimbalPitch(){
        return gimbalPitch;
    }

    public float getSpeed(){
        return speed;
    }

}
