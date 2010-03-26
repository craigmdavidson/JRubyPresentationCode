package demo.helpers;

public class JavaPerson {
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public String greet() {
		return "Hello " + name;
	}

}
