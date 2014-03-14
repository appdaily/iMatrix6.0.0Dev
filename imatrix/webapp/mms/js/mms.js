var processName="门户";//在线试用时用到
var currentTreeId;
//创建页面树菜单
function createViewTree(treeId){
	currentTreeId=treeId;
	$.ajaxSetup({cache:false});
	var url="";
	if(treeId=="page_manage_content"){//页面管理
		url=webRoot+"/module/module-page!tree.htm";
		page_manage_content_tree(treeId,url);
	}else if(treeId=="data_table_manage_content"){
		url=webRoot+"/form/data-table!dataTableStandardSysTree.htm";
		data_table_manage_content_tree(treeId,url);
	}else if(treeId=="form_manage_content"){
		url=webRoot+"/form/data-table!dataTableTree.htm?showTreeType=formView";
		form_manage_content_tree(treeId,url);
	}else{
		url=webRoot+"/form/data-table!dataTableTree.htm";
		list_manage_content_tree(treeId,url);
	}
}
function list_manage_content_tree(treeId,url){
	$.ajaxSetup({cache:false});
	tree.initTree({treeId:treeId,
		url:url,
		type:"ztree",
		initiallySelect:"",
		initiallySelectFirstChild:true,
		callback:{
				onClick:selectZNode
			}});
}
function data_table_manage_content_tree(treeId,url){
	$.ajaxSetup({cache:false});
	tree.initTree({treeId:treeId,
		url:url,
		type:"ztree",
		initiallySelectFirstChild:true,
		callback:{
				onClick:selectZNode
			}});
	
    }
function form_manage_content_tree(treeId,url){
	$.ajaxSetup({cache:false});
	//treeId:,url:,data(静态树才需要该参数):,multiple:,callback:
	tree.initTree({treeId:treeId,
		url:url,
		type:"ztree",
		initiallySelect:"",
		initiallySelectFirstChild:true,
		callback:{
				onClick:selectZNode
			}});
	
    }
function page_manage_content_tree(treeId,url){
	$.ajaxSetup({cache:false});
	//treeId:,url:,data(静态树才需要该参数):,multiple:,callback:
	tree.initTree({treeId:treeId,
		url:url,
		type:"ztree",
		initiallySelect:"",
		initiallySelectFirstChild:true,
		callback:{
				onClick:selectZNode
			}});
	
    }
function selectZNode(){
	var currentId = tree.getSelectNodeId();
	selectNode(currentId);
}
function selectNode(currentId){
	var preMenuId=$("#menuId").attr("value");
	//if(preMenuId!="" && typeof(preMenuId)!='undefined')$("#"+$("#menuId").attr("value")).children("a").removeClass("jstree-clicked");
	$("#menuId").attr("value",currentId);
	treechange(currentId);
}
function treechange(currentId){
	var url="";
	var treeId=tree.treeId;
	if(treeId=="page_manage_content"){
		url=webRoot+"/module/module-page-list.htm?menuId="+currentId;
	}else if(treeId=="data_table_manage_content"){
	    var type = currentId.split("_")[1];
	    var id = currentId.split("_")[0];
	    if(type=='STANDARD'){
	    	url=webRoot+"/form/data-table.htm";
	    	if(id!="SYSTEM"){
	    		url=webRoot+"/form/data-table.htm?menuId="+id;
	    	}
	    }else if(type=='CUSTOM'){
	    	url=webRoot+"/form/data-table-defaultDataTableList.htm";
	    	if(id!="SYSTEM"){
	    		url=webRoot+"/form/data-table-defaultDataTableList.htm?menuId="+id;
	    	}
	    }
		
	}else if(treeId=="list_manage_content"){
		url=webRoot+"/form/list-view.htm?menuId="+currentId;
	}else if(treeId=="form_manage_content"){
		if(currentId=='deleted_form_view'){
		   url=webRoot+"/form/list-data.htm?deletedFormViewFolder="+currentId;
		}else{
		   url=webRoot+"/form/list-data.htm?menuId="+currentId;
		}
	}
	$("#myIFrame").attr("src",url);
}

function ajaxSubmit(form, url, zoons, ajaxCallback,arg){
	if(typeof(removeSearchFlag)=="undefined")removeSearchFlag=true;//当不传该参数时需要去除查询
		if(typeof(ajaxCallback) == "function"){
			if(arg!=""){
				ajaxAnyWhereSubmit(form, url, zoons, ajaxCallback,arg);
			}else if(typeof(arg)!="undefined"){
				ajaxAnyWhereSubmit(form, url, zoons, ajaxCallback,arg);
			}else{
				ajaxAnyWhereSubmit(form, url, zoons, ajaxCallback);
			}
		}else{
			ajaxAnyWhereSubmit(form, url, zoons,"");
		}
}
//公用的返回调用。如果本页面没有back()方法的实现，就调用父页面的，父页面也没有就关闭本窗口。
function back_main(){
	try{
		back();
	}catch(message){
		try{
			window.parent.back();
		}catch(message){
			//这种情况应该是自己做为独立页面被打开
			window.close();
		}
	}
}

//返回调用,保持页数。
function goBack(form,url,zone,jemesaId){
	//getPageStateAttr(jemesaId, form);
	ajaxSubmit(form, url, zone);
}

//去多少页
function class_go(form,url,zone,pagesize,inputId,ajaxCallback){
	var str=$("#"+inputId).attr("value");
	if(str==null&&str==""){
		return;
	}
	if(parseInt(str)<=0){
		$("#"+inputId).attr("value","");
		return;
	}
	if(parseInt(str)>pagesize){
		alert("输入的值超过范围");
		$("#"+inputId).attr("value","");
		return;
	}
	ajaxSubmit(form,url+str,zone,ajaxCallback);
}

//菜单选中
function selete(obj){
	if(obj!=null){
		$(".index").removeClass("selectind");
		$(obj).parent("li").addClass("selectind");
	}
}

//显示提示信息，3秒后隐藏
function showMsg(id,time){
	if(id==undefined)id="message";
	$("#"+id).show();
	if(time==undefined)time=3000;
	setTimeout('$("#'+id+'").hide();',time);
}


//通用消息提示
function showMessage(id, msg){
	if(msg != ""){
		$("#"+id).html(msg);
	}
	$("#"+id).show("show");
	setTimeout('$("#'+id+'").hide("show");',3000);
}
//错误提示
function errorTip(message,id){
	if(id==undefined)id="message";
	$("#"+id).html('<font class="onError"><nobr>'+message+'</nobr></font>');
	showMsg();
}
//成功提示
function successTip(message,id){
	if(id==undefined)id="message";
	$("#"+id).html('<font class="onSuccess"><nobr>'+message+'</nobr></font>');
	showMsg();
}

function initIframe(iframeId){
    $("#"+iframeId).contents().find("body").css("background-color","transparent");
    $("#"+iframeId).contents().find('body').ready(function(){
		$("#"+iframeId,parent.document).height($("#"+iframeId).contents().find('.widget-place').height()+10);
	   parent.automaticHeight();
	}) ;
}


//作用：让点击菜单选中。 所以点击菜单页签时，要调用该方法
//obj为菜单提供链接的<a>标签
function selectmenu(obj){
	var li =$(obj).parent().parent().children("li");
	for(var  i=0;i<li.length;i++){
		$(li[i]).removeClass("selected");//.attr("class","");
	}
	$(obj).parent().addClass("selected");
}
//ss=setInterval(getFormContent,600)" onblur="clearInterval(ss)"
var formContentControlTimer ;
var areaId,areaTipId,areaMaxNum;
function fouceHandler(areaId,areaTipId,areaMaxNum){
	this.areaId=areaId;
	this.areaTipId=areaTipId;
	this.areaMaxNum=areaMaxNum;
	formContentControlTimer=setInterval(FormContentControl,600);
}
function blurHandler(){
	clearInterval(formContentControlTimer);
}
function FormContentControl(){
	var len = $("#"+areaId).html().length;
	if(len>areaMaxNum)$("#"+areaId).html($("#"+areaId).html().substring(0,areaMaxNum));
	$('#'+areaTipId).html("最大输入"+areaMaxNum+"字,你已经输入了"+len);
}
function resetJmessaPageNo(arg){
	if(arg=="" || typeof(arg)=="undefined")	arg="page";	
	changePageNoState(arg);
}

function getEsc(str){
	for(var i=0;i<str.length ;i++){
		str = str.replace("<","&lt;");
		str = str.replace(">","&gt;");
		str = str.replace("'","&apos;");
		str = str.replace('"',"&quot;");
	}
	return str;
}

/*---------------------------------------------------------
函数名称:leftChange
参          数:
功          能:三级导航左边栏变色
------------------------------------------------------------*/
function leftChange(obj){
	if($(obj).parent().attr("nodeName")=="DIV"){
		$(obj).parent().parent().find(".leftCol").each(function(i){
			if($(this).children().attr("nodeName")=="STRONG"){
			   $(this).html($(this).children().html());
			}
		 });
		$(obj).parent().html("<strong >"+$(obj).parent().html()+"</strong>");
	}
}
function demonstrateOperate(){
	alert("为确保系统的正常演示，屏蔽了该功能");
}