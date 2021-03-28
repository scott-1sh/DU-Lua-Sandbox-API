/*    
    Screen.java 
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import chrriis.dj.nativeswing.NSComponentOptions;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import sandbox.execWindow;


public class Screen extends BaseElement {
    public int sizeX;
    public int sizeY;
    public String html = "";
    private String cr = "\r\n";

//    String baseHtml = "<html><head></head><body></body></html>\r\n";
    
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
    		"</style>\r\n</head><body><div style=\"color: #FFFFFF; background-color: #000000; width: 100%; height: 100%; \"> </div></body></html>\r\n";
    		
    // public Screen(int pid, String pname, int px, int py, String phtml) {this(pid, pname, px, py, 1014, 620, phtml);}    
	public Screen(int pid, String pname, int px, int py, int psizeX, int psizeY, boolean pverboseJava) {
		
		name = pname;
		x = px;
		y = py;
		sizeX = psizeX;
		sizeY = psizeY;
		id = pid;

		verboseJava = pverboseJava;
		
		// set the size
		// baseHtml = String.format(baseHtml, sizeX, sizeY);
		
		panel = new JPanel();		
		panel.setLayout(null);
		// panel.setVisible(false);
		panel.setBounds(px, py, psizeX+6, psizeY+25);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);

		// lua require and interface 
		urlAPI = "src/duElementAPI/screen.lua";
        luaCall = pname+" = createInterfaceScreen(\""+pid+"\", \""+pname+"\")";
        
        if(verboseJava) System.out.println("[JAVA] Screen ["+name+"] created");	 		
	}

	    
	// Initialize a screen
	@Override
    public void LoadScreen(String phtml) {		
		 web = new JWebBrowser(JWebBrowser.destroyOnFinalization()); 
		// NSComponentOptions.constrainVisibility(),NSComponentOptions.destroyOnFinalization()
        web.setStatusBarVisible(false);
		web.setMenuBarVisible(false);
		// web.setBorder(null);		
		web.setBounds(1, 0, sizeX, sizeY);
		web.setButtonBarVisible(false);
		web.setBarsVisible(false);
		web.setAutoscrolls(false);
		web.setDoubleBuffered(true);
		web.setBackground(Color.black);
 
		
		web.setJavascriptEnabled(true);
		// web.navigate("https://www.dualthegame.com/fr/");
         
		web.getNativeComponent().addMouseMotionListener(new MouseMotionAdapter() {
			 @Override
			public void mouseMoved(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
		});
		
		web.getNativeComponent().addMouseListener(new MouseAdapter() {
	        @Override
			public void mousePressed(MouseEvent me) { mouseState = 1; } /* System.out.println("Left Click!"); e.getX(), e.getY() */
	        @Override
			public void mouseReleased(MouseEvent me) { mouseState = 0; } /* System.out.println("released");  */
	    });
		web.setHTMLContent(baseHtml);
	
		panel.add(web, 1, 0);
		panel.setVisible(true);
		web.setHTMLContent(baseHtml);
    }

	@Override
    public void update(String[] param) {
//		String pname = param[2];
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


}
