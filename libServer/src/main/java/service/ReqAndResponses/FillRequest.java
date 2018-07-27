package service.ReqAndResponses;
/**
 * Object that can hold information needed to make a fill request.
 */
public class FillRequest {
    String UserName;
    int NumGenerations;

    public FillRequest(String userName, int numGenerations) {
        UserName = userName;
        NumGenerations = numGenerations;
    }

    public String getUserName() {
        return UserName;
    }

    public int getNumGenerations() {
        return NumGenerations;
    }
}
