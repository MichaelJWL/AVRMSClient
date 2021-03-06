package avrms.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ChatServer Client
 *
 * @author Jiji_Sasidharan
 */
@ClientEndpoint
public class WebsocketClientEndpoint {
//
//    Session userSession = null;
//    private MessageHandler messageHandler;
//
//    public WebsocketClientEndpoint(URI endpointURI) {
//        try {
//            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//            container.connectToServer(this, endpointURI);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * Callback hook for Connection open events.
//     *
//     * @param userSession the userSession which is opened.
//     */
//    @OnOpen
//    public void onOpen(Session userSession) {
//        System.out.println("opening websocket");
//        this.userSession = userSession;
//        
//        System.out.println("opened");
//		Process proc;
//		try {
//			JSONObject json = new JSONObject();
//			json.put("id", args[1]);
//			this.sendMessage("");
////			proc = Runtime.getRuntime().exec("rostopic echo /mavros/global_position/raw/fix");
//			proc = Runtime.getRuntime().exec("ping google.com");
//			System.out.println("toString():"+arg0.toString()+"\n http status message:"+arg0.getHttpStatusMessage()+"\n content:"+arg0.getContent());
//			
//			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//			String line = null;
//			while((line = br.readLine())!=null){
//				this.sendMessage(line);
//			}
//		
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//
//    /**
//     * Callback hook for Connection close events.
//     *
//     * @param userSession the userSession which is getting closed.
//     * @param reason the reason for connection close
//     */
//    @OnClose
//    public void onClose(Session userSession, CloseReason reason) {
//        System.out.println("closing websocket :: "+reason);
//     
//        this.userSession = null;
//    }
//
//    /**
//     * Callback hook for Message Events. This method will be invoked when a client send a message.
//     *
//     * @param message The text message
//     */
//    @OnMessage
//    public void onMessage(String message) {
//        if (this.messageHandler != null) {
//            this.messageHandler.handleMessage(message);
//        }
//    }
//
//    /**
//     * register message handler
//     *
//     * @param msgHandler
//     */
//    public void addMessageHandler(MessageHandler msgHandler) {
//        this.messageHandler = msgHandler;
//    }
//
//    /**
//     * Send a message.
//     *
//     * @param message
//     */
//    public void sendMessage(String message) {
//        this.userSession.getAsyncRemote().sendText(message);
//    }
//
//    /**
//     * Message handler.
//     *
//     * @author Jiji_Sasidharan
//     */
//    public static interface MessageHandler {
//
//        public void handleMessage(String message);
//    }
}
