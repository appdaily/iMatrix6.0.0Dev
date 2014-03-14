<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/mms-taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
	<title>表单管理</title>
	<%@ include file="/common/mms-iframe-meta.jsp"%>
	<script type="text/javascript" src="${resourcesCtx}/widgets/timepicker/timepicker-all-1.0.js"></script>
	<script type="text/javascript" src="${resourcesCtx}/widgets/validation/validate-all-1.0.js"></script>

	<script src="${imatrixCtx}/widgets/formeditor/kindeditor.js" type="text/javascript"></script>
	<script src="${imatrixCtx}/widgets/formeditor/lang/zh_CN.js" type="text/javascript"></script>
	<script src="${imatrixCtx}/widgets/formeditor/formeditor.js" type="text/javascript"></script>
	<link href="${imatrixCtx}/widgets/formeditor/formeditor.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
	function choiceControl(){
		if($("#tableColumnId").get(0).selectedIndex==0){
			$("#name").attr("value","");
		}
		ajaxAnyWhereSubmit("textForm", "", "controlContent");
	}
	function generateHtml(){
		var controlId = $("#controlId").attr("value");
		var dataType = $("#dataType").attr("value");
		var format = "yyyy-MM-dd";
		var customType="";
		if(dataType=='TIME'){
			format = "yyyy-MM-dd HH:mm";
		}
		if($("#customTypeValue").val()!=""){
			format = $("#customTypeValue").val();
			customType="CUSTOM";
		}

		var classStyle="";
		var styleContent="";
		if($("#classId").attr("value")!=""){
			classStyle=" class=\""+$("#classId").attr("value")+"\"";
		}
		if($("#styleId").attr("value")!=""){
			styleContent=" style=\""+$("#styleId").attr("value")+"\"";
		}
		var html = "<input  pluginType=\"TIME\" id=\""+controlId+"\"" 
		+" name=\""+ $("#name").attr("value")+"\"" 
		+" customType=\""+ customType+"\"" 
		+" title=\""+$("#title").attr("value")+"\""
		+" dataType=\""+dataType+"\""
		+" format=\""+format+"\""
		+" request=\""+$("#request").attr("value")+"\""
		+classStyle
		+styleContent
		+" readOnly=\"readOnly\" value=\""+$("#controlValue").attr("value")+"\"";
		if("${standard}"=="true"){
			if($("#tableColumnId").get(0).selectedIndex==0){
				alert("请选择对应字段");
				return;
			}else{
				if(dataType!="DATE"&&dataType!="TIME"){
					alert("请选择日期或时间类型的字段");
					return;
				}else{
					html = html
						+" columnId=\""+$("#tableColumnId").attr("value")+"\""
						+" dbName=\""+$("#dbName").attr("value")+"\"";
				}
			}
		}
		html=html+"/>";
		parent.timeHtml(html);
	}

	function getDateFormatOption(){
		var result="<option value=''>请选择</option>";
		result+="<option value='yyyy-m'>yyyy-m</option>";
		result+="<option value='m-d'>m-d</option>";
		result+="<option value='yyyy年m月d日'>yyyy年m月d日</option>";
		result+="<option value='yyyy年m月'>yyyy年m月</option>";
		result+="<option value='m月d日'>m月d日</option>";
		result+="<option value='二O一四年一月一日'>二O一四年一月一日</option>";
		result+="<option value='二O一四年一月'>二O一四年一月</option>";
		result+="<option value='一月一日'>一月一日</option>";
		return result;
	}
	
	function getTimeFormatOption(){
		var result=getDateFormatOption();
		result+="<option value='h:mm'>h:mm</option>";
		result+="<option value='h时mm分'>h时mm分</option>";
		return result;
	}

	function changeDataType(){
		var dataType = $("#dataType").val();
		var value = $("#controlValue").val();
		var valParent = $("#controlValue").parent();
		if(dataType=="TIME"){
			$("#customTypeValue").attr("value","");
			$("#customTypeValue").find("option").remove();
			$("#customTypeValue").html(getTimeFormatOption());
			$("#controlValue").remove();
			$(valParent).append('<input name="formControl.controlValue" id="controlValue" readonly="readonly" value="'+value+'"></input>');
				$("#controlValue").datetimepicker({
				  "dateFormat":'yy-mm-dd',
				   changeMonth:true,
				   changeYear:true,
				   showSecond: false,
					showMillisec: false,
					"timeFormat": 'hh:mm'
			   });
		}else if(dataType=="DATE"){
			$("#customTypeValue").attr("value","");
			$("#customTypeValue").find("option").remove();
			$("#customTypeValue").html(getDateFormatOption());
			$("#controlValue").remove();
			$(valParent).append('<input name="formControl.controlValue" id="controlValue" readonly="readonly" value="'+value+'"></input>');
			$("#controlValue").datepicker({
				  "dateFormat":'yy-mm-dd',
				  changeMonth:true,
				  changeYear:true,
				  showButtonPanel:"true"
			   });
		}else if(dataType=="CUSTOM"){
			$("#controlValue").remove();
			$(valParent).append('<input name="formControl.controlValue" id="controlValue" value="'+value+'"></input>');
		}else{
			$("#customTypeValue").attr("value","");
			$("#controlValue").remove();
			$(valParent).append('<input name="formControl.controlValue" id="controlValue" value="'+value+'"></input>');
		}
	}
	function changeCustomTypeValue(){
		var customTypeValue = $("#customTypeValue").val();
		var valParent = $("#controlValue").parent();
		$("#controlValue").remove();
		$(valParent).append('<input name="formControl.controlValue" id="controlValue" value=""></input>');
		customTypeCalendarControl($("#controlValue"),customTypeValue);
	}
	</script>
	<style type="text/css">
	.form-table-without-border td input{
		width:200px;
	}
	</style>
</head>
<body onload="getContentHeight();">
<div class="ui-layout-center">
<div class="opt-body">
	<div class="opt-btn">
		<button class="btn" onclick="$('#textForm').submit();"><span><span>确定</span></span></button>
		<button class="btn" onclick='parent.$.colorbox.close();'><span><span >取消</span></span></button>
	</div>
	<div id="opt-content">
		<aa:zone name="controlContent">
			<form name="textForm" id="textForm" action="${mmsCtx }/form/form-view!text.htm">
				<s:hidden name="id"></s:hidden>
				<s:hidden id="code" name="code"></s:hidden>
				<s:hidden id="version" name="version"></s:hidden>
				<s:hidden id="standard" name="standard"></s:hidden>
				<s:hidden id="occasion" name="occasion" value="changeSource"></s:hidden>
				<table class="form-table-without-border">
					<tbody>
						<tr>
							<td class="content-title">控件类型：</td>
							<td class="no-edit">
								<s:property value="formControl.controlType.code"/>
								<s:hidden theme="simple" id="controlType" name="formControl.controlType" ></s:hidden>
							</td>
							<td>
							</td>	
						</tr>	
						<s:if test="standard">
							<tr>
								<td class="content-title">对应字段：</td>
								<td>
									<s:hidden id="dataType" name="formControl.dataType"></s:hidden>
									<s:select onchange="choiceControl();" id="tableColumnId" name="tableColumnId" list="columns" theme="simple" listKey="id" listValue="alias" headerKey="0" headerValue="请选择"></s:select>
								</td>
								<td></td>	
							</tr>
							<tr>	
								<td class="content-title">格式设置：</td>	
								<td>
									 <select id="customTypeValue" onchange="changeCustomTypeValue();" >
										<option value="">请选择</option>
										<option value="yyyy-m">yyyy-m</option>
										<option value="m-d">m-d</option>
										<option value='yyyy年m月d日'>yyyy年m月d日</option>
										<option value='yyyy年m月'>yyyy年m月</option>
										<option value='m月d日'>m月d日</option>
										<option value='二O一四年一月一日'>二O一四年一月一日</option>
										<option value='二O一四年一月'>二O一四年一月</option>
										<option value='一月一日'>一月一日</option>
										<s:if test='formControl.dataType.toString()=="TIME"'>
										<option value="h:mm">h:mm</option>
										<option value='h时mm分'>h时mm分</option>
										</s:if>
									</select>
								</td>
							</tr>
							<tr>
								<td class="content-title">字段名：</td>
								<td>
									<s:if test="tableColumnId==null||tableColumnId==0">
										<s:textfield theme="simple" id="name" name="formControl.name" cssClass="{required:true,messages: {required:'必填'}}"></s:textfield>
									</s:if>
									<s:else>
										<s:textfield theme="simple" id="name" name="formControl.name" readonly="true" cssClass="{required:true,messages: {required:'必填'}}"></s:textfield>
									</s:else>
									<s:hidden  theme="simple" name="formControl.dbName" id="dbName"/>
									<s:hidden  theme="simple" name="formControl.dataType" id="dataType"/>
								</td>
								<td>
									<span id="nameTip"></span>
								</td>	
							</tr>
						</s:if>
						<s:else>
							<tr>
								<td class="content-title">字段名：</td>
								<td>
									<s:textfield theme="simple" id="name" name="formControl.name"  cssClass="{required:true,messages: {required:'必填'}}" onblur="fieldNameOk(this);" maxlength="27"></s:textfield>
								</td>
								<td>
									<span id="nameTip"></span>
								</td>	
							</tr>
							<tr>	
								<td class="content-title">字段类型：</td>	
								<td>
									 <s:select theme="simple" id="dataType" list="#{'':'请选择','DATE':'日期','TIME':'时间'}" 
							 					name="formControl.dataType" onchange="changeDataType();" cssClass="{required:true,messages: {required:'必填'}}" ></s:select>
								</td>
							</tr>
							<tr>	
								<td class="content-title">格式设置：</td>	
								<td>
									 <select id="customTypeValue" onchange="changeCustomTypeValue();" >
										<option value="">请选择</option>
										<option value="yyyy-m">yyyy-m</option>
										<option value="m-d">m-d</option>
										<option value='yyyy年m月d日'>yyyy年m月d日</option>
										<option value='yyyy年m月'>yyyy年m月</option>
										<option value='m月d日'>m月d日</option>
										<option value='二O一四年一月一日'>二O一四年一月一日</option>
										<option value='二O一四年一月'>二O一四年一月</option>
										<option value='一月一日'>一月一日</option>
										<s:if test='formControl.dataType.toString()=="TIME"'>
										<option value="h:mm">h:mm</option>
										<option value='h时mm分'>h时mm分</option>
										</s:if>
									</select>
								</td>
							</tr>
						</s:else>
						<tr>
							<td class="content-title">字段别名：</td>
							<td>
								<s:textfield theme="simple" id="title" name="formControl.title"  cssClass="{required:true,messages: {required:'必填'}}"></s:textfield>
								<s:hidden  theme="simple" name="formControl.controlId" id="controlId"/>
							</td>
							<td><span id="titleTip"></span></td>	
						</tr>
						<tr>
							<td class="content-title">样式类名：</td>
							<td>
								<s:textfield theme="simple" id="classId" name="formControl.classStyle"></s:textfield>
								<br/>注：多个类名以空格分隔
							</td>
							<td></td>	
						</tr>
						<tr>
							<td class="content-title">内联样式：</td>
							<td>
								<s:textfield theme="simple" id="styleId" name="formControl.styleContent"></s:textfield>
								<br/>例如：color: red;
							</td>
							<td></td>	
						</tr>	
						<s:if test="standard">
						<tr>
							<td class="content-title">默认值：</td>
							<td >
							<s:if test="tableColumn.dataType.enumName=='TIME'">
							<script type="text/javascript">
								$(function(){
									$("#controlValue").datetimepicker({
									  "dateFormat":'yy-mm-dd',
									   changeMonth:true,
									   changeYear:true,
									   showSecond: false,
										showMillisec: false,
										"timeFormat": 'hh:mm'
								   });
								});
								</script>
								<input name="formControl.controlValue" id="controlValue" readonly="readonly" value="${formControl.controlValue }"></input>
							</s:if>
							<s:elseif test="tableColumn.dataType.enumName=='DATE'">
							<script type="text/javascript">
								$(function(){
									$("#controlValue").datepicker({
									  "dateFormat":'yy-mm-dd',
									  changeMonth:true,
									  changeYear:true,
									  showButtonPanel:"true"
								   });
								});
								</script>
								<input name="formControl.controlValue" id="controlValue" readonly="readonly" value="${formControl.controlValue }"></input>
							</s:elseif><s:else>
							<script type="text/javascript">
								$(function(){
									$("#controlValue").datetimepicker({
									  "dateFormat":'yy-mm-dd',
									   changeMonth:true,
									   changeYear:true,
									   showSecond: false,
										showMillisec: false,
										"timeFormat": 'hh:mm'
								   });
								});
								</script>
								<input name="formControl.controlValue" id="controlValue" readonly="readonly" value="${formControl.controlValue }"></input>
							</s:else>
							</td>
							<td><span id="controlValueTip"></span></td>	
						</tr>
						</s:if><s:else>
							<tr>
							<td class="content-title">默认值：</td>
							<td id="controlValueTd">
							<s:if test="formControl.dataType.enumName=='DATE'">
							<script type="text/javascript">
								$(function(){
									$("#controlValue").datepicker({
									  "dateFormat":'yy-mm-dd',
									  changeMonth:true,
									  changeYear:true,
									  showButtonPanel:"true"
								   });
								});
								</script>
								<input name="formControl.controlValue" id="controlValue" readonly="readonly" value="${formControl.controlValue }"></input>
							
							</s:if><s:elseif test="formControl.dataType.enumName=='TIME'">
							<script type="text/javascript">
									$(function(){
										$("#controlValue").datetimepicker({
										  "dateFormat":'yy-mm-dd',
										   changeMonth:true,
										   changeYear:true,
										   showSecond: false,
											showMillisec: false,
											"timeFormat": 'hh:mm'
									   });
									});
									</script>
								<input name="formControl.controlValue" id="controlValue" readonly="readonly" value="${formControl.controlValue }"></input>
							</s:elseif><s:else>
							<script type="text/javascript">
									$(function(){
										$("#controlValue").datetimepicker({
										  "dateFormat":'yy-mm-dd',
										   changeMonth:true,
										   changeYear:true,
										   showSecond: false,
											showMillisec: false,
											"timeFormat": 'hh:mm'
									   });
									});
									</script>
								<input name="formControl.controlValue" id="controlValue" readonly="readonly" value="${formControl.controlValue }"></input>
							</s:else>
							</td>
							<td><span id="controlValueTip"></span></td>	
							</tr>
						</s:else>
						<tr>
							<td>必填验证：</td>
							<td>
								<s:select id="request" theme="simple" list="#{'false':'不验证','true':'验证'}" name="formControl.request" >
								</s:select>
							</td>
							<td></td>	
						</tr>
					</tbody>
				</table>
			</form>
			<script type="text/javascript">
			$(document).ready(function () { 
				settingCustomTypeValue();
			});
			function settingCustomTypeValue(){
				if("${formControl.customType}"=="CUSTOM"){
					$("#customTypeValue").attr("value","${formControl.format}");
					var valParent = $("#controlValue").parent();
					$("#controlValue").remove();
					$(valParent).append('<input name="formControl.controlValue" id="controlValue" value="${formControl.controlValue }"></input>');
					customTypeCalendarControl($("#controlValue"),"${formControl.format}");
				}
			}
			function validateText(){
				$("#textForm").validate({
					submitHandler: function() {
						generateHtml();
					}
				});
			}
			validateText();
			</script>
		</aa:zone>
	</div>
</div>
</div>
</body>
</html>
