package ie.ucd.rawana.simulatorapi.DTO;

import java.util.HashSet;
import java.util.Set;

public class ResetConfig {

    private String aisles;

    private String sections;

    private String shelves;

    private String[] packagingAreas;

    Set<WorkerDto> workers = new HashSet<>();

    Set<ItemDto> items = new HashSet<>();

    public ResetConfig() {
    }

    public String getAisles() {
        return aisles;
    }

    public void setAisles(String aisles) {
        this.aisles = aisles;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public String getShelves() {
        return shelves;
    }

    public void setShelves(String shelves) {
        this.shelves = shelves;
    }

    public String[] getPackingAreas() {
        return packagingAreas;
    }

    public void setPackingAreas(String[] packingAreas) {
        this.packagingAreas = packingAreas;
    }

    public Set<WorkerDto> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<WorkerDto> workers) {
        this.workers = workers;
    }

    public Set<ItemDto> getItems() {
        return items;
    }

    public void setItems(Set<ItemDto> items) {
        this.items = items;
    }
}
