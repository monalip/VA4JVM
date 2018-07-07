package se.kth.jpf_visual.jpf;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;



import gov.nasa.jpf.Config;
import gov.nasa.jpf.report.Publisher;
import gov.nasa.jpf.shell.ShellManager;
import gov.nasa.jpf.shell.ShellPanel;
import gov.nasa.jpf.shell.commands.VerifyCommand;
import gov.nasa.jpf.shell.listeners.VerifyCommandListener;
import gov.nasa.jpf.shell.util.ProgressTrackerUI;
import se.kth.jpf_visual.ClassFieldExplorer;
import se.kth.jpf_visual.ClassMethodExplorer;
import se.kth.jpf_visual.ErrorTableAndMapPane;
import se.kth.jpf_visual.FieldNode;
import se.kth.jpf_visual.MethodNode;
import se.kth.jpf_visual.PaneConstants;
import se.kth.jpf_visual.TraceData;
import se.kth.tracedata.Pair;
import se.kth.tracedata.Path;

/**
 * Basic output panel that divides new trace printer's results into browseable
 * topics. This panel uses a
 * {@link gov.nasa.jpf.shell.listeners.VerifyCommandListener} to keep track of
 * when the VerifyCommand is executed.
 * 
 * 
 * 
 */


public  class ErrorTracePanel extends ShellPanel implements VerifyCommandListener {

	/**
	 * 
	 */
	private static final String TOPICS = "TOPICS";
	private static final String PROGRESS = "PROGRESS";
	public ProgressTrackerUI tracker = new ProgressTrackerUI();
	private Path path;
	
	
	private TraceData td = null;




	
	private ErrorTableAndMapPane errorTrace = new ErrorTableAndMapPane();
	
	public se.kth.jpf_visual.ErrorTracePanel gui = new se.kth.jpf_visual.ErrorTracePanel();
	private CardLayout layout = new CardLayout();
	private static final long serialVersionUID = 1L;
	

	public ErrorTracePanel() {
		super("Error Trace", null, "View JPF's Output");
		ShellManager.getManager().addCommandListener(VerifyCommand.class, this);
		JPanel tablePanel = new JPanel();
		tablePanel=  gui.tablePanel;
		
		setLayout(layout);

		add(tablePanel, TOPICS);
		add(tracker,PROGRESS);
		
		
		//layout.show(this, PROGRESS);
		
		
		
	}

	String publishers = null;
	@Override
	//a variable is declared with final keyword, it’s value can’t be modified,essentially, a constant.
		public void preCommand(final VerifyCommand command) {
			requestShellFocus();
			Config config = ShellManager.getManager().getConfig();
			publishers = config.getProperty("report.publisher", "");
			if (publishers.contains("errorTracePrinter")) {
				config.put("report.publisher", config.getProperty("report.publisher", "") + ",errorTrace");
				config.put("report.errorTrace.class", ErrorTracePrinter.class.getName());
			}
			tracker.resetFields();
			
			
		}

	@Override
	/**
	 * Just show the results of the JPF verification.
	 * 
	 * @param command
	 */
public void postCommand(VerifyCommand command) {
		
		
		
		//'Added displayErrMsg(boolean err) which If there is an error in program. 

				gui.displayErrMsg(command.errorOccured());
				
				boolean found = false;
				for(Publisher publisher: command.getJPF().getReporter().getPublishers())
				{
				
					if (publisher instanceof ErrorTracePrinter) 
					{
						if (!found) {
							found = true;
						}
						//path = ((ErrorTracePrinter) publisher).getPath();
						path = ((ErrorTracePrinter) publisher).getPath();
					}
				}	
					//path= null;
					if (found && path != null) 
					{
						// reset
						td = new TraceData(path);
						gui.drowErrTrace(path,found);
						
						layout.show(this, TOPICS);
						getShell().requestFocus(this);
						
					}
					else 
					{
						ShellManager.getManager().getConfig().put("report.publisher", publishers);
						publishers = null;
						if (found && path == null) {
							JOptionPane.showMessageDialog(this, "No error trace is generated!", "No Error Found",
									JOptionPane.NO_OPTION | JOptionPane.ERROR_MESSAGE);

							
						}else
						{
							JOptionPane.showMessageDialog(this, "ErrorTracePrinter is not set as a publisher!", "Error config .jpf",
									JOptionPane.NO_OPTION | JOptionPane.ERROR_MESSAGE);

						}

					}
						
			
	}



	/**
	 * Once JPF creates an instance of the TopicPublisher it is grabbed after
	 * initialization by the tracker.
	 * 
	 * @param command
	 */
	@Override
	public void afterJPFInit(VerifyCommand command) {
		tracker.attachJPF(command.getJPF());
		layout.show(this, PROGRESS);
		
	}
	@Override
	public void exceptionDuringVerify(Exception ex) {
		// TODO Auto-generated method stub
		
	}

}
	
