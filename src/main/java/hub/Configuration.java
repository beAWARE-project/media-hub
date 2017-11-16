/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hub;

/**
 *
 * @author andreadisst
 */
public class Configuration {
    
    public static String video_IP = "video-analysis";//"160.40.49.112";
    public static int video_port = 7777;
    
    public static String image_IP = "image-analysis";
    public static int image_port = 7788;
    
    public static String audio_IP = "160.40.49.114";
    public static int audio_port = 2030;
    
    public static String JAAS_CONFIG_PROPERTY = "java.security.auth.login.config";
    public static String key = "key";
    
    public static String incident_report_topic = "TOP021_INCIDENT_REPORT";
    public static String video_analyzed_topic = "TOP017_video_analyzed";
    public static String image_analyzed_topic = "TOP018_image_analyzed";
    public static String audio_analyzed_topic = "TOP010_AUDIO_ANALYZED";
    
}
