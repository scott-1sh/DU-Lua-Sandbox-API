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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	
	// caption header (tools)
    int labelY = 16;
	public boolean maximized = true;
	private JButton captionIcon;
	
	
	public void setMaximized(int sizeX, int sizeY) {
		captionIcon.setIcon(new ImageIcon("src/pictures/close_caption.png")); 		
		panel.setBounds(x, y, sizeX, sizeY+labelY);
	}
	
	public void setClosedCaption(int sizeX, int labelY) {
		captionIcon.setIcon(new ImageIcon("src/pictures/open_caption.png"));		
		panel.setBounds(x, y, sizeX, labelY);		
	}	
	
	public void createHeader(int sizeX, int sizeY) {
		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, sizeX-18, labelY);		
		panel.add(lblname, 1, 0);		
		
		// expand/close icons
		captionIcon = new JButton();  
		captionIcon.setBounds(sizeX-18, 2, 16, 16);
		if(maximized) { 
			captionIcon.setIcon(new ImageIcon("src/pictures/close_caption.png")); 		
		} else {
			captionIcon.setIcon(new ImageIcon("src/pictures/open_caption.png")); 		
		}
		captionIcon.setPressedIcon(new ImageIcon("src/pictures/pressed_button.png"));
		captionIcon.setBackground(null);
        captionIcon.setMargin(new Insets(0, 0, 0, 0));
        captionIcon.setBorder(null);
		
        captionIcon.addActionListener(new ActionListener() { 
            @Override
			public void actionPerformed(ActionEvent e) {
                maximized = !maximized;
        		if(maximized) { 
            		setMaximized(sizeX, sizeY);
        		} else {
            		setClosedCaption(sizeX, sizeY);
        		}
        		
            } 
         });
        
        panel.add(captionIcon, 1, 0);

		/*
	 	JLabel lblPic = new JLabel("test");		
		lblPic.setBounds(sizeX-18, 2, 16, 16);
		
		if(maximized) { 
			lblPic.setIcon(new ImageIcon("src/pictures/close_caption.png")); 		
		} else {
			lblPic.setIcon(new ImageIcon("src/pictures/open_caption.png")); 		
		}
		panel.add(lblPic, 1, 0);
		*/
/*		
		// quit addon icon
	 	JLabel lblClose = new JLabel();		
	 	lblClose.setBounds(sizeX-34, 2, 16, 16);
		lblClose.setIcon(new ImageIcon("src/pictures/quit_addon.png")); 		
		panel.add(lblClose, 1, 0);
*/
				
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
