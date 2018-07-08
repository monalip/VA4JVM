package se.kth.jpf_visual;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class ClassMethodExplorer extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private TraceData td;

	public ClassMethodExplorer(TraceData td) {

		super(new GridLayout(1, 0));
		this.td = td;

		// Create the nodes.
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Methods");
		createNodes(top);

		// Create a tree that allows one selection at a time.
		tree = new MyJTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);

		Dimension minimumSize = new Dimension(200, 100);
		treeView.setMinimumSize(minimumSize);

		// Add the split pane to this panel.
		add(treeView);
	}

	public JTree getTree() {
		return this.tree;
	}

	public void addTreeSelectionListener(TreeSelectionListener listener) {
		for (TreeSelectionListener tsl : this.tree.getTreeSelectionListeners()) {
			this.tree.removeTreeSelectionListener(tsl);
		}
		this.tree.cancelEditing();
		this.tree.clearSelection();
		((MyJTree) this.tree).clear();
		this.tree.resetKeyboardActions();
		this.tree.updateUI();

		this.tree.addTreeSelectionListener(listener);
	}

	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode parent = null;
		DefaultMutableTreeNode leaf = null;
		Map<String, Set<String>> classMethodStructure = td.getClassMethodStructure();

		for (String clsName : classMethodStructure.keySet()) {

			parent = new DefaultMutableTreeNode(clsName);
			top.add(parent);

			for (String methodName : classMethodStructure.get(clsName)) {
				leaf = new DefaultMutableTreeNode(new MethodNode(clsName, methodName));
				parent.add(leaf);
			}
		}

	}

}

//changed the visibility to public as we have to use this class in the ErrorTracePanel. 
//As public type class need to be defined in its own file hence we are creating new class of MethodNode


/*public class MethodNode {
	public String clsName;
	public String methodName;

	public MethodNode(String clsName, String methodName) {
		this.clsName = clsName;
		this.methodName = methodName;
	}

	public String toString() {
		return this.methodName;
	}

}*/
