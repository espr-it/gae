package it.espr.gae.task.procesor;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.espr.gae.task.Task;
import it.espr.gae.task.TaskDataSerialiser;
import it.espr.gae.task.TaskException;

public abstract class TaskProcesor<Data, TaskType extends Task<Data>> {

	private static final Logger log = LoggerFactory.getLogger(TaskProcesor.class);

	protected TaskDataSerialiser taskDataSerialiser;

	public TaskProcesor(TaskDataSerialiser taskDataSerialiser) {
		super();
		this.taskDataSerialiser = taskDataSerialiser;
	}

	public void process(InputStream in) {
		try {
			TaskType task = taskDataSerialiser.<TaskType> deserialize(in);
			this.processTask(task);
		} catch (TaskException e) {
			log.error("Problem when processing detail task", e);
		} catch (Exception e) {
			log.error("Problem when processing detail task", e);
		}
	}

	public abstract void enqueue(Data data);

	protected abstract void processTask(TaskType task) throws TaskException, Exception;
}
