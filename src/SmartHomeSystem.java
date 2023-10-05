import java.util.ArrayList;
import java.util.List;

// Define the Device interface
interface Device {
    int getId();
    String getType();
    void turnOn();
    void turnOff();
    boolean getStatus();
}

// Concrete Light class implementing Device
class Light implements Device {
    private int id;
    private String type;
    private boolean status;

    public Light(int id) {
        this.id = id;
        this.type = "light";
        this.status = false;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void turnOn() {
        status = true;
    }

    public void turnOff() {
        status = false;
    }

    public boolean getStatus() {
        return status;
    }
}

// Concrete Thermostat class implementing Device
class Thermostat implements Device {
    private int id;
    private String type;
    private int temperature;

    public Thermostat(int id) {
        this.id = id;
        this.type = "thermostat";
        this.temperature = 70;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public void turnOn() {
        // Thermostat doesn't have a power button, so it's a no-op
    }

    public void turnOff() {
        // Thermostat doesn't have a power button, so it's a no-op
    }

    public boolean getStatus() {
        // Thermostat doesn't have an on/off state
        return true;
    }
}

// Concrete DoorLock class implementing Device
class DoorLock implements Device {
    private int id;
    private String type;
    private boolean locked;

    public DoorLock(int id) {
        this.id = id;
        this.type = "door";
        this.locked = true;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    public void turnOn() {
        // DoorLock doesn't have a power button, so it's a no-op
    }

    public void turnOff() {
        // DoorLock doesn't have a power button, so it's a no-op
    }

    public boolean getStatus() {
        // DoorLock doesn't have an on/off state
        return true;
    }
}

// Define a Scheduler to manage scheduled tasks
class Scheduler {
    private List<ScheduledTask> tasks;

    public Scheduler() {
        tasks = new ArrayList<>();
    }

    public void addScheduledTask(ScheduledTask task) {
        tasks.add(task);
    }

    public void removeScheduledTask(ScheduledTask task) {
        tasks.remove(task);
    }

    public void executeScheduledTasks() {
        for (ScheduledTask task : tasks) {
            task.execute();
        }
    }
}

// Define a ScheduledTask class
class ScheduledTask {
    private Device device;
    private String time;
    private String command;

    public ScheduledTask(Device device, String time, String command) {
        this.device = device;
        this.time = time;
        this.command = command;
    }

    public void execute() {
        // Implement task execution logic here
        System.out.println("Executing Task: " + command + " for " + device.getType() + " " + device.getId());
    }
}

// Define a Trigger class for automation
class Trigger {
    private String condition;
    private String action;

    public Trigger(String condition, String action) {
        this.condition = condition;
        this.action = action;
    }

    public String getCondition() {
        return condition;
    }

    public String getAction() {
        return action;
    }
}

// Define a SmartHomeHub class to manage devices, triggers, and schedules
class SmartHomeHub {
    private List<Device> devices;
    private List<Trigger> triggers;
    private Scheduler scheduler;

    public SmartHomeHub() {
        devices = new ArrayList<>();
        triggers = new ArrayList<>();
        scheduler = new Scheduler();
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void removeDevice(Device device) {
        devices.remove(device);
    }

    public void turnOnDevice(int deviceId) {
        for (Device device : devices) {
            if (device.getId() == deviceId) {
                device.turnOn();
            }
        }
    }

    public void turnOffDevice(int deviceId) {
        for (Device device : devices) {
            if (device.getId() == deviceId) {
                device.turnOff();
            }
        }
    }

    public void getStatusReport() {
        for (Device device : devices) {
            System.out.println(device.getType() + " " + device.getId() + " is " + (device.getStatus() ? "On" : "Off"));
        }
    }

    public void addTrigger(String condition, String action) {
        Trigger trigger = new Trigger(condition, action);
        triggers.add(trigger);
    }

    public void setSchedule(int deviceId, String time, String command) {
        for (Device device : devices) {
            if (device.getId() == deviceId) {
                ScheduledTask task = new ScheduledTask(device, time, command);
                scheduler.addScheduledTask(task);
            }
        }
    }

    public void executeScheduledTasks() {
        scheduler.executeScheduledTasks();
    }

    public void automateBasedOnTriggers() {
        for (Trigger trigger : triggers) {
            // Implement trigger evaluation and action execution logic here
            System.out.println("Trigger: " + trigger.getCondition() + " -> Action: " + trigger.getAction());
        }
    }
}

public class SmartHomeSystem {
    public static void main(String[] args) {
        SmartHomeHub hub = new SmartHomeHub();

        Device light1 = new Light(1);
        Device thermostat = new Thermostat(2);
        Device doorLock = new DoorLock(3);

        hub.addDevice(light1);
        hub.addDevice(thermostat);
        hub.addDevice(doorLock);

        hub.getStatusReport();

        // Turn on the light
        hub.turnOnDevice(1);
        hub.getStatusReport();

        // Set a schedule to turn off the light at 8:00 PM
        hub.setSchedule(1, "20:00", "turnOffDevice(1)");

        // Add a trigger to turn off the light when the thermostat reaches 75 degrees
        hub.addTrigger("thermostat.getTemperature() > 75", "turnOffDevice(1)");

        // Execute scheduled tasks
        hub.executeScheduledTasks();

        // Automate based on triggers
        hub.automateBasedOnTriggers();
    }
}

