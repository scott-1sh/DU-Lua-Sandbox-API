/*    
    execWindow.java 
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

package sandbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
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
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import duJavaAPI.Construct;
import duJavaAPI.HUD;
import duJavaAPI.Player;

public class execWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public static execLUA sandbox;
    static JDesktopPane desktop = null;
    public static boolean verboseJava = false;
    public static boolean verboseLua = false;
	static String preloadFile;
	public static JMenuBar mb;
	static JMenu fileMenu, editMenu, exportMenu, helpMenu;
	static int screenId = 0;
	public static int  mouseWheel = 0;
	public static boolean lockedView = false;
	public static boolean frozen = false;
	private static JComboBox runToolBox;
	public static List<String> editableScriptList = new ArrayList<String>();
	public static HUD hud;

	public static void StopServices(DUElement[] elements) {

	  // stop hud timer 
		if(hud.timer != null) { hud.timer.stop();}

	   // stop world construct timer
	   if(sandbox.timer != null) sandbox.timer.stop();	
		
	   // Run all Stop(), close databases and stop all timers
	   for (DUElement elem : elements ) {
		   if(elem == null) continue;
	       
		   // Stop all timers
		   for(execTimer tm : elem.element.Tick) {
				   if(tm == null) continue;
				   if(tm.timer == null) continue;
				   if(verboseJava) System.out.println("[JAVA] Timer "+tm.name+" disabled" );
				   tm.timer.stop();   
		   }
	      
	      if(elem.element.luaScriptStop != null) {
	    	  if(verboseJava) System.out.println("[JAVA] Triggering "+elem.name+":stop() Event" );
	    	  sandbox.RUN("self="+elem.element.name+" "+elem.element.luaScriptStop, elem.name+":stop()" );
	      }

	      if(elem.GetType().equals("duJavaAPI.DataBank")) {
			   if(verboseJava) System.out.println("[JAVA] closing DB "+elem.name);
			   String[] cmd1 = {"closeDB"};
			   elem.element.update(cmd1);
		   }

	      // close radars timers
	      if(elem.GetType().equals("duJavaAPI.Radar")) {
			   if(verboseJava) System.out.println("[JAVA] stopping radar "+elem.name);
			   String[] cmd1 = {"stopRadar"};
			   elem.element.get(cmd1);
		   }
	      
	      // dead status
	      // desktop.setBackground(Color.BLACK);
		}

	}
		
	public static void StopApplication(DUElement[] elements) {
		   StopServices(elements); 
		   frame.setVisible(false);
           NativeInterface.close();
		   System.out.println("dead");
		   System.exit(0);
	}
	
	// Destroy the actual window and restart with the specified preload.
	public static void StartApplication(String preload)
	{
	    // System.out.println("Reseting LUA env");
    	StopServices(sandbox.elements);
    	frame.setVisible(false);
    	frame.dispose();
    	frame = null;
    	sandbox = null;
    	System.gc();

	    // rebuild console
    	MAIN.wConsole = new winConsole("", System.out);
		System.setOut(new PrintStream(MAIN.wConsole));
    	
	    MAIN.preloadFile = preload;
	    System.out.println("Loading "+preload);  
	    // start preload engine
	    PreLoad pre;
	    pre = new PreLoad(preload);                               	
	    
	    try {
			MAIN.execWin = new execWindow(pre, MAIN.wConsole, preload);
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	
	
    public void showOnScreen( int screen ) {
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice[] gd = ge.getScreenDevices();
   
    	if( screen > -1 && screen < gd.length ) {
    		
    		// taskbar
    		Insets taskBarSize = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
    		// gd[screen].setFullScreenWindow(frame);
    		GraphicsConfiguration dc = gd[screen].getDefaultConfiguration();

    		// put it on selected screen 
    		frame.setBounds(dc.getBounds().x, dc.getBounds().y , dc.getBounds().width, dc.getBounds().height-taskBarSize.bottom);
    		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    		
        }
        else if( gd.length > 0 ) {
            gd[0].setFullScreenWindow( (JFrame)desktop.getTopLevelAncestor() );
        } else {
            throw new RuntimeException( "No Screens Found" );
        }
    }

    public void createMenuBar() {
    	execWindow.mb = new JMenuBar();
    	mb.add(Box.createHorizontalGlue());   

    	mb.setBackground(Color.darkGray);
        mb.setAlignmentX(RIGHT_ALIGNMENT);

        MenuActionListener actionMenu = new MenuActionListener(this, execWindow.frame, execWindow.sandbox.scriptAPI, execWindow.sandbox.elements , execWindow.screenId, execWindow.preloadFile);
        
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

        // edit
        editMenu = new JMenu("Edit");  
    	editMenu.setMnemonic(KeyEvent.VK_D);
    	editMenu.setForeground(Color.white);
    	editMenu.setFont(new Font("SansSerif Plain",Font.BOLD, 16));
        // add all editable scripts
        for (String filename : editableScriptList) {
        	JMenuItem mItem = new JMenuItem("Edit "+filename);
        	mItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						System.out.println("Opening "+filename+" in your default editor");  
						File filetmp = new File(filename);
						Desktop dk = Desktop.getDesktop(); 
						dk.open(filetmp); 
													 
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}});
        	editMenu.add(mItem);
        }

    	
        // export menu 
    	exportMenu = new JMenu("Export");
    	// exportMenu.addActionListener(new MenuActionListener());    	
    	exportMenu.setMnemonic(KeyEvent.VK_E);
    	exportMenu.setForeground(Color.white);
    	exportMenu.setFont(new Font("SansSerif Plain",Font.BOLD, 16));
    	JMenuItem mItem_export_json = new JMenuItem("Export to JSON");
    	mItem_export_json.addActionListener(actionMenu);
        exportMenu.add(mItem_export_json);
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
        // runToolBox.setForeground(Color.BLUE);
        // runToolBox.setBackground(Color.YELLOW);
        runToolBox.setBorder(LineBorder.createBlackLineBorder());
        
        // runToolBox.setBounds(r);
       // runToolBox.getEditor().getEditorComponent().   .setBackground(Color.RED);
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
        // btnReload.setBackground(new Color(9, 9, 159));
    //    btnReload.setForeground(Color.black);
    //    btnReload.setBorder(LineBorder.createGrayLineBorder());
        btnReload.setFocusPainted(false);
        btnReload.setFont(new Font("Arial", Font.BOLD, 12));
       btnReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("loading "+preloadFile);           	
            	StartApplication(preloadFile);
            }
        });
    	mb.add(btnReload);
        
    	// button run
    	JButton btnRun = new JButton("Run (F5)");   
    	// btnRun.setBounds(5, 5, sizeX-16, 20);
    	// btnRun.setBackground(new Color(78, 59, 159));
   // 	btnRun.setForeground(Color.black);
    	btnRun.setFocusPainted(false);
    	btnRun.setFont(new Font("Arial", Font.BOLD, 12));
    	btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String preload =  (String)runToolBox.getSelectedItem().toString();
            	System.out.println("loading "+preload);           	
            	StartApplication(preload);
            }
        });
    	mb.add(btnRun);
        
        mb.add(runToolBox);
        mb.add(fileMenu);
        mb.add(editMenu);
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
      int value = execWindow.mouseWheel;
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

      execWindow.mouseWheel = value;
    }
  }
   
   
  public execWindow(PreLoad preload, winConsole pConsole, String ppreloadFile) throws IOException  {

	execWindow.preloadFile = ppreloadFile;
	verboseLua = preload.verboseLua;
	verboseJava =preload.verboseJava;
	screenId = preload.showOnScreen; 
	editableScriptList = preload.editableScriptList;
	
	desktop = new JDesktopPane();
	desktop.setBackground(Color.gray);
	
	frame = new JFrame();
	showOnScreen(execWindow.screenId);	

	frame.setTitle("DU lua Sandbox "+MAIN.version+" - "+preloadFile);
	frame.setIconImage(new ImageIcon("src/pictures/LogoDUAPI_PNG.png").getImage());  
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
	frame.getContentPane().addMouseWheelListener(new wMouseWheelListener(this)); 
	
	frame.getContentPane().setBackground(Color.gray);
	frame.getContentPane().setFocusable(true);
	frame.setContentPane(desktop);
	// frame.setLayout(null);
                
    // frame event
	frame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
           StopApplication(preload.elements);
        }	
    });
    
	
	pConsole.buildFrame(frame.getBounds());
	desktop.add(pConsole.frame);

	// Show HUD	
	hud = new HUD(preload.hudParam.Script, preload.hudParam.x, preload.hudParam.y, preload.hudParam.sizeX, preload.hudParam.sizeY, verboseJava);
	if(!preload.hudParam.defaulthud) {
	  hud.buildFrame(frame.getBounds());
	  desktop.add(hud.frame); 
	}
    
	
	// setup a new LUA environment 
	sandbox = new execLUA(this, preload.elements, preload.worldConstruct, preload.worldPlayer, preload.MasterPlayerId, verboseLua);

	// create the menu bar (require a valid sandbox instance)
	createMenuBar();

	// insert elements in the lua and java environment
    for (DUElement elem : preload.elements ) {
	   if(elem == null) continue;	   
		   // System.out.println("ELEMENT: "+elem.GetType());
		   
		   // Activate a screen (html)
		   elem.element.LoadScreen("<html><body>[init]</body></html>");
		
		   // create the UI frame
		   JInternalFrame jframe = new execFrame(elem.element.name, elem.element).getFrame();
		   jframe.setBounds(elem.element.panel.getBounds());
		   jframe.getContentPane().add(elem.element.panel); 
	       jframe.setVisible(true);	
	      
		   desktop.add(jframe, BorderLayout.WEST);
		   
		   // add a LUA instance to all elements 
		   // * used to trigger .RUN() from element's events
		   elem.element.sandbox = execWindow.sandbox;
 		   
		   // add an execWindow instance to all elements
		   elem.element.execWin = this;
		   
		   // setup Units
		   if(elem.GetType().equals("duJavaAPI.Unit")) {
			   // add stats
               int scriptSize = sandbox.scriptMemoryUsed(preload.elements); // sandbox.luaScriptToText(preload.elements).length();	
               elem.element.AddtoStatPanel("memory:", scriptSize+"/20000");
               // stats for masterplayer
               if(sandbox.worldPlayer.size() > 0) {
            	   elem.element.AddtoStatPanel("Master: ", preload.MasterPlayerId+" - "+ sandbox.worldPlayer.get(preload.MasterPlayerId).name);
               } else {
            	   preload.MasterPlayerId = 0;
            	   elem.element.AddtoStatPanel("Master: ", "0 - N/A");
          	       if(verboseJava) System.out.println("[JAVA] Warning <i>No internal database for players and constructs.  Use setupDatabase in the PRELOAD phase to setup an iternal database</i>");
               }
               
			   // Add a sandbox instance to Units timers
			   for(execTimer tm : elem.element.Tick) {
				   if(tm == null) continue;
				   tm.sandbox = execWindow.sandbox;   
			   }
	      }
	}
    
	frame.setVisible(true); 	
	frame.getContentPane().setVisible(true); 
	
	// Run all Start() scripts 
	for (DUElement elem : preload.elements ) {
		if(elem == null) continue;
		if(elem.GetType().equals("duJavaAPI.Unit")) {
			if(elem.element.luaScriptStart != null) sandbox.RUN("self="+elem.element.name+" "+elem.element.luaScriptStart, elem.name+":start()" );		    
 	    }		 
    }
	
	// start update scripts
	// hud update
	if(preload.hudParam.Script != null) {
		// start update timer
		hud.StartUpdateTimer(preload.hudParam.Script, sandbox);
	} 
	
	
  }
}

