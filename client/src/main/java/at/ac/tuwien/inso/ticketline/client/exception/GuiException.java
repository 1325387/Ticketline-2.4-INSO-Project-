package at.ac.tuwien.inso.ticketline.client.exception;

/**
 * Exception handler for gui layer.
 *
 * @author Aysel Oeztuerk 0927011.
 */
public class GuiException extends Exception {

    private static final long serialVersionUID = 3411131267454855885L;

    /**
     * Instantiates a new gui exception.
     */
    public GuiException() {
        super();
    }

    /**
     * Instantiates a new gui exception.
     *
     * @param message the message
     */
    public GuiException(String message) {
        super(message);
    }

    /**
     * Instantiates a new gui exception.
     *
     * @param throwable the throwable
     */
    public GuiException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new gui exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public GuiException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
