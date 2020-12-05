package ie.ucd.rawana.managementapi.DTO;

public class PickerAccuracyDto {

	private String pickerId;
	
	private String completedOrders;
	
	private String RunningOrders;

	public PickerAccuracyDto() {
	
	}

	public String getPickerId() {
		return pickerId;
	}

	public void setPickerId(String pickerId) {
		this.pickerId = pickerId;
	}

	public String getCompletedOrders() {
		return completedOrders;
	}

	public void setCompletedOrders(String completedOrders) {
		this.completedOrders = completedOrders;
	}

	public String getRunningOrders() {
		return RunningOrders;
	}

	public void setRunningOrders(String runningOrders) {
		RunningOrders = runningOrders;
	}

	

}
