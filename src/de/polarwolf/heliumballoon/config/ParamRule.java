package de.polarwolf.heliumballoon.config;

public enum ParamRule {

	IS_DEFAULT ("isDefault"),
	HIGH_ABOVE_PLAYER ("highAbovePlayer"),
	DISTANCE_FROM_PLAYER ("distanceFromPlayer"),
	ANGLE_FROM_PLAYER_DIRECTION ("angleFromPlayerDirection"),
	NORMAL_SPEED ("normalSpeed"),
	SWITCH_TO_FAST_SPEED_AT_DISTANCE ("switchToFastSpeedAtDistance"),
	ENABLE_RISING_Y_WORKAROUND("enableRisingYWorkaround");
		
	private final String attributeName;
	

	private ParamRule(String attributeName) {
		this.attributeName = attributeName;
	}


	public String getAttributeName() {
		return attributeName;
	}

}