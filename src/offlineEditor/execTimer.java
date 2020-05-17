/*    
    execTimer.java 
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
package offlineEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
// import java.util.Timer;
// import java.util.TimerTask;
import javax.swing.Timer;

import duJavaAPI.Navigator;
import duJavaAPI.Unit;

public class execTimer /* extends TimerTask */ {
	public String name = "commonName";
	private String elementName = "unit";
    public int id = -1;
    public execLUA sandbox = null;     
	public String luaScript = null;
	Timer timer = null;
	Unit unit = null;
	static JFrame srcFrame = null;
	private boolean verboseJava;
	
    public execTimer(int pid, Unit punit, String ptimerName, String pluaScript, boolean pverboseJava) {
	  	name = ptimerName;
	  	unit = punit;
	  	id = pid;
	  	luaScript = pluaScript;
	  	verboseJava = pverboseJava;
	}

	public void startTimer(Float psec) {

      int msec = Math.round(psec*1000);
	  if(verboseJava) System.out.println("timer activé avec un delais de "+msec+" ms");
	  
      // timer.schedule(this, sec, sec);
  	  timer = new Timer(msec, new ActionListener() { 
  		  @Override
  		  public void actionPerformed(ActionEvent ae) {
 			sandbox.RUN("self="+unit.name+" "+luaScript, name+":tick()");
  		  }
  		   
  	   });	  

  	  timer.start();
    }
}
