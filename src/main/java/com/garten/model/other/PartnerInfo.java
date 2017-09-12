package com.garten.model.other;

public class PartnerInfo {

	private String partnerId;
	private String partnerSecret;
	private String partnerToken;
	private Long tokenTime;
	private Long tokenExpireTime;
	private Integer gartenId;
	private Integer machineId;
	
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerSecret() {
		return partnerSecret;
	}
	public void setPartnerSecret(String partnerSecret) {
		this.partnerSecret = partnerSecret;
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
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getMachineId() {
		return machineId;
	}
	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}
	public PartnerInfo(String partnerId, String partnerSecret, String partnerToken, Long tokenTime,
			Long tokenExpireTime, Integer gartenId, Integer machineId) {
		super();
		this.partnerId = partnerId;
		this.partnerSecret = partnerSecret;
		this.partnerToken = partnerToken;
		this.tokenTime = tokenTime;
		this.tokenExpireTime = tokenExpireTime;
		this.gartenId = gartenId;
		this.machineId = machineId;
	}
	public PartnerInfo() {
		super();
	}
	
	
	
}
