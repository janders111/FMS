package service.ReqAndResponses;
/**
 * Object that can hold information needed to make a fill request.
 */
public class FillRequest {
    String UserName;
    int Generation;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getGeneration() {
        return Generation;
    }

    public void setGeneration(int generation) {
        Generation = generation;
    }
}
