package ie.ucd.rawana.simulatorapi.DTO;

public class DestinationDto {

    private String desLocation;

    public DestinationDto() {
    }

    public DestinationDto(String desLocation) {
        this.desLocation = desLocation;
    }

    public String getDesLocation() {
        return desLocation;
    }

    public void setDesLocation(String desLocation) {
        this.desLocation = desLocation;
    }
}
