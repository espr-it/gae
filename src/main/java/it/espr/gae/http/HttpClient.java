package it.espr.gae.http;

public interface HttpClient<Type> {

	public Type get(String url);
	
	public Type get(String url, int timeout, boolean followRedirects);
	
	public void post(String url, Type data);
}
