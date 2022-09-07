package agent.agentapp.exceptions;

public class InvalidUsername extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public InvalidUsername(String message) {
        super(message);
    }
}