/*
    BaseElement.java
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
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import offlineEditor.execLUA;
import offlineEditor.execTimer;
import offlineEditor.execWindow;


public class BaseElement {
	public int x = 0;
	public int y = 0;
	public JPanel panel = new JPanel();
	public String name = "commonName";
	public String urlAPI= "";
    public String luaCall = "";
    public execLUA sandbox = null;
    public String luaScriptStart = null; 
    public String luaScriptStop = null;
    public execTimer[] Tick = new execTimer[10];    
    public int mouseState = 0;
    public int mouseX = 0;
    public int mouseY = 0;
    public HashMap<String, String>  Script = new HashMap<String, String>();
    execWindow execWin = null;    
	boolean verboseJava = true;		
	
	public void CreateHeader(int sizeX, int labelY) {
		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, sizeX-18, labelY);		
		panel.add(lblname, 1, 0);		
		
		// expand/close icons
	 	JLabel lblPic = new JLabel();		
		lblPic.setBounds(sizeX-18,1,16,16);
		lblPic.setIcon(new ImageIcon("src/close_caption.png")); 
		panel.add(lblPic, 1, 0);		
		
	}
	
	
    public void actionListener(ActionEvent e) {
    	// override me!
    }

    public String export() {
    	// override me!
    	return "";
    }
    
    public void update(String[] param) {    	
    	// override me!
    }

    public Object get(String[] param) {
    	// override me!
    	return "";
    }

    public void LoadScreen(String phtml) {
		// override me!
	}

	public void CreateTimer(Unit punit, String ptimerName, String pluaScript /*, JFrame srcframe*/) {
		// override me!		
	}	
}
