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
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;


import se.kth.tracedata.Pair;
import se.kth.tracedata.Path;


/**
 * Class {@code ContentPane} is the central pane of the error trace panel. Also
 * it is the left pane of ErrorTableAndMapPane.
 * 
 * @author qiyitang
 */

public class ContentPane {

	private double cellWidth = 0;
	private mxGraph graph;
	private mxIGraphModel model;
	private Object parent;

	private Map<String, Object> swimStyle;
	private Map<String, Object> contentStyle;

	private int numOfThreads = -1;
	private Path path;
	private List<Pair<Integer, Integer>> group = new ArrayList<>();
	private Map<Integer, TextLineList> lineTable;
	private double htPerLine;
	private double wtPerLine = 7;
	private int numOfRows = -1;

	private LocationInGraph location;

	/**
	 * Class constructor.
	 * 
	 * Draw the table.
	 * 
	 * @param width
	 *            the cell width of each thread in the table
	 * @param nThreads
	 *            number of threads of the programme
	 * @param p
	 *            the path of the error trace
	 * @param grp
	 *            the list of <the start thread, the end thread> for each row
	 * @param lt
	 *            the map of <row, TextLineList>
	 * @param locate
	 *            the place to store the components of the table
	 */
	public ContentPane(double width, int nThreads, Path p, List<Pair<Integer, Integer>> grp,
			Map<Integer, TextLineList> lt, LocationInGraph locate) {

		this.lineTable = lt;
		this.numOfThreads = nThreads;
		this.group = grp;
		this.path = p;
		this.cellWidth = width;
		this.numOfRows = group.size();
		this.location = locate;

		// create graph
		graph = new mxGraph() {
			public mxRectangle getStartSize(Object swimlane) {
				mxRectangle result = new mxRectangle();
				mxCellState state = view.getState(swimlane);
				Map<String, Object> temp = getCellStyle(swimlane);

				Map<String, Object> style = (temp != null) ? temp : state.getStyle();

				if (style != null) {
					double size = mxUtils.getDouble(style, mxConstants.STYLE_STARTSIZE, mxConstants.DEFAULT_STARTSIZE);

					if (mxUtils.isTrue(style, mxConstants.STYLE_HORIZONTAL, true)) {
						result.setHeight(size);
					} else {
						result.setWidth(size);
					}
				}

				return result;
			}
		};

		// get the model
		model = graph.getModel();

		// set the properties of the graph
		graph.setCellsEditable(false);
		graph.setCellsResizable(false);
		graph.setCollapseToPreferredSize(false);

		// set the styles in the graph
		setStyles();

		// initiate the height of each line
		this.htPerLine = mxUtils.getFontMetrics(mxUtils.getFont(graph.getStylesheet().getStyles().get("content")))
				.getHeight() + 5;

		// define the folding actions
		installFoldingHandler();

		// set the layout manager
		setLayoutManager();

		// draw the table
		drawTable();

	}

	/**
	 * @return the mxGraph of the table
	 */
	public mxGraph getGraph() {
		return graph;
	}

	/**
	 * @return the style of the cell
	 */
	public String getCellStyle(Object cell) {
		return model.getStyle(cell);
	}

	/**
	 * 
	 * @return the id the given cell
	 */
	public String getCellId(Object cell) {
		return ((mxCell) cell).getId();
	}

	/**
	 * resize the table with the new cell width as the width of the thread cell
	 * 
	 * @param newCellWidth
	 */
	public void resize(double newCellWidth) {
		this.cellWidth = newCellWidth;
		// resize row cells
		for (Object rowCell : location.getAllRowCells()) {
			setCellWidth(rowCell, PaneConstants.SIGN_SIZE + PaneConstants.RANGE_SIZE + numOfThreads * cellWidth);
		}

		// resize right cells
		for (Object rightCell : location.getAllRightCells()) {
			setCellWidth(rightCell, PaneConstants.SIGN_SIZE + numOfThreads * cellWidth);

		}

		// resize thread labels
		for (Object threadLabel : location.getAllThreadLabels()) {
			setCellWidth(threadLabel, numOfThreads * cellWidth);
			String st = model.getStyle(threadLabel);
			Map<String, Object> tmpStyle = graph.getStylesheet().getStyles().get(st);
			int threadIdx = Integer.parseInt(((mxCell) threadLabel).getId());
			tmpStyle.put(mxConstants.STYLE_SPACING_LEFT,
					cellWidth * threadIdx + cellWidth / 2 - PaneConstants.LEFT_SPACE / 2);

		}

		// resize summary blanks
		for (Object summaryBlank : location.getAllSummaryBlanks()) {
			int threadIdx = Integer.parseInt(((mxCell) summaryBlank).getId());
			setCellWidth(summaryBlank, threadIdx * cellWidth);
		}

		// resize summary border cells
		for (Object summaryBorder : location.getAllSummaryBorderCells()) {
			int threadIdx = Integer.parseInt(((mxCell) summaryBorder).getId());
			setCellWidth(summaryBorder, (numOfThreads - threadIdx) * cellWidth);
		}

		// resize swim cells
		for (Object swimCell : location.getAllSwimCells()) {
			if (!graph.isCellCollapsed(swimCell)) {
				setCellWidth(swimCell, numOfThreads * cellWidth + PaneConstants.SIGN_SIZE);
			} else {
				model.getGeometry(swimCell).getAlternateBounds()
						.setWidth(numOfThreads * cellWidth + PaneConstants.SIGN_SIZE);
			}
			graph.foldCells(!((mxCell) swimCell).isCollapsed(), false, new Object[] { swimCell }, false);
			graph.foldCells(!((mxCell) swimCell).isCollapsed(), false, new Object[] { swimCell }, false);

		}

		graph.refresh();
	}

	/**
	 * 
	 * @param set
	 *            the set of cells <row, the line> which will be highlighted
	 * @param color
	 *            the color in which the cells should be highlighted
	 * @param reset
	 *            if true, then the set should be reset to the default style;
	 *            else if false, the set should be highlighted in the new style
	 *            with the color.
	 */
	public void highlightCells(Set<Pair<Integer, Integer>> set, String color, boolean reset) {
		Set<Integer> rowSet = new HashSet<>();
		for (Pair<Integer, Integer> p : set) {
			rowSet.add(p._1);
		}

		Map<Integer, Set<Integer>> rowLineMap = new HashMap<>();
		for (Pair<Integer, Integer> p : set) {
			int rowNum = p._1;

			if (rowLineMap.containsKey(rowNum)) {
				rowLineMap.get(rowNum).add(p._2);
			} else {
				Set<Integer> newSet = new HashSet<>();
				newSet.add(p._2);
				rowLineMap.put(rowNum, newSet);
			}
		}

		for (int row : rowSet) {
			int htChange = 0;
			for (int line : rowLineMap.get(row)) {

				Pair<Integer, Integer> p = new Pair<>(row, line);
				TextLine tl = lineTable.get(row).getList().get(line);

				Object contentBox = location.getContentCell(p);
				mxCell content = (mxCell) graph.getChildCells(contentBox)[0];

				SummaryCell sCell = location.getSummaryCell(p);
				mxCell summaryBox = (sCell == null) ? null : (mxCell) sCell.getSummary();

				if (!reset) {
					if (tl.isHighlightedColor(color)) {
						return;
					}
					// graph.foldCells(false, false, new Object[] {
					// location.getSwimCell(row) });

					String hlStyleName = "highlight" + color;
					addNewStyle(hlStyleName, color);

					if (!tl.isHighlighted()) {

						// the detailed content
						content.setStyle(hlStyleName);

						// the summary
						htChange += highlightSummaryLine(sCell, hlStyleName);
					} else {
						// the detailed content
						addColorBlock(contentBox, hlStyleName);

						// the summary
						if (sCell != null) {
							addColorBlock(summaryBox, hlStyleName);
						}
					}
					tl.setHighlight(color);
				} else {
					// reset
					if (!tl.isHighlightedColor(color)) {
						return;
					}
					tl.resetHighlight(color);

					// detail content
					String newColor = tl.getOneColor();
					removeColorBlock(contentBox, color, newColor);

					boolean isHighlight = tl.isHighlighted();

					if (!isHighlight) {
						content.setStyle("content");
					} else {
						content.setStyle("highlight" + newColor);
					}

					// the summary
					htChange += resetSummaryLine(sCell, color, newColor, isHighlight);
				}

			}

			reformatRow(row);

			mxCell summaryBorder = (mxCell) location.getSummaryBorderCell(row);
			if (htChange == 0) {
				continue;
			}
			double tmpHt = htPerLine * htChange + model.getGeometry(summaryBorder).getHeight();

			setCellHeight(summaryBorder, tmpHt);
			if (((mxCell) summaryBorder).isVisible()) {
				setCellHeight(location.getRightCell(row), tmpHt);
				setCellHeight(location.getRowCell(row), tmpHt);
				setCellHeight(location.getSwimCell(row), tmpHt);
			} else {
				setCellAlterHeight(location.getSwimCell(row), tmpHt);
			}

		}

		// graph.refresh();

	}

	/**
	 * set the alternative height of the given swim cell
	 * 
	 * @param cell
	 *            the cell
	 * @param h
	 *            the new alternative height of the cell
	 */
	private void setCellAlterHeight(Object cell, double h) {
		model.getGeometry(cell).getAlternateBounds().setHeight(h);
	}

	/**
	 * set the height of the given cell
	 * 
	 * @param cell
	 *            the cell
	 * @param h
	 *            the new height of the cell
	 */
	private void setCellHeight(Object cell, double h) {
		model.getGeometry(cell).setHeight(h);
	}

	/**
	 * set the width of the given cell
	 * 
	 * @param cell
	 *            the cell
	 * @param h
	 *            the new width of the cell
	 */
	private void setCellWidth(Object cell, double w) {
		model.getGeometry(cell).setWidth(w);
	}

	/**
	 * redraw the summary cell in the given row
	 * 
	 * @param row
	 *            the row which should be redraw
	 */
	private void reformatRow(int row) {
		mxCell sw = (mxCell) location.getSwitchCell(row);
		if (sw != null) {
			graph.foldCells(!sw.isCollapsed(), false, new Object[] { sw }, false);
		}
	}

	/**
	 * add a small color square for the given cell (the cell should be detail
	 * contentBox or the summary contentBox)
	 * 
	 * @param cell
	 *            the cell that the color block should be added
	 * @param styleName
	 *            the style name of the added color block
	 */
	private void addColorBlock(Object cell, String styleName) {
		mxCell colorBlock = (mxCell) graph.insertVertex(cell, null, " ", 0, 0, 5, htPerLine, styleName);
		colorBlock.setConnectable(false);
	}

	/**
	 * remove style with the removeColor from the SummaryCell and set the style
	 * which has the new color
	 * 
	 * @param sCell
	 *            the given cell
	 * @param removeColor
	 *            the color should be reset
	 * @param newColor
	 *            the color the new style should have
	 * @param isHighlight
	 *            is the cell already highlighted
	 * @return the height change of the given summary cell
	 */
	private int resetSummaryLine(SummaryCell sCell, String removeColor, String newColor, boolean isHighlight) {
		if (sCell == null)
			return 0;

		int htChange = 0;
		mxCell summaryBox = (mxCell) sCell.getSummary();
		mxCell summaryContent = (mxCell) model.getChildAt(summaryBox, 0);
		mxCell nextDots = (mxCell) sCell.getNextDots();
		mxCell nextSrc = (mxCell) sCell.getNextSrc();

		removeColorBlock(summaryBox, removeColor, newColor);

		if (!isHighlight) {
			if (!sCell.isFirst() && !sCell.isLast()) {
				summaryBox.setVisible(false);
				htChange--;
			}

			summaryContent.setStyle("content");

			// check the previous visible cell and set the dots
			// next to it as visible
			SummaryCell prevCell = sCell.getPreviousSummary();
			while (prevCell != null && !((mxCell) prevCell.getSummary()).isVisible()) {
				prevCell = prevCell.getPreviousSummary();
			}

			if (prevCell != null && !((mxCell) prevCell.getNextDots()).isVisible()) {
				((mxCell) prevCell.getNextDots()).setVisible(true);
				htChange++;
			}

			if (nextSrc != null && nextDots.isVisible()) {
				nextDots.setVisible(false);
				htChange--;
			}
		} else {
			summaryContent.setStyle("highlight" + newColor);
		}
		return htChange;
	}

	/**
	 * add a new style based on the contentStyle
	 * 
	 * @param name
	 *            the name of the new style
	 * @param color
	 *            the color of the new style
	 */
	private void addNewStyle(String name, String color) {
		Map<String, Object> hlStyle = new HashMap<>(contentStyle);
		hlStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, color);
		graph.getStylesheet().putCellStyle(name, hlStyle);
	}

	/**
	 * highlight the given cell (should be a SummaryCell) with the style
	 * 
	 * @param sCell
	 *            the SummaryCell will be highlighted
	 * @param style
	 *            the style the cell will be set to
	 * @return the height change of the Summary Cell
	 */
	private int highlightSummaryLine(SummaryCell sCell, String style) {
		if (sCell == null)
			return 0;

		int htChange = 0;
		mxCell summaryBox = (mxCell) sCell.getSummary();
		mxCell summaryContent = (mxCell) model.getChildAt(summaryBox, 0);
		mxCell prevDots = (mxCell) sCell.getPrevDots();
		mxCell nextDots = (mxCell) sCell.getNextDots();
		mxCell prevSrc = (mxCell) sCell.getPrevSrc();
		mxCell nextSrc = (mxCell) sCell.getNextSrc();
		if (!summaryBox.isVisible()) {
			summaryBox.setVisible(true);
			htChange++;
		}

		summaryContent.setStyle(style);

		if (prevSrc != null && prevSrc.isVisible()) {
			if (prevDots.isVisible()) {
				prevDots.setVisible(false);
				htChange--;
			}
		}

		if (nextSrc != null && nextSrc.isVisible() && nextDots.isVisible()) {
			nextDots.setVisible(false);
			htChange--;
		} else if (nextSrc != null && !nextSrc.isVisible() && !nextDots.isVisible()) {
			nextDots.setVisible(true);
			htChange++;
		}

		return htChange;
	}

	/**
	 * remove the color block of the given cell
	 * 
	 * @param cell
	 *            the cell for which the color block should be removed
	 * @param removeColor
	 *            the color of the block should be removed
	 * @param newColor
	 *            the color set for the cell
	 */
	private void removeColorBlock(Object cell, String removeColor, String newColor) {

		for (int i = 1; i < model.getChildCount(cell); i++) {
			mxCell o = (mxCell) model.getChildAt(cell, i);
			if (o.getStyle() != null
					&& (o.getStyle().contains(removeColor) || (newColor != null && o.getStyle().contains(newColor)))) {
				o.removeFromParent();
			}
		}

	}

	/**
	 * fold/expand all the swim cells
	 * 
	 * @param b
	 *            if <code>true</code> fold all the swim celss otherwise expand
	 *            all the swim cells,
	 */
	public void foldAll(boolean b) {
		for (Object swimCell : location.getAllSwimCells()) {
			graph.foldCells(!((mxCell) swimCell).isCollapsed(), false, new Object[] { swimCell }, false);
			graph.foldCells(b, false, new Object[] { swimCell }, false);
		}
	}

	/**
	 * install the folding handler
	 */
	protected void installFoldingHandler() {
		mxIEventListener foldingHandler = new mxIEventListener() {
			@Override
			public void invoke(Object sender, mxEventObject evt) {
				Object[] cells = (Object[]) evt.getProperty("cells");
				for (int i = 0; i < cells.length; i++) {
					mxCell c = (mxCell) cells[i];// swim
					if (!c.getStyle().contains("swim")) {
						return;
					}

					if (graph.isCellCollapsed(cells[i])) {
						// fold
						// swim's parent is rightCell
						mxCell rightCell = (mxCell) c.getParent();
						double tmpHt = 0;
						if (model.getChildCount(rightCell) > 2) {
							mxCell cell = (mxCell) model.getChildAt(rightCell, 1);
							mxCell cell2 = (mxCell) model.getChildAt(rightCell, 2);

							cell.setVisible(true);
							cell2.setVisible(true);
							tmpHt = cell2.getGeometry().getHeight();

						}
						setCellHeight(rightCell, tmpHt);

						mxCell rowCell = (mxCell) rightCell.getParent();
						setCellHeight(rowCell, tmpHt);
						int row = Integer.parseInt(rightCell.getId());
						setCellHeight(location.getRangeCell(row), tmpHt);

					} else {
						if (model.getChildCount(c.getParent()) > 2) {
							mxCell cell = (mxCell) model.getChildAt(c.getParent(), 1);
							mxCell cell2 = (mxCell) model.getChildAt(c.getParent(), 2);

							cell.setVisible(false);
							cell2.setVisible(false);
						}
						mxCell rightCell = (mxCell) c.getParent();
						int row = Integer.parseInt(rightCell.getId());
						double currHt = (lineTable.get(row).getHeight() + 1) * htPerLine + 7 + 10;
						setCellHeight(rightCell, currHt);

						mxCell rowCell = (mxCell) rightCell.getParent();
						setCellHeight(rowCell, currHt);

						setCellHeight(location.getRangeCell(row), currHt);
					}
				}
				graph.refresh();
			}
		};

		graph.addListener(mxEvent.FOLD_CELLS, foldingHandler);

	}

	// set the default layout manager
	protected void setLayoutManager() {
		@SuppressWarnings("unused")
		mxLayoutManager layoutMng = new mxLayoutManager(graph) {
			public mxIGraphLayout getLayout(Object parent) {
				String st = model.getStyle(parent);

				if (model.getChildCount(parent) > 0 && (st == null || st.contains("swim") || st == "summary")) {
					// vertical
					return new mxStackLayout(graph, false);
				} else {
					// horizontal
					return new mxStackLayout(graph, true);
				}
			}

		};
	}

	/*
	 * set the default styles
	 */
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
		graph.getStylesheet().putCellStyle("border", borderStyle);

		Map<String, Object> rangeStyle = new HashMap<String, Object>(style);
		rangeStyle.put(mxConstants.STYLE_SPACING_LEFT, PaneConstants.LEFT_SPACE);
		rangeStyle.put(mxConstants.STYLE_STROKECOLOR, "none");
		graph.getStylesheet().putCellStyle("range", rangeStyle);

		swimStyle = new HashMap<String, Object>(style);
		swimStyle.remove(mxConstants.STYLE_VERTICAL_ALIGN);
		swimStyle.put(mxConstants.STYLE_HORIZONTAL, false);
		swimStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_SWIMLANE);
		swimStyle.put(mxConstants.STYLE_FOLDABLE, true);
		swimStyle.put(mxConstants.STYLE_STROKECOLOR, "none");
		swimStyle.put(mxConstants.STYLE_STARTSIZE, PaneConstants.SIGN_SIZE);
		swimStyle.put(mxConstants.STYLE_SPACING_TOP, PaneConstants.TOP_SPACE);
		swimStyle.put(mxConstants.STYLE_SWIMLANE_LINE, 0);
		graph.getStylesheet().putCellStyle("swim", swimStyle);

		contentStyle = new HashMap<String, Object>(style);
		contentStyle.put(mxConstants.STYLE_STROKECOLOR, "none");
		contentStyle.put(mxConstants.STYLE_OPACITY, 0);
		contentStyle.put(mxConstants.STYLE_FILL_OPACITY, 0);
		contentStyle.put(mxConstants.STYLE_SPACING_TOP, PaneConstants.TOP_SPACE);
		contentStyle.remove(mxConstants.STYLE_VERTICAL_ALIGN);
		contentStyle.put(mxConstants.STYLE_FONTFAMILY, "Courier");
		graph.getStylesheet().putCellStyle("content", contentStyle);
	}

	protected void drawTable() {
		parent = graph.getDefaultParent();

		model.beginUpdate();
		try {
			// show the details
			for (int row = 0; row < numOfRows; row++) {

				if (!lineTable.containsKey(row) || lineTable.get(row).isNoSrc()) {
					// lineTable.remove(row);
					continue;
				}

				TextLineList lineList = lineTable.get(row);
				double currHt = (lineList.getHeight() + 1) * htPerLine + 5 + 10;

				/**
				 * The big box around the first row with black border
				 */
				mxCell rowCell = (mxCell) drawRowCell(row);

				/**
				 * The transition range no border
				 */
				drawRangeCell(row, rowCell);

				/**
				 * The big box outside the swimlane
				 */
				mxCell rightCell = (mxCell) drawRightCell(row, rowCell);

				/**
				 * The swimlane
				 */
				mxCell swimCell = (mxCell) drawSwimCell(row, rightCell, currHt);

				/**
				 * The thread label
				 */
				int from = group.get(row)._1;
				int threadIdx = path.get(from).getThreadIndex();
				drawThreadLabel(row, swimCell, threadIdx);

				/**
				 * draw the detail line one at a time
				 */
				drawDetailContentLines(row, swimCell);

				/**
				 * draw summary line one at a time; hide those lines
				 */
				drawSummaryBlankCell(row, threadIdx, rightCell);
				mxCell summaryBorder = (mxCell) drawSummaryBorder(row, threadIdx, rightCell);

				drawSummaryContent(row, threadIdx, summaryBorder);

				setCellWidth(rightCell, cellWidth * numOfThreads + PaneConstants.SIGN_SIZE);
				setCellWidth(swimCell, cellWidth * numOfThreads + PaneConstants.SIGN_SIZE);
				setCellWidth(rowCell, PaneConstants.SIGN_SIZE + PaneConstants.RANGE_SIZE + numOfThreads * cellWidth);
			}

		} finally {
			model.endUpdate();
		}

		// foldAll(true);
	}

	/**
	 * @return if all the row cells are folded
	 */
	public boolean areAllFolded() {
		for (Object o : location.getAllSwimCells()) {
			if (!((mxCell) o).isCollapsed()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return if all the row cells are expanded
	 */
	public boolean areAllExpanded() {
		for (Object o : location.getAllSwimCells()) {
			if (((mxCell) o).isCollapsed()) {
				return false;
			}
		}
		return true;
	}

	private Object drawRowCell(int row) {
		mxCell rowCell = (mxCell) graph.insertVertex(parent, null, null, 0, 0,
				PaneConstants.SIGN_SIZE + PaneConstants.RANGE_SIZE + numOfThreads * cellWidth, 0, "border");
		rowCell.setConnectable(false);
		rowCell.setId("" + row);
		location.addRowCell(row, rowCell);
		return rowCell;
	}

	private Object drawRangeCell(int row, Object rowCell) {
		int from = group.get(row)._1;
		int to = group.get(row)._2;
		String rangeStr = null;
		if (from != to) {
			rangeStr = from + "-" + to;
		} else {
			rangeStr = "" + from;
		}
		mxCell rangeCell = (mxCell) graph.insertVertex(rowCell, null, rangeStr, 0, 0, PaneConstants.RANGE_SIZE,
				PaneConstants.START_SIZE, "range");
		rangeCell.setConnectable(false);
		rangeCell.setId("" + row);
		location.addRangeCell(row, rangeCell);
		return rangeCell;
	}

	private Object drawRightCell(int row, Object rowCell) {
		mxCell rightCell = (mxCell) graph.insertVertex(rowCell, null, null, 0, 0,
				PaneConstants.SIGN_SIZE + numOfThreads * cellWidth, 0, "right");
		rightCell.setId("" + row);
		rightCell.setConnectable(false);
		location.addRightCell(row, rightCell);
		return rightCell;
	}

	private Object drawSwimCell(int row, Object rightCell, double currHt) {
		Map<String, Object> swimStyleI = new HashMap<>(swimStyle);
		graph.getStylesheet().putCellStyle("swim" + row, swimStyleI);
		mxCell swimCell = (mxCell) graph.insertVertex(rightCell, null, null, 0, 0,
				numOfThreads * cellWidth + PaneConstants.SIGN_SIZE, currHt, "swim" + row);
		swimCell.setConnectable(false);
		swimCell.setId("" + row);
		location.addSwimCell(row, swimCell);
		// graph.foldCells(true, false, new Object[] { swimCell }, true);
		model.getGeometry(swimCell)
				.setAlternateBounds(new mxRectangle(0, 0, PaneConstants.SIGN_SIZE, PaneConstants.SIGN_SIZE));
		return swimCell;
	}

	private void drawThreadLabel(int row, Object swimCell, int threadIdx) {
		Map<String, Object> threadLabel = new HashMap<String, Object>(contentStyle);
		threadLabel.put(mxConstants.STYLE_SPACING_LEFT,
				cellWidth * threadIdx + cellWidth / 2 - PaneConstants.LEFT_SPACE / 2);
		graph.getStylesheet().putCellStyle("thread" + row, threadLabel);

		mxCell threadRow = (mxCell) graph.insertVertex(swimCell, null, "" + threadIdx, 0, 0, numOfThreads * cellWidth,
				htPerLine + 10, "thread" + row);
		threadRow.setId("" + threadIdx);
		threadRow.setConnectable(false);
		location.addThreadLabel(row, threadRow);
	}

	private void drawDetailContentLines(int row, Object swimCell) {
		List<TextLine> txtLines = lineTable.get(row).getList();

		for (int ithLine = 0; ithLine < txtLines.size(); ithLine++) {
			TextLine txt = txtLines.get(ithLine);
			String txtStr = txt.getText();
			if (txt.isSrc()) {
				txtStr = " " + txt.getLocationString() + ": " + txtStr;
			}
			double txtWidth = txtStr.length() * wtPerLine;
			mxCell contentBox = (mxCell) graph.insertVertex(swimCell, "" + ithLine, null, 0, 0, txtWidth, 0, "content");
			contentBox.setConnectable(false);
			contentBox.setId("" + ithLine);

			mxCell content = (mxCell) graph.insertVertex(contentBox, "" + ithLine, txtStr, 0, 0, txtWidth, htPerLine,
					"content");
			content.setConnectable(false);
			content.setId("" + ithLine);
			location.addContentCell(row, ithLine, contentBox);
		}
	}

	private void drawSummaryBlankCell(int row, int threadIdx, Object rightCell) {
		mxCell summaryBlank = (mxCell) graph.insertVertex(rightCell, null, null, 0, 0, threadIdx * cellWidth, 5,
				"content");
		summaryBlank.setConnectable(false);
		summaryBlank.setId("" + threadIdx);
		summaryBlank.setVisible(false);
		location.addSummaryBlank(row, summaryBlank);
	}

	private Object drawSummaryBorder(int row, int threadIdx, Object rightCell) {
		Map<String, Object> summaryStyle = new HashMap<String, Object>(contentStyle);
		graph.getStylesheet().putCellStyle("summary", summaryStyle);

		mxCell summaryBorder = (mxCell) graph.insertVertex(rightCell, null, null, 0, 0,
				(numOfThreads - threadIdx) * cellWidth, 0, "summary");
		summaryBorder.setConnectable(false);
		summaryBorder.setId("" + threadIdx);
		location.addSummaryBorderCell(row, summaryBorder);
		return summaryBorder;
	}

	private void drawSummaryContent(int row, int threadIdx, Object summaryBorder) {
		boolean nonSrcInBetween = false;
		int sumNum = 0;
		SummaryCell prevCell = null;
		String prevTxt = null;

		for (TextLine tl : lineTable.get(row).getList()) {

			if (tl.isSrc()) {
				if (prevTxt != null && nonSrcInBetween && prevTxt.equals(tl.getText())) {
					nonSrcInBetween = false;
					continue;
				}

				double sumWt = tl.getText().length() * wtPerLine;
				mxCell summaryBox = (mxCell) graph.insertVertex(summaryBorder, null, null, 0, 0, sumWt, htPerLine,
						"content");
				summaryBox.setId(tl.getLineNum() + "");
				summaryBox.setConnectable(false);

				mxCell summaryContent = (mxCell) graph.insertVertex(summaryBox, null, tl.getText(), 0, 0, sumWt,
						htPerLine, "content");// "summaryContent" +
												// ithRow);
				summaryContent.setId(tl.getLineNum() + "");
				summaryContent.setConnectable(false);

				sumNum++;
				summaryBox.setVisible(false);

				mxCell summaryDots = (mxCell) graph.insertVertex(summaryBorder, null, "...", 0, 0, 30, htPerLine,
						"content");
				summaryDots.setId(-1 + "");
				summaryDots.setConnectable(false);
				summaryDots.setVisible(false);

				SummaryCell sCell = new SummaryCell(summaryBox, threadIdx);

				sCell.setNextDots(summaryDots);

				if (prevCell != null) {
					sCell.setPreviousSummary(prevCell);
					sCell.setPrevSrc(prevCell.getSummary());
					sCell.setPrevDots(prevCell.getNextDots());
					prevCell.setNextSrc(summaryBox);
				}

				if (tl.isFirst()) {
					summaryBox.setVisible(true);
					sCell.setFirst(true);
				}

				location.addSummaryCell(row, tl.getLineNum(), sCell);
				prevTxt = tl.getText();
				prevCell = sCell;

			} else {
				nonSrcInBetween = true;
			}
		}

		// is last line of src code
		prevCell.setLast(true);
		((mxCell) prevCell.getSummary()).setVisible(true);
		mxCell switchLine = (mxCell) graph.insertVertex(summaryBorder, null, null, 0, 0, 0, 0, "switch");
		switchLine.getGeometry().setAlternateBounds(new mxRectangle(0, 0, 0, 0));
		switchLine.setId(row + "");
		switchLine.setConnectable(false);
		switchLine.setVisible(false);
		location.addSwitchCell(row, switchLine);

		int numOfLines = 0;

		if (sumNum > 2) {
			((mxCell) model.getChildAt(summaryBorder, 1)).setVisible(true);
			numOfLines = 3;
		} else {
			numOfLines = sumNum;
		}

		double sumHt = numOfLines * htPerLine + 10;
		setCellHeight(summaryBorder, sumHt);
		((mxCell) summaryBorder).setVisible(false);

	}

}
