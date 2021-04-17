/*    
HUD.java 
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
import sandbox.execLUA;
import sandbox.execTimer;
import sandbox.execWindow;


public class HUD {
public int sizeX;
public int sizeY;
public int speed = 40;
public String updateScript;
private JWebBrowser web = null;
public String html = "";
private String cr = "\r\n";
public int x = 0;
public int y = 0;
public int id = 0;
public JPanel panel = new JPanel();
public JInternalFrame frame;
public static Timer timer = null; 
//String baseHtml = "<html><head></head><body></body></html>\r\n";
String hudBg;
String baseHtml = "<html>\r\n" + 
		"<head>\r\n" + 
        "</head><body>" +
		"</body></html>\r\n";
		
public HUD(String pupdateScript, int pspeed, int px, int py, int psizeX, int psizeY,  boolean pverboseJava) {
    x = px;
	y = py;
	speed = pspeed;
	sizeX = psizeX;
	sizeY = psizeY;
	updateScript = pupdateScript;
	
	hudBg = "<div style=\"overflow-y:hidden;overflow-x:hidden; "
                       + " background-attachment: fixed; "
                       + " background-size: cover;"
                       + "background-image: "+loadEncodedBG("./src/pictures/hudbg_enc64.txt")+"; "
                       + "width: 100vw; height: 100vh;\">";
    
	baseHtml = "<html>\r\n" + 
	           "<head>\r\n" + 
               "</head>" +
              "<style>" +
	           "body {" +
	               "height: 100%;" +
	               "width: 100%;" +
	               "margin: 0;" +
	               "padding: 0;" +
	           "}" +
	           "</style>" +
               "<body>"+hudBg+"</body></html>\r\n";
	           
	
	
    if(pverboseJava) System.out.println("[JAVA] HUD created");	 		
}

private static String loadEncodedBG(String txtFile) {	
	try {
	  return new String(Files.readAllBytes(Paths.get(txtFile))); 
	} catch (IOException e) 
	{ return "(Encode64) bg file not found: "+txtFile; }

}
/*
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
*/

public void buildFrame(Rectangle deskSize) {
	// if sizeX or sizeY is null, set to default position
	if(x+y+sizeX+sizeY == 0) {
		x = deskSize.width-605;
		y = 0;
		sizeX = 600;
		sizeY = (deskSize.height/2)-36;		
	}

    panel = new JPanel();		
	panel.setLayout(null);
	// panel.setVisible(false);
	panel.setBounds(x, y, sizeX, sizeY);
	panel.setBorder(LineBorder.createBlackLineBorder());
	
	panel.setBackground(Color.black);

    LoadScreen();

	frame = new JInternalFrame ("HUD", true, true, false, true);
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);       
    
    frame.setBounds(panel.getBounds());
    frame.setPreferredSize(new Dimension(panel.getBounds().width, panel.getBounds().height));
    Container contentPane = frame.getContentPane ();
    contentPane.setLayout (new BorderLayout ());
    contentPane.add (
        new JScrollPane (
            panel, 
            JScrollPane.VERTICAL_SCROLLBAR_NEVER , 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
        BorderLayout.CENTER);
    frame.pack();
/*
    frame.addComponentListener(new ComponentAdapter() 
    {  
            public void componentResized(ComponentEvent evt) {                
                panel.setBounds(frame.getBounds());
                web.setBounds(frame.getBounds());
                frame.pack();
                web.setHTMLContent(html);            }
    });
*/    
   

    frame.setVisible(true);
}
	

public void update(String htmlDoc) {
	// create an overlay
	html = "<style>" +
	           "body {" +
            "height: 100%;" +
            "width: 100%;" +
            "margin: 0;" +
            "padding: 0;" +
        "}" +
        "</style>"; 
	html += hudBg+htmlDoc+"</div>";
	html = html.replace("\"", "\\\"");
	html = "document.getElementsByTagName(\"HTML\")[0].innerHTML =  \""+html+"\";";	    	
	html = html.replace( "\r", "");
	html = html.replace( "\n", "");
	// System.out.println("HTML OUT: "+html.replaceAll("<[^>]*>", ""));	 	
	web.executeJavascript(html);
}

    
// Initialize a screen
public void LoadScreen() {		
	 web = new JWebBrowser(JWebBrowser.destroyOnFinalization());
	// NSComponentOptions.constrainVisibility(),NSComponentOptions.destroyOnFinalization()
	web.setStatusBarVisible(false);
	web.setMenuBarVisible(false);
	// web.setBorder(null);		
	web.setBounds(0, 0, sizeX-13, sizeY-36);
	web.setButtonBarVisible(false);
	web.setBarsVisible(false);
	web.setAutoscrolls(false);
	 web.setDoubleBuffered(true);
	// web.setBackground(Color.black);
	web.setJavascriptEnabled(true);
	// web.setHTMLContent(baseHtml);
	web.setHTMLContent(baseHtml);
	panel.add(web, 1, 0);
	
	panel.setVisible(true);
	web.setHTMLContent(baseHtml);

	
}


public void StartUpdateTimer(String pupdateScript, execLUA sandbox) {
	if(execWindow.verboseJava) System.out.println("[JAVA] Hud update events are now triggered every "+speed+"ms");
	timer = new Timer(speed, new ActionListener() { 
		  @Override
		  public void actionPerformed(ActionEvent ae) {
			 //  System.out.println("OUT:"+pupdateScript);
			  sandbox.RUN(pupdateScript, "system:update()");
		       }     			  
		  });	  
	 timer.start();

}

// Export all scripts related to this element
// used for text and json export
		
public ArrayList<String[]> export() {
   ArrayList<String[]> listScript = new ArrayList<String[]>();
   
   if(updateScript != null) {
	   // update
	   String sign = "update()";
	   String args = "[]";
	   String slotKey = "-2";
	   listScript.add(new String[]{updateScript, args, sign, slotKey}); 
   }
   return listScript;
}
	
}
