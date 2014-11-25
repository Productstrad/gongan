/**
 * 
 */
package util;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * @author mendz 2014年7月2日
 */
public class JsonUtil {
	
	public static String toJson(Object object) {
		if (object instanceof List || object.getClass().isArray()) {			
			return JSONArray.fromObject(object).toString();
		}		
		return JSONObject.fromObject(object).toString();
	}

	public static Object toBean(String json, Class clazz) {		
		return JSONObject.toBean(JSONObject.fromObject(json), clazz);
	}

	public static Object toBeanList(String json, Class clazz) {
		return JSONArray.toCollection(JSONArray.fromObject(json), clazz);
	}

	public static Object toBeanArray(String json, Class clazz) {
		return JSONArray.toArray(JSONArray.fromObject(json),clazz);
	}
	
//	public static void main(String[] args) {
////		Map<String, Object> params = new HashMap<String, Object>();
////		params.put("areaID", 2);
////		params.put("type", 1);
////		System.out.println(JsonUtil.toJson(params));
////		TotalVO vo = new TotalVO();
////		vo.setId(6);
////		vo.setBaseHit(100L);
////		System.out.println(JsonUtil.toJson(vo));
////
////		List<TotalVO> list = new ArrayList<TotalVO>();
////		list.add(vo);
////		TotalVO vo5 = new TotalVO();
////		vo5.setId(7);
////		vo5.setBaseHit(110L);
////		list.add(vo5);
////		System.out.println(JsonUtil.toJson(list));
////
////		System.out.println(JsonUtil.toJson(new String[] { "8", "9" }));
//
//		// TotalVO
//		// vo2=(TotalVO)toBean("{\"areaName\":\"\",\"baseHit\":100,\"id\":6,\"indexHit\":0,\"ratio\":0,\"state\":0,\"type\":0}",
//		// TotalVO.class);
//		// System.out.println(vo2.getAreaID());
//
//		Map[] params2 =(Map[])toBeanArray("[{\"areaID\":2,\"type\":1},{\"areaID\":5,\"type\":5}]", Map.class);
//		 System.out.println(params2);
//		 
//		 List<TotalVO> params1 =(List<TotalVO>)toBeanList("[{\"areaID\":2,\"type\":1},{\"areaID\":5,\"type\":5}]", TotalVO.class);
//		 System.out.println(params1);
//		
////		 List<Map> params5 =(List<Map>)toBean("[{\"areaID\":2,\"type\":1},{\"areaID\":5,\"type\":5}]", new ArrayList<Map>().getClass());
////		 System.out.println(params5);
//	}
}
