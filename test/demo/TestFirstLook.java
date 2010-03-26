package demo;

import demo.helpers.JavaPerson;
import demo.helpers.RubyPerson;

public class TestFirstLook extends JRubyTestCase {
	
	public void testHelloWorld(){
		String result = eval("'Hello from Ruby'.upcase", String.class);
		assertEquals("HELLO FROM RUBY", result);
	}
	
	public void testRubyPerson(){
		require("ruby/ruby_person.rb");
		RubyPerson p = eval("RubyPerson.new", RubyPerson.class);
		p.setName("Craig");
		assertEquals("Hello Craig", p.greet());
	}
	
	public void testUseYamlLibrary(){
		require("yaml");
		require("ruby/ruby_person.rb");
		RubyPerson p = eval("RubyPerson.new", RubyPerson.class);
		p.setName("Craig");
		assertEquals(
			"--- !ruby/object:RubyPerson \n" +
			"name: Craig\n", p.toYaml());
	}
	
	public void testJavaPerson(){
		JavaPerson javaPerson = new JavaPerson();
		assign("@javaPerson", javaPerson);
		eval("@javaPerson.name = 'Craig'");
		assertEquals("Hello Craig", javaPerson.greet());
		assertEquals("Hello Craig", eval("@javaPerson.greet", String.class));
	}
}