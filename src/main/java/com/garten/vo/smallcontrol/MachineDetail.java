package com.garten.vo.smallcontrol;

public class MachineDetail {

	private String gartenName;
	private Integer gartenId;
	private Integer type;
	private String macId;
	private Integer machineId;
	private String partnerId;
	private String partnerSecret;

	
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public Integer getMachineId() {
		return machineId;
	}
	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}
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
	public MachineDetail() {
		super();
	}
	public MachineDetail(String gartenName, Integer gartenId, Integer type, String macId, Integer machineId,
			String partnerId, String partnerSecret) {
		super();
		this.gartenName = gartenName;
		this.gartenId = gartenId;
		this.type = type;
		this.macId = macId;
		this.machineId = machineId;
		this.partnerId = partnerId;
		this.partnerSecret = partnerSecret;
	}
	public MachineDetail(String gartenName, Integer type, String macId, Integer machineId, String partnerId,
			String partnerSecret) {
		super();
		this.gartenName = gartenName;
		this.type = type;
		this.macId = macId;
		this.machineId = machineId;
		this.partnerId = partnerId;
		this.partnerSecret = partnerSecret;
	}
	
}
