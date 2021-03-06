package it.espr.gae.task;


public class SimpleTask<Data> implements Task<Data> {

	private static final long serialVersionUID = 1L;

	protected Data data;

	public SimpleTask(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Data task of type '" + data.getClass() + "' and content '" + data + "'";
	}
}
