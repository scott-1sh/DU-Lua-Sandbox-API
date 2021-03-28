/*    
    ToggleUnit.java 
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

/*
 ****************************************************************************************
 * Used for Force fields, ship doors, landing gears, manual switches and light squares  * 
 ****************************************************************************************

  use setPictures(String disabledPicture, String activePicture ) to change the pictures
		
*/	

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ToggleUnit extends BaseElement {
    private String disabledPicture = "src/pictures/porte0.png";
    private String activePicture = "src/pictures/porte1.png";
 	private JLabel lblPic = new JLabel();
	public int status = 0;
	public String type = "";
    int sizeX = 100;
    int sizeY = 100;
    private int picX = 2;
    private int picY = 2;
    private int picSizeX = 93;
    private int picSizeY = 75;
    
	// types:  Door, ForceField, LandingGear, Switch, Light 
	public ToggleUnit(int pid, String ptype, String pname, int px, int py, boolean pverboseJava) {
		
		name = pname;
		x = px;
		y = py;
		id = pid;
		type = ptype;
		verboseJava = pverboseJava;
		
		switch (ptype) {
	      case "Door":					
		    disabledPicture = "src/pictures/elements/Door_Closed.png";
		    activePicture = "src/pictures/elements/Door_Open.png";
		    break;
		  case "ForceField":					
		    disabledPicture = "src/pictures/elements/Door_Closed.png";
		    activePicture = "src/pictures/elements/Door_Open.png";
		    break;
		  case "LandingGear":					
		    disabledPicture = "src/pictures/elements/LandingGear_Off.png";
		    activePicture = "src/pictures/elements/LandingGear_On.png";
		    break;
		  case "Switch":
		    disabledPicture = "src/pictures/elements/ToggleButton_Off.png";
		    activePicture = "src/pictures/elements/ToggleButton_On.png";
		    break;
		  case "Light":
		    disabledPicture = "src/pictures/elements/Light_Off.png";
		    activePicture = "src/pictures/elements/Light_On.png";
		}
		
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
		if(status == 0) {
	 	    setPicture(lblPic, disabledPicture, picX, picY, picSizeX, picSizeY);
		} else {
	 	    setPicture(lblPic, activePicture, picX, picY, picSizeX, picSizeY);
		}
	    lblPic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	sandbox.RUN(name+".toggle()", name+":Manual activation");
            }

        });		
		panel.add(lblPic, 1, 0);
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/toggleUnit.lua";
        luaCall = pname+" = createInterfaceToggleUnit(\""+pid+"\", \""+pname+"\", \""+ptype+"\")";
		
        if(verboseJava) System.out.println("[JAVA] "+type+" ["+pname+"] created");	 		
	}


	@Override	
    public Object get(String[] param) {    	
		String pcommand = param[0];
		if(pcommand.equals("getState")) { // internal function
			return status; 
	    }		
		if(pcommand.equals("getType")) { // internal function
			return type; 
	    }		
		return -1;
    }
	@Override
	public void update(String[] param) {
		// {State,name}
		panel.remove(lblPic);
		JLabel lblPic = new JLabel();
		if(Integer.valueOf(param[0]) == 0) {
			   status = 0;
			   setPicture(lblPic, disabledPicture, picX, picY, picSizeX, picSizeY);
		} else {
			   status = 1;
			   setPicture(lblPic, activePicture, picX, picY, picSizeX, picSizeY);
		}
	    lblPic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	sandbox.RUN(name+".toggle()", name+":Manual activation");
            }

        });			
		panel.add(lblPic, 1, 0);
		panel.revalidate();
	    panel.repaint(); 
		if(verboseJava) System.out.println("[JAVA] "+type+" " + param[1] + " state set to  " + param[0]); 
	}
}
