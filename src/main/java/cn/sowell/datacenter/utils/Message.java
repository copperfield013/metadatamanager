package cn.sowell.datacenter.utils;

import cn.sowell.copframe.dto.ajax.NoticeType;

/**
 * 消息数据
 * @author so-well
 *
 */
public class Message {
	private NoticeType noticeType;
	private String message;
	public NoticeType getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
