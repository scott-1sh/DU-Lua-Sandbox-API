/*    
    Unit.java 
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
package duJavaAPI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.eclipse.swt.custom.StyleRange;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import sandbox.DUElement;
import sandbox.execTimer;
import sandbox.execWindow;

public class Unit extends BaseElement {
    public JWebBrowser web = null;
    public String html = "";
    int sizeX = 200;
    int sizeY = 100;   
    int timerId = 0;
   
	public Unit(int pid, int px, int py, String pname, String pluaScriptStart, String pluaScriptStop, boolean pverboseJava) {
	    luaScriptStart = pluaScriptStart;
	    luaScriptStop = pluaScriptStop;	    
		name = pname;
		x = px;
		y = py;		
		id = pid;
		
		verboseJava = pverboseJava;			
			 
		// create panel
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);		
		panel.setBounds(x, y, sizeX, sizeY);
		
		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(1, 1, sizeX-3, sizeY-3);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);

		// picture
	 	JLabel lblPic = new JLabel();
	 	setPicture(lblPic, "src/pictures/elements/Unit_02.png", 2, 2, 100, 80);
		panel.add(lblPic, 1, 0);
		
		// stats panel
		CreateStatPanel(name+" ("+id+")", sizeX, sizeY);		
		panel.add(stats, 1, 0);
		
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/unit.lua";
        luaCall = name+" = createInterfaceUnit(\""+pid+"\", \""+name+"\")";		
        if(verboseJava) System.out.println("[JAVA] Unit ["+name+"] created");

	}

	@Override
	public void CreateTimer(Unit punit, String ptimerName, String pluaScript /*, JFrame srcframe */) {
		Tick[timerId] = new execTimer(timerId, punit, ptimerName, pluaScript, verboseJava);
		AddtoStatPanel("Timer "+timerId+": ", ptimerName);
		timerId++;
	}
	
	// Export all scripts related to this element
	// used for text and json export
	@Override
	public ArrayList<String[]> export() {
       
       ArrayList<String[]> listScript = new ArrayList<String[]>();
       
       // start()
       String sign = "start()";
       String args = "[]";
       String slotKey = "-1";
       listScript.add(new String[]{luaScriptStart, args, sign, slotKey}); 
       
       // stop()
	   if(luaScriptStop.length()>0) {
	       sign = "stop()";
	       args = "[]";
	       slotKey = "-1";
	       listScript.add(new String[]{luaScriptStop, args, sign, slotKey }); 
	   }
	   
       // timers
	   for (execTimer t : Tick ) {
 	       if(t == null) continue;
 	       
	       sign = "stop()";
	       args = "[{\"value\":\""+t.name+"\"}]";
	       slotKey = "-1";
	       listScript.add(new String[]{t.luaScript, args, sign, slotKey }); 
	   }	   
	   
	   return listScript;
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
	        execWindow.StopServices(execWindow.sandbox.elements);
	        execWindow.frame.setTitle(execWindow.frame.getTitle()+" - dead (unit.exit)");
	        // System.exit(0);	    	
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
