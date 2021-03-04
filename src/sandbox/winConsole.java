package sandbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLDocument;

public class winConsole extends OutputStream {
	public JTextPane textArea;
    private StringBuilder buffer;
    private String prefix;
    public PrintStream old;
    public JInternalFrame frame;
    
	public winConsole(String prefix, PrintStream pprintStream) {

		buffer = new StringBuilder(5000);
		this.prefix = prefix;
		this.old = pprintStream;
		  
		textArea = new JTextPane();
		textArea.setContentType("text/html");
		textArea.setBackground(Color.BLACK);
        textArea.setEditable (false);

	}

	public void buildFrame(Rectangle deskSize) {

        frame = new JInternalFrame ("System.out", true, true, false, true);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);       
        
        frame.setBounds(deskSize.width-605, (deskSize.height/2)-50, 600, deskSize.height/2);
        frame.setPreferredSize(new Dimension(600, deskSize.height/2));
        Container contentPane = frame.getContentPane ();
        contentPane.setLayout (new BorderLayout ());
        contentPane.add (
            new JScrollPane (
                textArea, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
            BorderLayout.CENTER);
        frame.pack();
        
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        frame.setVisible(true);
        
	}

	@SuppressWarnings("unused")
	private void scrolldown() {
		textArea.setCaretPosition(textArea.getDocument().getLength());
		// try {
			// textArea.scrollRectToVisible((Rectangle)textArea.modelToView2D(textArea.getCaretPosition()));
			textArea.update(textArea.getGraphics());
			frame.revalidate();
		// } catch (BadLocationException e) { e.printStackTrace(); }
		
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
		// scrolldown();
	}		
	
	@Override
	public void write(int b) throws IOException {
		 char c = (char) b;
	        String value = Character.toString(c);
	        buffer.append(value);
	        if (value.equals("\n")) {
	        	
	        	String pre = "<span style=\"color: #FFFFFF; font-family: monospace;\">";
	        	String end = "</span>";
	        	
	        	if(buffer.toString().startsWith("[LUA]")) {
	        	   pre = "<span style=\"color: #9090FF; font-family: monospace;\">";
	        	}
	        	if(buffer.toString().startsWith("[JAVA]"))  {
	        		buffer.toString().replace("[JAVA]","[SANDBOX]");
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
