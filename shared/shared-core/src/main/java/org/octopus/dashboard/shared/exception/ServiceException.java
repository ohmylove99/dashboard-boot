package org.octopus.dashboard.shared.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3050055243620412866L;
	public ErrorCode errorCode;

	public ServiceException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
