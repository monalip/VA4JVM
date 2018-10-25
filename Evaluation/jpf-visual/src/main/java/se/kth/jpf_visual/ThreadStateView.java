package se.kth.jpf_visual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;

import se.kth.tracedata.Pair;
import se.kth.tracedata.Path;

public class ThreadStateView {

	private double cellWidth = 0;
	private mxIGraphModel model;
	private Object parent;
	private mxGraph graph;
	private mxGraphComponent graphComponent;

	private Map<String, Object> contentStyle;

	private int numOfThreads = -1;
	private Path path;
	private List<Pair<Integer, Integer>> group = new ArrayList<>();
	private Map<Integer, TextLineList> lineTable;
	private Map<Pair<Integer, Integer>, List<Pair<Integer, String>>> threadStateMap;
	private double htPerLine;
	private int numOfRows = -1;

	private Map<Integer, String> previousColor = new HashMap<>();
	private Map<Integer, Object> previousThreadCell = new HashMap<>();

	private LocationInGraph location;

	private List<Double> heightList = new ArrayList<>();

	public ThreadStateView(double width, int nThreads, Path p, List<Pair<Integer, Integer>> grp,
			Map<Integer, TextLineList> lt, Map<Pair<Integer, Integer>, List<Pair<Integer, String>>> threadStateMap,
			LocationInGraph locate) {
		this.cellWidth = width;
		this.lineTable = lt;
		this.numOfThreads = nThreads;
		this.group = grp;
		this.path = p;
		this.cellWidth = width;
		this.numOfRows = group.size();
		this.threadStateMap = threadStateMap;
		this.location = locate;
		// create graph
		this.graph = new mxGraph();

		// get the model
		this.model = graph.getModel();

		graph.setCellsEditable(false);
		graph.setCellsResizable(false);
		graph.setCellsSelectable(false);

		setStyles();
		this.htPerLine = mxUtils.getFontMetrics(mxUtils.getFont(graph.getStylesheet().getStyles().get("content")))
				.getHeight() * 0.5;
		setLayoutManager();
		drawTable();

	}

	public void setCellWidth(double newWidth) {
		this.cellWidth = newWidth;
	}

	protected void drawTable() {
		parent = graph.getDefaultParent();

		model.beginUpdate();
		try {
			// show the details
			double absoluteY = 0;
			for (int row = 0; row < numOfRows; row++) {
				// if (!lineTable.containsKey(row) ||
				// lineTable.get(row).isNoSrc()) {
				// lineTable.remove(row);
				// continue;
				// }

				TextLineList lineList = lineTable.get(row);
				double currHt = lineList.getHeight() * htPerLine;

				/**
				 * The big box around the first row with black border
				 */
				mxCell rowCell = (mxCell) drawRowCell(row);

				/**
				 * The arrow no border
				 */
				mxCell arrowCell = (mxCell) drawArrowCell(row, currHt, rowCell);

				/**
				 * The transition range no border
				 */
				mxCell rangeCell = (mxCell) drawRangeCell(row, currHt, rowCell);

				/**
				 * The big box outside the swimlane
				 */
				mxCell rightCell = (mxCell) drawRightCell(row, currHt, rowCell);

				int from = group.get(row)._1;
				int threadIdx = path.get(from).getThreadIndex();

				List<TextLine> txtLines = lineTable.get(row).getList();
				int plainLines = 0;
				for (int ithLine = 0; ithLine < txtLines.size(); ithLine++) {

					/**
					 * The blank thread
					 */
					mxCell blankCell = (mxCell) drawBlankCell(currHt, rightCell);
					blankCell.setId("" + row);
					/**
					 * The thread state in threadCell
					 */
					boolean hasState = drawThreadState(row, ithLine, threadIdx, blankCell);

					// draw transition for every thread
					if (row == (numOfRows - 1) && ithLine == (txtLines.size() - 1)) {
						drawLastTransition(row, threadIdx, blankCell);
					} else if (!hasState) {
						plainLines++;
						changeHeight(blankCell, htPerLine * PaneConstants.PlainLineScale);
					}
				}
				double realHt = plainLines * htPerLine * PaneConstants.PlainLineScale
						+ (txtLines.size() - plainLines) * htPerLine;
				// resize row and right cell
				changeHeight(rowCell, realHt);
				changeHeight(rightCell, realHt);
				changeHeight(rangeCell, realHt);
				changeHeight(arrowCell, realHt);
				absoluteY += realHt;
				heightList.add(absoluteY);
			}

		} finally {
			model.endUpdate();
		}
	}

	protected void setLayoutManager() {
		@SuppressWarnings("unused")
		mxLayoutManager layoutMng = new mxLayoutManager(graph) {
			public mxIGraphLayout getLayout(Object parent) {
				String st = model.getStyle(parent);

				if (model.getChildCount(parent) > 0 && (st == null || st.contains("right") || st.contains("range"))) {
					// vertical
					return new mxStackLayout(graph, false);
				} else if (model.getChildCount(parent) > 0 && !st.contains("content")) {
					// horizontal
					return new mxStackLayout(graph, true);
				}
				return null;
			}

		};
	}

	protected void setStyles() {

		Map<String, Object> style = graph.getStylesheet().getDefaultVertexStyle();

		style.put(mxConstants.STYLE_VERTICAL_ALIGN, "middle");
		style.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "white");
		style.put(mxConstants.STYLE_FONTSIZE, PaneConstants.FONT_SIZE);
		style.put(mxConstants.STYLE_STARTSIZE, 0);
		style.put(mxConstants.STYLE_HORIZONTAL, true);
		style.put(mxConstants.STYLE_FONTCOLOR, "black");
		style.put(mxConstants.STYLE_STROKECOLOR, "black");
		style.put(mxConstants.STYLE_ALIGN, "left");
		style.put(mxConstants.STYLE_FOLDABLE, false);
		style.put(mxConstants.STYLE_FILL_OPACITY, 0);
		style.put(mxConstants.STYLE_FILLCOLOR, "none");

		Map<String, Object> switchStyle = new HashMap<>(style);
		switchStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_SWIMLANE);
		switchStyle.put(mxConstants.STYLE_STROKECOLOR, "none");
		graph.getStylesheet().putCellStyle("switch", switchStyle);

		Map<String, Object> rightStyle = new HashMap<>(style);
		graph.getStylesheet().putCellStyle("right", rightStyle);

		Map<String, Object> borderStyle = new HashMap<>(style);
		borderStyle.put(mxConstants.STYLE_STROKECOLOR, "none");
		graph.getStylesheet().putCellStyle("border", borderStyle);

		Map<String, Object> rangeStyle = new HashMap<String, Object>(style);
		rangeStyle.put(mxConstants.STYLE_SPACING_LEFT, PaneConstants.LEFT_SPACE);
		// rangeStyle.put(mxConstants.STYLE_STROKECOLOR, "none");
		graph.getStylesheet().putCellStyle("range", rangeStyle);

		Map<String, Object> arrowStyle = new HashMap<String, Object>(style);
		// arrowStyle.put(mxConstants.STYLE_SPACING_LEFT,
		// PaneConstants.LEFT_SPACE);
		arrowStyle.put(mxConstants.STYLE_STROKECOLOR, "none");
		arrowStyle.put(mxConstants.STYLE_FONTCOLOR, "blue");
		graph.getStylesheet().putCellStyle("arrow", arrowStyle);

		contentStyle = new HashMap<String, Object>(style);
		contentStyle.put(mxConstants.STYLE_STROKECOLOR, "none");
		contentStyle.put(mxConstants.STYLE_OPACITY, 0);
		contentStyle.put(mxConstants.STYLE_FILL_OPACITY, 0);
		contentStyle.put(mxConstants.STYLE_SPACING_TOP, PaneConstants.TOP_SPACE);
		contentStyle.remove(mxConstants.STYLE_VERTICAL_ALIGN);
		contentStyle.put(mxConstants.STYLE_FONTFAMILY, "Courier");

		graph.getStylesheet().putCellStyle("content", contentStyle);
	}

	public mxGraph getGraph() {
		return this.graph;
	}

	public List<Double> getHeightList() {
		return new ArrayList<>(heightList);
	}

	public void resize() {
		// resize rowCell
		for (Object rowCell : graph.getChildCells(parent)) {
			if (!model.isVertex(rowCell)) {
				continue;
			}
			changeWidth(rowCell, PaneConstants.RANGE_SIZE + PaneConstants.ARROW_SIZE + numOfThreads * cellWidth);
			// resize rightCell
			for (Object rightCell : graph.getChildCells(rowCell)) {
				if (!model.getStyle(rightCell).contains("right")) {
					continue;
				}
				changeWidth(rightCell, numOfThreads * cellWidth);
				// resize blankCell
				for (Object blankCell : graph.getChildCells(rightCell)) {
					changeWidth(blankCell, numOfThreads * cellWidth);
					// reposition trheadStateCell
					for (Object threadStateCell : graph.getChildCells(blankCell)) {
						int ti = Integer.parseInt(((mxCell) threadStateCell).getId());
						model.getGeometry(threadStateCell).setX(ti * cellWidth);
					}
				}
			}
		}
		graph.refresh();
	}

	private void changeWidth(Object cell, double width) {
		model.getGeometry(cell).setWidth(width);
	}

	private void changeHeight(Object cell, double height) {
		model.getGeometry(cell).setHeight(height);
	}

	private Object drawRowCell(int row) {
		mxCell rowCell = (mxCell) graph.insertVertex(parent, null, null, 0, 0,
				PaneConstants.RANGE_SIZE + PaneConstants.ARROW_SIZE + numOfThreads * cellWidth, 0, "border");
		rowCell.setConnectable(false);
		return rowCell;
	}

	private Object drawArrowCell(int row, double currHt, Object rowCell) {
		mxCell arrowCell = (mxCell) graph.insertVertex(rowCell, null, null, 0, 0, PaneConstants.ARROW_SIZE, currHt,
				"arrow");
		arrowCell.setConnectable(false);
		arrowCell.setId("" + row);
		location.addArrowCell(row, arrowCell);
		return arrowCell;

	}

	public String getCellStyle(Object cell) {
		return model.getStyle(cell);
	}

	public String getCellId(Object cell) {
		String style = getCellStyle(cell);
		if (style != null && style.contains("vertex")) {
			mxCell myCell = (mxCell) cell;
			return getCellId(model.getParent(myCell));
		} else {
			return ((mxCell) cell).getId();
		}
	}

	public Object setArrow(int row) {

		Object cell = location.getArrowCell(row);
		if (cell == null)
			return null;
		model.setValue(cell, "➤");
		return cell;
	}

	public void resetArrow(Object cell) {
		model.setValue(cell, null);
	}

	private Object drawRangeCell(int row, double currHt, Object rowCell) {
		int from = group.get(row)._1;
		int to = group.get(row)._2;
		String rangeStr = null;
		if (from != to) {
			rangeStr = from + "-" + to;
		} else {
			rangeStr = "" + from;
		}
		mxCell rangeCell = (mxCell) graph.insertVertex(rowCell, null, rangeStr, 0, 0, PaneConstants.RANGE_SIZE, currHt,
				"range");
		rangeCell.setConnectable(false);
		rangeCell.setId("" + row);
		return rangeCell;
	}

	private Object drawRightCell(int row, double currHt, Object rowCell) {
		mxCell rightCell = (mxCell) graph.insertVertex(rowCell, null, null, 0, 0, numOfThreads * cellWidth, currHt,
				"right");
		rightCell.setId("" + row);
		rightCell.setConnectable(false);
		return rightCell;
	}

	private Object drawBlankCell(double currHt, Object rightCell) {
		mxCell blankCell = (mxCell) graph.insertVertex(rightCell, null, null, 0, 0, numOfThreads * cellWidth, htPerLine,
				"content");
		blankCell.setConnectable(false);
		return blankCell;

	}

	private boolean drawThreadState(int row, int ithLine, int threadIdx, Object blankCell) {

		Pair<Integer, Integer> pair = new Pair<>(row, ithLine);
		if (threadStateMap.containsKey(pair)) {
			List<Pair<Integer, String>> list = threadStateMap.get(pair);
			for (Pair<Integer, String> p : list) {
				int thrd = p._1;
				String strId = p._2;
				String color = PaneConstants.threadState.get(strId);
				String styleName = "vertex" + strId;

				addNewVertexStyle(styleName, color);
				mxCell threadStateCell = (mxCell) graph.insertVertex(blankCell, null, null, thrd * cellWidth, 0,
						PaneConstants.THREAD_STATE_WIDTH, htPerLine, styleName);
				threadStateCell.setId("" + thrd);
				if (previousThreadCell.containsKey(thrd)) {
					String prevColor = previousColor.get(thrd);
					addNewEdgeStyle("edge" + prevColor, prevColor);
					graph.insertEdge(parent, null, null, previousThreadCell.get(thrd), threadStateCell,
							"edge" + prevColor);
				}
				previousThreadCell.put(thrd, threadStateCell);
				if (!(strId == "LOCK" && threadIdx == thrd)) {
					previousColor.put(thrd, color);
				}
			}
			return true;
		}
		return false;
	}

	private void drawLastTransition(int row, int ithLine, Object blankCell) {
		Pair<Integer, Integer> pair = new Pair<>(row, ithLine);
		Set<Integer> exptSet = new HashSet<>();
		if (threadStateMap.containsKey(pair)) {
			for (Pair<Integer, String> p : threadStateMap.get(pair)) {
				exptSet.add(p._1);
			}
		}

		for (int ti = 0; ti < numOfThreads; ti++) {
			if (previousColor.containsKey(ti) && previousColor.get(ti) != "none" && !exptSet.contains(ti)) {
				addNewVertexStyle("vertex" + "none", "none");
				mxCell threadStateCell = (mxCell) graph.insertVertex(blankCell, null, null, ti * cellWidth, htPerLine,
						PaneConstants.THREAD_STATE_WIDTH, 0, "vertex" + "none");
				threadStateCell.setId("" + ti);
				if (previousThreadCell.containsKey(ti)) {
					String prevColor = previousColor.get(ti);
					addNewEdgeStyle("edge" + prevColor, prevColor);
					graph.insertEdge(parent, null, null, previousThreadCell.get(ti), threadStateCell,
							"edge" + prevColor);
				}
			}
		}
	}

	private void addNewVertexStyle(String name, String color) {
		Map<String, Object> hlStyle = new HashMap<>(graph.getStylesheet().getDefaultVertexStyle());
		hlStyle.put(mxConstants.STYLE_FILLCOLOR, color);
		hlStyle.put(mxConstants.STYLE_STROKECOLOR, "none");

		graph.getStylesheet().putCellStyle(name, hlStyle);
	}

	private void addNewEdgeStyle(String name, String color) {

		Map<String, Object> hlStyle = new HashMap<>(graph.getStylesheet().getDefaultEdgeStyle());
		hlStyle.put(mxConstants.STYLE_FILLCOLOR, color);
		hlStyle.put(mxConstants.STYLE_FILL_OPACITY, 0);
		hlStyle.put(mxConstants.STYLE_STROKECOLOR, color);
		hlStyle.put(mxConstants.STYLE_STROKEWIDTH, 2);

		hlStyle.put(mxConstants.STYLE_ENDARROW, "none");
		graph.getStylesheet().putCellStyle(name, hlStyle);

	}

}
