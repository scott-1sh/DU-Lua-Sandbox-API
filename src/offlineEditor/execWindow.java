/*    
    execWindow.java 
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

package offlineEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import duJavaAPI.Construct;
import duJavaAPI.Player;

public class execWindow extends Frame {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public static execLUA sandbox;
	public String name = "test";
    static JDesktopPane desktop = null;
    public boolean verboseJava = false;
    public boolean verboseLua = false;
	static String preloadFile;
	static JMenuBar mb;
	static JMenu fileMenu, exportMenu, helpMenu;
	static int screenId = 0;
	public static int  mouseWheel = 0;
	public static boolean lockedView = false;
	public static boolean frozen = false;
	private static JComboBox runToolBox;
	// static JMenuItem mItem_exit, mItem_export_txt, mItem_ ;

	
	public static void StopApplication(DUElement[] elements) {

 	        // Run all Stop() scripts 
		   for (DUElement elem : elements ) {
		      if(elem == null) continue;
		      if(elem.element.luaScriptStop != null) sandbox.RUN("self="+elem.element.name+" "+elem.element.luaScriptStop, elem.name+":stop()" );
  		   }

		// closing all databases
		   for (DUElement elem : elements) {
			   if(elem == null) continue;
			   if(elem.GetType().equals("duJavaAPI.DataBank")) {
				   System.out.println("closing DB "+elem.name);
				   String[] cmd1 = {"closeDB"};
				   elem.element.update(cmd1);
			   }
		   }
		   frame.setVisible(false);
           NativeInterface.close();
		   System.out.println("dead");
		   System.exit(0);
	}
	
	public static void StartApplication(String preload)
	{

	   final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator;
	   String cmd = javaBin+"java -jar DUOfflineSandbox.jar";
	   System.out.println("loading "+preload);
	   System.out.println("command: "+cmd+" \""+preload+"\"");
	   
	   try {
		   Process proc = Runtime.getRuntime().exec(cmd+" \""+preload+"\"");

/*		   
		   BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
 		   BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));		
			String s = null; // Read the output from the command
			while ((s = stdInput.readLine()) != null) { System.out.println(s); }
			s = null; // Read any errors from the attempted command
			while ((s = stdError.readLine()) != null) { System.out.println(s); }
*/		
	   } catch (IOException ie) { ie.printStackTrace(); }
	     catch (Exception e) {
			System.out.println("\nLOAD ERROR: \n"+e.getMessage());
			System.exit(-1);
		}
	}
	
	
	
    public void showOnScreen( int screen ) {
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        if( screen > -1 && screen < gd.length ) {
            frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x, frame.getY());
        } else if( gd.length > 0 ) { 
            frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x, frame.getY());
        } else {        	
            throw new RuntimeException( "No Screens Found" );
        }
    }

    public void createMenuBar() {
    	this.mb = new JMenuBar();
    	mb.add(Box.createHorizontalGlue());   

    	mb.setBackground(Color.darkGray);
        mb.setAlignmentX(RIGHT_ALIGNMENT);

        MenuActionListener actionMenu = new MenuActionListener(this, this.frame, this.sandbox.scriptAPI, this.sandbox.elements , this.screenId, this.preloadFile);
        
        // file menu
        fileMenu = new JMenu("File"); 
        fileMenu.setMnemonic(KeyEvent.VK_F);
    	fileMenu.setForeground(Color.white);
    	fileMenu.setFont(new Font("SansSerif Plain",Font.BOLD, 16));

    	JMenuItem mItem_loadfrom = new JMenuItem("Load from file system");
    	mItem_loadfrom.addActionListener(actionMenu);
        fileMenu.add(mItem_loadfrom);

    	JMenuItem mItem_loadnew = new JMenuItem("Load in a new window");
    	mItem_loadnew.addActionListener(actionMenu);
        fileMenu.add(mItem_loadnew);
        
        JMenuItem mItem_reload = new JMenuItem("Reload (F9)");
    	mItem_reload.addActionListener(actionMenu);
        fileMenu.add(mItem_reload);        
    	
        JMenuItem mItem_print = new JMenuItem("Print");
    	mItem_print.addActionListener(actionMenu);
        fileMenu.add(mItem_print);        
    	
        JMenuItem mItem_exit = new JMenuItem("Exit");
    	mItem_exit.addActionListener(actionMenu);
         fileMenu.add(mItem_exit);

        // export menu 
    	exportMenu = new JMenu("Export");
    	// exportMenu.addActionListener(new MenuActionListener());    	
    	exportMenu.setMnemonic(KeyEvent.VK_E);
    	exportMenu.setForeground(Color.white);
    	exportMenu.setFont(new Font("SansSerif Plain",Font.BOLD, 16));
    	JMenuItem mItem_export_txt = new JMenuItem("Export LUA to .txt");
    	mItem_export_txt.addActionListener(actionMenu);
        exportMenu.add(mItem_export_txt);
    	JMenuItem mItem_screenshot = new JMenuItem("Screenshot");
    	mItem_screenshot.addActionListener(actionMenu);
        exportMenu.add(mItem_screenshot);
    	JMenuItem mItem_dump_api = new JMenuItem("Export API to .txt");
    	mItem_dump_api.addActionListener(actionMenu);
        exportMenu.add(mItem_dump_api);
    	
        // help menu
        helpMenu = new JMenu("Help"); 
    	helpMenu.setMnemonic(KeyEvent.VK_H);
    	helpMenu.setForeground(Color.white);
    	helpMenu.setFont(new Font("SansSerif Plain",Font.BOLD, 16));
    	JMenuItem mItem_About = new JMenuItem("About");
    	mItem_About.addActionListener(actionMenu);
        helpMenu.add(mItem_About);

        // Load file combo 
        runToolBox = new JComboBox();
        runToolBox.addItem(preloadFile);
        File dir = new File(System.getProperty("user.dir"));
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("_load.lua");
            }
        });
        for (File loadFile : files) {
        	runToolBox.addItem(loadFile.getAbsoluteFile());
        	// runToolBox.addItem(loadFile.getName());
        }
        runToolBox.setPreferredSize(new Dimension(400,25));
        runToolBox.setMaximumSize(new Dimension(400,25));
//        loadText.setEditable(true);
        runToolBox.setSelectedIndex(0);
        
        // Input map (keyboard shortcuts F5 and F9)
        InputMap im = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = frame.getRootPane().getActionMap();
        KeyStroke F9 = KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0);
        KeyStroke F5 = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
        im.put(F9, "Reload");
        im.put(F5, "Run");
        am.put("Reload", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("loading "+preloadFile);           	
            	StartApplication(preloadFile);
            	StopApplication(sandbox.elements);
            }
        });
        am.put("Run", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String preload =  (String)runToolBox.getSelectedItem().toString();
            	System.out.println("loading "+preload);           	
            	StartApplication(preload);
            	StopApplication(sandbox.elements);
            }
        });

        
        // button reload
        JButton btnReload = new JButton("Reload (F9)");
        btnReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("loading "+preloadFile);           	
            	StartApplication(preloadFile);
            	StopApplication(sandbox.elements);
            }
        });
    	mb.add(btnReload);
        
    	// button run
    	JButton btnRun = new JButton("Run (F5)");   	
        btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String preload =  (String)runToolBox.getSelectedItem().toString();
            	System.out.println("loading "+preload);           	
            	StartApplication(preload);
            	StopApplication(sandbox.elements);
            }
        });
    	mb.add(btnRun);
        
        mb.add(runToolBox);
        mb.add(fileMenu);
    	mb.add(exportMenu);
    	mb.add(helpMenu);
    	frame.setJMenuBar(mb);
    }

    // listener used to pass mousewheel info to the lua.
    private static final class wMouseWheelListener implements MouseWheelListener {
     static execWindow eWindow;
     int max = 10;
   
     public wMouseWheelListener(execWindow peWindow){
    	 eWindow = peWindow;
     }

	@Override
 	public void mouseWheelMoved(MouseWheelEvent e) {
      // System.out.println("wheel: "+e.getWheelRotation());
      int value = eWindow.mouseWheel;
      if ( e.getWheelRotation() == 1 ) {
    	  value += 1;
      } else if ( e.getWheelRotation() == -1 ) {
    	  value -= 1;    	  
      }
      
      if(value > max) {
    	  value = max;  
      } else if(value < -max) {
    	  value = -max;
      }

      eWindow.mouseWheel = value;
    }
  }
   
   
  public execWindow(DUElement[] elements, List<Construct> worldConstruct, List<Player> worldPlayer,int pMasterPlayerId, int pshowOnScreen, boolean pverboseLua, boolean pverboseJava, String ppreloadFile) throws IOException  {
        
    	this.preloadFile = ppreloadFile;
		verboseLua = pverboseLua;
        verboseJava = pverboseJava;
        screenId = pshowOnScreen;        
        
		
        // create a frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.setBounds(0, 0, screenSize.width, screenSize.height);		
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		// frame.setUndecorated(true);
		frame.setLayout(null);
		showOnScreen(this.screenId);

		// frame.getContentPane().addKeyListener(new wKeyListener(preloadFile, elements)); 
		frame.getContentPane().addMouseWheelListener(new wMouseWheelListener(this)); 

	    frame.setTitle("DU offline API - Sandbox "+MAIN.version);
	    frame.setIconImage(new ImageIcon("src/pictures/LogoDUAPI_PNG.png").getImage());  
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
        frame.getContentPane().setBackground(Color.gray);
        frame.getContentPane().setFocusable(true);
        
        // frame event
		frame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	           StopApplication(elements);
	        }
	    });

		// setup a new LUA environment 
		sandbox = new execLUA(this, elements, worldConstruct, worldPlayer, pMasterPlayerId, verboseLua);

		// create the menu bar (require a valid sandbox instance)
		createMenuBar();

		// insert elements in the lua and java environment
 	    for (DUElement elem : elements ) {
		   if(elem == null) continue;

		   // System.out.println("ELEMENT: "+elem.GetType());
		   
		   // Activate a screen (html)
		   elem.element.LoadScreen("<html><body bgcolor=\"black\">&nbsp;</body></html>");

		   // add java elements to the frame
		   frame.getContentPane().add(elem.element.panel,1,0);	
		   
		   // add a LUA instance to all elements 
		   // * used to trigger .RUN() from element's events
		   elem.element.sandbox = this.sandbox;
		   
	 	   // Add a LUA instance to unit timers
           if(elem.GetType().equals("duJavaAPI.Unit")) {
        	   for(execTimer tm : elem.element.Tick) {
        		  if(tm == null) continue;
        		  tm.sandbox = this.sandbox;   
        	   }
           }
		}
 	    
		frame.setVisible(true); 	
		frame.getContentPane().setVisible(true); 
		
		// Run all Start() scripts 
		for (DUElement elem : elements ) {
			if(elem == null) continue;
		    if(elem.element.luaScriptStart != null) sandbox.RUN("self="+elem.element.name+" "+elem.element.luaScriptStart, elem.name+":start()" );		    
		}		 
	}

}
