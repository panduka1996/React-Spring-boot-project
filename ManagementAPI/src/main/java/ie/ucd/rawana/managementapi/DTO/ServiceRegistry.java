package ie.ucd.rawana.managementapi.DTO;

public class ServiceRegistry {

    private String failed;

    private String name;

    private String uri;

    public ServiceRegistry() {
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFailed() {
        return failed;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
