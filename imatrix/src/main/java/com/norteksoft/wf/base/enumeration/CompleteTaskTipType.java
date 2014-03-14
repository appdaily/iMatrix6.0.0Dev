package com.norteksoft.wf.base.enumeration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.norteksoft.product.api.ApiFactory;
import com.norteksoft.product.api.entity.User;

/**
 * 完成任务后的提示类型
 * @author wurong
 *
 */
public enum CompleteTaskTipType {
	/**
	 * 任务完成
	 */
	OK,
	
	/**
	 * 流程定义返回url
	 */
	RETURN_URL,
	
	/**
	 * 选择具体办理人
	 */
	SINGLE_TRANSACTOR_CHOICE,	
	
	/**
	 * 选择环节url
	 */
	TACHE_CHOICE_URL,
	/**
	 * 指派
	 */
	ASSIGN_TASK,
	/**
	 * 提示信息
	 */
	MESSAGE;

	private String content;
	private Map<String,String>  canChoiceTaches = new HashMap<String,String>();
	private Collection<String> canChoiceTransactor = new ArrayList<String>();
	private Collection<Long> canChoiceTransactorId = new ArrayList<Long>();

	/**
	 * 返回的内容
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * 获得可供选择的环节集合
	 * @return 流向名为key，环节名为value
	 */
	public Map<String, String> getCanChoiceTaches() {
		return canChoiceTaches;
	}

	/**
	 * 设置可供选择的环节
	 * @param canChoiceTaches 可供选择的环节
	 * @return 调用者
	 */
	public CompleteTaskTipType setCanChoiceTaches(Map<String, String> canChoiceTaches) {
		this.canChoiceTaches = canChoiceTaches;
		return this;
	}

	/**
	 * 获得可供选择的办理人
	 * @return 可供选择的办理人登录名集合
	 */
	public Collection<String> getCanChoiceTransactor() {
		if(canChoiceTransactor.size()>0) return canChoiceTransactor;
		for(Long transactorId:canChoiceTransactorId){
			User user = ApiFactory.getAcsService().getUserById(transactorId);
			canChoiceTransactor.add(user.getLoginName());
		}
		return canChoiceTransactor;
	}
	/**
	 * 获得可供选择的办理人
	 * @return 可供选择的办理人登录名集合
	 */
	public Collection<Long> getCanChoiceTransactorId() {
		if(canChoiceTransactorId.size()>0) return canChoiceTransactorId;
		for(String transactor:canChoiceTransactor){
			User user = ApiFactory.getAcsService().getUserByLoginName(transactor);
			canChoiceTransactorId.add(user.getId());
		}
		return canChoiceTransactorId;
	}

	/**
	 * 设置可供选择的办理人
	 * @param canChoiceTransactor 办理人登录名集合
	 * @return 调用者
	 */
	public CompleteTaskTipType setCanChoiceTransactor(Collection<String> canChoiceTransactor) {
		this.canChoiceTransactor = canChoiceTransactor;
		return this;
	}
	/**
	 * 设置可供选择的办理人
	 * @param canChoiceTransactorId 办理人id集合
	 * @return 调用者
	 */
	public CompleteTaskTipType setCanChoiceTransactorId(Collection<Long> canChoiceTransactorId) {
		this.canChoiceTransactorId = canChoiceTransactorId;
		return this;
	}

	/**
	 * 设置内容
	 * @param content 内容
	 * @return 当前对象
	 */
	public CompleteTaskTipType setContent(String content) {
		this.content = content;
		return this;
	}
	
}
