# AVRMSClient
AVRMS(Aero Vehicle Remote Monitoring System)
MAVROS support
For Aerial Robot using ROS
This program should be run on PC that runs drone's rosnode
This program echos mavros topic that contains GPS information
===updated
161204: AVRMSClient can give more information! (battery, vehicle state)
161205: AVRMSClient can give attitude information!
Currently AVRMS Server has closed! We'll open sooner!

****How to use****
/**
 * Now (after 161204)! We can set rosnode name to monitor
 *
 * (before, rosnode name was set to ‘mavros’ as default
 */
$ java -jar AVRMSClient.jar {node name} {[AVRMS target address]:[port]/avrms} {customized id}

ex)java -jar AVRMSClient.jar mavros http://211.217.156.76:8080/avrms AerialRobot

———————————————————————————————————————————
/**
 *(only for version that published on 161123, 161127)
 */
$ java -jar AVRMSClient.jar {[AVRMS target address]:[port]/avrms} {customized id}


========This is developed for Aerospace SW Project in Korea Aerospace Univ.========

Contact me for more information and any suggestion.
Developer mail : jaeseung17@me.com
