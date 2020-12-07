package ie.ucd.rawana.simulatorapi.DTO;

import java.util.HashSet;
import java.util.Set;

public class WorkerDetail {

    private String name;

    private String location;

    private String capacity;

    private Set<ActionDto> actions = new HashSet<>();

    private String nextAction;

    private float weight;

    private String notificationUri;

    private Set<ItemDto> holding = new HashSet<>();

    public WorkerDetail() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Set<ActionDto> getActions() {
        return actions;
    }

    public void setActions(Set<ActionDto> actions) {
        this.actions = actions;
    }

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getNotificationUri() {
        return notificationUri;
    }

    public void setNotificationUri(String notificationUri) {
        this.notificationUri = notificationUri;
    }

    public Set<ItemDto> getHolding() {
        return holding;
    }

    public void setHolding(Set<ItemDto> holding) {
        this.holding = holding;
    }

}
