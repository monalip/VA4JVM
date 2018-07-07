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

public class ClassFieldExplorer extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private TraceData td;

	public ClassFieldExplorer(TraceData td) {

		super(new GridLayout(1, 0));
		this.td = td;

		// Create the nodes.
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Fields");
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
		Map<String, Set<String>> classFieldStructure = td.getClassFieldStructure();

		for (String clsName : classFieldStructure.keySet()) {

			parent = new DefaultMutableTreeNode(clsName);
			top.add(parent);

			for (String fieldName : classFieldStructure.get(clsName)) {
				leaf = new DefaultMutableTreeNode(new FieldNode(clsName, fieldName));
				parent.add(leaf);
			}
		}

	}
}

class MyJTree extends JTree {

	private static final long serialVersionUID = 1L;

	public MyJTree(DefaultMutableTreeNode top) {
		super(top);
	}

	public void clear() {
		this.clearToggledPaths();
	}
}

//class FieldNode {   
// changed the visibility to public as we have to use this class in the ErrorTracePanel. 
//As public type class need to be defined in its own file hence we are creating new class of FieldNode
	
/*public class FieldNode {   
	public String clsName;
	public String fieldName;

	public FieldNode(String clsName, String fieldName) {
		this.clsName = clsName;
		this.fieldName = fieldName;
	}

	public String toString() {
		return this.fieldName;
	}

}*/
