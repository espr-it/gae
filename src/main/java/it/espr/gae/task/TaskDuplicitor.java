package it.espr.gae.task;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class TaskDuplicitor<TaskType extends Task> {

	private static final Logger log = LoggerFactory.getLogger(TaskDuplicitor.class);

	private static final MemcacheService cache = MemcacheServiceFactory.getMemcacheService();

	private TaskType task;

	private String key;

	public TaskDuplicitor(TaskType task) {
		this.task = task;
		this.key = key();
	}

	private String key() {
		int hashCode = 0;

		if (task instanceof DataTask<?>) {
			DataTask<?> dataTask = (DataTask<?>) task;
			if (dataTask.getData() instanceof String) {
				hashCode += dataTask.getData().hashCode();
			} else if (dataTask.getData() instanceof Collection) {
				for (Object o : (Collection<?>) dataTask.getData()) {
					hashCode += o.hashCode();
				}
			}

		}
		log.debug("Generated key {} for task {}", hashCode, task);
		return "" + hashCode;
	}

	public void check() throws TaskException {
		Date date = (Date) cache.get(key);
		
		if (date != null) {
			log.debug("The task {} is duplicit and was first seen at {}", task, date);
			// throw new TaskException("The task is duplicit and was first seen at '" + date + "'");
		} else {
			log.debug("The task {} is not duplicit", task);
		}
	}

	public void mark() {
		cache.put(key, new Date(), Expiration.byDeltaSeconds(60));
	}

	public void unMark() {
		cache.delete(key);
	}
}
