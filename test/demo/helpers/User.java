package demo.helpers;

import java.util.List;


public interface User {
	String getUsername(); void setUsername(String string);
	String getPassword(); void setPassword(String string);
	
	long getId();
	void save();
	void destroy();
	
	List<Project> getProjects();
	
	
}
