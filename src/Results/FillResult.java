package Results;

/**
 * result for filling database for a specific name
 */

public class FillResult extends Result {
    private String username;
    private String message;

    public FillResult(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
