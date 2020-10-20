package com.rheal.security.idm.property.config;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class PasswordSettingManagement {
	
    private boolean enableHistory;
    private int historyNum;

    
	public boolean isEnableHistory() {
		return enableHistory;
	}
	public void setEnableHistory(boolean enableHistory) {
		this.enableHistory = enableHistory;
	}
	public int getHistoryNum() {
		return historyNum;
	}
	public void setHistoryNum(int historyNum) {
		this.historyNum = historyNum;
	}
}