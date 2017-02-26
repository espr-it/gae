package it.espr.gae.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.ObjectifyService;

public class TaskDataRepository {

	private static final Logger log = LoggerFactory.getLogger(TaskDataRepository.class);

	static {
		ObjectifyService.register(TaskData.class);
	}

	public TaskData get(String id) {
		log.debug("Loading task data with id {} from database", id);
		TaskData taskData = ObjectifyService.ofy().load().type(TaskData.class).id(id).now();
		log.debug("Found task data {} for id {} in database ", taskData, id);
		return taskData;
	}

	public void set(TaskData taskData) {
		if (taskData == null) {
			log.error("Can't save empty task data into database, skipping...");
			return;
		}
		log.debug("Saving task data with id {} to database", taskData.getId());
		ObjectifyService.ofy().save().entity(taskData).now();
		log.debug("Saved task data with id {} in database ", taskData.getId());
	}
}
