package test;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.ProgressBar;


public class BB extends ApplicationWindow {

	/**
	 * Create the application window.
	 */
	public BB() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		{
			Label lblNewLabel = new Label(container, SWT.NONE);
			lblNewLabel.setImage(SWTResourceManager.getImage(BB.class, "/conf/5311590_224403066262_2.jpg"));
			lblNewLabel.setBounds(64, 10, 299, 239);
			lblNewLabel.setText("New Label");
		}
		{
			Canvas canvas = new Canvas(container, SWT.NONE);
			canvas.setBounds(89, 273, 274, 220);
		}
		{
			ControlDecoration controlDecoration = new ControlDecoration(container, SWT.LEFT | SWT.TOP);
			controlDecoration.setDescriptionText("Some description");
		}
		
		CLabel lblNewLabel_1 = new CLabel(container, SWT.NONE);
		lblNewLabel_1.setImage(SWTResourceManager.getImage("/home/lee/Desktop/091D5B74F4293FE0147BCE628623EBA3.png"));
		lblNewLabel_1.setBounds(439, 161, 76, 23);
		lblNewLabel_1.setText("New Label");
		
		Browser browser = new Browser(container, SWT.NONE);
		browser.setUrl("www.google.com");
		browser.setBounds(451, 250, 546, 243);
		
		ProgressBar progressBar = new ProgressBar(container, SWT.NONE);
		progressBar.setSelection(50);
		progressBar.setBounds(136, 494, 150, 14);

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
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			BB window = new BB();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Application");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}
