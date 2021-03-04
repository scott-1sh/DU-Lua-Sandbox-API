/*    
HUD.java 
Copyright (C) 2020 Stephane Boivin (Discord: Nmare418#6397)

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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import chrriis.dj.nativeswing.NSComponentOptions;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import sandbox.execWindow;


public class HUD {
public int sizeX;
public int sizeY;
public JWebBrowser web = null;
public String html = "";
private String cr = "\r\n";
public int x = 0;
public int y = 0;
public int id = 0;
public JPanel panel = new JPanel();
public JInternalFrame frame;
Timer timer = null; 
//String baseHtml = "<html><head></head><body></body></html>\r\n";

String baseHtml = "<!DOCTYPE html>\r\n" + 
		"<html>\r\n" + 
		"<head>\r\n" + 
		"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
		"<style>\r\n" + 
		"#overlay {\r\n" + 
		"  position: fixed;" + 
		"  display: block;" + 
		"  width: 100%%;" + 
		"  height: 100%%;" + 
		"  top: 0;" + 
		"  left: 0;" + 
		"  right: 0;" + 
		"  bottom: 0;" + 
		"  background-color: rgba(0,0,0,0);" + 
		"  z-index: 2;" + 
		"}\r\n" + 
		"</style>\r\n</head><body></body></html>\r\n";
		
public HUD(String pupdateScript, int px, int py, int psizeX, int psizeY,  boolean pverboseJava) {
    x = px;
	y = py;
	sizeX = psizeX;
	sizeY = psizeY;
   
    if(pverboseJava) System.out.println("[JAVA] HUD created");	 		
}

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

public void buildFrame(Rectangle deskSize) {
	// if sizeX or sizeY is null, set to default position
	if(x+y+sizeX+sizeY == 0) {
		x = deskSize.width-605;
		y = 1;
		sizeX = 600;
		sizeY = (deskSize.height/2)-30;		
	}

    panel = new JPanel();		
	panel.setLayout(null);
	// panel.setVisible(false);
	panel.setBounds(x, y, sizeX, sizeY);
	panel.setBorder(LineBorder.createBlackLineBorder());
	
	panel.setBackground(Color.black);

	frame = new JInternalFrame ("HUD", true, true, false, true);
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);       
    
    frame.setBounds(panel.getBounds());
    frame.setPreferredSize(new Dimension(600, panel.getBounds().height));
    Container contentPane = frame.getContentPane ();
    contentPane.setLayout (new BorderLayout ());
    contentPane.add (
        new JScrollPane (
            panel, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
        BorderLayout.CENTER);
    frame.pack();
    
    LoadScreen();
    
    frame.setVisible(true);
}

    
// Initialize a screen
public void LoadScreen() {		
	 web = new JWebBrowser(JWebBrowser.destroyOnFinalization());
	// NSComponentOptions.constrainVisibility(),NSComponentOptions.destroyOnFinalization()
    web.setStatusBarVisible(false);
	web.setMenuBarVisible(false);
	// web.setBorder(null);		
	web.setBounds(0, 0, sizeX-13, sizeY-37);
	web.setButtonBarVisible(false);
	web.setBarsVisible(false);
	web.setAutoscrolls(false);
	// web.setDoubleBuffered(true);
	web.setBackground(Color.black);
	web.setJavascriptEnabled(true);

	panel.add(web, 1, 0);
	panel.setVisible(true);
	web.setHTMLContent(baseHtml);
} 

public void StartUpdateTimer(String updateScript) {

	timer = new Timer(16, new ActionListener() { 
		  @Override
		  public void actionPerformed(ActionEvent ae) {

			  // run script/
		  
		  
		       }     			  
		  }
		   
	   });	  

	  timer.start();
}

	
	
	
}


/*
@Override
public void update(String[] param) {
//	String pname = param[2];
	String phtml = param[1];
	String pcommand = param[0];
	 
    if(pcommand.equals("setSVG")) {

    	// create an overlay
    	html = "<div id=\"overlay\">"+phtml+"</div>";
    	html = html.replace("\"", "\\\"");
    	html = "document.getElementsByTagName(\"BODY\")[0].innerHTML = document.getElementsByTagName(\"BODY\")[0].innerHTML + \""+html+"\";";	    	
    	html = html.replace( "\r", "");
    	html = html.replace( "\n", "");	    	
    	
    	web.executeJavascript(html);
        // System.out.println("test:"+html);
		
    }	
    if(pcommand.equals("setCenteredText")) {
        
    	String centerhtml = "<div style=\"color: #FFFFFF; font-size: 5VW; background-color: #000; width: 100%; height: 100%; text-align: center; line-height: 100VH;\">"+phtml+"</div>";	    
   
    	// create an overlay
    	html = "<div id=\"overlay\">"+centerhtml+"</div>";
    	html = html.replace("\"", "\\\"");
    	html = "document.getElementsByTagName(\"BODY\")[0].innerHTML = document.getElementsByTagName(\"BODY\")[0].innerHTML + \""+html+"\";";	    	
    	html = html.replace( "\r", "");
    	html = html.replace( "\n", "");	    	
    	
    	web.executeJavascript(html);
        // System.out.println("test:"+html);
		
    }	
    if(pcommand.equals("addContent")) {

    	// create an overlay
    	html = "<div id=\"overlay\">"+phtml+"</div>";
    	html = html.replace("\"", "\\\"");
    	html = "document.getElementsByTagName(\"BODY\")[0].innerHTML = document.getElementsByTagName(\"BODY\")[0].innerHTML + \""+html+"\";";	    	
    	html = html.replace( "\r", "");
    	html = html.replace( "\n", "");	    	
    	
    	web.executeJavascript(html);
        // System.out.println("test:"+html);
    }	
    if(pcommand.equals("clear")) {
    	web.executeJavascript("document.getElementsByTagName(\"BODY\")[0].innerHTML = \"\";");
    }	
		
}

*/
}
