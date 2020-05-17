/*    
    DataBank.java 
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import offlineEditor.execWindow;

public class DataBank extends BaseElement {
    public JWebBrowser web = null;
    public String html = "";
 	private JButton button = new JButton();
    int sizeX = 104;
    int sizeY = 100;
	JdbcDataSource ds = new JdbcDataSource();
    
	public DataBank(int pid, String pname, int px, int py, boolean pverboseJava) {
		name = pname;
		x = px;
		y = py;		
        
		verboseJava = pverboseJava;		
	
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px, py, sizeX, sizeY+43);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);

		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, sizeX, 16);	
		panel.add(lblname, 1, 0);		

		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(2,22,100,98);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);

	 	// picture
	 	JLabel lblPic = new JLabel();		
		lblPic.setBounds(2,22,100,100);
		lblPic.setIcon(new ImageIcon("src/pictures/elements/Databank.png")); 
		panel.add(lblPic, 1, 0);
		
	  	// button 		
		button.setText("console sql");
		button.setBounds(1,sizeY+22,sizeX-2,20);
		panel.add(button, 1, 0);		
		
		// Activate a H2 embeded database
		//jdbc:h2:tcp://localhost/C:\Database\Data\production;
		ds.setURL("jdbc:h2:./DataBank.db;AUTO_SERVER=TRUE");
		// ds.setURL("jdbc:h2:tcp://localhost/d:\\DataBank.db;AUTO_SERVER=TRUE");
 	    ds.setUser("sa");
		ds.setPassword("sa");
		// create table if not exist
		try {
			Connection db = ds.getConnection();
			var st = db.createStatement();
		    st.executeUpdate("CREATE TABLE IF NOT EXISTS "+name+" (key VARCHAR(255) PRIMARY KEY, string VARCHAR(4000), int  BIGINT, float DOUBLE);");
		} catch (SQLException e) {
			System.out.println("Error in DataBank "+name+" inside DataBank constructor");
			e.printStackTrace();
			System.exit(0);
		} finally {
			if(verboseJava) System.out.println("[JAVA] H2 Database session created for dataBank "+name);
		}

		button.addActionListener(new ActionListener() { 
            @Override
			public void actionPerformed(ActionEvent e) {
        		button.addActionListener(new ActionListener() { 
                    @Override
					public void actionPerformed(ActionEvent e) {
          			 
               	       try { 
       	    	   		//org.h2.tools.Server.openBrowser("jdbc:h2:./src/pictures/DataBank.db;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE");
           	    	   	org.h2.tools.Console.main("-browser");
            		   } catch (Exception err) {
            			  System.out.println("Error in DataBank "+name+" while calling H2 server");
            			  System.out.println("error code: " +err.getMessage());
            		   } finally {
            			  if(verboseJava) System.out.println("H2 server openned in your browser.");
            		   }

                    } 
	                });
            } 
         });		
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/databank.lua";
        luaCall = name+" = createInterfaceDataBank(\""+pid+"\", \""+name+"\")";		
        if(verboseJava) System.out.println("[JAVA] DataBank ["+name+"] created");	 		
	}


	@Override	
    public Object get(String[] param) {    	
		String pcommand = param[0];
		
		try {
			Connection db = ds.getConnection();				
			Statement st = db.createStatement();
			switch (pcommand) {
				case "hasKey":					
 	                var rs = st.executeQuery("select count(key) from "+name+" where key ='"+ param[1].replace( "'", "''") +"'");
		             rs.first();
		             return rs.getInt(1);
				case "getNbKeys":
		             rs = st.executeQuery("select count(key) from "+name);
		             rs.first();
		             return rs.getInt(1);
				case "getStringValue":
		             rs = st.executeQuery("select string from "+name+" where key = '"+ param[1].replace( "'", "''") +"'");
		             if(!rs.first()) return null;		             
		             return rs.getString(1);
				case "getIntValue":
		             rs = st.executeQuery("select int from "+name+" where key = '"+ param[1].replace( "'", "''") +"'");
		             rs.first();
		             return rs.getInt(1);
				case "getFloatValue":
		             rs = st.executeQuery("select float from "+name+" where key = '"+ param[1].replace( "'", "''") +"'");
		             rs.first();
		             return rs.getFloat(1);
				case "getKeys":
		             rs = st.executeQuery("select rownum, key from "+name+";");
                     String json = "{ ";
                     String comma = "";
                     while (rs.next()) {
                    	json += String.format("%s %d: \"%s\" ", comma, rs.getInt(1), rs.getString(2));  
                    	comma = ",";		 
                     }
					 json += " }";
	 				return json;					
			}
		} catch (SQLException e) {
			System.out.println("Error in DataBank "+name+" (get) command: "+pcommand);		
			System.out.println("error code:"+e.getErrorCode()+" : " +e.getMessage());
			// e.printStackTrace();
			System.exit(0);
		}

		return "";
    }
	
	@Override	
    public void update(String[] param) {
		String pcommand = param[0];
		// System.out.println("[JAVA] DataBank update (" + param[0] + ", " + param[1] + ", " + param[2] + ")"); 
		try {
			Connection db = ds.getConnection();				
			Statement st = db.createStatement();
        
			switch (pcommand) {
				case "clear":
				    st.executeUpdate("DELETE FROM "+name+";");
					break;
				case "setStringValue":
				    st.executeUpdate("MERGE INTO "+name+" KEY(key) VALUES ('"+param[1].replace( "'", "''")+"' , '"+param[2].replace( "'", "''")+"', NULL, NULL);");					
					break;
				case "setIntValue":
				    st.executeUpdate("MERGE INTO "+name+" KEY(key) VALUES ('"+param[1].replace( "'", "''")+"' , NULL, "+param[2]+", NULL);");					
					break;
				case "setFloatValue":
				    st.executeUpdate("MERGE INTO "+name+" KEY(key) VALUES ('"+param[1].replace( "'", "''")+"' , NULL, NULL, "+param[2]+");");					
					break;
				case "closeDB":
				    ds.getConnection().close();   //  executeUpdate("MERGE INTO "+name+" KEY(key) VALUES ('"+param[1].replace( "'", "''")+"' , NULL, NULL, "+param[2]+");");					
					break;
			}
	        db.close();

 		} catch (SQLException e) {
			System.out.println("Error in DataBank"+name+" (update) command: "+pcommand);
			System.out.println("error code:"+e.getErrorCode()+" : " +e.getMessage());
			// e.printStackTrace();
			System.exit(0);
		}
		
    }
}
