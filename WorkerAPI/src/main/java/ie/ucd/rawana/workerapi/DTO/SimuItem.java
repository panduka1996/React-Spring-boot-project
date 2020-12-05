package ie.ucd.rawana.workerapi.DTO;

public class SimuItem {

	private String itemId;

	private String checked;

	private String count;

	public SimuItem() {
		
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
