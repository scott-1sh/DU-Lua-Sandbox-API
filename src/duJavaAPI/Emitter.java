/*    
    Emitter.java 
     Copyright (C) 2021 Stephane Boivin (Discord: Nmare418#6397)
    
    This file is part of "DU lua sandbox API".

    "DU lua sandbox API" is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    "DU lua sandbox API" is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with "DU lua sandbox API".  If not, see <https://www.gnu.org/licenses/>.
*/
package duJavaAPI;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import sandbox.DUElement;
import sandbox.execWindow;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Emitter extends BaseElement {
    int sizeX = 200;
    int sizeY = 100;
	public int status = 0;
	private int range;
	
	
	public Emitter(int pid, String pname, int px, int py, int prange, boolean pverboseJava) {
		
		name = pname;
		x = px;
		y = py;
		id = pid;
		range = prange;
		
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
	 	setPicture(lblPic, "src/pictures/elements/Emitter.png", 9, 5, 55, 71);
		panel.add(lblPic, 1, 0);
		
		// stats
		CreateStatPanel(name+" ("+id+")", sizeX, sizeY);		
		AddtoStatPanel("Range:", range+"m");
		panel.add(stats, 1, 0);
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/emitter.lua";
        luaCall = pname+" = createInterfaceEmitter(\""+pid+"\", \""+pname+"\")";
		
        if(verboseJava) System.out.println("[JAVA] Door "+pid+".name="+pname+" created");	 		
	}

	// notice: export() scripts is on the emitter script
	
	@Override
	public void update(String[] param) {
		if(param[0].contains("send")) {			
			for (DUElement elem : sandbox.elements ) {
 		       if(elem == null) continue;
			   if(elem.GetType().equals("duJavaAPI.Receiver"))
			   {
  		           String script = "";
				   for(String channel : elem.element.Script.keySet()) {
					   if(channel.equals(param[1])) {
						   script = "local channel='"+param[1]+"' local message='"+param[2].replace( "'", "\'")+"' "+elem.element.Script.get(channel);
						   sandbox.RUN("self="+elem.element.name+" "+script,name+":send()  channel=["+param[1]+"]  message=["+param[2]+"]");
					   }
				   }
			   }
			}
		}
	}
}
