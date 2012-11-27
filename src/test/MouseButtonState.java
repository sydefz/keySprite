package test;
/*
 * Control example snippet: print mouse state and button (down, move, up)
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class MouseButtonState {

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				String string = "Unknown";
				switch (e.type) {
				case SWT.MouseDown:
					string = "DOWN";
					break;
				case SWT.MouseMove:
					string = "MOVE";
					break;
				case SWT.MouseUp:
					string = "UP";
					break;
				}
				string += ": button: " + e.button + ", ";
				string += "stateMask=0x" + Integer.toHexString(e.stateMask);
				if ((e.stateMask & SWT.BUTTON1) != 0)
					string += " BUTTON1";
				if ((e.stateMask & SWT.BUTTON2) != 0)
					string += " BUTTON2";
				if ((e.stateMask & SWT.BUTTON3) != 0)
					string += " BUTTON3";
				if ((e.stateMask & SWT.BUTTON4) != 0)
					string += " BUTTON4";
				if ((e.stateMask & SWT.BUTTON5) != 0)
					string += " BUTTON5";
				System.out.println(string);
			}
		};
		shell.addListener(SWT.MouseDown, listener);
		shell.addListener(SWT.MouseMove, listener);
		shell.addListener(SWT.MouseUp, listener);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}