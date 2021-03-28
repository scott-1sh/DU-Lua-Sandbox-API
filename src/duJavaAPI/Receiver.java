/*    
    Receiver.java 
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
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import sandbox.execWindow;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Receiver extends BaseElement {
    int sizeX = 200;
    int sizeY = 100;
	public int status = 0;
	private int range;

	public Receiver(int pid, String pname, int px, int py, int prange, boolean pverboseJava) {
		
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
	 	setPicture(lblPic, "src/pictures/elements/Receiver.png", 8, 5, 55, 71);
		panel.add(lblPic, 1, 0);
		
		// stats
		CreateStatPanel(name+" ("+id+")", sizeX, sizeY);		
		AddtoStatPanel("Range:", range+"m");
		panel.add(stats, 1, 0);
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/receiver.lua";
        luaCall = pname+" = createInterfaceReceiver(\""+pid+"\", \""+pname+"\")";
		
        if(verboseJava) System.out.println("[JAVA] Door "+pid+".name="+pname+" created");	 		
	}

	@Override
	public ArrayList<String[]> export() {	       
	   ArrayList<String[]> listScript = new ArrayList<String[]>();
	   String sign;
	   String args;
	   String slotKey;

		for(String channel : Script.keySet()) {
		      if(Script.get(channel) == null) continue;
		      sign = "receive(channel,message)";
		      args = "[{\"value\":\"channel\"},{\"value\":\"message\"}]";
		      slotKey = Integer.toString(id);
		      listScript.add(new String[]{Script.get(channel), args, sign, slotKey}); 			
	   }		
	   	   	   
	   return listScript;
	}
	
	@Override
	public void update(String[] param) {
		// {State,name}

		// System.out.println("[JAVA] Door " + param[1] + " state set to  " + param[0]); 
	}
}
