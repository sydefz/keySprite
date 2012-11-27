import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tracker;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.DragDetectEvent;

public class MainFrame extends ApplicationWindow {
	private Table table;
	protected Control shell;
	final static Display display = new Display();
	private int defaultImgWidth = 300;
	private int defaultImgHeight = 200;
	private static ArrayList<Integer> hpPoints = new ArrayList<Integer>();
	private int screen_x = 200;
	private int screen_y = 200;
	private Image image = new Image(display, defaultImgWidth, defaultImgHeight);
	private Label labelMonitorPoints;
	private Spinner spinner_HPMin;
	private Spinner spinner_HPMax;
	private Spinner spinner_HP;
	private final FormToolkit formToolkit = new FormToolkit(
			display.getDefault());
	private Text text;
	private Text text_1;

	/**
	 * Create the application window.
	 */
	public MainFrame() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setBounds(10, 28, 858, 590);

		TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("Friend");

		final Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem_1.setControl(composite);

		Group grpData = new Group(composite, SWT.NONE);
		grpData.setText("Moniter");
		grpData.setBounds(340, 10, 231, 208);

		table = new Table(grpData, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setToolTipText("");
		table.setBounds(10, 37, 200, 161);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(58);
		tblclmnNewColumn.setText("HP%");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(66);
		tblclmnNewColumn_1.setText("Color");

		Button btnGenerate = new Button(composite, SWT.NONE);
		btnGenerate.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				table.removeAll();
				int r_hpMin = spinner_HPMin.getSelection();
				int r_hpMax = defaultImgWidth + spinner_HPMax.getSelection();
				int r_hpHeight = defaultImgHeight / 2
						- spinner_HP.getSelection();

				for (int i : hpPoints) {
					float per = (float) i / (float) 100;
					int r_x = Math.round(per * (r_hpMax - r_hpMin) + r_hpMin), r_y = r_hpHeight;
					int a_x = r_x + screen_x, a_y = r_y + screen_y;

					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(0, Integer.toString(i));
					int rbg = image.getImageData().getPixel(r_x, r_hpHeight);
					tableItem.setText(1, "#" + Integer.toHexString(rbg));

				}
			}
		});
		btnGenerate.setBounds(114, 470, 91, 29);
		formToolkit.adapt(btnGenerate, true, true);
		btnGenerate.setText("Generate");

		Group grpStep = new Group(composite, SWT.NONE);
		grpStep.setText("step 1");
		grpStep.setBounds(10, 10, 327, 63);
		formToolkit.adapt(grpStep);
		formToolkit.paintBordersFor(grpStep);

		final Spinner spinner_x = new Spinner(grpStep, SWT.BORDER);
		spinner_x.setBounds(10, 28, 67, 22);
		spinner_x.setMaximum(1024);
		spinner_x.setSelection(screen_x);
		formToolkit.adapt(spinner_x);
		formToolkit.paintBordersFor(spinner_x);

		final Spinner spinner_y = new Spinner(grpStep, SWT.BORDER);
		spinner_y.setBounds(83, 28, 67, 22);
		spinner_y.setMaximum(1024);
		spinner_y.setSelection(screen_y);
		formToolkit.adapt(spinner_y);
		formToolkit.paintBordersFor(spinner_y);

		final Button btnCaptureThePortrait = new Button(grpStep, SWT.NONE);
		btnCaptureThePortrait.setBounds(157, 28, 146, 28);
		btnCaptureThePortrait.setText("Capture the Portrait");

		Group grpStep_1 = new Group(composite, SWT.NONE);
		grpStep_1.setText("step 2");
		grpStep_1.setBounds(10, 79, 327, 301);
		formToolkit.adapt(grpStep_1);
		formToolkit.paintBordersFor(grpStep_1);

		int imgX = 10, imgY = 25;

		final Label labelHP = new Label(grpStep_1, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		labelHP.setLocation(imgX, imgY + defaultImgHeight / 2);
		labelHP.setSize(defaultImgWidth, 2);

		labelHP.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		labelHP.setText("HP");
		final Label lblMin = new Label(grpStep_1, SWT.SEPARATOR | SWT.VERTICAL);
		lblMin.setLocation(10, 190);
		lblMin.setSize(2, 50);

		lblMin.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblMin.setText("Min");
		final Label lblMax = new Label(grpStep_1, SWT.SEPARATOR);
		lblMax.setLocation(imgX + defaultImgWidth, 190);
		lblMax.setSize(2, 50);

		lblMax.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblMax.setText("Max");

		final Label LabelImg = formToolkit.createLabel(grpStep_1,
				"Press the button above", SWT.NONE);
		LabelImg.setLocation(imgX, imgY);
		LabelImg.setSize(300, 200);

		spinner_HP = new Spinner(grpStep_1, SWT.BORDER);
		spinner_HP.setLocation(120, 262);
		spinner_HP.setSize(51, 22);
		spinner_HP.setMinimum(-defaultImgHeight / 2);
		spinner_HP.setMaximum(defaultImgHeight / 2);
		spinner_HP.addSelectionListener(new SelectionAdapter() {
			Rectangle oriPos = labelHP.getBounds();

			@Override
			public void widgetSelected(SelectionEvent e) {
				int selection = spinner_HP.getSelection();
				labelHP.setBounds(oriPos.x, oriPos.y - selection, oriPos.width,
						oriPos.height);
			}
		});
		spinner_HP.setMaximum(defaultImgHeight / 2);
		spinner_HP.setMinimum(-defaultImgHeight / 2);
		formToolkit.adapt(spinner_HP);
		formToolkit.paintBordersFor(spinner_HP);

		spinner_HPMax = new Spinner(grpStep_1, SWT.BORDER);
		spinner_HPMax.setLocation(259, 262);
		spinner_HPMax.setSize(51, 22);
		spinner_HPMax.addSelectionListener(new SelectionAdapter() {
			Rectangle oriPos = lblMax.getBounds();

			@Override
			public void widgetSelected(SelectionEvent e) {
				int selection = spinner_HPMax.getSelection();
				lblMax.setBounds(oriPos.x + selection, oriPos.y, oriPos.width,
						oriPos.height);
			}
		});
		spinner_HPMax.setMinimum(-1 * defaultImgWidth / 2);
		spinner_HPMax.setMaximum(0);
		formToolkit.adapt(spinner_HPMax);
		formToolkit.paintBordersFor(spinner_HPMax);

		spinner_HPMin = new Spinner(grpStep_1, SWT.BORDER);
		spinner_HPMin.setLocation(10, 262);
		spinner_HPMin.setSize(51, 22);
		spinner_HPMin.addSelectionListener(new SelectionAdapter() {
			Rectangle oriPos = lblMin.getBounds();

			@Override
			public void widgetSelected(SelectionEvent e) {
				int selection = spinner_HPMin.getSelection();
				lblMin.setBounds(oriPos.x + selection, oriPos.y, oriPos.width,
						oriPos.height);
			}
		});
		spinner_HPMin.setMinimum(0);
		spinner_HPMin.setMaximum(defaultImgWidth / 2);
		formToolkit.adapt(spinner_HPMin);
		formToolkit.paintBordersFor(spinner_HPMin);

		Group grpStep_2 = new Group(composite, SWT.NONE);
		grpStep_2.setText("step 3");
		grpStep_2.setBounds(10, 397, 327, 49);
		formToolkit.adapt(grpStep_2);
		formToolkit.paintBordersFor(grpStep_2);

		labelMonitorPoints = new Label(grpStep_2, SWT.NONE);
		labelMonitorPoints.setBounds(0, 20, 195, 17);
		formToolkit.adapt(labelMonitorPoints, true, true);
		setMonitorPointLabel();

		final Spinner spinnerMonitor = new Spinner(grpStep_2, SWT.BORDER);
		spinnerMonitor.setBounds(201, 20, 51, 22);
		spinnerMonitor.setIncrement(5);
		spinnerMonitor.setSelection(50);
		formToolkit.adapt(spinnerMonitor);
		formToolkit.paintBordersFor(spinnerMonitor);

		Button btnAddNew = new Button(grpStep_2, SWT.NONE);
		btnAddNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!hpPoints.contains(spinnerMonitor.getSelection())) {
					hpPoints.add(spinnerMonitor.getSelection());
					sortMonitorPoints();
					setMonitorPointLabel();
				}
			}
		});
		btnAddNew.setBounds(258, 20, 57, 22);
		btnAddNew.setText("Add");
		
		TabFolder tabFolder_1 = new TabFolder(composite, SWT.NONE);
		tabFolder_1.setBounds(350, 224, 448, 301);
		formToolkit.adapt(tabFolder_1);
		formToolkit.paintBordersFor(tabFolder_1);
		
		TabItem tabItem_3 = new TabItem(tabFolder_1, SWT.NONE);
		tabItem_3.setText("New Item");
		
		Composite composite_1 = new Composite(tabFolder_1, SWT.NONE);
		tabItem_3.setControl(composite_1);
		formToolkit.paintBordersFor(composite_1);
		
		text_1 = new Text(composite_1, SWT.BORDER);
		text_1.setText("123123");
		text_1.setBounds(10, 10, 424, 237);
		formToolkit.adapt(text_1, true, true);
		
		TabItem tabItem_2 = new TabItem(tabFolder_1, SWT.NONE);
		tabItem_2.setText("New Item");
		
		text = new Text(tabFolder_1, SWT.BORDER);
		tabItem_2.setControl(text);
		
		TabItem tabItem = new TabItem(tabFolder_1, SWT.NONE);
		tabItem.setText("80%");

		btnCaptureThePortrait.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				screen_x = spinner_x.getSelection();
				screen_y = spinner_y.getSelection();
				GC gc = new GC(display);
				image = new Image(display, defaultImgWidth, defaultImgHeight);
				gc.copyArea(image, screen_x, screen_y);
				LabelImg.setImage(image);
			}
		});

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * 
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		hpPoints.add(20);
		hpPoints.add(40);
		hpPoints.add(60);
		hpPoints.add(80);
		try {
			MainFrame window = new MainFrame();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMonitorPointLabel() {
		labelMonitorPoints.setText(hpPoints.toString());
	}

	public void sortMonitorPoints() {
		Collections.sort(hpPoints, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return a - b;
			}
		});
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		this.shell = newShell;
		newShell.setText("Key Sprite");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(844, 682);
	}

	public static String getPixelColor(int x, int y) {
		Robot robot;
		try {
			robot = new Robot();
			Color color = robot.getPixelColor(x, y);
			return toHex(color);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String toHex(Color color) {
		return String.format("%02X", color.getRed())
				+ String.format("%02X", color.getGreen())
				+ String.format("%02X", color.getBlue());
	}
}
