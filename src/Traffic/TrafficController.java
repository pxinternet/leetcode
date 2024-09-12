package Traffic;

import java.util.LinkedList;
import java.util.Queue;

public class TrafficController {

    private Queue<VehicleRequest> leftQueue;
    private Queue<VehicleRequest> rightQueue;

    private String currentDirection;
    private int maxVehiclesPerPass = 5;
    private long maxWaitTime = 5000;

    public TrafficController() {
        leftQueue = new LinkedList<>();
        rightQueue = new LinkedList<>();
        currentDirection = "left";
    }

    public void processVehicleRequest(VehicleRequest request) {
        if (request.getDirection().equals("left")) {
            leftQueue.offer(request);
        } else {
            rightQueue.offer(request);
        }
    }

    public void switchDirection() {
        if (currentDirection.equals("left")) {
            currentDirection = "right";
        } else {
            currentDirection = "left";
        }
    }

    public void allowTraffic() {

        int vehicleCount = 0;
        long startTime = System.currentTimeMillis();

        Queue<VehicleRequest> currentQueue = currentDirection.equals("left") ? leftQueue : rightQueue;

        while (!currentQueue.isEmpty() && vehicleCount < maxVehiclesPerPass
                && (System.currentTimeMillis() - startTime) < maxWaitTime) {

            VehicleRequest request = currentQueue.poll();
            vehicleCount++;

        }

        switchDirection();
    }

}
