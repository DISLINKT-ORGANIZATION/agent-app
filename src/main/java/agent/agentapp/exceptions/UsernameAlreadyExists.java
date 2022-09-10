package agent.agentapp.exceptions;

public class UsernameAlreadyExists extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public UsernameAlreadyExists(String message) {
        super(message);
    }
}