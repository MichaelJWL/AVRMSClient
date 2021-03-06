package key.control;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class KeyController extends JFrame implements KeyListener, ActionListener
{


    JTextArea displayArea;
    JTextField typingArea;
    static final String newline = System.getProperty("line.separator");
    
    public static void _main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
    	KeyController frame = new KeyController("KeyEventDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set up the content pane.
        frame.addComponentsToPane();
        
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    private void addComponentsToPane() {
        
        JButton button = new JButton("Clear");
        button.addActionListener(this);
        
        JButton btn_roscore = new JButton("Roscore");
        btn_roscore.addActionListener(this);
        
        
        typingArea = new JTextField(20);
        typingArea.addKeyListener(this);
        typingArea.setToolTipText("make action");
        //Uncomment this if you wish to turn off focus
        //traversal.  The focus subsystem consumes
        //focus traversal keys, such as Tab and Shift Tab.
        //If you uncomment the following line of code, this
        //disables focus traversal and the Tab events will
        //become available to the key event listener.
        //typingArea.setFocusTraversalKeysEnabled(false);
        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        
        getContentPane().add(btn_roscore, BorderLayout.BEFORE_FIRST_LINE);
        getContentPane().add(typingArea, BorderLayout.AFTER_LINE_ENDS);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(button, BorderLayout.PAGE_END);
        
    }
    
    public KeyController(String name) {
        super(name);
    }
    
    
    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
//        displayInfo(e, "KEY TYPED: ");
        try {
			keyActionControl(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
//        displayInfo(e, "KEY PRESSED: ");
        try {
			keyActionControl(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
//        displayInfo(e, "KEY RELEASED: ");
        try {
			keyActionControl(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    /** Handle the button click. */
    public void actionPerformed(ActionEvent e) {
    	
    	String action = e.getActionCommand();
    	if("Clear".equals(action)){
    		 //Clear the text components.
            displayArea.setText("");
            typingArea.setText("");
            System.out.println("DEBUG: actionPerformed");
            //Return the focus to the typing area.
            typingArea.requestFocusInWindow();
    	}else if("Roscore".equals(action)){
//    		try {
    			System.out.println(e.getSource().getClass());
    			JButton tmp_btn = (JButton)e.getSource();
    			tmp_btn.setEnabled(false);
//				readOutPut(Runtime.getRuntime().exec("roscore"));
			
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
    	}
       
    }
    
    /*
     * We have to jump through some hoops to avoid
     * trying to print non-printing characters
     * such as Shift.  (Not only do they not print,
     * but if you put them in a String, the characters
     * afterward won't show up in the text area.)
     */
    private void displayInfo(KeyEvent e, String keyStatus){
        
        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
                    + KeyEvent.getKeyText(keyCode)
                    + ")";
        }
        
        int modifiersEx = e.getModifiersEx();
        String modString = "extended modifiers = " + modifiersEx;
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += " (no extended modifiers)";
        }
        
        String actionString = "action key? ";
        if (e.isActionKey()) {
            actionString += "YES";
        } else {
            actionString += "NO";
        }
        
        String locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
            locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
            locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
            locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
            locationString += "numpad";
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
            locationString += "unknown";
        }
        
        displayArea.append(keyStatus + newline
                + "    " + keyString + newline
                + "    " + modString + newline
                + "    " + actionString + newline
                + "    " + locationString + newline);
        displayArea.setCaretPosition(displayArea.getDocument().getLength());
    }
    public void keyActionControl(KeyEvent e) throws IOException, InterruptedException{
    	int id = e.getID();
        String keyString;
    	if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
                    + KeyEvent.getKeyText(keyCode)
                    + ")";
        }
    	int keyCode;
    	if(id==KeyEvent.KEY_PRESSED){
    		keyCode = e.getKeyCode();
    		
    		if(keyCode == 37){
    			/**
    			 * left
    			 */
    			pressActionKeyLeft();
    		}else if(keyCode == 38){
    			/**
    			 * up
    			 */
    			pressActionKeyUp();
    		}else if(keyCode == 39){
    			/**
    			 * right
    			 */
    			pressActionKeyRight();
    		}else if(keyCode == 40){
    			/**
    			 * down
    			 */
    			pressActionKeyDown();
    		}
    	}else if(id==KeyEvent.KEY_RELEASED){
    		keyCode =  e.getKeyCode();
    		
    		if(keyCode == 37){
    			/**
    			 * left
    			 */
    			releaseActionKeyLeft();
    		}else if(keyCode == 38){
    			/**
    			 * up
    			 */
    			releaseActionKeyUp();
    		}else if(keyCode == 39){
    			/**
    			 * right
    			 */
    			releaseActionKeyRight();
    		}else if(keyCode == 40){
    			/**
    			 * down
    			 */
    			releaseActionKeyDown();
    		}
    	}
    	
    }
    
    public void pressActionKeyRight() throws IOException{
    	/**
    	 * command : 
    	 */
//    	Runtime.getRuntime().exec("**Command**");
    }
    public void pressActionKeyLeft() throws IOException{
    	/**
    	 * command : 
    	 */
//    	Runtime.getRuntime().exec("**Command**");
    }
    public void pressActionKeyUp() throws IOException{
    	/**
    	 * command : 
    	 */
//    	Runtime.getRuntime().exec("**Command**");
    }
    public void pressActionKeyDown() throws IOException, InterruptedException{
    	/**
    	 * command : 
    	 */
    	System.out.println("DEBUG::pressKEYDOWN");
    	readOutPut(Runtime.getRuntime().exec("ping -c 3 www.google.com"));
    }
    public void releaseActionKeyRight() throws IOException{
    	/**
    	 * command : 
    	 */
//    	Runtime.getRuntime().exec("**Command**");
    }
    public void releaseActionKeyLeft() throws IOException{
    	/**
    	 * command : 
    	 */
//    	Runtime.getRuntime().exec("**Command**");
    }
    public void releaseActionKeyUp() throws IOException{
    	/**
    	 * command : 
    	 */
//    	Runtime.getRuntime().exec("**Command**");
    }
    public void releaseActionKeyDown() throws IOException{
    	/**
    	 * command : 
    	 */
//    	Runtime.getRuntime().exec("**Command**");
    }
    
    
    public void readOutPut(Process proc) throws IOException, InterruptedException{
    	// Read the output

  	  int initialDelay = 100; // start after 0.1 seconds
      int period = 100;        // repeat every 0.1 seconds	
    	Timer timer = new Timer();
  	  TimerTask task = new TimerTask() {
  		  public void run() {
  			BufferedReader reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = "";
			try {
				if((line = reader.readLine()) != null){
					displayArea.append(line+"\n");
					displayArea.setCaretPosition(displayArea.getDocument().getLength());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  		  }
  	  };
  	  timer.scheduleAtFixedRate(task, initialDelay, period);
    }
}