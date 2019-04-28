
package ru.ancevt.desktoputils.pictaker;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DoubleBufferedPanel extends JPanel {
	
	private static final long serialVersionUID = -8706440803094008806L;

	private Image bufferImage;
	private Graphics bufferGraphics;
	private int bufferWidth, bufferHeight;
	
	public DoubleBufferedPanel() {
		resetBuffer();
	}
	
	private void resetBuffer() {
		bufferWidth = getSize().width;
		bufferHeight = getSize().height;
		
		if (bufferGraphics != null) {
			bufferGraphics.dispose();
			bufferGraphics = null;
		}
		
		if (bufferImage != null) {
			bufferImage.flush();
			bufferImage = null;
		}
		
		if (bufferWidth != 0 && bufferHeight != 0) {
			
			bufferImage = new BufferedImage(
				bufferWidth, 
				bufferHeight, 
				BufferedImage.TYPE_INT_RGB
			);
			bufferGraphics = bufferImage.getGraphics();
		}
	}
	
	@Override
	public void paint(Graphics g) {

		super.paint(g);
		resetBuffer();
		
		if (bufferGraphics != null) {
			bufferGraphics.clearRect(0, 0, bufferWidth, bufferHeight);
			doubleBufferedPaint(bufferGraphics);
			g.drawImage(bufferImage, 0, 0, this);
		}
		
	}
	
	public void doubleBufferedPaint(Graphics g ) {
		
	}
}