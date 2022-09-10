package agent.agentapp.exceptions;

public class EmailAlreadyExists extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public EmailAlreadyExists(String message) {
        super(message);
    }
}