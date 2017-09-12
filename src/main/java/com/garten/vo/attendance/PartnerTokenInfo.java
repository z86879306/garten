package com.garten.vo.attendance;

public class PartnerTokenInfo {
	
	
	private String partnerId;  // 合作伙伴ID
	private String partnerToken;  //身份令牌
	private Long tokenTime;  //生成时间
	private Long tokenExpireTime;    //失效时间
	
	
	public PartnerTokenInfo(String partnerId, String partnerToken, Long tokenTime, Long tokenExpireTime) {
		super();
		this.partnerId = partnerId;
		this.partnerToken = partnerToken;
		this.tokenTime = tokenTime;
		this.tokenExpireTime = tokenExpireTime;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerToken() {
		return partnerToken;
	}
	public void setPartnerToken(String partnerToken) {
		this.partnerToken = partnerToken;
	}
	public Long getTokenTime() {
		return tokenTime;
	}
	public void setTokenTime(Long tokenTime) {
		this.tokenTime = tokenTime;
	}
	public Long getTokenExpireTime() {
		return tokenExpireTime;
	}
	public void setTokenExpireTime(Long tokenExpireTime) {
		this.tokenExpireTime = tokenExpireTime;
	}
	
	
	
	
	
	
}
