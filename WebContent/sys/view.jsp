<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
</head>
<script language="JavaScript">
<!--
function s_sel(strtype){document.all.item(strtype).className = "sel";}
function s_uel(strtype){document.all.item(strtype).className = "un";}
function showol(strtype){document.all.item(strtype).style.display = "block";}
function hide(strtype){document.all.item(strtype).style.display = "none";}
function show(strtype,tabid){
hide('tel');hide('hezhuo');hide('pingfen');
document.all.item(strtype).style.display = "block";
s_uel('ctab1');s_uel('ctab2');s_uel('ctab3');
document.all.item(tabid).className = "sel";
}
-->
</script>
<body>

</div>
<div id="mainarea">
  <div class="mfl">
  <div class="k " style="margin-bottom:12px">
    <div class="bd iarea"><table border="0" cellspacing="0" cellpadding="0" class="t">
  <tr>
    <td>
     <div class="col2"><span class="a">联系人：</span><span class="black">李四</span><a href="#" class="btn">隐私投诉</a><a href="#" class="btn-g">信息变更</a></div>
     <div class="col2">
       <div class="col"><span class="a">联系电话：</span><span class="black">李四</span></div>
      <div class="col"><span class="a">固定电话：</span><span class="black">李四</span></div>
      <div class="col"><span class="a">传真号：</span><span class="black">李四</span></div>

       <div class="col"><span class="a">QQ号码：</span><span class="black">2321124</span></div>
      <div class="col"><span class="a">微信号：</span><span class="black">dfaefdfasdf</span></div>
      
      <div class="col"><span class="a">联系预约：</span><span class="black">李四</span></div>
 </div>
 <div class="col2"><span class="a">邮件地址：</span><span class="black"> <a href="#">eraeare434@qq.com</a> <a class="icon i-mail" href="#">发邮件</a></span></div>
    </td>
  </tr>
</table>

        </div>
    <div class="f bg"></div>
  </div><div class="k ">
    <div class="bd iarea"><table border="0" cellspacing="0" cellpadding="0" class="t">
  <tr>
    <td>
     <div class="col2">
       <div class="col"><span class="a">客户分组：</span><span class="black">李四</span></div>
      <div class="col"><span class="a">客户类型：</span><span class="black">李四</span></div>
      <div class="col"><span class="a">代理产品：</span><span class="black">李四</span></div>

       <div class="col"><span class="a">操作区域：</span><span class="black">2321124</span></div>
      <div class="col"><span class="a">销售渠道：</span><span class="black">dfaefdfasdf</span></div>
       </div>
      
      
    </td>
  </tr>
</table>
<div class="col2"><span class="a">标签：</span><span class="black">23234@qq.com</span>
     
 </div>
      </div>
    <div class="f bg"></div>
  </div>
  <div class="k itable">
    <div class="hd">
     <ul>
     <li class="sel" id="ctab1"><a href=" javascript:show('tel','ctab1')">电话记录</a></li>
     <li class="un" id="ctab2"><a href=" javascript:show('hezhuo','ctab2')">合作情况</a></li>
     <li class="un" id="ctab3"><a href=" javascript:show('pingfen','ctab3')">评分</a></li>
     </ul>
    </div>
    <div class="bd" id="tel" style="display: block">
    <div class="teladd">
    <input name="" type="text" class="i1" /> <input name="" type="text"  class="i2" /><a href="#" class="btn">提交</a>
    </div>
    <div class="tellist">
    <ul>
    <li><div class="col1">2014-10-12 12:22</div><div class="col2">开会内容<span>与小陈电话会议，关于医院取药情况</span></div></li>
    <li><div class="col1">2014-10-12 12:22</div><div class="col2">开会内容<span>与小陈电话会议，关于医院取药情况</span></div></li>
    <li><div class="col1">2014-10-12 12:22</div><div class="col2">开会内容<span>与小陈电话会议，关于医院取药情况</span></div></li>    
    </ul>
    </div>
    <div class="page_num_tab"><a href="" class="page_numb">上一页</a>|<a href="" class="page_numb">1</a><a href="" class="page_numb">2</a><a href="" class="page_numb">3</a>|<a href="" class="page_numb">下一页</a></div>
    </div>
    <div class="bd" id="hezhuo" style="display:none">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="listtable">
        <tr class="tth">
          <td width="15%">合作信息</td>
          <td width="14%">操作医院</td>
          <td width="15%">协议编号</td>
          <td width="15%">发货日期</td>
          <td width="15%">收货地址</td>
          <td width="15%">发货时间</td>
          <td width="11%">收货人</td>
        </tr>
        <tr class="ttd">
          <td class="ttdf">海资产负1号</td>
          <td>海南省医院</td>
          <td>Z23909580</td>
          <td>2014-10-11</td>
          <td>海南省医院</td>
          <td>2014-10-11</td>
          <td>了了</td>
        </tr>
        <tr class="ttd2">
          <td class="ttdf">海资产负1号</td>
          <td>海南省医院</td>
          <td>Z23909580</td>
          <td>2014-10-11</td>
          <td>海南省医院</td>
          <td>2014-10-11</td>
          <td>了了</td>
        </tr>
        <tr class="ttd">
          <td class="ttdf">海资产负1号</td>
          <td>海南省医院</td>
          <td>Z23909580</td>
          <td>2014-10-11</td>
          <td>海南省医院</td>
          <td>2014-10-11</td>
          <td>了了</td>
        </tr>
        <tr class="ttd2">
          <td class="ttdf">海资产负1号</td>
          <td>海南省医院</td>
          <td>Z23909580</td>
          <td>2014-10-11</td>
          <td>海南省医院</td>
          <td>2014-10-11</td>
          <td>了了</td>
        </tr>
      </table>
      <div class="page_num_tab"><a href="" class="page_numb">上一页</a>|<a href="" class="page_numb">1</a><a href="" class="page_numb">2</a><a href="" class="page_numb">3</a>|<a href="" class="page_numb">下一页</a></div>
    </div>
    <div class="bd pingf" id="pingfen" style="display:none">
    <h3>评分：<span class="org">8.9</span></h3>
    <div class="col">
    <div class="c">规模实力：<span class="bg star"></span><span class="bg star"></span></div>
<div class="c">合作信誉：<span class="bg star"></span><span class="bg star"></span><span class="bg star"></span><span class="bg star"></span></div>
<div class="c">销售能力：<span class="bg star"></span><span class="bg star"></span><span class="bg star"></span><span class="bg star"></span></div>
    </div>
    <div class="note">
    <h4 >说明</h4>
    <p>诺贝尔官方网站消息,2014年&ldquo;瑞典国家银行纪念阿尔弗雷德&middot;诺贝尔经济学奖&rdquo;在当地时间10月13日下午揭晓,让&middot;梯若尔(Jean Tirole)教授获奖,让&middot;梯... </p>
    </div>
    </div>
    <div class="f bg"></div>
  </div>
  </div>
  <div class="conrig">
  <div class="rigbox">
  <div class="hd"><h3>自定义分组</h3><span></span></div>
  <div class="bd tui-text-list">
  <ul>
   <li><a href="#">五公祠的荔枝树&ldquo;长&rdquo;塑料牌(图)</a></li>
<li><a href="#">沿滨江至白沙门感受海口慢悠生活</a></li>
<li><a href="#">五公祠的荔枝树&ldquo;长&rdquo;塑料牌(图)</a></li>
<li><a href="#">沿滨江至白沙门感受海口慢悠生活</a></li>
               
<li><a href="#">沿滨江至白沙门感受海口慢悠生活</a></li>
<li><a href="#">五公祠的荔枝树&ldquo;长&rdquo;塑料牌(图)</a></li>
  </ul>
  </div>
  <div class="fd"></div>
  </div>
  <div class="rigbox">
  <div class="hd">
    <h3>联系提醒</h3><span></span></div>
  <div class="bd tui-text-list">
  <ul>
   <li><a href="#">五公祠的荔枝树&ldquo;长&rdquo;塑料牌(图)</a></li>
<li><a href="#">沿滨江至白沙门感受海口慢悠生活</a></li>
<li><a href="#">五公祠的荔枝树&ldquo;长&rdquo;塑料牌(图)</a></li>
<li><a href="#">沿滨江至白沙门感受海口慢悠生活</a></li>
               
<li><a href="#">沿滨江至白沙门感受海口慢悠生活</a></li>
<li><a href="#">五公祠的荔枝树&ldquo;长&rdquo;塑料牌(图)</a></li>
  </ul>
  </div>
  <div class="fd"></div>
  </div>
  <div class="rigbox">
  <div class="hd">
    <h3>常见问题提醒</h3>
    <span></span></div>
  <div class="bd tui-text-list">
  <ul>
   <li><a href="#">五公祠的荔枝树&ldquo;长&rdquo;塑料牌(图)</a></li>
<li><a href="#">沿滨江至白沙门感受海口慢悠生活</a></li>
<li><a href="#">五公祠的荔枝树&ldquo;长&rdquo;塑料牌(图)</a></li>
<li><a href="#">沿滨江至白沙门感受海口慢悠生活</a></li>
               
<li><a href="#">沿滨江至白沙门感受海口慢悠生活</a></li>
<li><a href="#">五公祠的荔枝树&ldquo;长&rdquo;塑料牌(图)</a></li>
  </ul>
  </div>
  <div class="fd"></div>
  </div>
  <div class="rigbox">
    <div class="hd">
      <h3>工作日志</h3>
      <span><a href="javascript:showol('adddaily')"><img src="images/icon-wr.gif" width="27" height="28" /></a></span>
      </div>
    <div class="bd dailyarea">
    <div class="dailylayer" id="adddaily" style="display:none">
        
      <div class="bd"> <a href="javascript:hide('adddaily')" class="close" ></a>
      <div class="col"><input name="" type="text" class="bg"/></div>
      <div class="col">
        <textarea name="input"></textarea>
      </div>
      <div class="col"><a href="#" class="btn">添加</a></div>
      </div>
      </div>
    <div class="lyears">
    <div class="fl"><</div>
    <div class="fc">2014年10月</div>
    <div class="fr">></div>
    </div>
    <div class="lweek"><span >日</span><span >一</span><span >二</span><span >三</span><span >四</span><span >五</span><span >六</span></div>
    <div class="lday cf">
    <a href="#">1</a><a href="#">2</a><a href="#"class="on">3</a><a href="#">4</a><a href="#">5</a><a href="#" >6</a><a href="#">7</a><a href="#">8</a><a href="#">9</a><a href="#">10</a><a href="#">11</a><a href="#" class="on">12</a><a href="#">13</a><a href="#">14</a><a href="#">15</a><a href="#">16</a><a href="#">17</a><a href="#" class="on">18</a><a href="#">19</a><a href="#">20</a><a href="#">21</a><a href="#">22</a><a href="#">23</a><a href="#">24</a><a href="#">25</a><a href="#">26</a><a href="#">27</a><a href="#">28</a><a href="#">29</a><a href="#">30</a><a href="#">31</a>
    </div>
    <div class="lm">
    <ul>
    <li>
    <p>开会在营销部</p>
    <p class="time">2014-10-11 12:29</p>
    </li>
    <li>
    <p>开会在营销部</p>
    <p class="time">2014-10-11 12:29</p>
    </li>
    <li>
    <p>开会在营销部</p>
    <p class="time">2014-10-11 12:29</p>
    </li>
    </ul>
    </div>
    </div>
    <div class="fd"></div>
  </div>
  
  </div>
</div>

</body>
</html>
