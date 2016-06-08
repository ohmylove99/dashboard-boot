package org.octopus.dashboard.shared.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -5501169657236500900L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
