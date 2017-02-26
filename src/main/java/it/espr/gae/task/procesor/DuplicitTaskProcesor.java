package it.espr.gae.task.procesor;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.espr.gae.task.Task;
import it.espr.gae.task.TaskDataSerialiser;
import it.espr.gae.task.TaskDuplicitException;
import it.espr.gae.task.TaskDuplicitor;
import it.espr.gae.task.TaskException;

public abstract class DuplicitTaskProcesor<Data, TaskType extends Task<Data>> extends TaskProcesor<Data, TaskType> {

	private static final Logger log = LoggerFactory.getLogger(DuplicitTaskProcesor.class);

	private TaskDuplicitor<TaskType> taskDuplicitor;

	public DuplicitTaskProcesor(TaskDataSerialiser taskDataSerialiser) {
		super(taskDataSerialiser);
	}

	public void process(InputStream in) {
		try {
			TaskType task = taskDataSerialiser.<TaskType> deserialize(in);
			taskDuplicitor = new TaskDuplicitor<TaskType>(task);
			taskDuplicitor.check();
			taskDuplicitor.mark();

			this.processTask(task);

		} catch (TaskDuplicitException e) {
			log.error("Duplicit task detected, skipping...", e);
		} catch (TaskException e) {
			log.error("Problem when processing detail task", e);
			this.unMark();
		} catch (Exception e) {
			log.error("Problem when processing detail task", e);
			this.unMark();
		}
	}

	private void unMark() {
		if (taskDuplicitor != null) {
			taskDuplicitor.unMark();
		}
	}
}
