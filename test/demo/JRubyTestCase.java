package demo;

import java.util.ArrayList;
import java.util.List;

import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.runtime.builtin.IRubyObject;
import junit.framework.TestCase;

public abstract class JRubyTestCase extends TestCase {
	Ruby runtime = Ruby.newInstance();
	
	public <T>T eval(String rubyCode, Class javaClass) {
		IRubyObject result = eval(rubyCode);
		if (result instanceof RubyArray)
		  return (T)respin((RubyArray)result, javaClass);
		else
		  return (T)result.toJava(javaClass);
	}

	List respin(RubyArray results, Class targetClass) throws ClassCastException {
		List javaList = new ArrayList();
		for (Object o : results)
			if (o instanceof IRubyObject)
				javaList.add(((IRubyObject)o).toJava(targetClass));
			else
				javaList.add(o);
		return javaList;
	}
	
	IRubyObject eval(String rubyCode) {
		return runtime.evalScriptlet(rubyCode);
	}

	void require(String lib) {
		eval("require '" + lib + "'", null);
	}
	
	void require(String ...libs){
		for (String lib : libs) 
			require(lib);
	}

	public interface Assigner {void put(Object val);}
	void assign(String var, Object val) {
		Assigner in = eval(
			"class Assigner\n" +
			"attr_reader :val\n" + 
			"def put obj\n" + "@val = obj\n" + "end\n" +
			"end\n" +
			"@in=Assigner.new", Assigner.class);
		in.put(val);
		eval(var + "=@in.val");
	}
}