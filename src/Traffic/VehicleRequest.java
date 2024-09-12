package Traffic;

public class VehicleRequest {
    private int vehicleId;
    private long arrivalTime;
    private String direction;

    public VehicleRequest(int vehicleId, long arrivalTime, String direction) {
        this.vehicleId = vehicleId;
        this.arrivalTime = arrivalTime;
        this.direction = direction;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


}

