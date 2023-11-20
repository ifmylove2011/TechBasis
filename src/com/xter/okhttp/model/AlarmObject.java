package com.xter.okhttp.model;

import java.util.List;

/**
 * msgType为2，3，12，21
 */
public class AlarmObject {

	public String alarmDesc;
	public String alarmId;
	public Integer alarmTime;
	public Integer alarmType;
	public String careObjectId;
	public String careObjectName;
	public ContactsDTO contacts;
	public String deviceId;
	public String deviceMac;
	public Integer deviceType;
	public Integer duration;
	public Integer gpsIsDetail;
	public Integer isOutdoor;
	public Integer isOwnArea;
	public String lat;
	public String lgt;
	public String phoneNum;
	public String positionDesc;
	public String reportPositionDesc;
	public SipAccountDTO sipAccount;
	public Integer startTime;
	public Integer status;
	public String telephone;
	public String emergencyPhone;
	public String lastTrack;
	public Integer lastTrackTime;
	public String careObjectPhoto;
	public Integer deviceStatus;
	public Integer deviceStatusDuration;
	public String managerUsername;
	public Integer power;
	public Integer heartRate;
	public Integer systolicPressure;
	public Integer diastolicPressure;
	public Integer bloodOxygen;
	public List<PositionStatusDTO> positionStatus;
	public Integer heartRateIsShow;
	public Integer bloodPressureIsShow;
	public Integer bloodOxygenIsShow;
	public Integer isAutomatic;
	public DisposeModeDTO disposeMode;
	public Integer deviceIterationNumber;
	public Double bodyTemperature;
	public Integer bodyTemperatureIsShow;
	public Long devicePhone;
	public Integer coordsys;

	public static class ContactsDTO {
		public String phoneNum;
		public String relationDesc;
	}

	public static class SipAccountDTO {
		public String sipAccount;
		public String sipAccountType;
		public String sipIp;
		public String sipPassword;
		public String sipPort;
	}

	public static class DisposeModeDTO {
		public Integer alarmMode;
		public Integer setTime;
		public Integer recoveryTime;
		public String alarmSetName;
		public Integer alarmSetType;
		public String relationDesc;
	}

	public static class PositionStatusDTO {
		public String bandId;
		public String careObjectId;
		public Integer duration;
		public Integer positionStatus;
		public String recordId;
		public Integer recordTime;
	}
}


