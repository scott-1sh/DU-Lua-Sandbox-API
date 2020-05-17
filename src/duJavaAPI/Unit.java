/*    
    Unit.java 
    Copyright (C) 2020 Stephane Boivin (Devgeek studio enr.)
    
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
package duJavaAPI;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import offlineEditor.execTimer;
import offlineEditor.execWindow;

public class Unit extends BaseElement {
    public JWebBrowser web = null;
    public String html = "";
    int sizeX = 134;
    int sizeY = 106;   
    int timerId = 0;
    
	public Unit(int pid, int px, int py, String pname, String pluaScriptStart, String pluaScriptStop, boolean pverboseJava) {
	    luaScriptStart = pluaScriptStart;
	    luaScriptStop = pluaScriptStop;	    
		name = pname;
		x = px;
		y = py;		
		
		verboseJava = pverboseJava;			
		
		panel = new JPanel();		
		panel.setLayout(null);
		// panel.setVisible(false);
		panel.setBounds(px, py, sizeX, sizeY+16);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);

		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, sizeX, 16);	
		panel.add(lblname, 1, 0);		

		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(2,22,130,98);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);

	 	// picture
	 	JLabel lblPic = new JLabel();		
		lblPic.setBounds(2,22,130,100);
		lblPic.setIcon(new ImageIcon("src/pictures/elements/Unit_02.png")); 
		panel.add(lblPic, 1, 0);
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/unit.lua";
        luaCall = name+" = createInterfaceUnit(\""+pid+"\", \""+name+"\")";		
        if(verboseJava) System.out.println("[JAVA] Unit ["+name+"] created");

	}

	@Override
	public void CreateTimer(Unit punit, String ptimerName, String pluaScript /*, JFrame srcframe */) {
		Tick[timerId] = new execTimer(timerId, punit, ptimerName, pluaScript, verboseJava);
		timerId++;
	}

	// Export all scripts related to this element to text
	@Override
	public String export() {
      String script = "";

       // start and stop
	   script += "-----------------------------------------\n";
	   script += "-- Element name: "+this.name+"\n";
	   script += "-- Element: Unit\n";
	   script += "-- Event: Start()\n";
	   script += "-----------------------------------------\n";
	   script += luaScriptStart+"\n";
	   script += "\n\n\n\n";
	   script += "-----------------------------------------+\n";
	   script += "-- Element name: "+this.name+"\n";
	   script += "-- Element: Unit\n";
	   script += "-- Event: Stop()\n";
	   script += "-----------------------------------------\n";
	   script += luaScriptStop+"\n";
       script += "\n\n\n\n";
	   // timers
	   // add to script

/*      
		   script += "-----------------------------------------";
		   script += "-- Element name: "+this.name;
		   script += "-- Element: Unit";
		   script += "-- Event: receive()";
		   script += "-----------------------------------------";
*/
	   
	   for (execTimer t : Tick ) {
 	       if(t == null) continue;
		   script += "-----------------------------------------\n";
		   script += "-- Element: Unit\n";
		   script += "-- Event: timer("+t.name+")\n";
		   script += "-----------------------------------------\n";
		   script += t.luaScript+"\n";
		   script += "\n\n\n\n";
	   }	   
	   
	   return script;
	}
	
	// find and start a timer
	public void startTimer(String pname, Float psec) {
 	    for (execTimer t : Tick ) {
 	       if(t == null) continue;
		   if(t.name.contentEquals(pname)) {
			   t.startTimer(psec);
			   break;
		   }
 	    }
	}

	@Override	
    public void update(String[] param) {
		String pcommand = param[0];
//	    System.out.println("[JAVA] Unit update (" + param[0] + ", " + param[1] + ", " + param[2] + ")"); 
		if(pcommand.equals("setTimer")) {
			String pname = param[1];
			Float psec = Float.valueOf(param[2]);
	    	this.startTimer(pname, psec);	    	
	    }				
		if(pcommand.equals("exit")) {
	        if(verboseJava) System.out.println("[JAVA] closing the unit");
	    	System.exit(0);	    	
	    }	
	}

	@Override	
    public Object get(String[] param) {    	
		String pcommand = param[0];
		if(pcommand.equals("getMasterPlayerId")) {
			return sandbox.MasterPlayerId; 
	    }
		if(pcommand.equals("getMasterRelativePosition")) {
	        LuaTable result = new LuaTable();
			result.set(1, CoerceJavaToLua.coerce(sandbox.worldPlayer.get(sandbox.MasterPlayerId).worldPos[0]));
			result.set(2, CoerceJavaToLua.coerce(sandbox.worldPlayer.get(sandbox.MasterPlayerId).worldPos[1]));
			result.set(3, CoerceJavaToLua.coerce(sandbox.worldPlayer.get(sandbox.MasterPlayerId).worldPos[2]));
			return result; 
	    }
		
		return -1;	
     }	
}
