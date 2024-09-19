/**
 * 
 * Author: Alan John
 * Student ID: 170236456
 * Recitation: 214.30 Sec 4
 * 
 */
import java.util.*;

/**
 * Represents an elevator request with source and destination floors and the time the request was entered.
 */
class Request {
    private int sourceFloor;
    private int destinationFloor;
    private int timeEntered;

    /**
     * Constructs a default Request with all fields initialized to zero.
     */
    public Request() {
        sourceFloor = 0;
        destinationFloor = 0;
        timeEntered = 0;
    }

    /**
     * Constructs a Request with random source and destination floors within the specified number of floors.
     * @param floors the maximum number of floors in the building
     */
    public Request(int floors) {
        sourceFloor = (int) (Math.random() * floors + 1);
        destinationFloor = (int) (Math.random() * floors + 1);
        timeEntered = 0;
    }

    // Accessors and mutators for private fields
    public int getSourceFloor() { 
    	return sourceFloor; 
    	}
    public void setSourceFloor(int sourceFloor) { 
    	this.sourceFloor = sourceFloor; 
    	}
    public int getDestinationFloor() { 
    	return destinationFloor;
    	}
    public void setDestinationFloor(int destinationFloor) { 
    	this.destinationFloor = destinationFloor; 
    	}
    public int getTimeEntered() { 
    	return timeEntered; 
    	}
    public void setTimeEntered(int time) { 
    	this.timeEntered = time; 
    	}
}

/**
 * Custom exception for handling situations when the request queue is empty.
 */
class EmptyQueueException extends Exception {
    public EmptyQueueException() {
        super("The queue is empty");
    }
}

/**
 * Custom exception for handling situations when the request queue is full.
 */
class FullQueueException extends Exception {
    public FullQueueException() {
        super("The queue is full");
    }
}

/**
 * A queue implementation specifically for managing elevator requests.
 */
class RequestQueue extends ArrayList<Request> {
    public RequestQueue() {
        super();
    }

    /**
     * Adds a request to the end of the queue.
     * @param request the Request to add
     */
    public void enqueue(Request request) {
        this.add(request);
    }

    /**
     * Removes and returns the first request in the queue.
     * @return the removed Request
     * @throws NoSuchElementException if the queue is empty
     */
    public Request dequeue() {
        if (!this.isEmpty()) {
            return this.remove(0);
        } else {
            throw new NoSuchElementException("Queue is empty");
        }
    }
}

/**
 * A utility class to determine if a new request should be generated based on a probability.
 * This was taken from the slides
 */
class BooleanSource {
    private double probability;

    /**
     * Constructs a BooleanSource with a specified probability.
     * @param p the probability that a new request arrives
     * @throws IllegalArgumentException if the probability is not between 0.0 and 1.0
     */
    public BooleanSource(double p) {
        if (p < 0.0 || p > 1.0) throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        probability = p;
    }

    /**
     * Determines whether a request arrives.
     * @return true if a request arrives, false otherwise
     */
    public boolean requestArrived() {
        return Math.random() < probability;
    }
}

/**
 * Represents an elevator with a state indicating whether it is idle, moving to a source floor, or moving to a destination floor.
 */
class Elevator {
    public static final int IDLE = 1;
    public static final int TO_SOURCE = 2;
    public static final int TO_DESTINATION = 3;
    
    int currentFloor;
    int elevatorState;
    Request request;

    /**
     * Constructs an Elevator that starts idle at floor 1.
     */
    public Elevator() {
        request = null;
        elevatorState = IDLE;
        currentFloor = 1;
    }

    // Accessor and mutator methods for private fields
    public int getCurrentFloor() { 
    	return currentFloor; 
    	}
    public void setCurrentFloor(int currentFloor) { 
    	this.currentFloor = currentFloor; 
    	}
    public int getElevatorState() { 
    	return elevatorState; 
    	}
    public void setElevatorState(int a) {
        if (!(a == IDLE || a == TO_SOURCE || a == TO_DESTINATION)) {
            throw new IllegalArgumentException("Invalid elevator state");
        }
        elevatorState = a;
    }
    public Request getRequest() { 
    	return request; 
    	}
    public void setRequest(Request request) { 
    	this.request = request; 
    	}

    /**
     * Updates the elevator's current floor based on its target destination.
     * @param currentTime The current time of the simulation
     * @param stats A list containing total wait time and request count for updating
     */
    public void shift(int currentTime, List<Integer> stats) {
        if (request == null) return;

        int targetFloor = (elevatorState == TO_SOURCE) ? request.getSourceFloor() :
                          (elevatorState == TO_DESTINATION) ? request.getDestinationFloor() : currentFloor;

        if (currentFloor == targetFloor) {
            if (elevatorState == TO_SOURCE) {
                elevatorState = TO_DESTINATION;
                stats.set(0, stats.get(0) + (currentTime - request.getTimeEntered()));
                stats.set(1, stats.get(1) + 1);
            }
        } else {
            currentFloor += (currentFloor < targetFloor) ? 1 : -1;
        }
    }
}

/**
 * Simulates an elevator system in a building.
 * This was partly based on the carSimulation from the slides
 */
class Simulator {
    /**
     * Runs the elevator simulation.
     * @param floors the number of floors in the building
     * @param elevators the number of elevators available
     * @param simulatorTime the total time to run the simulation
     * @param requestProb the probability of a request arriving each unit of time
     */
    public static void simulator(int floors, int elevators, int simulatorTime, double requestProb) {
        RequestQueue elevatorQueue = new RequestQueue();
        BooleanSource elevatorProb = new BooleanSource(requestProb);
        Elevator[] elevatorArray = new Elevator[elevators];
        List<Integer> stats = Arrays.asList(0, 0);  

        for (int i = 0; i < elevators; i++) {
            elevatorArray[i] = new Elevator();
        }

        for (int currentTime = 0; currentTime < simulatorTime; currentTime++) {
            if (elevatorProb.requestArrived()) {
                Request newRequest = new Request(floors);
                newRequest.setTimeEntered(currentTime);
                elevatorQueue.enqueue(newRequest);
                System.out.println("Step " + (currentTime + 1) + ": A request arrives from Floor " +
                                   newRequest.getSourceFloor() + " to Floor " + newRequest.getDestinationFloor());
            } else {
                System.out.println("Step " + (currentTime + 1) + ": Nothing arrives");
            }

            for (Elevator elevator : elevatorArray) {
                if (elevator.getElevatorState() == Elevator.IDLE && !elevatorQueue.isEmpty()) {
                    Request request = elevatorQueue.dequeue();
                    elevator.setRequest(request);
                    elevator.setElevatorState(Elevator.TO_SOURCE);
                }
                elevator.shift(currentTime, stats);
            }
            printStatus(elevatorQueue, elevatorArray);
        }

        double avgWaitTime = (stats.get(1) > 0) ? (double) stats.get(0) / stats.get(1) : 0.0;
        System.out.println("Average Wait Time: " + avgWaitTime + " seconds.");
        System.out.println("Total Wait Time: " + stats.get(0) + " seconds.");
        System.out.println("Requests: " + stats.get(1));
    }

    /**
     * Prints the current status of the request queue and each elevator.
     * @param queue The request queue
     * @param elevators Array of elevators
     */
    private static void printStatus(RequestQueue queue, Elevator[] elevators) {
        System.out.print("Requests: ");
        for (Request req : queue) {
            System.out.print("(" + req.getSourceFloor() + ", " + req.getDestinationFloor() + ", " + req.getTimeEntered() + ") ");
        }
        System.out.println();
        System.out.print("Elevators: ");
        for (Elevator elev : elevators) {
            System.out.print("[Floor " + elev.getCurrentFloor() + ", " +
                    (elev.getElevatorState() == Elevator.IDLE ? "IDLE" :
                            elev.getElevatorState() == Elevator.TO_SOURCE ? "TO_SOURCE" : "TO_DESTINATION") + ", " +
                    (elev.getRequest() == null ? "---" : "(" + elev.getRequest().getSourceFloor() + ", " +
                            elev.getRequest().getDestinationFloor() + ", " + elev.getRequest().getTimeEntered() + ")") + "] ");
        }
        System.out.println();
    }
}


/**
 * Main class to run the elevator simulation.
 */
public class Analyzer {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Elevator simulator!");
        System.out.println("Please enter the probability of arrival for Requests:");
        double requestProb = input.nextDouble();
        input.nextLine();
        System.out.println("Please enter the number of floors:");
        int floors = input.nextInt();
        input.nextLine();
        System.out.println("Please enter the number of elevators:");
        int elevators = input.nextInt();
        input.nextLine();
        System.out.println("Please enter the length of the simulation (in time units):");
        int time = input.nextInt();
        input.nextLine();
        try{
        	Simulator.simulator(floors, elevators, time, requestProb);
        }
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
    }
}
