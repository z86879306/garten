package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

public class AgentInfo {
	
	private Integer agentId;
	private String phoneNumber;
	private String pwd;
	private Integer agentGrade;
	private BigDecimal agentMoney;
	private BigDecimal creditMoney;
	private Long agentStartTime;
	private Long agentEndTime;
	private Long registTime;
	private String name;
	private String agentName;
	private Integer rebate;
	private Integer frost;
	private String province;
	private String city;
	private String countries;
	private String[] cardFragment;
	private String token;
	private Long tokenTime;
	private Integer receiveType;
	private String card;
	private String cardName;
	@Override
	public String toString() {
		return "AgentInfo [agentId=" + agentId + ", phoneNumber=" + phoneNumber + ", pwd=" + pwd + ", agentGrade="
				+ agentGrade + ", agentMoney=" + agentMoney + ", creditMoney=" + creditMoney + ", agentStartTime="
				+ agentStartTime + ", agentEndTime=" + agentEndTime + ", registTime=" + registTime + ", name=" + name
				+ ", agentName=" + agentName + ", rebate=" + rebate + ", frost=" + frost + ", province=" + province
				+ ", city=" + city + ", countries=" + countries + ", cardFragment=" + Arrays.toString(cardFragment)
				+ ", token=" + token + ", tokenTime=" + tokenTime + ", receiveType=" + receiveType + ", card=" + card
				+ ", cardName=" + cardName + "]";
	}
	public AgentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AgentInfo(Integer agentId, String phoneNumber, String pwd, Integer agentGrade, BigDecimal agentMoney,
			BigDecimal creditMoney, Long agentStartTime, Long agentEndTime, Long registTime, String name,
			String agentName, Integer rebate, Integer frost, String province, String city, String countries,
			String[] cardFragment, String token, Long tokenTime, Integer receiveType, String card, String cardName) {
		super();
		this.agentId = agentId;
		this.phoneNumber = phoneNumber;
		this.pwd = pwd;
		this.agentGrade = agentGrade;
		this.agentMoney = agentMoney;
		this.creditMoney = creditMoney;
		this.agentStartTime = agentStartTime;
		this.agentEndTime = agentEndTime;
		this.registTime = registTime;
		this.name = name;
		this.agentName = agentName;
		this.rebate = rebate;
		this.frost = frost;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.cardFragment = cardFragment;
		this.token = token;
		this.tokenTime = tokenTime;
		this.receiveType = receiveType;
		this.card = card;
		this.cardName = cardName;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Integer getAgentGrade() {
		return agentGrade;
	}
	public void setAgentGrade(Integer agentGrade) {
		this.agentGrade = agentGrade;
	}
	public BigDecimal getAgentMoney() {
		return agentMoney;
	}
	public void setAgentMoney(BigDecimal agentMoney) {
		this.agentMoney = agentMoney;
	}
	public BigDecimal getCreditMoney() {
		return creditMoney;
	}
	public void setCreditMoney(BigDecimal creditMoney) {
		this.creditMoney = creditMoney;
	}
	public Long getAgentStartTime() {
		return agentStartTime;
	}
	public void setAgentStartTime(Timestamp agentStartTime) {
		this.agentStartTime = agentStartTime.getTime()/1000;
	}
	public Long getAgentEndTime() {
		return agentEndTime;
	}
	public void setAgentEndTime(Timestamp agentEndTime) {
		this.agentEndTime = agentEndTime.getTime()/1000;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Integer getRebate() {
		return rebate;
	}
	public void setRebate(Integer rebate) {
		this.rebate = rebate;
	}
	public Integer getFrost() {
		return frost;
	}
	public void setFrost(Integer frost) {
		this.frost = frost;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountries() {
		return countries;
	}
	public void setCountries(String countries) {
		this.countries = countries;
	}
	public String[] getCardFragment() {
		return cardFragment;
	}
	public void setCardFragment(String cardFragment) {
		this.cardFragment = cardFragment.split(",");
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getTokenTime() {
		return tokenTime;
	}
	public void setTokenTime(Timestamp tokenTime) {
		this.tokenTime = tokenTime.getTime()/1000;
	}
	public Integer getReceiveType() {
		return receiveType;
	}
	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
}
