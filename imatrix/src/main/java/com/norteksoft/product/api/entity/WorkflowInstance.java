package com.norteksoft.product.api.entity;

import java.io.Serializable;
import java.util.Date;
import com.norteksoft.wf.base.enumeration.ProcessState;


public class WorkflowInstance implements Serializable{
	
	//entity
	private Long id;
	private Long companyId;
	private String creator; // 创建者登录名
	private String creatorName;//创建人姓名
	private Long creatorId; // 创建者id
	private Long subCompanyId;//所属分支机构id
	private String subCompanyName;//所属分支机构名称，和流程创建人的所属分支机构一致
	//WorkflowInstance
	private static final long serialVersionUID = 1L;
	private Long workflowDefinitionId;//流程定义扩展类的Id
	private String processDefinitionId;//jbpm流程定义的ID
	private String  processInstanceId;//流程实例ID jbpm实例的id
	private String parentProcessId;//父流程的id
	private String parentProcessTacheName;//父流程的环节名
	private String parentExcutionId;//当前子流程对应的父流程Excution
	private Date startTime;//发起日期
	private String processName;//流程名字
	private String processCode;//流程名字
	private String currentCustomState;//当前实例的状态
	private ProcessState processState; //流程运行中的状态
	private Date submitTime;//提交时间
	private Date endTime;//流程结束时间
	private Long dataId;//对应数据ID
	private Long formId;//表单Id
	private String formName;//表单名字
	private String currentActivity;//当前环节
	private String currentActivityTitle;//当前环节标题
	private String instanceTitle;//当前实例标题
	private Long typeId;//所属流程类型id
	private Long systemId; //系统ID
	private Boolean sharedForm;//是否共享表单，当是子流程时这个字段才有效 true为是，false为否
	private String formUrl;//查看表单的url
	private Long firstTaskId;//第一个环节的id。新建表单时，第一次提交生成的任务的id
	private Integer priority;//紧急程度
	private Integer totalStep;//总步数，自由流中有用
	private Integer currentStep;//当前步数，自由流中有用
	private String emergencyUrl;//应急url
	private String reminderStyle;//催办方式
	private Long duedate;//开始催办时限
	private Long repeat;//催办间隔时间
	private Date lastReminderTime;//上次催办时间
	private Integer reminderLimitTimes;//催办次数上限  0表示一直催办
	private Integer alreadyReminderTimes;//已催办次数
	private String reminderNoticeStyle;//催办次数达到上限后，通知相关人员的方式
	private String reminderNoticeUserCondition;//催办通知用户 的条件
	private String customType;//自定义类别
	private String previousActivity;//上一环节名称
	private String previousActivityTitle;//上一环节标题
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getWorkflowDefinitionId() {
		return workflowDefinitionId;
	}
	public void setWorkflowDefinitionId(Long workflowDefinitionId) {
		this.workflowDefinitionId = workflowDefinitionId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getParentProcessId() {
		return parentProcessId;
	}
	public void setParentProcessId(String parentProcessId) {
		this.parentProcessId = parentProcessId;
	}
	public String getParentProcessTacheName() {
		return parentProcessTacheName;
	}
	public void setParentProcessTacheName(String parentProcessTacheName) {
		this.parentProcessTacheName = parentProcessTacheName;
	}
	public String getParentExcutionId() {
		return parentExcutionId;
	}
	public void setParentExcutionId(String parentExcutionId) {
		this.parentExcutionId = parentExcutionId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getCurrentCustomState() {
		return currentCustomState;
	}
	public void setCurrentCustomState(String currentCustomState) {
		this.currentCustomState = currentCustomState;
	}
	public ProcessState getProcessState() {
		return processState;
	}
	public void setProcessState(ProcessState processState) {
		this.processState = processState;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getCurrentActivity() {
		return currentActivity;
	}
	public void setCurrentActivity(String currentActivity) {
		this.currentActivity = currentActivity;
	}
	public String getCurrentActivityTitle() {
		return currentActivityTitle;
	}
	public void setCurrentActivityTitle(String currentActivityTitle) {
		this.currentActivityTitle = currentActivityTitle;
	}
	public String getInstanceTitle() {
		return instanceTitle;
	}
	public void setInstanceTitle(String instanceTitle) {
		this.instanceTitle = instanceTitle;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public Long getSystemId() {
		return systemId;
	}
	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}
	public Boolean getSharedForm() {
		return sharedForm;
	}
	public void setSharedForm(Boolean sharedForm) {
		this.sharedForm = sharedForm;
	}
	public String getFormUrl() {
		return formUrl;
	}
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	public Long getFirstTaskId() {
		return firstTaskId;
	}
	public void setFirstTaskId(Long firstTaskId) {
		this.firstTaskId = firstTaskId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getTotalStep() {
		return totalStep;
	}
	public void setTotalStep(Integer totalStep) {
		this.totalStep = totalStep;
	}
	public Integer getCurrentStep() {
		return currentStep;
	}
	public void setCurrentStep(Integer currentStep) {
		this.currentStep = currentStep;
	}
	public String getEmergencyUrl() {
		return emergencyUrl;
	}
	public void setEmergencyUrl(String emergencyUrl) {
		this.emergencyUrl = emergencyUrl;
	}
	public String getReminderStyle() {
		return reminderStyle;
	}
	public void setReminderStyle(String reminderStyle) {
		this.reminderStyle = reminderStyle;
	}
	public Long getDuedate() {
		return duedate;
	}
	public void setDuedate(Long duedate) {
		this.duedate = duedate;
	}
	public Long getRepeat() {
		return repeat;
	}
	public void setRepeat(Long repeat) {
		this.repeat = repeat;
	}
	public Date getLastReminderTime() {
		return lastReminderTime;
	}
	public void setLastReminderTime(Date lastReminderTime) {
		this.lastReminderTime = lastReminderTime;
	}
	public Integer getReminderLimitTimes() {
		return reminderLimitTimes;
	}
	public void setReminderLimitTimes(Integer reminderLimitTimes) {
		this.reminderLimitTimes = reminderLimitTimes;
	}
	public Integer getAlreadyReminderTimes() {
		return alreadyReminderTimes;
	}
	public void setAlreadyReminderTimes(Integer alreadyReminderTimes) {
		this.alreadyReminderTimes = alreadyReminderTimes;
	}
	public String getReminderNoticeStyle() {
		return reminderNoticeStyle;
	}
	public void setReminderNoticeStyle(String reminderNoticeStyle) {
		this.reminderNoticeStyle = reminderNoticeStyle;
	}
	public String getReminderNoticeUserCondition() {
		return reminderNoticeUserCondition;
	}
	public void setReminderNoticeUserCondition(String reminderNoticeUserCondition) {
		this.reminderNoticeUserCondition = reminderNoticeUserCondition;
	}
	public String getCustomType() {
		return customType;
	}
	public void setCustomType(String customType) {
		this.customType = customType;
	}
	public String getPreviousActivity() {
		return previousActivity;
	}
	public void setPreviousActivity(String previousActivity) {
		this.previousActivity = previousActivity;
	}
	public String getPreviousActivityTitle() {
		return previousActivityTitle;
	}
	public void setPreviousActivityTitle(String previousActivityTitle) {
		this.previousActivityTitle = previousActivityTitle;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Long getSubCompanyId() {
		return subCompanyId;
	}
	public void setSubCompanyId(Long subCompanyId) {
		this.subCompanyId = subCompanyId;
	}
	public String getSubCompanyName() {
		return subCompanyName;
	}
	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
}
