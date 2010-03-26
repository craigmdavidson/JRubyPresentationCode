package demo;

import demo.helpers.JavaPerson;

public class TestErb extends JRubyTestCase {
	String sample = 
		"<%=@person.greet %>, " +
		"Hey <%=@person.name %>, have we an offer for you";
	
	public void testErb(){
		assign("@text", sample);
		require("rubygems", "erb");
		eval("@template = ERB.new @text");

		JavaPerson javaPerson = new JavaPerson();
		javaPerson.setName("Craig");
		assign("@person", javaPerson);
		
		String result = eval("@template.result(binding)", String.class);
		assertEquals(
			"Hello Craig, Hey Craig, have we an offer for you", result);
		
		javaPerson.setName("Joe");
		result = eval("@template.result(binding)", String.class);
		assertEquals(
			"Hello Joe, Hey Joe, have we an offer for you", result);
	}
}