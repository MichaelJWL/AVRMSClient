package avrms.socket.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

public class Pose {
	private Double pos_x = 0.0;
	private Double pos_y = 0.0;
	private Double pos_z = 0.0;
	
	private Double ori_x = 0.0;
	private Double ori_y = 0.0;
	private Double ori_z = 0.0;
	private Double ori_w = 0.0;
	
	private static Pose instance;
	private static boolean p_f,p_x_f,p_y_f,p_z_f,o_f,o_x_f,o_y_f,o_z_f,o_w_f;
	
	private static final boolean[] pose_flag_arr = {p_f,p_x_f,p_y_f,p_z_f,o_f,o_x_f,o_y_f,o_z_f,o_w_f};
	
	private static HashMap<Integer, String> index_var_map;
	private static int flag_pointer = 0;
	
	private String resultJson;
	private Pose() {
		// TODO Auto-generated constructor stub
		
		index_var_map = new HashMap<>();
		index_var_map.put(1, "pos_x");
		index_var_map.put(2, "pos_y");
		index_var_map.put(3, "pos_z");
		index_var_map.put(5, "ori_x");
		index_var_map.put(6, "ori_y");
		index_var_map.put(7, "ori_z");
		index_var_map.put(8, "ori_w");
	}
	
	public static Pose getInstance(){
		if(instance==null){
			instance = new Pose();
			instance.initFlag();
		}
		return instance;
	}
	
	public void initFlag(){
		for (int i = 0; i < pose_flag_arr.length; i++) {
			pose_flag_arr[i]=false;
		}
		flag_pointer = 0;
	}
	public Boolean processInfo(String jsonData) throws NumberFormatException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		Gson jsonParser = new Gson();
		HashMap info = jsonParser.fromJson(jsonData, HashMap.class);
		Set keySet = info.keySet();
		Iterator it_key = keySet.iterator();
		Boolean flag = false;
//		System.out.println("processing data..."+info);
		while(it_key.hasNext()){
			String key = (String) it_key.next();

			Boolean flagForNext =false;
			if(key.equals("position")){
				if(flag_pointer==0)
					flagForNext = true;
			}else if(key.equals("x")){
				if(flag_pointer==1||flag_pointer ==5)
					flagForNext = true;
			}else if(key.equals("y")){
				if(flag_pointer==2||flag_pointer ==6)
					flagForNext = true;
			}else if(key.equals("z")){
				if(flag_pointer==3||flag_pointer ==7)
					flagForNext = true;
			}else if(key.equals("orientation")){
				if(flag_pointer==4)
					flagForNext = true;
			}else if(key.equals("w")){
				if(flag_pointer==8)
					flagForNext = true;
			}
			
			if(flagForNext){
				pose_flag_arr[flag_pointer] = true;
				String val = index_var_map.get(flag_pointer);
				if(val!=null){

					Field var = this.getClass().getDeclaredField(val);

					double value = Double.valueOf((String) info.get(key));
					var.set(this,value);
					
				}
				
				if((flag_pointer+1)==pose_flag_arr.length){
					/**
					 * 데이터 완성
					 */
					flag = true;
					initFlag();
					resultJson = createJsonResult();
				}else{
					flag_pointer++;
				}
			}
		}
		return flag;
	}
	
	private String createJsonResult(){
		JSONObject obj = new JSONObject();
		obj.put("pos_x", pos_x);
		obj.put("pos_y", pos_y);
		obj.put("pos_z", pos_z);
//		System.out.println("check:"+ori_x+", "+ ori_y+", "+", "+ ori_z+", "+ ori_w);
		double [] eulerValue = quarternionToEuler(ori_x, ori_y, ori_z, ori_w);
//		System.out.println(eulerValue);
		obj.put("ea_x", eulerValue[0]);
		obj.put("ea_y", eulerValue[1]);
		obj.put("ea_z", eulerValue[2]);

		JSONObject title_obj = new JSONObject();
		title_obj.put("attitude", obj.toJSONString());
		return title_obj.toJSONString();
	}
	private double[] quarternionToEuler(double qx, double qy, double qz, double qw){
		/*
		  static void toEulerianAngle(const Quaterniond& q, double& pitch, double& roll, double& yaw)
			{
				double ysqr = q.y() * q.y();
				double t0 = -2.0f * (ysqr + q.z() * q.z()) + 1.0f;
				double t1 = +2.0f * (q.x() * q.y() - q.w() * q.z());
				double t2 = -2.0f * (q.x() * q.z() + q.w() * q.y());
				double t3 = +2.0f * (q.y() * q.z() - q.w() * q.x());
				double t4 = -2.0f * (q.x() * q.x() + ysqr) + 1.0f;
			
				t2 = t2 > 1.0f ? 1.0f : t2;
				t2 = t2 < -1.0f ? -1.0f : t2;
			
				pitch = std::asin(t2);
				roll = std::atan2(t3, t4);
				yaw = std::atan2(t1, t0);
			}
		 
		 */
		double ysqr = qy*qy;
		double t0 = -2.0f * (ysqr + qz * qz) + 1.0f;
		double t1 = +2.0f * (qx * qy - qw * qz);
		double t2 = -2.0f * (qx * qz + qw * qy);
		double t3 = +2.0f * (qy * qz - qw * qx);
		double t4 = -2.0f * (qx * qx + ysqr) + 1.0f;
		
		t2 = t2 > 1.0f ? 1.0f : t2;
		t2 = t2 < -1.0f ? -1.0f : t2;
	
		double e_x = Math.asin(t2);
		double e_y = Math.atan2(t3, t4);
		double e_z = Math.atan2(t1, t0);

		double[] euler = {e_x,e_y,e_z} ;
		return euler;
		
	}

	public String getResultJson() {
		return resultJson;
	}
	
}
