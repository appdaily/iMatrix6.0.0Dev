$(document).ready(function() {
	var url=parent._obj.url+"?"+parent._url_prn;
	parent._crn_obj=parent._obj;
	if(typeof(parent._obj.tree)!="undefined"){
		$("#btnDiv").html('<div class="opt-btn"><button class="btn" onclick="_ok();"><span><span>确定</span></span></button></div>');
		var html="<ul>";
		var i=0;
		for(var title in parent._obj.tree){
			i++;
			var treeobj=parent._obj.tree[title];
			if(i==1){
				parent._crn_obj=treeobj;
				var tmpurl=treeobj.url;
				if(parent._crn_obj.url.indexOf("?")>=0){
					var urlArr=parent._crn_obj.url.split("?");
					tmpurl=urlArr[0];
				}
				var tmpprnurl=_get_url_parameter();
				url=tmpurl+"?"+tmpprnurl;
			}
			html+='<li><a href="#tabs-1" onclick="__changeViewSet(\''+title+'\');">'+title+'</a></li>';
		}
		html+='</ul>';
		$("#tagDiv").html(html);
		$( "#tabs" ).tabs();
	}else{
		if(!parent._crn_obj.mutiple){
			_jstree_single_bind_select();
		}
	}
	if(parent._crn_obj.multiple){
		$("#btnDiv").html('<div class="opt-btn"><button class="btn" onclick="_ok();"><span><span>确定</span></span></button></div>');
	}
	if(parent._obj.treeType=="jstree"){
		__create_tree(url);
	}else{
		__create_ztree(url);
	}
});

function __changeViewSet(title){
	parent._crn_obj=parent._obj.tree[title];
	var url="";
	var tmpurl=parent._crn_obj.url;
	if(parent._crn_obj.url.indexOf("?")>=0){
		var urlArr=parent._crn_obj.url.split("?");
		tmpurl=urlArr[0];
	}
	var tmpprnurl=_get_url_parameter();
	url=tmpurl+"?"+tmpprnurl;
	if(parent._obj.treeType=="jstree"){
		__create_tree(url);
	}else{
		__create_ztree(url);
	}
}

function __create_tree(url){
	$.ajaxSetup({cache:false});
	var jstreeOption={
		"json_data":{
			"ajax" : { "url" : encodeURI(url),
						"data" : function (n) {
							 return { currentId : n!=-1 ? n.attr("id") : 0 };   
						}
					}
		   },
		   "themes" : {  
			 "theme" : "classic",  
			 "dots" : true,  
			 "icons" : true 
			},
		   "plugins" : [ "themes", "json_data" ,"ui" ]
	};
	if(typeof(parent._crn_obj.multiple)!="undefined"&&parent._crn_obj.multiple){
		jstreeOption.plugins=[ "themes", "json_data" ,"ui","checkbox" ];
	}
	$("#treeDiv").jstree(jstreeOption);
}

function __create_ztree(url){
	$.ajaxSetup({cache:false});
	var checkObj = _getCheck();
	var setting = {
			async: {
				enable: true,
				url: encodeURI(url)
			},
			check: checkObj,
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: _ztree_single_bind_select,
				onCheck:___customTreeOnCheckExpandNode
			}
			
		};
	$.fn.zTree.init($("#treeDiv"), setting);
}

function _getCheck(){
	var checkStr = {};
	if(typeof(parent._crn_obj.multiple)!="undefined"&&parent._crn_obj.multiple){//多选时
		checkStr.chkboxType={Y : 'ps', N : 'ps' };
		checkStr.enable=true;
		checkStr.chkStyle="checkbox";
	}
	 return checkStr;
}

function ___customTreeOnClickExpandNode(event, treeId, treeNode){
	if(treeNode){
	 	var zTree = $.fn.zTree.getZTreeObj(treeId);
	 	if(parent._crn_obj.multiple){//多选时
	 		zTree.checkNode(treeNode, null, true);
	 		if(treeNode.checked){
	 			zTree.expandNode(treeNode,true,false,true,true);
	 		}
	 	}else{
	 		zTree.expandNode(treeNode,true,false,true,true);
	 	}
	  }
}
function ___customTreeOnCheckExpandNode(event, treeId, treeNode){
	if(parent._crn_obj.multiple){//多选时
		//获取所有选择节点集合selectNodeList
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		if(treeNode){
			if(treeNode.checked){
				zTree.expandNode(treeNode,true,false,true,true);
			}
		}
	}
}

function _ztree_single_bind_select(event, treeId, treeNode){
	if(!parent._crn_obj.multiple){//单选时
		parent._node_id=treeNode.id;
		parent._node_title=treeNode.name;
		if(typeof(parent._crn_obj.onsuccess)=="undefined"||parent._crn_obj.onsuccess==""){
			if(typeof(parent._obj.onsuccess)!="undefined"&&parent._obj.onsuccess!=""){
				parent._operate="ok";
				parent.$.colorbox.close();
			}else{
				var _inpObj=parent._crn_obj.inputObj;
				if(typeof(parent._crn_obj.inputObj)=="undefined"||parent._crn_obj.inputObj==""){
					_inpObj=parent._obj.inputObj;
				}
				parent.$("#"+_inpObj).attr("value",parent._node_title);
				parent.$.colorbox.close();
			}
		}else{
			if(parent._node_id=='_role'){
				alert("请选择角色");
			}else{
				parent._operate="ok";
				parent.$.colorbox.close();
			}
		} 
	}
		___customTreeOnClickExpandNode(event, treeId, treeNode);
	
}

function _jstree_single_bind_select(){
	if(parent._obj.treeType=="jstree"){//jstree选中节点时绑定事件
		$("#treeDiv").bind("select_node.jstree",function(e){
			parent._node_id=$(".jstree-clicked").parent().attr("id");
			parent._node_title=$("#treeDiv").jstree("get_text","#"+parent._node_id);
			if(typeof(parent._crn_obj.onsuccess)=="undefined"||parent._crn_obj.onsuccess==""){
				if(typeof(parent._obj.onsuccess)!="undefined"&&parent._obj.onsuccess!=""){
					parent._operate="ok";
					parent.$.colorbox.close();
				}else{
					var _inpObj=parent._crn_obj.inputObj;
					if(typeof(parent._crn_obj.inputObj)=="undefined"||parent._crn_obj.inputObj==""){
						_inpObj=parent._obj.inputObj;
					}
					parent.$("#"+_inpObj).attr("value",parent._node_title);
					parent.$.colorbox.close();
				}
			}else{
				if(parent._node_id=='_role'){
					alert("请选择角色");
				}else{
					parent._operate="ok";
					parent.$.colorbox.close();
				}
			} 
		});
	}
}

function _get_select_ztree_text(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDiv");
	var nodes = treeObj.getCheckedNodes();
	for(var i=0; i<nodes.length; i++){
		if(!nodes[i].getCheckStatus().half){//如果是全选状态
			if(typeof(parent._node_id)=="undefined"||parent._node_id==""){
				parent._node_id=nodes[i].id;
				parent._node_title=nodes[i].name;
			}else{
				parent._node_id=parent._node_id+","+nodes[i].id;
				parent._node_title=parent._node_title+","+nodes[i].name;
			}
		}
	}
}

function _get_select_nodes_id(){
	var lists ;
	if(typeof(parent._crn_obj.multiple)=="undefined"||!parent._crn_obj.multiple){
    	lists=$("#treeDiv").find("li a.jstree-clicked").parent();
	}else{
    	lists=$("#treeDiv").find("li.jstree-checked");
	}
	var v="" ;
	for(var i=0; i<lists.length; i++){
		v+=$(lists[i]).attr("id");
		if(i!=lists.length-1)
			v+=";";
	}
	if(v!=""){
		var arr=v.split(";");
		return arr;
	}else{
		return "";
	}
}

function _get_select_text(){
	var arr=_get_select_nodes_id();
	for(var i=0; i<arr.length; i++){
		if(typeof(parent._node_id)=="undefined"||parent._node_id==""){
			parent._node_id=arr[i];
			parent._node_title=$("li[id='"+arr[i]+"'] a").attr("title");
		}else{
			parent._node_id=parent._node_id+","+arr[i];
			parent._node_title=parent._node_title+","+$("li[id='"+arr[i]+"'] a").attr("title");
		}
	}
}

function _ok(){
	if(parent._obj.treeType=="ztree"){//ztree获得选中节点的值
		_get_select_ztree_text();
	}else{//jstree获得选中节点的值
		_get_select_text();
	}
	if(typeof(parent._crn_obj.onsuccess)=="undefined"||parent._crn_obj.onsuccess==""){
		if(typeof(parent._obj.onsuccess)!="undefined"&&parent._obj.onsuccess!=""){
			parent._operate="ok";
			parent.$.colorbox.close();
		}else{
			var _inpObj=parent._crn_obj.inputObj;
			if(typeof(parent._crn_obj.inputObj)=="undefined"||parent._crn_obj.inputObj==""){
				_inpObj=parent._obj.inputObj;
			}
			parent.$("#"+_inpObj).attr("value",parent._node_title);
			parent.$.colorbox.close();
		}
	}else{
		if(parent._node_id==""||typeof(parent._node_id)=="undefined"){
			alert("请选择节点!");
		}else{
			parent._operate="ok";
			parent._onsuccess=parent._crn_obj.onsuccess;
			parent.$.colorbox.close();
		}
	} 
}

function _get_url_parameter(){
	var _url_prn="";
	if(parent._crn_obj.url.indexOf("?")>=0){
		var urlArr=parent._crn_obj.url.split("?");
		_url_prn=urlArr[1];//'startDate='+startDate+'&endDate='+endDate
	}
	var paraObj=parent._crn_obj.postData;
	if(typeof(paraObj)=="undefined"){
		paraObj=parent._obj.postData;
	}
	if(typeof(paraObj)!="undefined"){
		for(var p in paraObj){
			// 方法
			if(typeof(paraObj[p])!="function"){
				// p 为属性名称，obj[p]为对应属性的值 
				if(_url_prn==""){
					_url_prn=p+"="+paraObj[p];
				}else{
					_url_prn=_url_prn+"&"+p+"="+paraObj[p];
				}
			}
		}
	}
	if(_url_prn==""){
		return parent._url_prn;
	}else{
		return _url_prn;
	}
}