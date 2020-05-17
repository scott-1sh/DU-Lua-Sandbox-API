package offlineEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

public class winConsole extends OutputStream {
	JTextPane textArea;
    private StringBuilder buffer;
    private String prefix;
    private PrintStream old;
    JFrame frame;
    
	public winConsole(String prefix, PrintStream old) {
       

		buffer = new StringBuilder(5000);
        // buffer.append("[").append(prefix).append("] ");
		this.prefix = prefix;
		this.old = old;
		
		buildFrame();

	}

	private void buildFrame() {

		textArea = new JTextPane();
		textArea.setContentType("text/html");
		textArea.setBackground(Color.BLACK);
        textArea.setEditable (false);
//        textArea.setAutoscrolls(true);

        frame = new JFrame ("System.out ("+MAIN.preloadFile+")");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	    frame.setIconImage(new ImageIcon("src/pictures/LogoDUAPI_PNG.png").getImage()); 
        frame.setLayout(null);        
        // frame.setBounds(200, 200, 300, 200);
        frame.setPreferredSize(new Dimension(600,800));
//        frame.setExtendedState(Frame.NORMAL );
        Container contentPane = frame.getContentPane ();
        contentPane.setLayout (new BorderLayout ());
        contentPane.add (
            new JScrollPane (
                textArea, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
            BorderLayout.CENTER);
        frame.pack ();
        frame.setVisible(true);
		
	}

	private void scrolldown() {
		textArea.setCaretPosition(textArea.getDocument().getLength());
		try {
			textArea.scrollRectToVisible((Rectangle)textArea.modelToView2D(textArea.getCaretPosition()));
			textArea.update(textArea.getGraphics());
			frame.revalidate();
		} catch (BadLocationException e) { e.printStackTrace(); }
		
	}

	private void println(String text) {
		HTMLDocument doc=(HTMLDocument) textArea.getStyledDocument();
		try {
			// LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))+" - "+
			doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), text+"<br>");
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
		}
		scrolldown();
	}		
	
	@Override
	public void write(int b) throws IOException {
		 char c = (char) b;
	        String value = Character.toString(c);
	        buffer.append(value);
	        if (value.equals("\n")) {
	        	
	        	String pre = "<span style=\"color: #FFFFFF; font-family: arial;\">";
	        	String end = "</span>";
	        	
	        	if(buffer.toString().startsWith("[LUA]")) {
	        	   pre = "<span style=\"color: #0000FF; font-family: monospace;\">";
	        	}
	        	if(buffer.toString().startsWith("[JAVA]"))  {
		           pre = "<span style=\"color: #00FF00; font-weight: bold; font-family: monospace;\">";
		        } 
	        	println(pre+buffer.toString()+end);
	        	// textArea.setText(textArea.getText()+pre+buffer.toString()+end);
	        	// textArea.update(textArea.getGraphics());
	            buffer.delete(0, buffer.length());
	            // buffer.append("[").append(prefix).append("] ");
	        }
	        old.print(c);
	}
}
