package agent.agentapp.exceptions;

public class UsernameOrEmailAlreadyExists extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public UsernameOrEmailAlreadyExists(String message) {
        super(message);
    }
}