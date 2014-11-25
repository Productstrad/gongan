package app.weixin.message.req;

/** 
 * 文本消息 
 *  
 * @author xsy 
 * @date 2014-05-12 
 */  
public class TextMessage extends BaseMessage {  
    // 消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  
