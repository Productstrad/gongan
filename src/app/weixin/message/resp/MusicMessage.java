package app.weixin.message.resp;

/** 
 * 音乐消息 
 *  
 * @author xsy 
 * @date 2014-05-12 
 */  
public class MusicMessage extends BaseMessage {  
    // 音乐  
    private Music Music;  
  
    public Music getMusic() {  
        return Music;  
    }  
  
    public void setMusic(Music music) {  
        Music = music;  
    }  
}  
