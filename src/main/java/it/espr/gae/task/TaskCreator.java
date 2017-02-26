package it.espr.gae.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskCreator {

	private static final Logger log = LoggerFactory.getLogger(TaskCreator.class);

	private TaskDataSerialiser taskDataSerialiser;

	public TaskCreator(TaskDataSerialiser taskDataSerialiser) {
		super();
		this.taskDataSerialiser = taskDataSerialiser;
	}

	public void enqueue(String queueName, Task task) {
		log.debug("Enqueuing task {} into queue {}", task, queueName);
		TaskOptions options = TaskOptions.Builder.withUrl("/task/" + queueName);

		try {
			options.payload(taskDataSerialiser.<Task> serialize(task));
			log.debug("Serialised task data into {} bytes", options.getPayload().length);
		} catch (Exception e) {
			log.error("Problem when serialising task {}, skipping processing...", task, e);
			return;
		}

		if (options.getPayload() == null) {
			log.error("Serialised payload from task {} is empty, skipping processing...", task);
			return;
		}

		String queue = "grabber";

		if (queueName.equals("fusion")) {
			queue = "fusion";
		}

		QueueFactory.getQueue(queue).add(options);
	}
}
