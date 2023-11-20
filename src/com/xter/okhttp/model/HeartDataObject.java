package com.xter.okhttp.model;

/**
 * msgType为53
 */
public class HeartDataObject {

	public String recordId;
	public String iotDeviceId;
	public String nbVersion;
	public String deviceVersion;
	public String reportTime;
	public String deviceType;
	public String positionType;
	public String position;
	public String power;
	public AddHealthDTODTO addHealthDTO;
	public AddDevicePowerDTODTO addDevicePowerDTO;
	public AddWearStatusDTODTO addWearStatusDTO;
	public AddHumitureDTODTO addHumitureDTO;
	public AddWifiDTODTO addWifiDTO;
	public AddAmbientBeaconDTODTO addAmbientBeaconDTO;
	public AddSignalQualityDTODTO addSignalQualityDTO;
	public AddPositionStatusDTODTO addPositionStatusDTO;

	public static class AddHealthDTODTO {
		public String sportStatus;
		public String measureType;
		public String measureTime;
		public String heartRate;
		public String systolicPressure;
		public String diastolicPressure;
		public String bloodOxygen;
	}

	public static class AddDevicePowerDTODTO {
		public String mac;
		public String power;
	}

	public static class AddWearStatusDTODTO {
		public String status;
		public String duration;
		public String powerStatus;
	}

	public static class AddHumitureDTODTO {
		public String recordTime;
		public String temperature;
		public String humidity;
	}

	public static class AddWifiDTODTO {
		public String recordTime;
		public String wifiInfo;
	}

	public static class AddAmbientBeaconDTODTO {
		public String recordTime;
		public String macList;
	}

	public static class AddSignalQualityDTODTO {
		public String rSSI;
		public String rSRQ;
		public String rSRP;
	}

	public static class AddPositionStatusDTODTO {
		public Integer type;
		public String status;
		public String duration;
	}
}
