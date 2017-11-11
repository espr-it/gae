package it.espr.gae.task;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskDataSerialiser {

	private static final class TaskDataDb implements Serializable {

		private static final long serialVersionUID = 1L;

		String id;

		public TaskDataDb(String id) {
			this.id = id;
		}
	}

	private static final Logger log = LoggerFactory.getLogger(TaskDataSerialiser.class);

	private TaskDataRepository taskDataRepository;

	public TaskDataSerialiser(TaskDataRepository taskDataRepository) {
		super();
		this.taskDataRepository = taskDataRepository;
	}

	@SuppressWarnings("unchecked")
	public <Type extends Serializable> Type deserialize(InputStream input) throws TaskException {
		Type data = null;
		try {
			Object object = SerializationUtils.deserialize(new Base64InputStream(input));
			if (object != null) {
				if (object instanceof TaskDataDb) {
					log.debug("Large task data detected, loading from db.");
					TaskData taskData = taskDataRepository.get(((TaskDataDb) object).id);
					data = (Type) taskData.getData();
				} else {
					log.debug("Small task data detected, loading from payload.");
					data = (Type) object;
				}
			}
		} catch (Exception e) {
			log.error("Problem when parsing tak payload from request", e);
		}

		if (data == null) {
			throw new TaskException("Problem when deserialising task");
		}

		log.debug("Found data {} in the task payload", data);
		return data;
	}

	public <Type extends Serializable> byte[] serialize(Type data) {

		byte[] serialized = Base64.encodeBase64(SerializationUtils.serialize(data));

		if (serialized == null || serialized.length < 90000) {
			return serialized;
		}

		log.debug("Task data too large, saving to db");
		try {
			TaskData taskData = new TaskData(data);
			taskDataRepository.set(taskData);
			return Base64.encodeBase64(SerializationUtils.serialize(new TaskDataDb(taskData.getId())));
		} catch (Exception e) {
			log.error("Problem when saving task data into database", e);
		}

		return null;
	}
}
