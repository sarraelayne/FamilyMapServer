package Results;

/**
 * returns message on success of load
 */
public class LoadResult extends Result {
    private String message;

    public LoadResult(String message) {
        this.message = message;
    }
}
