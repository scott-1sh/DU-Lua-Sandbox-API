/*    
    Emitter.java 
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
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import offlineEditor.DUElement;
import offlineEditor.execWindow;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Emitter extends BaseElement {
    int sizeX = 104;
    int sizeY = 130;
	public int status = 0;
	public int id = 0;
	
	public Emitter(int pid, String pname, int px, int py, boolean pverboseJava) {
		
		name = pname;
		x = px;
		y = py;
		id = pid;
		
		verboseJava = pverboseJava;
	
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px, py, sizeX, sizeY+22);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);
		
		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, sizeX, 16);	
		panel.add(lblname, 1, 0);		
		
		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(1,22,102,129);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);

	 	// picture
	 	JLabel lblPic = new JLabel();		
		lblPic.setBounds(2,20,100,130);
		lblPic.setIcon(new ImageIcon("src/pictures/elements/Emitter.png")); 
		panel.add(lblPic, 1, 0);

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
