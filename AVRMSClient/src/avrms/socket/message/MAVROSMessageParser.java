package avrms.socket.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import avrms.socket.bean.NavSatFix;

public class MAVROSMessageParser {
	public MAVROSMessageParser() {
		// TODO Auto-generated constructor stub
	}
	
	public String parseMessage(String type,String msg){
		/**
		 * Choosing message parsing method
		 */
		String result_msg = null;
		switch (type) {
		case "gps":
			result_msg = GPSMessageParsing(msg);
			break;
		case "state":
			result_msg = StateMessageParsing(msg);
			break;
		case "local_pose":
			result_msg = LocalPoseMessageParsing(msg);
			break;
		case "battery":
			result_msg = BatteryMessageParsing(msg);
			break;
		case "test":
			result_msg = msg;
			break;
		}
		return result_msg;
	}
	
	
	private String GPSMessageParsing(String msg){
		
		String[] data_name = {"latitude","longitude","altitude"};
		return getParsedJsonMessage(data_name,msg);
		
//		
//		String latitude_pattern = "latitude: (.)*";
//		String longitude_pattern = "longitude: (.)*";
//		String altitude_pattern = "altitude: (.)*";
//		
//		Matcher matcher = null;
//		
//		
//		if(msg.matches(latitude_pattern)){
//
//			Pattern lat_pat = Pattern.compile("latitude: (.*)");
//			matcher = lat_pat.matcher(msg);
//			if(matcher.find()){
//
//				json = new JSONObject();
//				json.put("latitude", matcher.group(1));
//				
//				result = json.toJSONString();
//				
//			}
//		}else if(msg.matches(longitude_pattern)){
//
//			Pattern long_pat = Pattern.compile("longitude: (.*)");
//			matcher = long_pat.matcher(msg);
//			if(matcher.find()){
//
//				json = new JSONObject();
//				json.put("longitude", matcher.group(1));
//				
//				result = json.toJSONString();
//			}
//		}else if(msg.matches(altitude_pattern)){
//
//			Pattern alt_pat = Pattern.compile("altitude: (.*)");
//			matcher = alt_pat.matcher(msg);
//			if(matcher.find()){
//
//				json = new JSONObject();
//				json.put("altitude", matcher.group(1));
//
//				result = json.toJSONString();
//			}
//		}else{
//
//		}
//		
//		return result;
	}
	private String StateMessageParsing(String msg){
		String[] data_name = {"connected","armed","guided","mode"};
		
		return getParsedJsonMessage(data_name, msg);
	}
	private String LocalPoseMessageParsing(String msg){
		
		
		return null;
	}
	private String BatteryMessageParsing(String msg){
		String[] data_name = {"voltage","current","remaining"};
		return getParsedJsonMessage(data_name, msg);
	}
	private String ParsingProcess(String data_name,String info_msg){
		String pattern = data_name+": (.)*";
		Matcher matcher = null;
		String result_string = null;
		if(info_msg.matches(pattern)){

			Pattern _pat = Pattern.compile(data_name+": (.*)");
			matcher = _pat.matcher(info_msg);
			if(matcher.find()){
				result_string = matcher.group(1);
			}
		}
		return result_string;
	}
	
	private String getParsedJsonMessage(String[]data_name,String msg){

		JSONObject json = new JSONObject();
		
		for (int i = 0; i < data_name.length; i++) {
			String _data_name = data_name[i];
			String parsedData = ParsingProcess(_data_name,msg);
			if(parsedData!=null){
				json.put(_data_name, parsedData);
			}
		}
		if(json.size()>0){
			return json.toJSONString();
		}else{
			return null;
		}
	
	}
	
}
