package app.weixin.util;

import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.net.ConnectException;  
import java.net.URL;  
  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;  
import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLSocketFactory;  
import javax.net.ssl.TrustManager;  
  













import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
import net.sf.json.JSONException;  

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

import util.HttpRequstUtil;
import util.JsonUtil;
import util.OsCache;
import app.weixin.pojo.AccessToken;
import app.weixin.pojo.Menu;
  
/** 
 * 公众平台通用接口工具类 
 *  
 * @author xsy 
 * @date 2014-05-13 
 */  
public class WeixinUtil {  
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
    // 第三方用户唯一凭证  
    public static String appId = "wx357aaac95c32863d";  
    // 第三方用户唯一凭证密钥  
    public static String appSecret = "526729b8b25ea1b3d26a85a14e195544"; 
    // 获取access_token的接口地址（GET） 限200（次/天）  
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 菜单创建（POST） 限100（次/天）  
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
    //删除菜单限100（次/天）
    public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    //查询菜单
    public static String menu_query_url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    //推送新闻
    public static String upload_news_url="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
    //上传媒体文件
    public static String upload_media_url="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    //群发接口
    public static String qun_send_url="https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
    //取分组信息
    public static String get_groupInfo_url="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
    //获取微信用户信息
    public static String get_fansInfo_url="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
    //发送消息
    public static String send_info_url="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
    /** 
     * 获取access_token 
     *  
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @return 
     */  
    public static AccessToken getAccessToken(String appid, String appsecret) {  
        AccessToken accessToken = null;  
        //从缓存读取
        try {
			accessToken=(AccessToken) OsCache.getInstance().get("tokenCache", 7100);
			
		} catch (Exception e1) {
			
		}
        if(accessToken==null){
	        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);  
	        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
	        // 如果请求成功  
	        if (null != jsonObject) {  
	            try {  
	                accessToken = new AccessToken();  
	                accessToken.setToken(jsonObject.getString("access_token"));  
	                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
	            } catch (JSONException e) {  
	                accessToken = null;  
	                // 获取token失败  
	                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	            }  
	        }
	        if(accessToken!=null){
	        	OsCache.getInstance().put("tokenCache", accessToken);
	        }
        }
        return accessToken;  
    }  
    /** 
     * 创建菜单 
     *  
     * @param menu 菜单实例 
     * @param accessToken 有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */  
    public static int createMenu(Menu menu, String accessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
        // 将菜单对象转换成json字符串  
        String jsonMenu = JSONObject.fromObject(menu).toString();  
        // 调用接口创建菜单  
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
      
        return result;  
    }
    /**
     * 删除菜单
     * * @param accessToken 有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */
    public static int deleteMenu(String accessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);  
        // 调用接口删除菜单  
        JSONObject jsonObject = httpRequest(url, "GET",null );  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                log.error("删除菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
      
        return result;  
    }
    /**
     * 查询菜单
     * * @param accessToken 有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */
    public static int queryMenu(String accessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = menu_query_url.replace("ACCESS_TOKEN", accessToken);  
        // 调用接口删除菜单  
        JSONObject jsonObject = httpRequest(url, "GET",null );  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                log.error("查询菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
      
        return result;  
    }
    
    public static String uploadNews(Object news,String accessToken){
    	String result = "-1";          
        // 拼装创建菜单的url  
        String url = upload_news_url.replace("ACCESS_TOKEN", accessToken);  
        // 换成json字符串  
        String json = JSONObject.fromObject(news).toString();  
        // 调用接口创建菜单  
        JSONObject jsonObject = httpRequest(url, "POST", json);  
      
        if (null != jsonObject) {  
            if (jsonObject.get("errcode")!=null&&0 != jsonObject.getInt("errcode")) {                   
                log.error("推送新闻失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }else{
            	result=jsonObject.getString("media_id");
            }
        }        
        return result;  
    }
    /**
     * 上传媒体文件
     * @param mediaPath
     * @param accessToken
     * @param type
     * @return
     * @author mengdz
     * 2014年11月14日
     */
    public static String uploadMedia(String mediaPath,String accessToken,String type){    	 
    	String response =null;          
        // 拼装创建菜单的url  
        String url = upload_media_url.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);  
        try {  
            response =  HttpRequstUtil.uploadFile(url, mediaPath); 
            //{"type":"image","media_id":"acDdhwY9pat698xPfzKUpWnJ-jw28i0DpN1Plim59cDAzKTKRhmk2DUnil4b8lAy","created_at":1415955086}
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return response;  
    }
    /**
     * 群发信息
     * @param groupId
     * @param accessToken
     * @return
     * @author mengdz
     * 2014年11月14日
     */
    public static int qunSendSMS(Map params,String accessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = qun_send_url.replace("ACCESS_TOKEN", accessToken);  
        // 换成json字符串  
        String json = JSONObject.fromObject(params).toString();
        // 调用接口删除菜单  
        JSONObject jsonObject = httpRequest(url, "POST",json );  
      
        if (null != jsonObject) {  
            if (jsonObject.get("errcode")!=null&&0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                log.error("查询菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
      
        return result;  
    }
    
    
    public static JSONArray getAllGroup(String accessToken) {          
        // 拼装创建菜单的url  
        String url = get_groupInfo_url.replace("ACCESS_TOKEN", accessToken);  
        // 调用接口删除菜单  
        JSONObject jsonObject = httpRequest(url, "GET",null );  
      
        if (null != jsonObject) {  
            if (jsonObject.get("errcode")!=null&&0 != jsonObject.getInt("errcode")) {               
                log.error("查询分组失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
                return null;
            } 
            JSONArray jsonArray=jsonObject.getJSONArray("groups");           
            return jsonArray;
        }  
      
        return null;  
    }
    /**
     * 取某个粉丝的信息
     * @param openID
     * @param accessToken
     * @return
     * @author mengdz
     * 2014年11月21日
     */
    public static JSONObject getFansInfo(String openID,String accessToken) {    
    	// 拼装创建菜单的url  
        String url = get_fansInfo_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openID);  
        // 调用接口删除菜单  
        JSONObject jsonObject = httpRequest(url, "GET",null );
        if (null != jsonObject) {  
            if (jsonObject.get("errcode")!=null&&0 != jsonObject.getInt("errcode")) {               
                log.error("查询失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
                return null;
            }             
        }  
        return jsonObject;  
    }
    /**
     * 
     * @param accessToken
     * @return
     * @author mengdz
     * 2014年11月21日
     */
    public static JSONObject sendInfo(String json,String accessToken) {    
    	// 拼装创建菜单的url  
        String url = send_info_url.replace("ACCESS_TOKEN", accessToken);  
        // 调用接口
        log.error("url"+url+"</n>json:"+json);
        JSONObject jsonObject = httpRequest(url, "POST",json );
        log.error("jsonObject"+jsonObject);
        if (null != jsonObject) {  
            if (jsonObject.get("errcode")!=null&&0 != jsonObject.getInt("errcode")) {               
                log.error("查询失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
                return jsonObject;
            }             
        }  
        return jsonObject;  
    }
    /**
     * 发送文本信息
     * @param touser
     * @param content
     * @param accessToken
     * @return
     * @author mengdz
     * 2014年11月21日
     */
    public static JSONObject sendTextInfo(String touser,String content,String accessToken) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("touser", touser);
		map.put("msgtype","text");
		Map<String, Object> contentMap=new HashMap<String, Object>();
		contentMap.put("content", content);
		map.put("text", contentMap);
		JSONObject jsonObject = sendInfo(JsonUtil.toJson(map), accessToken);
		return jsonObject;
	}
//    public static void main(String[] args) {
//    	AccessToken at = WeixinUtil.getAccessToken(WeixinUtil.appId, WeixinUtil.appSecret);	
//    	getFansInfo("o5l4es50_f9WdtMCpea_ieUfNp_4", at.getToken());
////		uploadMedia("C:\\Users\\mengdz\\Desktop\\temp\\2774687.jpg", at.getToken(), "image");
////    	Map<String, Object> map=(Map<String, Object>) JsonUtil.toBean("{\"type\":\"image\",\"media_id\":\"acDdhwY9pat698xPfzKUpWnJ-jw28i0DpN1Plim59cDAzKTKRhmk2DUnil4b8lAy\",\"created_at\":1415955086}", HashMap.class);
////    	System.out.println(map);
//	}
}
