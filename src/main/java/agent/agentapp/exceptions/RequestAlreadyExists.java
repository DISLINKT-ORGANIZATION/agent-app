package agent.agentapp.exceptions;

public class RequestAlreadyExists extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public RequestAlreadyExists(String message) {
        super(message);
    }
}