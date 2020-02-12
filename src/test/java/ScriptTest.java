

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.web.client.RestTemplate;

import javax.script.ScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;

import junit.framework.TestCase;

public class ScriptTest extends TestCase {
	public void testJuel() throws ScriptException{
//		  ScriptEngineManager scriptEngineMgr = new ScriptEngineManager();
//		  
//		  List<ScriptEngineFactory> factories = scriptEngineMgr.getEngineFactories();
//		    for (ScriptEngineFactory factory : factories)
//		    {
//		        System.out.println("ScriptEngineFactory Info");
//		        String engName = factory.getEngineName();
//		        String engVersion = factory.getEngineVersion();
//		        String langName = factory.getLanguageName();
//		        String langVersion = factory.getLanguageVersion();
//		        System.out.printf("\tScript Engine: %s (%s)\n", engName, engVersion);
//		        List<String> engNames = factory.getNames();
//		        for (String name : engNames)
//		        {
//		            System.out.printf("\tEngine Alias: %s\n", name);
//		        }
//		        System.out.printf("\tLanguage: %s (%s)\n", langName, langVersion);
//		        }
//		    
//
//		  ScriptEngine engine = scriptEngineMgr.getEngineByName("juel");
//		  Map user = new HashMap();
//		  user.put("departId", "aaa");
//		  engine.put("user", user);
//		 // engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
//		  Object o =engine.eval("${user.departId}");
//		  
//		  this.assertEquals("aaa", o);
		RestTemplate rest = new RestTemplate();
	}
}
