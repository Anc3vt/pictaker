package ru.ancevt.desktoputils.pictaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PictakerPanel extends DoubleBufferedPanel {
	private static final long serialVersionUID = -723672982230416333L;

	private BufferedImage screenshot;
	private Rectangle cropRectangle;
	private BufferedImage croppedImage;
	private int startX, startY, x, y;
	private ImageAreaSelectListener imageAreaSelectListener;
	
	public PictakerPanel(BufferedImage screenshot) {
		this.screenshot = screenshot;
		
		cropRectangle = new Rectangle();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				startX = e.getX();
				startY = e.getY();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				croppedImage = screenshot.getSubimage(
					cropRectangle.x, 
					cropRectangle.y, 
					cropRectangle.width, 
					cropRectangle.height
				);
				
				imageAreaSelectListener.selectingAreaComplete();
				setVisible(false);
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				repaint();
				super.mouseDragged(e);
			}
		});
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				imageAreaSelectListener.selectingAreaCanceled();
			}
		});
	}
	
	@Override
	public void doubleBufferedPaint(Graphics g) {
		super.doubleBufferedPaint(g);

		final Graphics2D g2d = (Graphics2D)g;

		g2d.drawImage(screenshot, 0, 0, this);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.RED);
		
		final int dx = startX <= x ? startX : x;
		final int dy = startY <= y ? startY : y;
		final int dw = Math.abs(x - startX);
		final int dh = Math.abs(y - startY);
		
		g2d.drawRect(dx, dy, dw, dh);

		cropRectangle.setBounds(dx, dy, dw, dh);
	}
	
	public ImageAreaSelectListener getAreaSelectListener() {
		return imageAreaSelectListener;
	}

	public void setAreaSelectListener(ImageAreaSelectListener listener) {
		this.imageAreaSelectListener = listener;
	}

	public BufferedImage getCroppedImage() {
		return croppedImage;
	}
}
