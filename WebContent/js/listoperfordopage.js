function doAction(act, ids) {
	//以下为删除、还原、彻底删除逻辑
	if (act == 'logicdelete') {
		if (!confirm('确定删除选中项？删除后可进入回收站恢复')) {
			return;
		}
	} else if (act == 'delete') {
		if (!confirm('确定彻底删除选中项?删除后将不可恢复')) {
			return;
		}
	}

	if (ids == 'selected') {
		ids = new Array();
		$("input[name='input']:checked").each(function() {
			ids.push($(this).val());
		});
	}
	if(ids==''){
		alert('请先选择删除条目');
		return;
	}
	$.ajax({
		type : "POST",
		url : act+".do",
		data : {
			'ids[]' : ids
		},
		dataType : "text",
		success : function(result) {
			var jsonObj=eval("("+result+")");
			if(jsonObj.suc>0){
				alert('操作成功');				
				window.location.href = window.location.href;				
			}else{
				if(jsonObj.msg==null){
					alert('操作失败');
				}else{
					alert('操作失败:'+jsonObj.msg);
				}	
			}	
		},
		error : function(request, error) {
			alert(error);
		}
	});
}

function postForm(formId,postUrl){	
	$.ajax({
			type : "POST",
			url : postUrl,
			data : $('#'+formId).serialize(),
			dataType : "text",
			success : function(result) {
				var jsonObj=eval("("+result+")");
				if(jsonObj.suc>0){
					alert('操作成功');
					if(window.parent){
						window.parent.location.href = window.parent.location.href;
					}else{
						window.location.href = window.location.href;
					}
				}else{
					if(jsonObj.msg==null){
						alert('操作失败');
					}else{
						alert('操作失败:'+jsonObj.msg);
					}					
				}			
			},
			error : function(request, error) {
				alert(error);
			}
	});	
}

function postFormNofresh(formId,postUrl){	
	$.ajax({
			type : "POST",
			url : postUrl,
			data : $('#'+formId).serialize(),
			dataType : "text",
			success : function(result) {
				var jsonObj=eval("("+result+")");
				if(jsonObj.suc>0){
					alert('操作成功');					
				}else{
					if(jsonObj.msg==null){
						alert('操作失败');
					}else{
						alert('操作失败:'+jsonObj.msg);
					}					
				}			
			},
			error : function(request, error) {
				alert(error);
			}
	});	
}

function delcache(cacheName){
	jQuery.ajax({ url: "/sys/cache/delcache.do?cacheName="+cacheName+"&rnd=math.random()", context: document.body, success: function(result){		
		alert(result);
    }});	
}

$(document).ready(function() {
	// 设置全选、取消全选功能
	$("#quanxuan").click(function() {
		if ($(this).attr("checked")) {
			$("input[name='input']").attr("checked", true);
		} else {
			$("[name='input']").removeAttr("checked");// 取消全选
		}
	});
});