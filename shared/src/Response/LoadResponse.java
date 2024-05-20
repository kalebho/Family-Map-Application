package Response;

/**
 * Response sent to client from server to clear the database and replace it with users' data
 */
public class LoadResponse {
    /**
     * Create LoadResponse and initialize its data members
     * @param message error message
     * @param success success status
     */
    public LoadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * error message
     */
    private String message;

    /**
     * success status
     */
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
