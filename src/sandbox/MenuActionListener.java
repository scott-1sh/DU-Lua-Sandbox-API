/*    
    MenuActionListener.java 
    Copyright (C) 2020 Stephane Boivin (Discord: Nmare418#6397)
    
    This file is part of "DU lua sandbox API".

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
package sandbox;

import java.awt.Desktop;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONObject;

class MenuActionListener implements ActionListener {
	private JFrame frame;
	private execLUA sandbox;
	private int screenId;
	private String sourceAPI;
	private DUElement[] elements;
	private static String preloadFile;
	private execWindow eWindow;
	  
	public MenuActionListener(execWindow peWindow, JFrame pframe, String psourceAPI, DUElement[] pelements, int pscreenId, String ppreloadFile) {
	    this.frame = pframe;
	    this.eWindow = peWindow;
	    // this.sandbox = psandbox;
		this.screenId = pscreenId;
		this.sourceAPI = psourceAPI;
		this.elements = pelements;
		this.preloadFile = ppreloadFile;
//		System.out.println(sourceAPI);
	}
	  
	public void screenShot(String pfileName) throws Exception
	{ 
		String fileName = pfileName;
		
		// default name if empty
		if(pfileName.isBlank()) {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		  Calendar now = Calendar.getInstance();
		  fileName = "export/screenshot"+formatter.format(now.getTime())+".jpg";		  
		}	  
		  
		GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices(); 
		
		System.out.println("screenshot on screenId: " + this.screenId);
		  
		Robot robot = new Robot(screens[this.screenId].getDefaultConfiguration().getDevice());
		BufferedImage screenShot = robot.createScreenCapture(new Rectangle(screens[this.screenId].getDefaultConfiguration().getBounds()));
		ImageIO.write(screenShot, "JPG", new File(fileName));
		System.out.println("screenshot saved as "+fileName);
	}
	  
	  public void print() throws Exception {
		// capture the screen
		screenShot("export/printjob.jpg");
		//TimeUnit.SECONDS.sleep(1);	
		// print  
	    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	    pras.add(new Copies(1));
	    pras.add(OrientationRequested.LANDSCAPE);
	    PrintService pss[] = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.JPEG, pras);
	    if (pss.length == 0)	
	      throw new RuntimeException("No printer services available.");
	    // PrintService ps = pss[0];
	    PrintService ps = ServiceUI.printDialog(null, 300, 200, pss, PrintServiceLookup.lookupDefaultPrintService(), DocFlavor.INPUT_STREAM.JPEG, pras);
	    System.out.println("Printing to " + ps);
	    
	    if(ps != null) {
	      DocPrintJob job = ps.createPrintJob();
	      FileInputStream fin = new FileInputStream("export/printjob.jpg");
	      Doc doc = new SimpleDoc(fin, DocFlavor.INPUT_STREAM.GIF, null);
	      job.print(doc, pras);
	      fin.close();    	
	    }

	  }

	  private void openfile(String pFile) {
		try {
		  Desktop desktop = null;
		  if (Desktop.isDesktopSupported()) {
		     desktop = Desktop.getDesktop();
		  }
		
		  desktop.open(new File(pFile));
		} catch (IOException ioe) { ioe.printStackTrace(); }
	  } 

	  
	  private String escape(String raw) {
		    String escaped = raw;
		    escaped = escaped.replace("\\", "\\\\");
		    escaped = escaped.replace("\"", "\\\"");
		    escaped = escaped.replace("\b", "\\b");
		    escaped = escaped.replace("\f", "\\f");
		    escaped = escaped.replace("\n", "\\n");
		    escaped = escaped.replace("\r", "");
		    escaped = escaped.replace("\t", "\\t");
		   // escaped = escaped.replace("/", "\\/");
		    return escaped;
		}
	  
	  // export to json
	  private void JsonExport(DUElement[] elements)
	  {
		 String strSlots = "";
		 String strJSON = "{\"slots\":{%s},\"handlers\":[%s],\"methods\":[],\"events\":[]}"; 
		 int slotId = 0; 
		 // JSONObject jsonObject = new JSONObject();
		 //  text = jsonObject.escape(text);

		  // slots
		  for (DUElement elem : elements ) {
			  if(elem == null || elem.GetType().equals("duJavaAPI.Unit")) continue;
			  strSlots += String.format("\"%d\":{\"name\":\"%s\",\"type\":{\"events\":[],\"methods\":[]}},", slotId, elem.name );			  
			  slotId++;
			  //script += elem.element.export(); 
		  }
		  // fill empty slots
		  for(int i = slotId; i < 10; i++) {
			  strSlots += String.format("\"%d\":{\"name\":\"%s\",\"type\":{\"events\":[],\"methods\":[]}},", i, "slot"+(i+1) );			  
		  }
		  strSlots += "\"-1\":{\"name\":\"unit\",\"type\":{\"events\":[],\"methods\":[]}},"
		  		    +"\"-2\":{\"name\":\"system\",\"type\":{\"events\":[],\"methods\":[]}},"
		  		    +"\"-3\":{\"name\":\"library\",\"type\":{\"events\":[],\"methods\":[]}}";
		
		 // handlers (and scripts) 
		 String strHandlers = "", strFilter;
		 String fHandler = "{\"code\":\"%s\",\"filter\":%s,\"key\":\"%s\"},";  
		 String fFilter = "{\"args\":%s,\"signature\":\"%s\",\"slotKey\":\"%s\"}";
		 int iKey = 0;
 	     // Scan each elements to find scripts
		  for (DUElement elem : elements ) {
			  if(elem == null || elem.element.export() == null) continue;
			  // handlers 
              for(String[] sHandler : elem.element.export()) {
            	  strFilter = String.format(fFilter, sHandler[1], sHandler[2], sHandler[3] );
            	  strHandlers += String.format(fHandler, escape(sHandler[0].stripTrailing()), strFilter,  iKey );			  
            	  iKey++; 
              }
		  }
		  strHandlers = strHandlers.substring(0,strHandlers.length()-1); // remove last virgul
		  
		  luaExport(String.format(strJSON, strSlots, strHandlers), "JSON", ".txt");
	  }  
	  
	  
	  // export to file
	  private void luaExport(String luaSource, String filePrefix, String ext) {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
	      Calendar now = Calendar.getInstance();
		  String fileName = "export/"+filePrefix+"_export"+formatter.format(now.getTime())+ext;
		  
		  try {
			Files.writeString(Paths.get(fileName), luaSource);
		  } catch (IOException e) { e.printStackTrace();}

		  System.out.println("API exported to  "+fileName ); 
		  openfile(fileName);
	  }  
	  
	  // scripts output to text
	  static public String luaScriptToText(DUElement[] elements) {
	      String script = "-- Generated by DU Lua Sandbox ("+MAIN.version+")\n\n";
	      
		  // Scan each elements to find scripts
		  for (DUElement elem : elements ) {
			  if(elem == null || elem.element.export() == null) continue;

			  for(String[] sHandler : elem.element.export()) {
            	  
				   script += "-----------------------------------------\n";
				   script += "-- Element name: "+elem.name+"\n";
				   script += "-- Element: "+elem.GetType() +"\n";
				   script += "-- Event: "+sHandler[1]+" \n";
				   script += "-----------------------------------------\n";
				   script += sHandler[0] + "\n\n";			  
            	   
              }			   
		  }
		  
		  return script;
	  }
	  

	  // load new app from filesystem dialog. 
	  public void LoadFrom(boolean stop) {
		  JFileChooser fileChooser = new JFileChooser(preloadFile);
		  fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		  fileChooser.setDialogTitle("Load from file system");
		  fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		  FileFilter filter = new FileNameExtensionFilter("lua File", "lua");
		  fileChooser.addChoosableFileFilter(filter);
		  fileChooser.setFileFilter(filter);
		  int result = fileChooser.showOpenDialog(execWindow.frame);
		  if (result == JFileChooser.APPROVE_OPTION) {
		      File selectedFile = fileChooser.getSelectedFile();
//		      System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		      execWindow.preloadFile = selectedFile.getAbsolutePath();
			  execWindow.StartApplication(execWindow.preloadFile);
			  if(stop) execWindow.StopApplication(this.elements);
 
		  }
	  }
	  
	  public void actionPerformed(ActionEvent e) {		  

	    // System.out.println("Selected: " + e.getActionCommand());
	  	  
	    switch (e.getActionCommand()) {
			case "Exit":
				System.exit(0);
		        return;
			case "Load from file system":
				LoadFrom(true);				
				return;
			case "Load in a new window":
				LoadFrom(false);
				return;
			case "Reload (F9)":
				execWindow.StartApplication(this.preloadFile);
				return;
			case "Print":
			    try {
				   print();
			    } catch (Exception e2) { e2.printStackTrace(); }
				return;
			case "Export LUA to .txt":
				luaExport(luaScriptToText(this.elements), "lua", ".lua");
				return;
			case "Export API to .txt":
				// System.out.println(this.sourceAPI);
				luaExport(this.sourceAPI, "api", ".lua");
				return;
			case "Export to JSON":
				JsonExport(this.elements);
				return;
			case "Screenshot":
			    try { 
			    	screenShot(""); 
			    } 
			    catch (Exception e1) { e1.printStackTrace(); }
				return;
			case "About":

	    }

	  }

}
