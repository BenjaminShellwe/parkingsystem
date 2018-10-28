package json;

import net.sf.json.JSONObject;

public class GetJSON<T> {
	//返回JSON数据
		public JSONObject getJSON(int code, T data) {
			JSONObject jObject = new JSONObject();
			jObject.put("code", code);
			jObject.put("data", data);
			return jObject;
		}

}
