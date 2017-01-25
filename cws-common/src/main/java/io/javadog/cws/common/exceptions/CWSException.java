package io.javadog.cws.common.exceptions;

import io.javadog.cws.api.common.Constants;

/**
 * Top Exception Class for CWS.
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public class CWSException extends RuntimeException {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    private final int returnCode;

    public CWSException(final int returnCode, final String message) {
        super(message);
        this.returnCode = returnCode;
    }

    public CWSException(final Throwable cause) {
        super(cause);
        this.returnCode = Constants.ERROR;
    }

    public CWSException(final int returnCode, final Throwable cause) {
        super(cause);
        this.returnCode = returnCode;
    }

    public CWSException(final int returnCode, final String message, final Throwable cause) {
        super(message, cause);
        this.returnCode = returnCode;
    }

    public int getReturnCode() {
        return returnCode;
    }
}