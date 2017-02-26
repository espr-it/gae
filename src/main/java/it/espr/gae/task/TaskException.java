package it.espr.gae.task;

public class TaskException extends Exception {

	private static final long serialVersionUID = 1L;

	public TaskException() {
		super();
	}

	public TaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TaskException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskException(String message) {
		super(message);
	}

	public TaskException(Throwable cause) {
		super(cause);
	}
}
