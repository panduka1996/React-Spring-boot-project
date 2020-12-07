package ie.ucd.rawana.simulatorapi.DTO;

public class WorkerAction {

    private String type;

    private String arguments;

    public WorkerAction() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
}
