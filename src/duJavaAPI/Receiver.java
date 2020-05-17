/*    
    Receiver.java 
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
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import offlineEditor.execWindow;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Receiver extends BaseElement {
    int sizeX = 104;
    int sizeY = 130;
	public int status = 0;
	public int id = 0;

	public Receiver(int pid, String pname, int px, int py, boolean pverboseJava) {
		
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
		lblPic.setIcon(new ImageIcon("src/pictures/elements/Receiver.png")); 
		panel.add(lblPic, 1, 0);
		
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/receiver.lua";
        luaCall = pname+" = createInterfaceReceiver(\""+pid+"\", \""+pname+"\")";
		
        if(verboseJava) System.out.println("[JAVA] Door "+pid+".name="+pname+" created");	 		
	}

	// Export all scripts related to this element to text
	@Override
	public String export() {
		String script = "";
		for(String channel : Script.keySet()) {
			   if(Script.get(channel) == null) continue;
			   
			   // add to script
			   script += "-----------------------------------------\n";
			   script += "-- Element name: "+this.name+"\n";
			   script += "-- Element: Receiver\n";
			   script += "-- Event: receive(channel,message)\n";
			   script += "-----------------------------------------";
			   script += Script.get(channel)+"\n";	
		       script += "\n\n\n\n";
			   
		   }		
		
		return script;
	}
	
	@Override
	public void update(String[] param) {
		// {State,name}

		// System.out.println("[JAVA] Door " + param[1] + " state set to  " + param[0]); 
	}
}
