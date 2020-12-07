package ie.ucd.rawana.simulatorapi.DTO;

public class SourceDto {

    private String souLocation;

    public SourceDto() {
    }

    public SourceDto(String souLocation) {
        this.souLocation = souLocation;
    }

    public String getSouLocation() {
        return souLocation;
    }

    public void setSouLocation(String souLocation) {
        this.souLocation = souLocation;
    }
}
