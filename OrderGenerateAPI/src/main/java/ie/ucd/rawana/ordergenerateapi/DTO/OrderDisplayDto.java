package ie.ucd.rawana.ordergenerateapi.DTO;

public class OrderDisplayDto {

    private long orderId;

    private String pickingState;

    private String packingState;

    private String shippingState;


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getPickingState() {
        return pickingState;
    }

    public void setPickingState(String pickingState) {
        this.pickingState = pickingState;
    }

    public String getPackingState() {
        return packingState;
    }

    public void setPackingState(String packingState) {
        this.packingState = packingState;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }



}
