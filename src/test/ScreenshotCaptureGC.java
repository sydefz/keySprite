package test;
/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
//package org.eclipse.swt.snippets;
/*
 * GC example snippet: capture a widget image with a GC
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ScreenshotCaptureGC {
	static boolean startDraw = false;
    private Robot robot;

    private Dimension size;

    private Point startPoint;

    private Point lastPoint;

    private int width = 0;
    private int w = 0;
    private int h = 0;
    private int height = 0;
    
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);

		Button button = new Button(shell, SWT.PUSH);
		button.setText("Capture");
		button.pack();
		button.setLocation(10, 140);
		button.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				GC gc = new GC(display);
				final Image image = new Image(display, 300, 400);
				gc.copyArea(image, 0, 0);
				gc.dispose();

				Shell popup = new Shell(shell);
				popup.setText("Image");
				popup.addListener(SWT.Close, new Listener() {
					public void handleEvent(Event e) {
						image.dispose();
					}
				});

				Canvas canvas = new Canvas(popup, SWT.NONE);
				canvas.setBounds(10, 10, 400 + 10, 400 + 10);
				canvas.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent e) {
						e.gc.drawImage(image, 0, 0);
					}
				});
				canvas.addListener(SWT.MouseDown, new Listener() {
					public void handleEvent(Event e) {
						startDraw = true;
				        e.gc.drawRectangle(10, 10, 200, 200);
				        
						System.out.println("mouse donw");
					}
				});
				canvas.addListener(SWT.MouseUp, new Listener() {
					public void handleEvent(Event e) {
						startDraw = false;
						System.out.println("mouse up");
					}
				});
				canvas.addListener(SWT.MouseMove, new Listener() {
					public void handleEvent(Event e) {
						if (startDraw)
							System.out.println("mouse move");
					}
				});
				popup.pack();
				popup.open();
			}
		});
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
}