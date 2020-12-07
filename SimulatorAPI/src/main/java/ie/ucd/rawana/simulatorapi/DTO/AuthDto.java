package ie.ucd.rawana.simulatorapi.DTO;

public class AuthDto {

    private boolean valid;

    private String jobType;

    public AuthDto() {
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}
