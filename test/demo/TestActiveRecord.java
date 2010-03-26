package demo;

import java.util.List;
import demo.helpers.Project;
import demo.helpers.User;

public class TestActiveRecord extends JRubyTestCase {
	public void testUseActiveRecord(){
		setUpActiveRecords();
		eval(
		 "class User < ActiveRecord::Base\n" +
		 "end");
		
		User user = eval("User.find_by_username 'craig'", User.class);
		assertEquals("craig", user.getUsername());
		assertEquals("secret", user.getPassword());
	}
	
	void setUpActiveRecords() {
		runtime.setJRubyHome("/jruby");
		require("rubygems");
		require("active_record");
		eval("ActiveRecord::Base.establish_connection(" +
			":adapter=>'jdbcmysql', :database=>'example', " +
			":username=>'root',   :password =>'')");
	}
	
	public void testActiveRecord_Crud(){
		setUpActiveRecords();
		
		eval(
		 "class User < ActiveRecord::Base\n" +
		 "end");
		
		User fred = eval("User.new", User.class);
		fred.setUsername("Zack");
		fred.setPassword("secret");
		fred.save();
		
		User saved = eval("User.find " + fred.getId(), User.class);
		assertEquals("Zack", saved.getUsername());
		assertEquals("secret", saved.getPassword());
		saved.destroy();
		
		User deleted = eval("User.find_by_username 'Zack'", User.class);
		assertNull(deleted);
	}
	
	public void testUseActiveRecordCollections(){
		setUpActiveRecords();
		eval(
		 "class User < ActiveRecord::Base\n" +
		 "end");
		
		List<User> users = eval("User.find :all", User.class);
		assertEquals(3, users.size());
		assertEquals("craig", users.get(0).getUsername());
		assertEquals("secret", users.get(0).getPassword());
	}

	public void testUseActiveRecordRelations(){
		try {
			setUpActiveRecords();
			eval(
			 "class User < ActiveRecord::Base\n" +
			 "  has_many :projects\n" +
			 "end");
			
			eval(
			 "class Project < ActiveRecord::Base\n" +
			 "end");
			
			User user = eval("User.find_by_username 'craig'", User.class);
			List<Project> projects = user.getProjects();
			assertEquals(3, projects.size());
			projects.get(0); // This bit goes bang
			fail("AssociationProxy isn't a RubyArray");
		} catch (Exception e){}
	}
}