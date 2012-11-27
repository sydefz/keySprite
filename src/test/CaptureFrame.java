package test;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;
/**
* 简单的截图程序
* @author Edgar108
* @date 2009-11-1 22:34
*/
public class CaptureFrame extends JFrame {
private int mPressedX, mPressedY;   //记录按下鼠标时的X，Y坐标
Rectangle r = new Rectangle(0, 0, 0, 0); //截图的位置
BufferedImage image;     //全屏的图像
JMenuItem saveas = new JMenuItem("另存为");
JMenuItem quit = new JMenuItem("退出");
JPopupMenu menu = new JPopupMenu();

//构造方法
public CaptureFrame() throws Exception {
   super();
   // TODO Auto-generated constructor stub
   Dimension d = Toolkit.getDefaultToolkit().getScreenSize();   //获得屏幕大小
   Robot robot = new Robot();
   image = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height)); //全屏截图
   setBak(image);   // 调用背景方法
   Container c = getContentPane(); // 获取JFrame面板
   JPanel jp = new JPanel(); // 创建个JPanel
   jp.setOpaque(false); // 把JPanel设置为透明 这样就不会遮住后面的背景 这样你就能在JPanel随意加组件了
   c.add(jp);
   this.setSize(d.width, d.height);
   MouseMonitor mm = new MouseMonitor();
   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   this.addMouseListener(mm);
   this.addMouseMotionListener(mm);
   this.setUndecorated(true);   //通过这个方法设置JFrame没有标题栏
   this.setVisible(true);
   this.popup();
   // Robot robot = null;
}
public void popup() {
   menu.add(saveas);
   menu.add(quit);
   MenuMonitor mm = new MenuMonitor();
   saveas.addActionListener(mm);
   quit.addActionListener(mm);
   // add(menu);
}
//菜单监听器
private class MenuMonitor implements ActionListener {
   @Override
   public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    Object obj = e.getSource();
    if (obj == saveas) {
     System.out.println("另存为");
     JFileChooser chooser = new JFileChooser();
     FileFilter filter = new ImageFileFilter();
     chooser.setFileFilter(filter);
     int returnVal = chooser.showSaveDialog(null);
     if (returnVal == JFileChooser.APPROVE_OPTION) {
      File f = chooser.getSelectedFile();
      String destPath = f.getAbsolutePath();
      System.out.println("You chose to open this file: "
        + destPath);
      cut(image, r.width, r.height, r.x, r.y, image.getWidth(),
        image.getHeight(), destPath);
     }
    } else if (obj == quit) {
     System.exit(0);
    }
   }
}
//绘图的方法
@Override
public void paint(Graphics g) {
   super.paint(g);
   // System.out.println("ppppppp");
   g.setColor(Color.RED);
   g.drawRect(r.x, r.y, r.width, r.height);
   //
}
//把全屏的图像设置为JFrame的背景
public void setBak(BufferedImage image) {
   ((JPanel) this.getContentPane()).setOpaque(false);
   ImageIcon img = new ImageIcon(image);
   // ImageIcon img = new ImageIcon("F:\\image\\hlbt\\1.jpg");
   JLabel background = new JLabel(img);
   this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
   background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
}
//鼠标监听器
private class MouseMonitor extends MouseAdapter {
   @Override
   public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub
    mPressedX = e.getX();
    mPressedY = e.getY();
    // System.out.println(e.getX()+" "+e.getY());
   }
   @Override
   public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub
    // System.out.println(e.getX()+" "+e.getY());
    r = new Rectangle(mPressedX, mPressedY, e.getX() - mPressedX, e
      .getY()
      - mPressedY);
    // System.out.println(mPressedX+" "+);
    repaint();
   }
   public void mouseReleased(MouseEvent e) {
    if (e.isPopupTrigger()) // 是否右键弹出
    {
     System.out.println(e.getComponent().getClass().getName());
     menu.show(e.getComponent(), e.getX(), e.getY());
    }
   }
}
/**
* 图片切割 ,这个方法是从网上找的，我自己修改了一下
* 
* @param bi
*            BufferedImage 图片对象
* @param w
*            切割宽度
* @param h
*            切割高度
* @param x1
*            开始x结点（left）
* @param y1
*            开始y结点（top）
* @param sw
*            图片宽度
* @param sh
*            图片高度
* @param destPath
*/
public void cut(BufferedImage bi, int w, int h, int x1, int y1, int sw,
    int sh, String destPath) {
   try {
    Image img;
    ImageFilter cropFilter;
    if (sw >= w && sh >= h) {
     Image image = bi.getScaledInstance(sw, sh, Image.SCALE_DEFAULT);
     // 剪切起始坐标点
     int x = x1;
     int y = y1;
     int destWidth = w; // 切片宽度
     int destHeight = h; // 切片高度
     // 图片比例
     double pw = sw;
     double ph = sh;
     double m = (double) sw / pw;
     double n = (double) sh / ph;
     int wth = (int) (destWidth * m);
     int hth = (int) (destHeight * n);
     int xx = (int) (x * m);
     int yy = (int) (y * n);
     // 四个参数分别为图像起点坐标和宽高
     // 即: CropImageFilter(int x,int y,int width,int height)
     cropFilter = new CropImageFilter(xx, yy, wth, hth);
     img = Toolkit.getDefaultToolkit().createImage(
       new FilteredImageSource(image.getSource(), cropFilter));
     BufferedImage tag = new BufferedImage(w, h,
       BufferedImage.TYPE_INT_RGB);
     Graphics g = tag.getGraphics();
     g.drawImage(img, 0, 0, null); // 绘制缩小后的图
     g.dispose();
     OutputStream out = new FileOutputStream(new File(destPath));
     // 输出为文件
     ImageIO.write(tag, "JPEG", out);
     out.flush();
     out.close();
    }
   } catch (Exception e) {
    e.printStackTrace();
   }
}
private class ImageFileFilter extends FileFilter {
   @Override
   public boolean accept(File f) {
    // TODO Auto-generated method stub
    String name = f.getName();
    System.out.println("name: " + name);
    if (name.lastIndexOf(".") > 1) {
     String type = name.substring(name.lastIndexOf("."), name
       .length());
     if (type.equals("jpg") || type.equals("png")) {
      return true;
     }
    }
    return false;
   }
   @Override
   public String getDescription() {
    // TODO Auto-generated method stub
    return null;
   }
}
public static void main(String[] args) throws Exception {
   // TODO Auto-generated method stub
   new CaptureFrame();
}
}