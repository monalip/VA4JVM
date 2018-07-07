package se.kth.jpf_visual;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;


import se.kth.tracedata.Pair;
import se.kth.tracedata.Path;


public class ErrorTableAndMapPane extends JPanel implements ComponentListener {
	private static final long serialVersionUID = 1L;
	private mxGraphComponent graphComponent;
	mxGraph graph;
	LocationInGraph location;
	private mxGraphComponent menuGraphComponent;
	private mxGraphOutline outln = null;
	private ThreadStateView threadStateView = null;
	private mxGraphComponent threadStateComponent;
	private Object previousArrowCell = null;

	private JButton foldButton = null;
	private JButton expandButton = null;

	private JButton outlnButton = null;

	private int numOfThreads = -1;
	private ContentPane content;

	private MenuPane menu;
	private JSplitPane splitPane;
	private JSplitPane mapPane;
	private JPanel buttonPanel;

	double cellWidth = -1;

	private Path path;
	private List<Pair<Integer, Integer>> group;
	private List<String> threadNames=null;
	private Map<Integer, TextLineList> lineTable;
	private Map<Pair<Integer, Integer>, List<Pair<Integer, String>>> threadStateMap;
	private List<Double> threadHeightList;

	public ErrorTableAndMapPane() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		graphComponent = new mxGraphComponent(new mxGraph());
		graphComponent.getGraphHandler().setRemoveCellsFromParent(false);
		graphComponent.addComponentListener(this);
		graphComponent.setBorder(BorderFactory.createEmptyBorder());
		menuGraphComponent = new mxGraphComponent(new mxGraph());
		outln = new mxGraphOutline(graphComponent);
		outln.setDrawLabels(true);

		threadStateComponent = new mxGraphComponent(new mxGraph());

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		mapPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buttonPanel, outln);
		mapPane.setOneTouchExpandable(false);
		mapPane.setDividerLocation(PaneConstants.CELL_HEIGHT);
		mapPane.setBorder(BorderFactory.createEmptyBorder());

		addButtons();

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphComponent, mapPane);
		splitPane.setOneTouchExpandable(false);
		splitPane.setDividerLocation(600);
		splitPane.setBorder(BorderFactory.createEmptyBorder());

		this.add(splitPane);

	}

	public void setButton(JButton foldButton, JButton expandButton) {
		this.foldButton = foldButton;
		this.expandButton = expandButton;
	}

	public void draw(TraceData td) {
		this.location = new LocationInGraph();

		path = td.getPath();
		numOfThreads = td.getNumberOfThreads();
		group = td.getGroup();
		threadNames = td.getThreadNames();
		lineTable = td.getLineTable();
		threadStateMap = td.getThreadStateMap();

		// the main table
		cellWidth = (splitPane.getLeftComponent().getBounds().getWidth() - PaneConstants.RANGE_SIZE
				- PaneConstants.SIGN_SIZE - PaneConstants.BAR_SIZE) / numOfThreads;
		content = new ContentPane(cellWidth, numOfThreads, path, group, lineTable, location);
		content.resize(cellWidth);

		double rightCellWidth = (splitPane.getWidth() - splitPane.getLeftComponent().getBounds().getWidth()
				- PaneConstants.RANGE_SIZE - PaneConstants.ARROW_SIZE - PaneConstants.SIGN_SIZE
				- PaneConstants.BAR_SIZE) / numOfThreads;
		threadStateView = new ThreadStateView(rightCellWidth, numOfThreads, path, group, lineTable, threadStateMap,
				location);
		threadStateComponent.setGraph(threadStateView.getGraph()); // =
																	// threadStateView.getComponent();
		threadStateComponent.addComponentListener(new MapListener());
		threadStateComponent.getGraphControl().addMouseListener(new ClickListener());
		threadStateComponent.setDragEnabled(false);
		// initialize
		previousArrowCell = null;

		threadHeightList = threadStateView.getHeightList();

		content.foldAll(true);
		graph = content.getGraph();
		graphComponent.setGraph(graph);

		graphComponent.addKeyListener(new CopyListener());
		graphComponent.getGraphControl().addMouseListener(new FoldListener());

		// set menu
	
		menu = new MenuPane(cellWidth, threadNames);
		mxGraph menuGraph = menu.getGraph();
		menuGraphComponent.setGraph(menuGraph);
		menuGraphComponent.getGraphHandler().setRemoveCellsFromParent(false);
		menuGraphComponent.setBorder(BorderFactory.createEmptyBorder());

		graphComponent.setColumnHeaderView(menuGraphComponent);
		outln.setGraphComponent(graphComponent);

	}

	private void addButtons() {
		ButtonListener buttonListener = new ButtonListener();
		outlnButton = new JButton("Thread State");
		outlnButton.addActionListener(buttonListener);

		buttonPanel.add(outlnButton);
	}

	public void expand(Set<Pair<Integer, Integer>> set, String color) {
		content.highlightCells(set, color, false);
	}

	public void resetContent(Set<Pair<Integer, Integer>> set, String color) {
		content.highlightCells(set, color, true);

	}

	public void foldAll(boolean b) {
		content.foldAll(b);
	}

	public boolean areAllFolded() {
		return content.areAllFolded();
	}

	public boolean areAllExpanded() {
		return content.areAllExpanded();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// do nothing
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// do nothing

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// do nothing
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		if (numOfThreads < 0)
			return;
		double newWidth = (splitPane.getLeftComponent().getBounds().getWidth() * 1.0 - PaneConstants.RANGE_SIZE
				- PaneConstants.SIGN_SIZE - PaneConstants.BAR_SIZE) / numOfThreads;
		cellWidth = newWidth;
		content.resize(newWidth);
		menu.resize(newWidth);

		outln.setGraphComponent(graphComponent);

	}

	public JSplitPane getPane() {
		return splitPane;
	}

	private class FoldListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (content.areAllExpanded()) {
				foldButton.setSelected(false);
				foldButton.setEnabled(true);
				expandButton.setSelected(false);
				expandButton.setEnabled(false);
			} else if (content.areAllFolded()) {
				foldButton.setSelected(false);
				foldButton.setEnabled(false);
				expandButton.setSelected(false);
				expandButton.setEnabled(true);
			} else {
				foldButton.setSelected(false);
				foldButton.setEnabled(true);
				expandButton.setSelected(false);
				expandButton.setEnabled(true);
			}
		}

	}

	private class ClickListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {

			Point point = e.getPoint();
			if (point == null) {
				return;
			}

			int row = findGroup(point.y);
			setArrow(row);

			scrollContentToRow(row);
		}
	}

	private void setArrow(int row) {
		if (previousArrowCell != null) {
			threadStateView.resetArrow(previousArrowCell);
		}
		previousArrowCell = threadStateView.setArrow(row);
	}

	private void scrollContentToRow(int row) {
		Object cell = location.getRowCell(row);
		if (cell == null) {
			return;
		}
		mxCellState state = graph.getView().getState(cell);
		mxRectangle bounds = state;
		graphComponent.getVerticalScrollBar().setValue((int) bounds.getY());
	}

	private class CopyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			if ((e.getKeyCode() == KeyEvent.VK_C) && (((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)
					|| (e.getModifiers() & KeyEvent.META_MASK) != 0)) {
				Object[] cells = graph.getSelectionCells();

				if (cells == null)
					return;

				StringBuilder myString = new StringBuilder();
				for (Object o : cells) {
					myString.append(((mxCell) o).getValue() + "\n");
				}
				StringSelection stringSelection = new StringSelection(myString.toString());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
			}
		}

	};

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			assert (e.getSource() instanceof JButton);

			JButton button = (JButton) e.getSource();
			if (button.getText().equals("Outline")) {
				outlnButton.setText("Thread State");
				mapPane.setBottomComponent(outln);
			} else {
				outlnButton.setText("Outline");
				mapPane.setBottomComponent(threadStateComponent);
			}

		}

	}

	private class MapListener implements ComponentListener {

		@Override
		public void componentResized(ComponentEvent e) {
			double rightCellWidth = ((splitPane.getWidth() - splitPane.getLeftComponent().getBounds().getWidth()
					- PaneConstants.RANGE_SIZE - PaneConstants.ARROW_SIZE - PaneConstants.SIGN_SIZE
					- PaneConstants.BAR_SIZE)) / numOfThreads;
			threadStateView.setCellWidth(rightCellWidth);
			threadStateView.resize();
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// do nothing

		}

		@Override
		public void componentShown(ComponentEvent e) {
			// do nothing

		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// do nothing

		}

	}

	private int findGroup(double y) {
		int len = threadHeightList.size();
		int left = 0;
		int right = len - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (y < threadHeightList.get(mid)) {
				right = mid - 1;
			} else if (y > threadHeightList.get(mid)) {
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return left;
	}

}