package json;

import java.util.List;

public class PositionDA {

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

    public PositionDA(){

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
