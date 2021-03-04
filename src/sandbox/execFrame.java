/*    
    execFrame.java 
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

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.ColorUIResource;

public class execFrame   {
	private JInternalFrame desktop;
   
	public execFrame(String name) {

		desktop = new JInternalFrame(name ,  false, true, false, true);
		desktop.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        /*
		//iconifie
		try {
			desktop.setIcon(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		// remove top left icon
		// BasicInternalFrameUI ui = (BasicInternalFrameUI)desktop.getUI();
		 javax.swing.plaf.basic.BasicInternalFrameUI ui = new javax.swing.plaf.basic.BasicInternalFrameUI(desktop); 
		 // ((javax.swing.plaf.basic.BasicInternalFrameUI) ui).setNorthPane(null);
	     desktop.setFrameIcon(new ImageIcon("src/pictures/open_caption.png"));
		   UIManager.put("InternalFrame.inactiveTitleForeground", Color.white);
	   UIManager.put("InternalFrame.activeTitleForeground", Color.white);
	        UIManager.put("InternalFrame.activeTitleBackground", new ColorUIResource(new Color(0,00,0)));
	        UIManager.put("InternalFrame.inactiveTitleBackground", new ColorUIResource(new Color(0,0,0)));
			// UIManager.getDefaults().put( "InternalFrame.icon", null ); 
	        // System.out.println("[JAVA] !!!!!! "+name);
	        desktop.setUI(ui);

	        
	        // sorry for the mess...  alot to do here
		// desktop.setClosed(false);
			// SoftBevelBorder border = new SoftBevelBorder(BevelBorder.LOWERED, Color.ORANGE, Color.ORANGE.darker(),Color.BLUE, Color.magenta.brighter()); 		   
			desktop.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	        /* 
			InternalFrame.borderColor : javax.swing.plaf.ColorUIResource[r=204,g=204,b=204]
			InternalFrame.inactiveTitleBackground : javax.swing.plaf.ColorUIResource[r=204,g=204,b=204]
			InternalFrame.maximizeIcon : javax.swing.plaf.metal.MetalIconFactory$InternalFrameMaximizeIcon@961dff
			InternalFrame.minimizeSound : sounds/FrameMinimize.wav
			InternalFrame.borderDarkShadow : javax.swing.plaf.ColorUIResource[r=102,g=102,b=102]
			InternalFrame.optionDialogBorder : javax.swing.plaf.metal.MetalBorders$OptionDialogBorder@1fc6e42
			InternalFrame.inactiveTitleForeground : javax.swing.plaf.ColorUIResource[r=0,g=0,b=0]
			InternalFrame.iconifyIcon : javax.swing.plaf.metal.MetalIconFactory$InternalFrameMinimizeIcon@1a082e2
			InternalFrame.border : javax.swing.plaf.metal.MetalBorders$InternalFrameBorder@1f297e7
			InternalFrame.paletteBorder : javax.swing.plaf.metal.MetalBorders$PaletteBorder@ac6a45
			InternalFrame.paletteTitleHeight : 11
			InternalFrame.restoreDownSound : sounds/FrameRestoreDown.wav
			InternalFrame.minimizeIcon : javax.swing.plaf.metal.MetalIconFactory$InternalFrameAltMaximizeIcon@42552c
			InternalFrameUI : javax.swing.plaf.metal.MetalInternalFrameUI
			InternalFrame.closeIcon : javax.swing.plaf.metal.MetalIconFactory$InternalFrameCloseIcon@8ee016
			InternalFrame.titleFont : javax.swing.plaf.FontUIResource[family=Dialog,name=Dialog,style=bold,size=12]
			InternalFrame.activeTitleForeground : javax.swing.plaf.ColorUIResource[r=0,g=0,b=0]
			InternalFrame.borderShadow : javax.swing.plaf.ColorUIResource[r=153,g=153,b=153]
			InternalFrame.paletteCloseIcon : javax.swing.plaf.metal.MetalIconFactory$PaletteCloseIcon@11563ff
			InternalFrame.closeSound : sounds/FrameClose.wav
			InternalFrame.icon : javax.swing.plaf.metal.MetalIconFactory$InternalFrameDefaultMenuIcon@1f06dc3
			InternalFrame.borderHighlight : javax.swing.plaf.ColorUIResource[r=255,g=255,b=255]
			InternalFrame.activeTitleBackground : javax.swing.plaf.ColorUIResource[r=204,g=204,b=255]
			InternalFrame.maximizeSound : sounds/FrameMaximize.wav
			InternalFrame.restoreUpSound : sounds/FrameRestoreUp.wav
			InternalFrame.borderLight : javax.swing.plaf.ColorUIResource[r=255,g=255,b=255] 
			         * 
	        //		ui.setNorthPane(null);
		/*
		Container north = (Container)ui.getNorthPane();
		north.remove(0);
		north.validate();
		north.repaint();
		*/
/*
 desktop.addInternalFrameListener(new InternalFrameListener() {
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				System.out.println("[JAVA] !!!!!! opened");
			}
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				System.out.println("[JAVA] !!!!!! closing");
			}
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				System.out.println("[JAVA] !!!!!! closed");
			}
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {
				System.out.println("[JAVA] !!!!!! iconified");
				 return;
			}
			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				System.out.println("[JAVA] !!!!!! deiconified");
			}
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				System.out.println("[JAVA] !!!!!! activated");
			}
			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {
				System.out.println("[JAVA] !!!!!! deactivated");
			}
        });
		*/		
		// javax.swing.plaf.basic.BasicInternalFrameUI ui = new javax.swing.plaf.basic.BasicInternalFrameUI(desktop); 
		// W/DU-Offline-editor/src/pictures/icon.png
		 // desktop.setFrameIcon(new ImageIcon("src/pictures/icon.png"));
		  // URL url = new URL("./src/pictures/LogoDUAPI_PNG.png");
//		  ImageIcon icon = new ImageIcon(url);
		   // ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(".\src\pictures\LogoDUAPI_PNG.png"));
//		  jframe.setFrameIcon(icon);
		  // jframe.setFrameIcon("src/pictures/LogoDUAPI_PNG.png");
		  // jframe.setFrameIcon(icon);

		
	}

    private void setInternalFrameIcon(String uiKey, String resource) {
	       ImageIcon icon = new ImageIcon(resource);
	                  
	       UIManager.put(uiKey, icon);
    }

	public JInternalFrame getFrame() {	 
		
		return desktop;
	}
	
}
