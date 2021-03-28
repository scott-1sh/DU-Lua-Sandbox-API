/*    
    DataBank.java 
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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.h2.jdbcx.JdbcDataSource;
import org.luaj.vm2.LuaError;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import sandbox.execWindow;

public class DataBank extends BaseElement {
    public JWebBrowser web = null;
    public String html = "";
 	private JButton button = new JButton();
    int sizeX = 200;
    int sizeY = 100;
	JdbcDataSource ds = new JdbcDataSource();
    
	public DataBank(int pid, String pname, int px, int py, boolean pverboseJava) {
		name = pname;
		x = px;
		y = py;		
		id = pid;
        
		verboseJava = pverboseJava;		
	
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px, py, sizeX, sizeY);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);

		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(1, 1, sizeX-4, sizeY-4);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);

	 	// picture
	 	JLabel lblPic = new JLabel();
	 	setPicture(lblPic, "src/pictures/elements/Databank.png", 6, 0, 55, 60);
		panel.add(lblPic, 1, 0);
		 	
		// button 		
		button.setText("sql");
		button.setBounds(5,sizeY-46,(int)(sizeX*.3),20);
		button.setBackground(new Color(59, 59, 59));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setFont(new Font("Arial", Font.BOLD, 12));
		panel.add(button, 1, 0);		
		
		// Activate a H2 embeded database
		ds.setURL("jdbc:h2:./DataBank.db;AUTO_SERVER=TRUE");
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
           	    	   	org.h2.tools.Console.main("-browser", "-url", "jdbc:h2:./DataBank.db;AUTO_SERVER=TRUE", "-user", "sa", "-password", "sa" );      	    	   	    
            		   } catch (Exception err) {
            			  System.out.println("Error in DataBank "+name+" while calling H2 server");
            			  System.out.println("error code: " +err.getMessage());
            		   } finally {
            			  	 System.out.println("H2 server openned in your browser.");
            		   }

                    } 
	                });
            } 
         });		
	
		// stats panel
		CreateStatPanel(name+" ("+id+")", sizeX, sizeY);		
		panel.add(stats, 1, 0);
		UpdateStats();
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/databank.lua";
        luaCall = name+" = createInterfaceDataBank(\""+pid+"\", \""+name+"\")";		
        if(verboseJava) System.out.println("[JAVA] DataBank ["+name+"] created");	 		
	}


	private void ThrowDatabankError(SQLException e, String location, String command) {
		String pattern = "[:](.*)$";
		Pattern r = Pattern.compile(pattern);

		System.out.println("[JAVA] Error in DataBank"+name+" ("+location+") command: "+command);
		System.out.println("[JAVA] error code:"+e.getErrorCode()+" : " +e.getMessage());

		String gmsg = "\nDatabank "+name+" Error\n\n";
	    
		// get last line of getMessage
		Matcher m = r.matcher(e.getMessage().replace("\n", ""));
	    if (m.find()) {
		  gmsg += "line "+m.group(1)+"\n\n";
	    }
	    
		System.out.println(gmsg);
		execWindow.StopServices(sandbox.elements);
		execWindow.frame.setTitle(execWindow.frame.getTitle()+" - dead!");

	}
	
	
	// update panel stats
	private void UpdateStats() {
		
		try {
			Connection db = ds.getConnection();				
			Statement st = db.createStatement();
			ResultSet rs = null;
		
			// databank size
			rs = st.executeQuery("SELECT sum(length(string)) + sum(nvl2(int, 4,  0)) + sum(nvl2(float, 4, 0)) FROM "+name);
			rs.first();
			AddtoStatPanel("data size: ", rs.getString(1) + " Bytes" );

			// memory used
			rs = st.executeQuery("call MEMORY_USED()");
			rs.first();
			AddtoStatPanel("Internal: ", rs.getString(1) + " Bytes");

			// records
            rs = st.executeQuery("select count(key) from "+name);
            rs.first();
            AddtoStatPanel("Records: ", rs.getString(1));

			
		} catch (SQLException e) {
			ThrowDatabankError(e, "UpdateStats", "N/A"); 
		}
		
		
	}
	

	
	@Override	
    public Object get(String[] param) {    	
		String pcommand = param[0];
		String pkey; 
		ResultSet rs = null;
		
		// lua return null for empty keys
		if(param.length == 1) {
			pkey = "";
		    // if(verboseJava && nullkeyError) System.out.println("[JAVA]["+name+"]["+pcommand+"] Warning: key parameter is null."); 
        } else {
			pkey = param[1];
        }
       		
		try {
			Connection db = ds.getConnection();				
			Statement st = db.createStatement();
			switch (pcommand) {
				case "hasKey":
					// if(param.length == 1 && verboseJava) System.out.println("[JAVA]["+name+"] \"hasKey\" Warning: key parameter is null."); 
 	                rs = st.executeQuery("select count(key) from "+name+" where key ='"+ pkey.replace( "'", "''") +"'");
		            rs.first();
		            if(rs.getInt(1) > 0) return true;
		            return false;
		            // return rs.getInt(1);
				case "getNbKeys":
		             rs = st.executeQuery("select count(key) from "+name);
		             rs.first();
		             return rs.getInt(1);
				case "getStringValue":
		             rs = st.executeQuery("select string from "+name+" where key = '"+ pkey.replace( "'", "''") +"'");
		             if(!rs.first()) return null;		             
		             return rs.getString(1);
				case "getIntValue":
		             rs = st.executeQuery("select int from "+name+" where key = '"+ pkey.replace( "'", "''") +"'");
		             rs.first();
		             return rs.getInt(1);
				case "getFloatValue":
		             rs = st.executeQuery("select float from "+name+" where key = '"+ pkey.replace( "'", "''") +"'");
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
			ThrowDatabankError(e, "get", pcommand); 
		}

		return "";
    }
	
	@Override	
    public void update(String[] param) {
		String pcommand = param[0];
		ResultSet rs = null;
		
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
					try { // Warning if a float is inserted
						Integer.parseInt(param[2]);
					} catch (NumberFormatException e){
						System.out.println("Warning! "+param[0]+" - key: \""+param[1]+"\" value: \""+param[2]+"\" invalid integer will return null in game environment.");
						param[2] = null;
				    }
				    st.executeUpdate("MERGE INTO "+name+" KEY(key) VALUES ('"+param[1].replace( "'", "''")+"' , NULL, "+param[2]+", NULL);");					
					break;
				case "setFloatValue":
					try { // Warning if a float is inserted
						Float.parseFloat(param[2]);
					} catch (NumberFormatException e){
						System.out.println("Warning! "+param[0]+" - key: \""+param[1]+"\" value: \""+param[2]+"\" invalid float will return null in game environment.");
						param[2] = null;
				    }
				    st.executeUpdate("MERGE INTO "+name+" KEY(key) VALUES ('"+param[1].replace( "'", "''")+"' , NULL, NULL, "+param[2]+");");					
					break;
				case "closeDB":
				    ds.getConnection().close();					
					break;
			}
			
			// update stats
			panel.remove(stats);
			CreateStatPanel(name+" ("+id+")", sizeX, sizeY);		
			panel.add(stats, 1, 0);
			UpdateStats();
			
			// closdedb
	        db.close();

 		} catch (SQLException e) {
			ThrowDatabankError(e, "update", pcommand); 
		}
		
		
    }
}
