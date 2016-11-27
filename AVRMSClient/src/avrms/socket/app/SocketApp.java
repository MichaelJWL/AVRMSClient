package avrms.socket.app;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.simple.*;

import avrms.socket.WebsocketClientEndpoint;
import avrms.socket.bean.NavSatFix;

public class SocketApp {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		/**
		 * args[0] : address (#ip#:#port#/#applicationName#)- ex) 127.0.0.1:8080/avrms
		 * args[1] : vehicle identification name 
		 */
		for (int i = 0; i < args.length; i++) {
			System.out.println("args: "+args[i]);
		}
		
		WebSocketClient wsc = new WebSocketClient(new URI("ws://"+args[0]+"/realtime/echo.do"),new Draft_17()) {
			Boolean approved = false;
			@Override
			public void onOpen(ServerHandshake arg0) {
				// TODO Auto-generated method stub
				System.out.println("opened");
				Process proc;
				/**
				 * 연결 허가
				 */
				this.send("APPROVAL: "+args[1]);
				
				try {
					JSONObject json = new JSONObject();
					json.put("id", args[1]);
					this.send("");
					proc = Runtime.getRuntime().exec("rostopic echo /mavros/global_position/raw/fix");
//					proc = Runtime.getRuntime().exec("ping google.com");

					BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
					String line = null;
					while((line = br.readLine())!=null){
//						System.out.println(line);
//						this.send(line);
						String msg_to_send = MessageParsing(line);

						if(msg_to_send!=null){
							this.send(msg_to_send);
						}
						
					}
				
				} catch (IOException e) {
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
				System.out.println("addShutdownHook");
				wsc.close();
			}
		}));
		wsc.connect();
		
		

		
//		// open websocket
//        final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("wss://real.okcoin.cn:10440/websocket/okcoinapi"));
//        
//        // add listener
//        clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
//            public void handleMessage(String message) {
//                System.out.println(message);
//            }
//        });
        
	}
	public static void readOutPut(Process proc) throws IOException, InterruptedException, URISyntaxException{
		// Read the output
		// open websocket
		
		
		
		Thread.sleep(10000);
	}
	public static String MessageParsing(String msg){
		String result = null;
		
		String latitude_pattern = "latitude: (.)*";
		String longitude_pattern = "longitude: (.)*";
		String altitude_pattern = "altitude: (.)*";
		
		Matcher matcher = null;
		NavSatFix position = null;
		
		JSONObject json = null;
		if(msg.matches(latitude_pattern)){
//			System.out.println("latitude detected//"+msg);
			Pattern lat_pat = Pattern.compile("latitude: (.*)");
			matcher = lat_pat.matcher(msg);
			if(matcher.find()){
//				System.out.println("found - "+matcher.group(1));
				position = new NavSatFix();
				// 			   position.setLatitude(matcher.group(1));
				json = new JSONObject();
				json.put("latitude", matcher.group(1));
				
				result = json.toJSONString();
				
			}
		}else if(msg.matches(longitude_pattern)){
//			System.out.println("longitude detected//"+msg);
			Pattern long_pat = Pattern.compile("longitude: (.*)");
			matcher = long_pat.matcher(msg);
			if(matcher.find()){
//				System.out.println("found - "+matcher.group(1));
				// 			   position.setLongitude(matcher.group(1));
				json = new JSONObject();
				json.put("longitude", matcher.group(1));
				
				result = json.toJSONString();
			}
		}else if(msg.matches(altitude_pattern)){
//			System.out.println("altitude detected//"+msg);
			Pattern alt_pat = Pattern.compile("altitude: (.*)");
			matcher = alt_pat.matcher(msg);
			if(matcher.find()){
//				System.out.println("found - "+matcher.group(1));
				// 			   position.setAltitude(matcher.group(1));
				json = new JSONObject();
				json.put("altitude", matcher.group(1));

				result = json.toJSONString();
			}
		}else{
//			System.out.println("not detected//"+msg);
		}
		
		return result;
	}
}