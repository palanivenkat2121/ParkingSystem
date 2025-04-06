import java.util.*;
class Vehicle {
    private String vehicleNumber;
    private String size;
    public Vehicle(String vehicleNumber, String size) {
        this.vehicleNumber = vehicleNumber;
        this.size = size.toUpperCase();
    }
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    public String getSize() {
        return size;
    }
    @Override
    public String toString() {
        return "VehicleNumber: " + vehicleNumber + ", Size: " + size;
    }
}
class ParkingLot {
    private int num2WheelSlots;
    private int num4WheelSlots;
    private int numHeavyVehicleSlots;
    private int available2WheelSlots;
    private int available4WheelSlots;
    private int availableHeavyVehicleSlots;
    private Map<Double, Vehicle> occupiedSlots;
    private Map<Double, Integer> billMap;
    public ParkingLot(int num2WheelSlots, int num4WheelSlots, int numHeavyVehicleSlots) {
        this.num2WheelSlots = num2WheelSlots;
        this.num4WheelSlots = num4WheelSlots;
        this.numHeavyVehicleSlots = numHeavyVehicleSlots;
        this.available2WheelSlots = num2WheelSlots;
        this.available4WheelSlots = num4WheelSlots;
        this.availableHeavyVehicleSlots = numHeavyVehicleSlots;
        this.occupiedSlots = new HashMap<>();
        this.billMap = new HashMap<>();
    }
    public double parkVehicle(Vehicle vehicle) {
        double ticketnumber = -1;
        int bill = 0;
        switch (vehicle.getSize()) {
            case "2W":
                if (available2WheelSlots > 0) {
                    ticketnumber = getNextAvailableSlot();
                    available2WheelSlots--;
                    bill = 20;
                }
                break;
            case "4W":
                if (available4WheelSlots > 0) {
                    ticketnumber = getNextAvailableSlot();
                    available4WheelSlots--;
                    bill = 40;
                }
                break;
            case "HV":
                if (availableHeavyVehicleSlots > 0) {
                    ticketnumber = getNextAvailableSlot();
                    availableHeavyVehicleSlots--;
                    bill = 60;
                }
                break;
            default:
                System.out.println("Invalid vehicle type.");
        }
        if (ticketnumber != -1) {
            occupiedSlots.put(ticketnumber, vehicle);
            billMap.put(ticketnumber, bill);
        } else {
            System.out.println("No slot available for this type.");
        }
        return ticketnumber;
    }
    private double getNextAvailableSlot() {
        double ticketnumber = Math.round(Math.random() * 1000000) / 100.0;
        while (occupiedSlots.containsKey(ticketnumber)) {
            ticketnumber++;
        }
        return ticketnumber;
    }
    public Vehicle unparkVehicle(double ticketnumber) {
        Vehicle vehicle = null;
        if (occupiedSlots.containsKey(ticketnumber)) {
            vehicle = occupiedSlots.remove(ticketnumber);
            billMap.remove(ticketnumber);
            switch (vehicle.getSize()) {
                case "2W": available2WheelSlots++; break;
                case "4W": available4WheelSlots++; break;
                case "HV": availableHeavyVehicleSlots++; break;
            }
        } else {
            System.out.println("Invalid ticket number.");
        }
        return vehicle;
    }
    public void currentOccupancyDetails() {
        System.out.println("\n--- Current Occupancy ---");
        System.out.println("2-Wheeler: Free = " + available2WheelSlots + ", Occupied = " + (num2WheelSlots - available2WheelSlots));
        System.out.println("4-Wheeler: Free = " + available4WheelSlots + ", Occupied = " + (num4WheelSlots - available4WheelSlots));
        System.out.println("Heavy Vehicle: Free = " + availableHeavyVehicleSlots + ", Occupied = " + (numHeavyVehicleSlots - availableHeavyVehicleSlots));
    }
    public void ticketDetails() {
        System.out.println("\n--- Ticket Details with Bills ---");
        for (Map.Entry<Double, Vehicle> entry : occupiedSlots.entrySet()) {
            double ticket = entry.getKey();
            Vehicle v = entry.getValue();
            int bill = billMap.getOrDefault(ticket, 0);
            System.out.println("Ticket Number: " + ticket + " | Vehicle: " + v + " | Bill: ₹" + bill);
        }
    }
}
public class ParkingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLot lot = new ParkingLot(2, 2, 1);
        while (true) {
            System.out.println("\n---- Parking System Menu ----");
            System.out.println("1. Park a Vehicle");
            System.out.println("2. Unpark a Vehicle");
            System.out.println("3. Show Occupancy");
            System.out.println("4. Show Ticket & Bill Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            switch (choice) {
                case 1:
                    System.out.print("Enter vehicle number: ");
                    String number = scanner.nextLine();
                    System.out.print("Enter vehicle type (2W/4W/HV): ");
                    String type = scanner.nextLine();
                    Vehicle v = new Vehicle(number, type);
                    double ticket = lot.parkVehicle(v);
                    if (ticket != -1) {
                        System.out.println("Vehicle parked. Ticket Number: " + ticket);
                    }
                    break;
                case 2:
                    System.out.print("Enter ticket number to unpark: ");
                    try {
                        double tno = Double.parseDouble(scanner.nextLine());
                        Vehicle removed = lot.unparkVehicle(tno);
                        if (removed != null) {
                            System.out.println("Unparked Vehicle: " + removed);
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid ticket number format.");
                    }
                    break;
                case 3:
                    lot.currentOccupancyDetails();
                    break;
                case 4:
                    lot.ticketDetails();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}