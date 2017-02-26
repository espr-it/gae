package it.espr.gae.task;

public class TaskDuplicitException extends TaskException {

	private static final long serialVersionUID = 1L;

	public TaskDuplicitException() {
		super();
	}

	public TaskDuplicitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TaskDuplicitException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskDuplicitException(String message) {
		super(message);
	}

	public TaskDuplicitException(Throwable cause) {
		super(cause);
	}
}
