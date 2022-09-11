package agent.agentapp.exceptions;

public class ServerException extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public ServerException(String message) {
        super(message);
    }
}