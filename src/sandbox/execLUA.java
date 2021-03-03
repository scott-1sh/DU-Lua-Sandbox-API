/*    
    execLUA.java 
    Copyright (C) 2020 Stephane Boivin (Discord: Nmare418#6397)
    
    This file is part of "DU offline sandbox API".

    "DU offline sandbox API" is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    "DU offline sandbox API" is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with "DU offline sandbox API".  If not, see <https://www.gnu.org/licenses/>.
*/

package sandbox;	

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Timer;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import duJavaAPI.Construct;
import duJavaAPI.Database;
import duJavaAPI.JavaLibrary;
import duJavaAPI.JavaSystem;
import duJavaAPI.Player;

public class execLUA {
    public String scriptAPI = "";
	public String scriptRequire = "";
	public String loadedAPI = ""; 
    public static Globals globals = JsePlatform.standardGlobals();
	public int Z = 10;
	public DUElement[] elements;
	public static List<Construct> worldConstruct = null;
	public List<Player> worldPlayer = null;
	public int MasterPlayerId = 1;
	private static Database database = null;
	static execWindow eWindow;
	Timer timer = null;    
    public int range = 10000;
	
	public String luaScriptToText(DUElement[] elements) {
		String script = "";
		// Scan each elements to find scripts
		for (DUElement elem : elements ) {
			if(elem == null) continue;
			script += elem.element.export(); 
		}
  
		return script;
    }	

	private String LoadAPI(String apiFile) {	
		String data = ""; 
		
		try { 
		  data = new String(Files.readAllBytes(Paths.get(apiFile))); 
		} catch (IOException e) 
		{ e.printStackTrace(); }
	   
		return data;
	}	
	
	
	// Préparation de l'Environnement
	public execLUA(execWindow peWindow, DUElement[] pelements, List<Construct> pworldConstruct, List<Player> pworldPlayer, int pMasterPlayerId,  boolean verboseLUA) {

		elements = pelements;
        worldConstruct = pworldConstruct;
        worldPlayer = pworldPlayer;
        eWindow = peWindow;
        MasterPlayerId = pMasterPlayerId;
        
        globals = null;
        globals = JsePlatform.standardGlobals();
        
        // lua output to winConsole
        globals.STDOUT = new PrintStream (MAIN.wConsole);
        
        // java functions (elements)		
        globals.set("JavaWindow", CoerceJavaToLua.coerce(new JavaWindow())); // notice: JavaWindow is a private class of execLUA
        
        // System functions
        globals.set("JavaSystem", CoerceJavaToLua.coerce(new JavaSystem(execWindow.verboseJava, eWindow)));
        
        // Library functions
        globals.set("JavaLibrary", CoerceJavaToLua.coerce(new JavaLibrary(execWindow.verboseJava)));
        
        // database
        execLUA.database = new Database(pworldConstruct, pworldPlayer, execWindow.verboseJava);
        globals.set("DUDatabase", CoerceJavaToLua.coerce(execLUA.database));

        if(verboseLUA) {
        	scriptAPI = "_verboseLUA = 1\n"; 
        } else { 
        	scriptAPI = "_verboseLUA = 0\n"; 
        }
        
        		
		// add "extends" functions	
		scriptAPI += LoadAPI("src/duElementAPI/system.lua")+"\n";
		scriptAPI += LoadAPI("src/duElementAPI/generic.lua")+"\n";
		scriptAPI += LoadAPI("src/duElementAPI/library.lua")+"\n";
		scriptAPI += LoadAPI("src/duElementAPI/Database.lua")+"\n";
		scriptAPI += LoadAPI("src/duElementAPI/JSON.lua")+"\n";

		// insert all elements in the LUA env
        for (DUElement elem : elements  ) {
        	if(elem == null) continue;

        	// load only once per elements type
        	if(!loadedAPI.contains("*"+elem.GetType())) { 
        		// insert api 
        		scriptAPI += LoadAPI(elem.element.urlAPI)+"\n";       		
        		loadedAPI += "*"+elem.GetType();
        	}

        	// calls to create interface
            scriptRequire += elem.element.luaCall+"\n";

        }

        scriptAPI = scriptAPI+"\n-- interfaces\n"+scriptRequire;  	    

        // System.out.println(scriptAPI);
	    LuaValue chunk = globals.load(scriptAPI);    

	    // run 
        chunk.call();
	    System.out.println("[LUA] API Loaded to the LUA environment");
        
	    // Start timers for world constructs
		if(worldConstruct != null) startTimer((float).3); 	    
	}

	// Timer for world constructs actions
    public void startTimer(Float psec) {
        int msec = Math.round(psec*1000);
        if(execWindow.verboseJava) System.out.println("[JAVA] World construct positions are now updated every "+msec+"ms");
        
    	  timer = new Timer(msec, new ActionListener() { 
    		  @Override
    		  public void actionPerformed(ActionEvent ae) {
    			  
    			  // tell the construct when to come back in the radar zone
    			  float bouncingRange = range+(range/10);
			   	  double distance = 0; 
    			  // move dyn constructs
    			  for(int i=0; i< worldConstruct.size(); i++) {
    	              if(worldConstruct.get(i).ctype == "static") continue;			  
    				  Construct tmpConstruct = worldConstruct.get(i);
    				  	  
 					  // System.out.println("test "+tmpConstruct.name+"  "+tmpConstruct.speed[0]+"*"+tmpConstruct.speed[1]+"*"+tmpConstruct.speed[2]);	
    				  // moving constructs
    				  tmpConstruct.pos[0] = tmpConstruct.pos[0] + (tmpConstruct.speed[0]);  
					  tmpConstruct.pos[1] = tmpConstruct.pos[1] + (tmpConstruct.speed[1]);
					  tmpConstruct.pos[2] = tmpConstruct.pos[2] + (tmpConstruct.speed[2]); 
    				  distance = Math.sqrt((Math.pow(0 - tmpConstruct.pos[0],2) + Math.pow(0 - tmpConstruct.pos[1],2) + Math.pow(0 - tmpConstruct.pos[2],2))) ;  

					  // ZONE OUT (bouncing)
					  if(distance > range) { 					     
 						  // come back?
 						  if(Math.abs(tmpConstruct.pos[0]) >= bouncingRange) tmpConstruct.speed[0] = -tmpConstruct.speed[0];
 						  if(Math.abs(tmpConstruct.pos[1]) >= bouncingRange) tmpConstruct.speed[1] = -tmpConstruct.speed[1];
 						  if(Math.abs(tmpConstruct.pos[2]) >= bouncingRange) tmpConstruct.speed[2] = -tmpConstruct.speed[2];

 						  if(!tmpConstruct.outOfRange) {
						    // if(eWindow.verboseJava) System.out.println(String.format("[JAVA] timer World construct - Construct %s %s (%s) ZONE OUT", worldConstruct.get(i).id, worldConstruct.get(i).name, worldConstruct.get(i).owner));
						    // tmpConstruct.outOfRange = true;
 						  }

					  }

					  // ZONE IN (bouncing)
					  if(tmpConstruct.outOfRange && distance < range) {
					    // if(eWindow.verboseJava) System.out.println(String.format("[JAVA] World construct - Construct %s %s (%s) ZONE IN",worldConstruct.get(i).id, worldConstruct.get(i).name, worldConstruct.get(i).owner));
					    // tmpConstruct.outOfRange = false;
					  }
		  
					  // rebuild 
					  worldConstruct.set(i, tmpConstruct);
    			  }     			  
    		  }
    		   
    	   });	  

    	  timer.start();
    }
	
    
    // lua interface for the java functions. 
	private class JavaWindow {

		@SuppressWarnings("unused")
		public String setState(int pid, String[] param) {
     		
     		elements[pid].element.update(param);
     		
			return param[0];
	    }
		
	    @SuppressWarnings("unused")
		public Object get(int pid, String[] param) {
	    	return elements[pid].element.get(param);
	    }
		
		@SuppressWarnings("unused")
		public int getMouseX(int pid) {     		
			return elements[pid].element.mouseX;
	    }
	
		@SuppressWarnings("unused")
		public int getMouseY(int pid) {     		
			return elements[pid].element.mouseY;
	    }
		
		@SuppressWarnings("unused")
		public int getMouseState(int pid) {     		
			return elements[pid].element.mouseState;
		}
	}

	
	@SuppressWarnings("static-access")
	public void RUN(String script, String Source) {
		String pattern = "[:](.*)$";
		Pattern r = Pattern.compile(pattern);
	    
		try {
			LuaValue chunk =  globals.load(script);	    
			chunk.call();
		}
			catch (Exception e) {
				String gmsg = "\nLUA ERROR: "+Source+"\n\n";
			    
				// message tail
				Matcher m = r.matcher(e.getMessage());
			    if (m.find( )) {
				  gmsg += "line "+m.group(1)+"\n\n";
			    }
			    System.out.println(gmsg);
				execWindow.StopServices(elements);
				execWindow.frame.setTitle(execWindow.frame.getTitle()+" - dead!");
//			    throw new LuaError(e);
				// System.exit(-1);
			}	    
	}
	
}
