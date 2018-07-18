package se.kth.jpf_visual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;

public class MenuPane {
	private double cellWidth = 0;
	private mxGraph graph;
	private mxIGraphModel menuModel;
	private int numOfThreads = -1;
	private Object menuParent;
	private List<String> threadNames;

	public MenuPane(double width, List<String> tName) {
		this.cellWidth = width;
		this.threadNames = tName;
		this.numOfThreads = threadNames.size();

		graph = new mxGraph();
		menuModel = graph.getModel();

		graph.setCellsResizable(false);
		graph.setCollapseToPreferredSize(false);
		graph.setCellsEditable(false);

		setStyles();

		setLayoutManager();

		menuParent = graph.getDefaultParent();
		drawMenu();
	}

	protected void setStyles() {

		Map<String, Object> mDefaultstyle = graph.getStylesheet().getDefaultVertexStyle();
		mDefaultstyle.put(mxConstants.STYLE_VERTICAL_ALIGN, "middle");
		mDefaultstyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "white");
		mDefaultstyle.put(mxConstants.STYLE_FONTSIZE, PaneConstants.FONT_SIZE);
		mDefaultstyle.put(mxConstants.STYLE_STARTSIZE, 0);
		mDefaultstyle.put(mxConstants.STYLE_HORIZONTAL, true);
		mDefaultstyle.put(mxConstants.STYLE_FONTCOLOR, "black");
		mDefaultstyle.put(mxConstants.STYLE_STROKECOLOR, "black");
		mDefaultstyle.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
		mDefaultstyle.remove(mxConstants.STYLE_FILLCOLOR);
		mDefaultstyle.put(mxConstants.STYLE_FOLDABLE, false);

		Map<String, Object> menuStyle = new HashMap<String, Object>(mDefaultstyle);
		menuStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
		graph.getStylesheet().putCellStyle("menu", menuStyle);
	}

	protected void setLayoutManager() {
		@SuppressWarnings("unused")
		mxLayoutManager menuLayoutMng = new mxLayoutManager(graph) {
			public mxIGraphLayout getLayout(Object parent) {
				if (menuModel.getChildCount(parent) > 0 && menuModel.getStyle(parent) != null
						&& menuModel.getStyle(parent).contains("menu")) {
					return new mxStackLayout(graph, true);
				}
				return null;
			}

		};
	}

	protected void drawMenu() {

		menuModel.beginUpdate();
		try {
			// draw the menu
			mxCell menu = (mxCell) graph.insertVertex(menuParent, null, null, 0, 0,
					PaneConstants.RANGE_SIZE + PaneConstants.SIGN_SIZE + cellWidth * numOfThreads, 0, "menu");
			menu.setConnectable(false);

			mxCell tranCell = (mxCell) graph.insertVertex(menu, null, "Trans.", 0, 0, PaneConstants.RANGE_SIZE,
					PaneConstants.CELL_HEIGHT, "menu");
			tranCell.setConnectable(false);

			for (int i = 0; i < numOfThreads; i++) {
				double cw = cellWidth;
				if (i == 0) {
					cw = cellWidth + PaneConstants.SIGN_SIZE;
				}
				((mxCell) graph.insertVertex(menu, null, threadNames.get(i) + "\n" + i, 0, 0, cw,
						PaneConstants.CELL_HEIGHT, "menu")).setConnectable(false);

			}
		} finally {
			menuModel.endUpdate();

		}
	}

	public void resize(double newCellWidth) {
		this.cellWidth = newCellWidth;
		Object parent = graph.getDefaultParent();
		for (Object menu : graph.getChildCells(parent)) {
			double menuWidth = PaneConstants.RANGE_SIZE + PaneConstants.SIGN_SIZE + cellWidth * numOfThreads;
			menuModel.getGeometry(menu).setWidth(menuWidth);
			System.out.println("resize menu: " + menuModel.getChildCount(menu));
			for (int i = 1; i < menuModel.getChildCount(menu); i++) {
				Object obj = menuModel.getChildAt(menu, i);
				double cw = cellWidth;
				if (i == 1) {
					cw = cellWidth + PaneConstants.SIGN_SIZE;
				}
				graph.resizeCell(obj, new mxRectangle(0, 0, cw, graph.getCellGeometry(obj).getHeight()));
			}
		}
		graph.refresh();
	}

	public mxGraph getGraph() {
		return graph;
	}
}
