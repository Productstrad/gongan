<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>左侧菜单 </title>
<meta name="keywords" content="www.cnblogs.com/jihua"/>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/left.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
    // 树状菜单
    $(document).ready(function () {
        $(".l1").toggle(function () {
            $(".slist").animate({ height: 'toggle', opacity: 'hide' }, "slow");
            $(this).next(".slist").animate({ height: 'toggle', opacity: 'toggle' }, "slow");
        }, function () {
            $(".slist").animate({ height: 'toggle', opacity: 'hide' }, "slow");
            $(this).next(".slist").animate({ height: 'toggle', opacity: 'toggle' }, "slow");
        });

        $(".l2").toggle(function () {
            $(this).next(".sslist").animate({ height: 'toggle', opacity: 'toggle' }, "slow");
        }, function () {
            $(this).next(".sslist").animate({ height: 'toggle', opacity: 'toggle' }, "slow");
        });

        $(".l2").click(function () {
            $(".l3").removeClass("currentl3");
            $(".l2").removeClass("currentl2");
            $(this).addClass("currentl2");
        });

        $(".l3").click(function () {
            $(".l3").removeClass("currentl3");
            $(this).addClass("currentl3");
        });

        $(".close").toggle(function () {
            $(".slist").animate({ height: 'toggle', opacity: 'show' }, "fast");
            $(".sslist").animate({ height: 'toggle', opacity: 'show' }, "fast");
        }, function () {
            $(".slist").animate({ height: 'toggle', opacity: 'hide' }, "fast");
            $(".sslist").animate({ height: 'toggle', opacity: 'hide' }, "fast");
        });
    });
</script>
<base target="main">
</head>

<body class="leftp">
<!--h1 class="title"><span class="close">全部收起/展开</span>Jihua树形菜单</h1-->
<div class="menu">
  <h1 class="l1"><span class="dot1"></span>新闻管理</h1>
  <div class="slist">
  	<h2 class="l3"><a href="news/add.do">发布新闻</a></h2>
    <h2 class="l3"><a href="news/list.do">新闻列表</a></h2>            
  </div>  
  <h1 class="l1"><span class="dot1"></span>栏目管理</h1>
  <div class="slist">
    <h2 class="l3"><a href="itemi/list.do">一级栏目管理</a></h2>
    <h2 class="l3"><a href="itemii/list.do">二级栏目管理</a></h2>    
  </div>
  <h1 class="l1"><span class="dot1"></span>微信管理</h1>  
  <div class="slist">
    <!-- <h2 class="l3"><a href="askmessage/list.do">互动咨询管理</a></h2> -->
    <h2 class="l3"><a href="members/list.do">微信会员管理</a></h2>    
  </div>
  <h1 class="l1"><span class="dot1"></span>调查问卷</h1>  
  <div class="slist">
    <h2 class="l3"><a href="survey/list.do">问卷管理</a></h2>
    <h2 class="l3"><a href="surveyrecord/list.do">答卷管理</a></h2>    
  </div>
  <h1 class="l1"><span class="dot1"></span>智能咨询系统</h1>  
  <div class="slist">
    <h2 class="l3"><a href="autoanswer/list.do">问题管理</a></h2>
    <h2 class="l3"><a href="questionrecord/list.do">提问记录</a></h2>    
  </div>
  <h1 class="l1"><span class="dot1"></span>系统管理</h1>
  <div class="slist">
    <h2 class="l3"><a href="admin/list.do">管理账户</a></h2>  
    <h2 class="l3"><a href="medialib/list.do">账户图库</a></h2>  
  </div>
  <!-- 参考例子 class="l3"说明是叶子节点链接才可点击
  <h1 class="l1"><span class="dot1"></span>一级菜单</h1>
  <div class="slist">
    <h2 class="l2"><a href="list.html">信息列表</a></h2>
    <ul class="sslist">
      <li class="l3"><a href="input.html">信息录入</a></li>
      <li class="l3"><a href="view.html">客户信息</a></li>
      <li class="l3"><a href="list.html" >信息列表</a></li>
      <li class="l3"><a href="#">三级菜单</a></li>
	  <li class="l3"><a href="index.html">首页</a></li>
    </ul>    
  </div>  
   -->
</div>
</body>
</html>