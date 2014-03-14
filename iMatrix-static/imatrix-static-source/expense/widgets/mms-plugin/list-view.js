var saveSign=false;

function mypropertyname(value, colname){
	var data=$("#propertyGridId").jqGrid('getRowData');
	if(data.length==1&&saveSign&&value==""){
		saveSign=false;
		return [true,""];
	}else{
		if(value==""){
			return [false,colname+"是必填的!"];
		}else{
			var reservedProperties=['url','prmNames','gridComplete','colNames','colModel','rownumbers','onSelectRow','ondblClickRow','editurl','rowNum','rowList','multiselect','multiboxonly','pager','serializeRowData','postData'];
			for(var i=0;i<reservedProperties.length;i++){
				if(value==reservedProperties[i]){
					return [false,"是保留字,请重新填写!"];
				}
			}
			return [true,""];
		}
	}
}

//页面验证
function  validate(){
	$("#viewSaveForm").validate({
		submitHandler: function() {
			saveSign=true;
			var subTable=getFormGridDatas("viewSaveForm","propertyGridId");
			if(subTable){
				ajaxSubmit("viewSaveForm",  webRoot+"/form/list-view!save.htm", "viewList",saveViewCallBack);
			}
		},
		rules: {
			code:"required",
			name: "required",
			remark:{
				maxlength:500
			},
			rowNum:{
				number:true
			},
			rowList:{maxlength:255},
			frozenNum:{number:true}
		},
		messages: {
			code:"必填",
			name: "必填",
			remark:{
				maxlength:"最多500字"
			},
			rowNum:{
				number:"请输入数字"
			},
			rowList:{maxlength:"请输入多个数字以逗号分隔"},
			frozenNum:{number:"请输入数字"}
		}
	});
	validateViewCode();
}

function saveViewCallBack(){
	showMsg();
	validate();
	$("#view_id1").attr("value",$("#viewId").val());//字段设置和按钮设置用到
}

function saveView(){
	$("#dataTableId").attr("value",$("#dataTable").val());
	$('#viewSaveForm').submit();
}

function createView(){
	$("#view_id").attr("value","");
	ajaxSubmit("defaultForm",webRoot+"/form/list-view!input.htm", "viewList", validate);
}

function copyView(){
	var ids = jQuery("#page").getGridParam('selarrrow');
	if(ids==""){
		showMessage("message", "<font color=\"red\">请选择一条数据</font>");
	}else if(ids.length > 1){
		showMessage("message", "<font color=\"red\">只能选择一条数据</font>");
	}else if(ids.length == 1){
		var meId = $("#menuId").val();
		$.colorbox({href:webRoot+"/form/list-view!copy.htm?menuId="+meId+"&viewId="+ids[0],iframe:true, innerWidth:400, innerHeight:300,overlayClose:false,title:"复制列表"});
	}
}

function updateViewCallBack(){
	validate();
	$("#view_id1").attr("value",$("#viewId").val());//字段设置和按钮设置用到
}

function updateView(id){
	if(id!=""&&typeof(id)!='undefined'){
		$("#view_id").attr("value",id);
		ajaxSubmit("defaultForm",webRoot+"/form/list-view!input.htm", "viewList", updateViewCallBack);
	}else{
		var ids = jQuery("#page").getGridParam('selarrrow');
		if(ids==""){
			showMessage("message", "<font color=\"red\">请选择一条数据</font>");
		}else if(ids.length > 1){
			showMessage("message", "<font color=\"red\">只能选择一条数据</font>");
		}else if(ids.length == 1){
			$("#view_id").attr("value",ids[0]);
			ajaxSubmit("defaultForm",webRoot+"/form/list-view!input.htm", "viewList,column", updateViewCallBack);
		}
	}
}

function deleteViews(){
	var ids = jQuery("#page").getGridParam('selarrrow');
	if(ids==""){
		showMessage("message", "<font color=\"red\">请选择一条数据</font>");
	}else if(ids.length >= 1){
		if(confirm("确定删除吗？")){
			for(var i=0;i<ids.length;i++){
				if($("#viewIds").attr("value")==""){
					$("#viewIds").attr("value",ids[i]);
				}else{
					$("#viewIds").attr("value",$("#viewIds").attr("value")+","+ids[i]);
				}
			}
			ajaxSubmit("defaultForm",webRoot+"/form/list-view!delete.htm", "viewTable",deleteCallBack);
		}
	}
}

function deleteCallBack(){
	$("#viewIds").attr("value","");
	showMsg();
}

//换页签
function changeViewSet(opt,obj){
	if($("#view_id1").attr("value")!=""){
		//selete(obj);
		if(opt=="basic"){
			ajaxSubmit("defaultForm1",webRoot+"/form/list-view!input.htm", "btnZone,viewZone",validate);
		}else{
			ajaxSubmit("defaultForm1",webRoot+"/form/list-column.htm", "btnZone,viewZone");
		}
	}else{
		alert("请先保存列表基本信息");
	}
}

function validateViewCode(){
	if($("#code").val()==0){
	$("#code").blur(function(){
		$.ajax({
			   type: "POST",
			   url: "list-view!validateCode.htm",
			   data: "myCode="+$("#code").attr("value")+"&viewId="+$("#viewId").attr("value"),
			   success: function(data){
			   		if(data=="true"){
			   			alert('编号 '+$("#code").attr("value")+' 已存在');
		   				$("#code").attr("value","");
		   				$("#code").focus();
			   		}
			   }
			}); 
	});
	}
}

function listViewBack(dataTableId,formId,url){
	goBack(formId,url, "viewList", "page");
	//viewChange(dataTableId);
	//leftChange($("#listA"+dataTableId));
}

function defaultDisplaySet(type){
	var ids = jQuery("#page").getGridParam('selarrrow');
	if(ids==""){
		alert("请选择数据");
	}else if(ids.length == 1){
		$("#view_id").attr("value",ids[0]);
		ajaxSubmit("defaultForm",webRoot+"/form/list-view!defaultDisplaySet.htm", "viewTable",showMsg);
	}else{
		alert("只能选择一条数据");
	}
}

