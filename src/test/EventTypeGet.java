package test;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class EventTypeGet {
  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setLayout(new GridLayout());

    Button button = new Button(shell, SWT.NONE);
    button.setText("Click and check the console");
    button.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event e) {
        System.out.println(getEventName(e.type)); 
        switch (e.type) {
        case SWT.Selection:
          System.out.println("Button pressed");
          break;
        }
      }
    });


    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
  public static String getEventName(int eventType) {
    switch(eventType) {
      case SWT.None:
        return "null";
      case SWT.KeyDown:
        return "key down";
      case SWT.KeyUp:
        return "key up";
      case SWT.MouseDown:
        return "mouse down";
      case SWT.MouseUp:
        return "mouse up";
      case SWT.MouseMove:
        return "mouse move";
      case SWT.MouseEnter:
        return "mouse enter";
      case SWT.MouseExit:
        return "mouse exit";
      case SWT.MouseDoubleClick:
        return "mouse double click";
      case SWT.Paint:
        return "paint";
      case SWT.Move:
        return "move";
      case SWT.Resize:
        return "resize";
      case SWT.Dispose:
        return "dispose";
      case SWT.Selection:
        return "selection";
      case SWT.DefaultSelection:
        return "default selection";
      case SWT.FocusIn:
        return "focus in";
      case SWT.FocusOut:
        return "focus out";
      case SWT.Expand:
        return "expand";
      case SWT.Collapse:
        return "collapse";
      case SWT.Iconify:
        return "iconify";
      case SWT.Deiconify:
        return "deiconify";
      case SWT.Close:
        return "close";
      case SWT.Show:
        return "show";
      case SWT.Hide:
        return "hide";
      case SWT.Modify:
        return "modify";
      case SWT.Verify:
        return "verify";
      case SWT.Activate:
        return "activate";
      case SWT.Deactivate:
        return "deactivate";
      case SWT.Help:
        return "help";
      case SWT.DragDetect:
        return "drag detect";
      case SWT.Arm:
        return "arm";
      case SWT.Traverse:
        return "traverse";
      case SWT.MouseHover:
        return "mouse hover";
      case SWT.HardKeyDown:
        return "hard key down";
      case SWT.HardKeyUp:
        return "hard key up";
      case SWT.MenuDetect:
        return "menu detect";
    }
    
    return "unkown ???";
  }

  
}