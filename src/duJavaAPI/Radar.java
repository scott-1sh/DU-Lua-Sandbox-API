/*    
    Radar.java
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
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import offlineEditor.execWindow;

public class Radar extends BaseElement {
//	public static List<Construct> construct = new ArrayList<>();    
	public String html = "";
    public JWebBrowser web = null;
    public String scriptEnter = "";
    public String scriptExit = "";
    int range = 10000;
 	int sizeX = 400;
    int sizeY = 400;
	Timer timer = null;    
    String baseHtml = "<!DOCTYPE html>\r\n" + 
    		"<html>\r\n" + 
    		"<head>\r\n" + 
    		"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
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
    		"  background-color: rgba(0,0,0,0);" +  //  background-color: rgba(0,0,0,0.5);\r\n" + 
    		"  z-index: 2;" + 
    		"} </style></head><body style=\"overflow-y:hidden;overflow-x:hidden; background-color: rgba(0,190,0,1);\"></body></html>";
    private String radarSVG = "<svg x=\"0px\" y=\"0px\" width=\"400px\" height=\"400px\" style=\"background-color: rgba(0,190,0,1);\">\r\n" +
    		"<g x=\"0px\" y=\"0px\" width=\"400px\" height=\"400px\" >\r\n" +
    		"   <line x1=\"0\"  y1=\"188\" x2=\"376\" y2=\"188\" stroke-dasharray=\"1, 1\" stroke=\"#000000\"  />" +
    		"   <line x1=\"188\"  y1=\"0\" x2=\"188\"   y2=\"376\" stroke-dasharray=\"1, 1\" stroke=\"#000000\" />" +
    		"	<circle fill=\"none\" stroke=\"#000000\" stroke-width=\"0.5\" cx=\"188\" cy=\"188\" r=\"5\"/>\r\n" + 
    		"	<circle fill=\"none\" stroke=\"#000000\" stroke-width=\"0.5\" cx=\"188\" cy=\"188\" r=\"45\"/>\r\n" + 
    		"	<circle fill=\"none\" stroke=\"#000000\" stroke-width=\"0.5\" cx=\"188\" cy=\"188\" r=\"85\"/>\r\n" + 
    		"	<circle fill=\"none\" stroke=\"#000000\" stroke-width=\"0.5\" cx=\"188\" cy=\"188\" r=\"125\"/>\r\n" + 
    		"	<circle fill=\"none\" stroke=\"#000000\" stroke-width=\"0.5\" cx=\"188\" cy=\"188\" r=\"165\"/>\r\n" + 
    		"	<circle fill=\"none\" stroke=\"#FFFFFF\" stroke-width=\"0.5\" cx=\"188\" cy=\"188\" r=\"185\"/>\r\n" +
    		"   <text x=\"360\" y=\"187\" font-family=\"Verdana\" font-size=\"15\">x</text>" +
    		"   <text x=\"189\" y=\"14\" font-family=\"Verdana\" font-size=\"15\">y</text>" +
    		"   <text x=\"5\" y=\"14\" font-family=\"Verdana\" font-size=\"13\">Map view</text>" +
    		String.format("   <text x=\"295\" y=\"385\" font-family=\"Verdana\" font-size=\"10\">Range: %skm</text>",(range/1000)) +
    	    "</g>%s</svg>";
    
	public Radar(int pid, String pname, int px, int py, int prange, String pscriptEnter, String pscriptExit, boolean pverboseJava )  {

		name = pname;
		x = px;
		y = py;
//		construct = pconstruct;
		scriptEnter = pscriptEnter;
		scriptExit = pscriptExit;

		range = prange;

		verboseJava = pverboseJava;

		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px, py, sizeX+4, sizeY+24);
		panel.setBorder(LineBorder.createBlackLineBorder());		
		panel.setBackground(Color.black);

		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, sizeX, 16);	
		panel.add(lblname, 1, 0);		
    
		// lua require and interface 
		urlAPI = "src/duElementAPI/radar.lua";
        luaCall = name+" = createInterfaceRadar(\""+pid+"\", \""+name+"\")";		
        if(verboseJava) System.out.println("[JAVA] Radar ["+name+"] created");	 		
	}

	@Override
    public void LoadScreen(String phtml) {		
		web = new JWebBrowser();
        web.setStatusBarVisible(false);
		web.setMenuBarVisible(false);
		// web.setBorder(null);		
		web.setBounds(2, 22, sizeX, sizeY);
		web.setButtonBarVisible(false);
		web.setBarsVisible(false);
		web.setAutoscrolls(false);
		// web.setDoubleBuffered(true);
		web.setBackground(Color.black);
 		
		web.setJavascriptEnabled(true);
		// web.navigate("https://www.dualthegame.com/fr/");
		web.setHTMLContent(baseHtml);

		panel.add(web, 1, 0);
		panel.setVisible(true);

		startTimer((float).3); 
    }
   
    public void startTimer(Float psec) {
        int msec = Math.round(psec*1000);
        if(verboseJava) System.out.println("[JAVA] timer  ["+name+"]  Updates is now active with a delay of "+msec+" ms");
        // timer.schedule(this, sec, sec);
    	  timer = new Timer(msec, new ActionListener() { 
    		  @Override
    		  public void actionPerformed(ActionEvent ae) {
    			  RefreshRadar();
    			  
    			  // tell the construct when to come back in the radar zone
    			  float bouncingRange = range+(range/10);
			   	  double distance = 0; 
    					  
    			  // move dyn constructs
    			  for(int i=0; i< sandbox.worldConstruct.size(); i++) {
    	              if(sandbox.worldConstruct.get(i).ctype == "static") continue;			  
    				  Construct tmpConstruct = sandbox.worldConstruct.get(i);
    				  
//					 System.out.println("test "+tmpConstruct.name+"  "+tmpConstruct.speed[0]+"*"+tmpConstruct.speed[1]+"*"+tmpConstruct.speed[2]);	
	  
			          distance = Math.sqrt((Math.pow(0 - tmpConstruct.pos[0],2) + Math.pow(0 - tmpConstruct.pos[1],2) + Math.pow(0 - tmpConstruct.pos[2],2))) ;  
					  
					  // event ZONE OUT
					  if(distance > range) { 					     
 						  if(!tmpConstruct.outOfRange) {
						    if(verboseJava) System.out.println(String.format("[JAVA] ["+name+"]  Construct %s %s (%s) ZONE OUT", sandbox.worldConstruct.get(i).id, sandbox.worldConstruct.get(i).name, sandbox.worldConstruct.get(i).owner));
						    sandbox.RUN("self="+name+" id="+tmpConstruct.id+" "+scriptExit, name+":leave(id)");
						    tmpConstruct.outOfRange = true;
 						  }
					  }
					  
					  // event ZONE IN
					  if(tmpConstruct.outOfRange && distance < range) {
					    if(verboseJava) System.out.println(String.format("[JAVA] ["+name+"] Construct %s %s (%s) ZONE IN",sandbox.worldConstruct.get(i).id, sandbox.worldConstruct.get(i).name, sandbox.worldConstruct.get(i).owner));
					    sandbox.RUN("self="+name+" id="+tmpConstruct.id+" "+scriptEnter, name+":enter(id)");
					    tmpConstruct.outOfRange = false;
					  }
					      				  
					  // rebuild 
					  sandbox.worldConstruct.set(i, tmpConstruct);
    			  }     			  
    		  }
    		   
    	   });	  

    	  timer.start();
    }
    
	public void RefreshRadar() {
		String svgPoint = "";
   	    String opacity;
   	    double distance = 0; 
		
        for(Construct b:sandbox.worldConstruct) {
        	 // System.out.println("construct:"+b.name+"  owner-> "+b.owner );
        	 String color = "#FFFFFF";
     		 double xx = 0, yy = 0; 
        	 int centerX = 188;
        	 int centerY = 188;
        	 xx = ((b.pos[0] * 188) / range);
        	 yy = ((b.pos[1] * 188) / range);        	 
        	 if(b.owner <= 0) {  // .equals("unreachable") 
        		color = "#808080";
        	 };
        	 
        	 distance = Math.sqrt((Math.pow(0 - b.pos[0],2) + Math.pow(0 - b.pos[1],2) + Math.pow(0 - b.pos[2],2))) ;  
        	 
        	 // blur constructs out of range
             opacity = "fill-opacity=\"1\"";
        	 if(distance > range) { opacity = "fill-opacity=\"0.4\""; color = "#242424"; } 
        	 
        	 // display dots
        	 if(b.ctype.equals("static")) {
        		 svgPoint = svgPoint+" <rect x=\""+(centerX+xx-2)+"\" y=\""+(centerY-yy-2)+"\" rx=\"1\" rx=\"1\" width=\"9\" height=\"9\" stroke=\"black\" stroke-width=\"1\" fill=\""+color+"\" "+opacity+"/>";
        	 } else { 
        		 svgPoint = svgPoint+" <circle cx=\""+(centerX+xx-2)+"\" cy=\""+(centerY-yy-2)+"\" r=\"4\" stroke=\"black\" stroke-width=\"1\" fill=\""+color+"\" "+opacity+"/>"; 
        	 }

        	 svgPoint = svgPoint + String.format("<text x=\""+(centerX+5+xx)+"\" y=\""+(centerY-yy)+"\" fill=\"black\" font-family=\"Arial\" font-size=\"11\" "+opacity+">%s - %s</text>",b.id,b.name); 
        }
		
		// create an overlay         
    	html = String.format(radarSVG, svgPoint);
    	html = "document.getElementsByTagName(\"BODY\")[0].innerHTML = \""+html.replace("\"", "\\\"")+"\";";	    	
    	html = html.replace( "\r", "");
    	html = html.replace( "\n", "");	    	    	
    	// System.out.println("OUT: "+html);    	
    	web.executeJavascript(html);

	}

	// Export all scripts related to this element to text
	@Override
	public String export() {
	  String script = "";	
		// timers
	  script += "-----------------------------------------\n";
	  script += "-- Element name: "+this.name+"\n";
	  script += "-- Element: Radar\n";
	  script += "-- Event: enter(id)\n";
	  script += "-----------------------------------------\n";
	  script += scriptEnter+"\n\n";
      script += "\n\n\n\n";
	  
	  script += "-----------------------------------------\n";
	  script += "-- Element name: "+this.name+"\n";
	  script += "-- Element: Radar\n";
	  script += "-- Event: leave(id)\n";
	  script += "-----------------------------------------\n";
	  script += scriptExit+"\n";
      script += "\n\n\n\n";
	  
      
	  return script;
	}
	
	@Override	
    public Object get(String[] param) {    	
		String pcommand = param[0];
		int id = 0;
		
		switch (pcommand) {
			case "getRange":					
		         return range;
			case "getEntries":
				 LuaTable result = new LuaTable();
		   	     double distance = 0;
		   	     int iCnt = 0;
				 for(int i = 0; i < sandbox.worldConstruct.size(); i++) {
				   // System.out.println(i+" id = "+construct.get(i).id ) ;
 	        	   distance = Math.sqrt((Math.pow(0 - sandbox.worldConstruct.get(i).pos[0],2) + Math.pow(0 - sandbox.worldConstruct.get(i).pos[1],2) + Math.pow(0 - sandbox.worldConstruct.get(i).pos[2],2))) ;                     
				   if(distance <= range) {
 				     iCnt++;
				     result.set(iCnt, CoerceJavaToLua.coerce(sandbox.worldConstruct.get(i).id));					   
				   }
				 }
				 
				 return result;
			case "getConstructName":
  				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).name;
			case "getConstructOwner":
 				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).owner;
			case "getConstructSize":
 				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).size;				
			case "getConstructType":
 				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).ctype;				
			case "getConstructWorldPos":
 				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).pos;
			case "getConstructWorldVelocity":
				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).speed;
			case "getConstructWorldAcceleration":
				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).speed;
			case "getConstructPos":
				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).pos;
			case "getConstructVelocity":
				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).speed;
			case "getConstructAcceleration":
				 id = Integer.valueOf(param[1])-1;
			     return sandbox.worldConstruct.get(id).speed;			
			}

		return "";
    }
	

}