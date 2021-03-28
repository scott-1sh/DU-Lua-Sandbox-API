/*
    BaseElement.java
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import sandbox.PreLoad;
import sandbox.execLUA;
import sandbox.execTimer;
import sandbox.execWindow;


public class BaseElement {
	public int x = 0;
	public int y = 0;
	public int id = 0;
	public JPanel panel = new JPanel();
	public JPanel stats = new JPanel();
    public JWebBrowser web = null;
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
    public execWindow execWin = null;
	boolean verboseJava = true;
    int labelY = 16;
	
    // helper method to add picture to frames.
	protected void setPicture(JLabel container, String picture, int x, int y, int width, int height) {				  
		ImageIcon icon = null;
		icon = new ImageIcon(picture);
		Image image = icon.getImage();
		Image newimage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		container.setIcon(new ImageIcon(newimage));			
		container.setBounds(x, y, width, height);
		container.revalidate();
		container.repaint();
	}	
	
	public void AddtoStatPanel(String label, String info) {
		stats.add(Box.createRigidArea(new Dimension(1, 1))); 
	    
		// html text
		String html = "<html> %s  %s</html>";
        String htmlLabel = "<i style=\"color: #B9B9B9;\">"+label+"</i>";
        String htmlInfo = "<i style=\"color: #FFFFFF;\">"+info+"</i>";
		JLabel detail = new JLabel(String.format(html, htmlLabel, htmlInfo), SwingConstants.LEFT);
		
		// label setup
		detail.setOpaque(true);
		detail.setAlignmentX(Component.CENTER_ALIGNMENT);	
		detail.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 11));
		detail.setBorder(LineBorder.createBlackLineBorder());
		detail.setBackground(new Color(100, 100 ,100, 50));
		detail.setPreferredSize(new Dimension(stats.getWidth(), 14));
		detail.setMaximumSize(new Dimension(stats.getWidth(), 14));   
		
		stats.add(detail);		
	}
	
    public void CreateStatPanel(String title, int sizeX, int sizeY) {		
    	stats = new JPanel();
		stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
		stats.setBorder(LineBorder.createBlackLineBorder());
		stats.setBackground(new Color(0,0,0,150));
		stats.setBounds(70, 3, (int)(sizeX*.62), (int)(sizeY*.73));
		JLabel stitle = new JLabel("<html><i style=\"margin-bottom: 3px;\">"+title+"</i></html>", SwingConstants.CENTER);
		stitle.setOpaque(true);
		stitle.setAlignmentX(Component.CENTER_ALIGNMENT);	
		stitle.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 10));
		stitle.setForeground(new Color(255, 255, 255));
		stitle.setBorder(LineBorder.createBlackLineBorder());
		stitle.setBackground(new Color(50,50,50,95));
		stitle.setPreferredSize(new Dimension(stats.getWidth(), 15));
		stitle.setMaximumSize(new Dimension(stats.getWidth(), 15));   
		stats.add(stitle);
    }
	
    public void actionListener(ActionEvent e) {
    	// override me!
    }

    
    // 0 lua code
    // 1 filter
    // 2 signature
    // 3 slotKey
    public ArrayList<String[]> export() {
    	// override me!
    	return null;
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
