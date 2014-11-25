package app.weixin.pojo;

public enum MenuMessageType {
	m11,m12,m13,m21,m22,m23,m24,m25,m31,m32,m33,m34;
	
	public static MenuMessageType getMenuMessageType(String MenuMsgType){  
        return valueOf(MenuMsgType.toLowerCase());  
     }
}
