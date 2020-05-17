/*    
    ToggleUnit.java 
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

/*
 ****************************************************************************************
 * Used for Force fields, ship doors, landing gears, manual switches and light squares  * 
 ****************************************************************************************

  use setPictures(String disabledPicture, String activePicture ) to change the pictures
		
*/	

import java.awt.Color;
import java.awt.Image;

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
	public int id = 0;
	public String type = "";

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
		
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px,py,104,124);
//		panel.setBackground(Color.white );
		panel.setBorder(LineBorder.createGrayLineBorder());
		panel.setBackground(Color.black);
		
		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, 95, 16);	
		panel.add(lblname, 1, 0);		

		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(2,22,100,100);
	 	lblPicWhite.setBackground(Color.white);
	 	panel.add(lblPicWhite, 1, 0);
		
		// picture
	 	ImageIcon icon = null;
	 	
		lblPic.setBounds(3,22,100,100);
		if(status == 0) {
		   setPictures(this.disabledPicture);
		   // lblPic.setIcon(new ImageIcon(disabledPicture).getImage().getScaledInstance(97, 97, java.awt.Image.SCALE_SMOOTH)); 			
		} else {
		   setPictures(this.activePicture);
		   // lblPic.setIcon(new ImageIcon(activePicture)); 			
		}
		panel.add(lblPic, 1, 0);
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/toggleUnit.lua";		                    
        luaCall = pname+" = createInterfaceToggleUnit(\""+pid+"\", \""+pname+"\", \""+ptype+"\")";
		
        if(verboseJava) System.out.println("[JAVA] "+type+" ["+pname+"] created");	 		
	}

	public void setPictures(String picture) {				  
		ImageIcon icon = null;
		icon = new ImageIcon(picture);
		Image image = icon.getImage();
		Image newimage = image.getScaledInstance(97, 98, java.awt.Image.SCALE_SMOOTH);
		lblPic.setIcon(new ImageIcon(newimage));			
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
	 	ImageIcon icon = null;
		if(Integer.valueOf(param[0]) == 0) {
			   status = 0;
			   setPictures(this.disabledPicture);
/*			   icon = new ImageIcon(this.disabledPicture);
			   Image image = icon.getImage();
			   Image newimage = image.getScaledInstance(97, 97, java.awt.Image.SCALE_SMOOTH);
			   lblPic.setIcon(new ImageIcon(newimage));
*/			   
		} else {
			   status = 1;
			   setPictures(this.activePicture);
/*			   icon = new ImageIcon(this.activePicture);
			   Image image = icon.getImage();
			   Image newimage = image.getScaledInstance(97, 97, java.awt.Image.SCALE_SMOOTH);
			   lblPic.setIcon(new ImageIcon(newimage)); */
		}
		lblPic.setVisible(true);
		if(verboseJava) System.out.println("[JAVA] "+type+" " + param[1] + " state set to  " + param[0]); 
	}
}
