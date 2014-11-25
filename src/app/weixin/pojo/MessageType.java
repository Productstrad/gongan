package app.weixin.pojo;

public enum MessageType {
	text,image,link,location,voice,event,subscribe,unsubscribe,click;
	
	public static MessageType getMessageType(String msgType){  
        return valueOf(msgType.toLowerCase());  
     }
}
