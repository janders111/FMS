package service.ReqAndResponses;
/*
This class is used for any service class that responds with a message.
 */
public class ResponseMessage {
    boolean error;
    String message;

    /**
     *
     * @param error Since this is a general class to hold a message, this can tell if the message returned indicates an error.
     * @param message
     */
    public ResponseMessage(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
