/*    
    Screen.java 
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import offlineEditor.execWindow;


public class Screen extends BaseElement {
    public int sizeX;
    public int sizeY;
    public JWebBrowser web = null;
    public String html = "";
    private String cr = "\r\n";

//    String baseHtml = "<html><head></head><body></body></html>\r\n";
    
    String baseHtml = "<!DOCTYPE html>\r\n" + 
    		"<html>\r\n" + 
    		"<head>\r\n" + 
    		"<meta name=\"viewport\" content=\"width=1024, height=612, initial-scale=1\">\r\n" + 
    		"<style>\r\n" + 
    		"#overlay {\r\n" + 
    		"  position: fixed;" + 
    		"  display: block;" + 
    		"  width: 100%;" + 
    		"  height: 100%;" + 
    		"  top: 0;" + 
    		"  left: 0;" + 
    		"  right: 0;" + 
    		"  bottom: 0;" + 
    		"  background-color: rgba(0,0,0,0);" +  //     		"  background-color: rgba(0,0,0,0.5);\r\n" + 
    		"  z-index: 2;" + 
    		"}" + 
    		"</style></head><body></body></html>\r\n";
    		
    // public Screen(int pid, String pname, int px, int py, String phtml) {this(pid, pname, px, py, 1014, 620, phtml);}    
	public Screen(int pid, String pname, int px, int py, int psizeX, int psizeY, boolean pverboseJava) {
		
		name = pname;
		x = px;
		y = py;
		sizeX = psizeX;
		sizeY = psizeY;

		verboseJava = pverboseJava;
		
		html = baseHtml;
		
		panel = new JPanel();		
		panel.setLayout(null);
		// panel.setVisible(false);
		panel.setBounds(px, py, psizeX+4, psizeY+22);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);

		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, sizeX, 16);	
		// add mouse events to the panel
		panel.add(lblname, 1, 0);

		// lua require and interface 
		urlAPI = "src/duElementAPI/screen.lua";
        luaCall = pname+" = createInterfaceScreen(\""+pid+"\", \""+pname+"\")";
        
        if(verboseJava) System.out.println("[JAVA] Screen ["+name+"] created");	 		
	}

	    
	// Initialize a screen
	@Override
    public void LoadScreen(String phtml) {		
		web = new JWebBrowser();
        web.setStatusBarVisible(false);
		web.setMenuBarVisible(false);
		// web.setBorder(null);		
		web.setBounds(2, 20, sizeX, sizeY);
		web.setButtonBarVisible(false);
		web.setBarsVisible(false);
		web.setAutoscrolls(false);
		// web.setDoubleBuffered(true);
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
		// System.out.println("[JAVA] LOADSCREEN " + name); 
    }
    
	@Override
    public void update(String[] param) {
		String pname = param[2];
		String phtml = param[1];
		String pcommand = param[0];
		 
	    if(pcommand.equals("addContent")) {
	    	// html += "<div id=\"overlay\">"+phtml+"</div>";

//			web.setHTMLContent(baseHtml);

	    	// create an overlay
	    	html = "<div id=\"overlay\">"+phtml+"</div>";
	    	html = html.replace("\"", "\\\"");
	    	html = "document.getElementsByTagName(\"BODY\")[0].innerHTML = document.getElementsByTagName(\"BODY\")[0].innerHTML + \""+html+"\";";	    	
	    	html = html.replace( "\r", "");
	    	html = html.replace( "\n", "");	    	
	    	
	    	web.executeJavascript(html);
            // System.out.println("test:"+html);
	    	// web.executeJavascript("document.getElementsByTagName(\"BODY\")[0].innerHTML = \"hello wolrd\"; ");
			
	    }	
	    if(pcommand.equals("clear")) {
	    	// web.executeJavascript("document.write(\""+baseHtml+"\");");
			// web.setHTMLContent(baseHtml);
	    	web.executeJavascript("document.getElementsByTagName(\"BODY\")[0].innerHTML = \"\";");

	    }	
   		
		// System.out.println("[JAVA] Screen " + param[2] + " " + param[0]); 
	}


}
