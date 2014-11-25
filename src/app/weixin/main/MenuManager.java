package app.weixin.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.weixin.message.resp.Article;
import app.weixin.message.resp.NewsMessage;
import app.weixin.message.resp.TextMessage;
import app.weixin.pojo.AccessToken;  
import app.weixin.pojo.Button;  
import app.weixin.pojo.CommonButton;  
import app.weixin.pojo.ComplexButton;  
import app.weixin.pojo.Menu;  
import app.weixin.pojo.MenuMessageType;
import app.weixin.pojo.MessageType;
import app.weixin.product.wenhuaproduct;
import app.weixin.util.MessageUtil;
import app.weixin.util.WeixinUtil;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
 
/** 
 * 菜单管理器类  
 *  
 * @author xsy 
 * @date 2014-05-13 
 */  
public class MenuManager {  
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
  
    public static void main(String[] args) {  
//        // 第三方用户唯一凭证  
//        String appId = "wx357aaac95c32863d";  
//        // 第三方用户唯一凭证密钥  
//      String appSecret = "526729b8b25ea1b3d26a85a14e195544";  
//      String MsgType="click";
//      String EventKey="m12";
//      MessageType result=MessageType.getMessageType(MsgType);
//      String respContent="";
//      String respMessage="";
//      String fromUserName="11111";
//      String toUserName="2222";
//   // 默认回复此文本消息  
//      TextMessage textMessage = new TextMessage();  
//      textMessage.setToUserName(fromUserName);  
//      textMessage.setFromUserName(toUserName);  
//      textMessage.setCreateTime(new Date().getTime());  
//      textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
//      textMessage.setFuncFlag(0);  
//      // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义  
//      textMessage.setContent("欢迎访问<a href=\"http://hnwhguolv.tmall.com/\">海南文化国旅</a>!");  
//      // 将文本消息对象转换成xml字符串  
//      respMessage = MessageUtil.textMessageToXml(textMessage);
//    //消息处理
//      switch(MessageType.getMessageType(MsgType)){
//      // 文本消息
//      case text:
//      	respContent = "您发送的是文本消息！"; 
//          textMessage.setContent(respContent);  
//          // 将文本消息对象转换成xml字符串  
//          respMessage = MessageUtil.textMessageToXml(textMessage); 
//          break;
//      case image:
//      	respContent = "您发送的是图片消息！";  
//          textMessage.setContent(respContent);  
//          // 将文本消息对象转换成xml字符串  
//          respMessage = MessageUtil.textMessageToXml(textMessage);
//      	break;
//      case link:
//      	respContent = "您发送的是链接消息！"; 
//          textMessage.setContent(respContent);  
//          // 将文本消息对象转换成xml字符串  
//          respMessage = MessageUtil.textMessageToXml(textMessage);
//      	break;
//      case location:
//      	respContent = "您发送的是地理位置消息！";  
//          textMessage.setContent(respContent);  
//          // 将文本消息对象转换成xml字符串  
//          respMessage = MessageUtil.textMessageToXml(textMessage);
//      	break;
//      case voice:
//      	respContent = "您发送的是音频消息！";
//          textMessage.setContent(respContent);  
//          // 将文本消息对象转换成xml字符串  
//          respMessage = MessageUtil.textMessageToXml(textMessage);
//      	break;
//      case event:// 事件类型 
//      	String eventType = MsgType;
//      	switch(MessageType.getMessageType(eventType))
//      	{
//      	case subscribe:// 订阅  
//      		// 回复单图文消息  
//              NewsMessage newsMessage = new NewsMessage();  
//              newsMessage.setToUserName(fromUserName);  
//              newsMessage.setFromUserName(toUserName);  
//              newsMessage.setCreateTime(new Date().getTime());  
//              newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
//              newsMessage.setFuncFlag(0);
//              List<Article> articleList = new ArrayList<Article>();
//          	Article article = new Article();  
//              article.setTitle("海南文化国旅在线预定");  
//              article.setDescription("文化国旅是在国家旅游局正式登记注册的国际社。多年来，主要接待过来自全球近20个国家和地区的游客并受到广泛好评。我们在全国酒店预订、港澳台旅游、出入境旅游及签证、旅游会展等业务具有良好声誉。欢迎您加入我们！");  
//              article.setPicUrl("http://shuangliu.tianya.cn/images/wenhualogo.jpg");  
//              article.setUrl("http://hnwhguolv.tmall.com/");  
//              articleList.add(article);  
//              // 设置图文消息个数  
//              newsMessage.setArticleCount(articleList.size());  
//              // 设置图文消息包含的图文集合  
//              newsMessage.setArticles(articleList);  
//              // 将图文消息对象转换成xml字符串  
//              respMessage = MessageUtil.newsMessageToXml(newsMessage);
//      		break;
//      	case unsubscribe:// 取消订阅  
//      		break;
//      	}
//      	break;
//      case click:// 自定义菜单点击事件  
//      	// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
//          String eventKey = EventKey;  
//          switch(MenuMessageType.getMenuMessageType(eventKey)){
//          case m11://酒店预定
//          	respContent = "酒店预定被点击！";  
//              textMessage.setContent(respContent); 
//              respMessage = MessageUtil.textMessageToXml(textMessage);
//              break;
//          case m12://景点门票
//          	respMessage = wenhuaproduct.GetLinesProduct(fromUserName, toUserName);
//          	break;
//          case m13://特价机票
//          	respContent = "特价机票被点击！"; 
//              textMessage.setContent(respContent); 
//              respMessage = MessageUtil.textMessageToXml(textMessage);
//          	break;
//          case m21://经典线路
//          	respMessage = wenhuaproduct.GetLinesProduct(fromUserName, toUserName); 
//          	break;
//          case m22://高尔夫
//          	respContent = "高尔夫被点击！";  
//              textMessage.setContent(respContent); 
//              respMessage = MessageUtil.textMessageToXml(textMessage);
//          	break;
//          case m31://关于文化国旅
//          	respContent = "关于文化国旅被点击！";  
//              textMessage.setContent(respContent); 
//              respMessage = MessageUtil.textMessageToXml(textMessage);
//          	break;
//          case m32://加入成会员
//          	respContent = "加入成会员被点击！";  
//              textMessage.setContent(respContent); 
//              respMessage = MessageUtil.textMessageToXml(textMessage);
//          	break;
//          }
//      	break;
//      } 
   
        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(WeixinUtil.appId, WeixinUtil.appSecret);  
        log.info(at.getToken());
        if (null != at) {  
        	//删除菜单
//        	int result = WeixinUtil.deleteMenu( at.getToken());
//        	if (0 == result)  
//                log.info("菜单删除成功！");  
//            else  
//                log.info("菜单删除失败，错误码：" + result);
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getgonganMenu(), at.getToken());  
  
            // 判断菜单创建结果  
            if (0 == result)  
                log.info("菜单创建成功！");  
            else  
                log.info("菜单创建失败，错误码：" + result);
//      	String accesstoken="sAc_7p9kkA9iPSXGTVOEshMuN1U-jhfm-keXhqP8DttoOzV-jCbneX_heGwCjfmsbzHTuYl2PelVJiMaBpPQzQ";
//        	//查询菜单
//        	int result=WeixinUtil.createMenu(getMenu(),accesstoken);
//        	// 判断菜单创建结果  
//          if (0 == result)  
//              log.info("菜单查询成功！");  
//          else  
//              log.info("菜单查询失败，错误码：" + result);
        }  

    }  
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getgonganMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("公安动态");  
        btn11.setType("click");  
        btn11.setKey("m11");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("公安发布");  
        btn12.setType("click");  
        btn12.setKey("m12");  
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("交通业务");  
        btn21.setType("click");  
        btn21.setKey("m21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("治安业务");  
        btn22.setType("click");  
        btn22.setKey("m22");  
	
        CommonButton btn23 = new CommonButton();  
        btn23.setName("出入境业务");  
        btn23.setType("click");  
        btn23.setKey("m23");

        CommonButton btn24 = new CommonButton();  
        btn24.setName("户籍业务");  
        btn24.setType("click");  
        btn24.setKey("m24");
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("联系方式");  
        btn31.setType("click");  
        btn31.setKey("m31");  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("满意度调查");  
        btn32.setType("click");  
        btn32.setKey("m32");  

        CommonButton btn33 = new CommonButton();  
        btn33.setName("线索征集");  
        btn33.setType("click");  
        btn33.setKey("m33");

        CommonButton btn34 = new CommonButton();  
        btn34.setName("失物招领");  
        btn34.setType("click");  
        btn34.setKey("m34");
         
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("公安信息");  
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("民生服务");  
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22,btn23,btn24 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("警民互动");  
        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32,btn33,btn34 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是"更多体验"，而直接是"幽默笑话"，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }

  
} 