package avrms.socket.app;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.simple.*;

import avrms.socket.WebsocketClientEndpoint;
import avrms.socket.bean.NavSatFix;
import avrms.socket.message.MAVROSMessageParser;

public class SocketApp {
	private static WebSocketClient wsc;
	private static MAVROSMessageParser msgparser = new MAVROSMessageParser();
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		/**
		 * args[0] : node name
		 * args[1] : address (#ip#:#port#/#applicationName#)- ex) 127.0.0.1:8080/avrms
		 * args[2] : vehicle identification name 
		 */
		String node_name = args[0];
		wsc = new WebSocketClient(new URI("ws://"+args[1]+"/realtime/echo.do"),new Draft_17()) {
			Boolean approved = false;
			@Override
			public void onOpen(ServerHandshake arg0) {
				// TODO Auto-generated method stub
				System.out.println("opened");
				Process proc;
				Process proc_test, proc_gps,proc_state,proc_local_pos,proc_battery;

				/**
				 * 연결 허가
				 */
				this.send("APPROVAL: "+args[2]);

				JSONObject json = new JSONObject();
				json.put("id", args[2]);

				Thread gps_mon = new Thread(new Runnable() {
					public void run() {
						try {
							Process proc = Runtime.getRuntime().exec("rostopic echo /"+node_name+"/global_position/raw/fix");
							_readOutPut(proc, "gps");

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				gps_mon.start();

				Thread state_mon = new Thread(new Runnable() {
					public void run() {
						try {
							Process proc = Runtime.getRuntime().exec("rostopic echo /"+node_name+"/state");
							_readOutPut(proc, "state");

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				state_mon.start();

				Thread battery_mon = new Thread(new Runnable() {
					public void run() {
						try {
							Process proc = Runtime.getRuntime().exec("rostopic echo /"+node_name+"/battery");
							_readOutPut(proc, "battery");

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				battery_mon.start();
				
				try {
					gps_mon.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onMessage(String arg0) {
				// TODO Auto-generated method stub
				System.out.println("message arrived : "+arg0);
			}
			
			@Override
			public void onError(Exception arg0) {
				// TODO Auto-generated method stub
				System.out.println("error");
			}
			
			@Override
			public void onClose(int arg0, String arg1, boolean arg2) {
				// TODO Auto-generated method stub
				System.out.println("closed");
			}
		};
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("end of program");
				wsc.close();
			}
		}));
		wsc.connect();
		
        
	}
	
	public static void _readOutPut(Process proc, String type)throws IOException, InterruptedException{
    	// Read the output
		BufferedReader reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line = "";
		while((line = reader.readLine()) != null){

			String msg_to_send = msgparser.parseMessage(type, line);

			if(msg_to_send!=null){
				wsc.send(msg_to_send);
			}
		}
	}
	
}
