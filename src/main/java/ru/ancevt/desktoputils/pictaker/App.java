package ru.ancevt.desktoputils.pictaker;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class App implements ImageAreaSelectListener {
	
	private static final int FILE_NAME_LENGTH = 10;
			
	public static void main(String[] args) throws HeadlessException, AWTException {
		new App();
	}

	private final PictakerFrame pictakerFrame;
	private final PictakerPanel pictakerPanel;
	
	private App() throws HeadlessException, AWTException {
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		pictakerFrame = new PictakerFrame();
		pictakerFrame.setTitle("Ancevt's Pictaker");
		pictakerFrame.setLocation(0, 0);
		pictakerFrame.setSize(screenSize);

		pictakerPanel = new PictakerPanel(takeScreenshot());
		//pictakerPanel.setSize(screenSize);
//		pictakerPanel.setPreferredSize(screenSize);
//		pictakerPanel.setAreaSelectListener(this);
		pictakerPanel.repaint();
		pictakerPanel.invalidate();
	
		pictakerFrame.getContentPane().add(pictakerPanel);
		pictakerFrame.setVisible(true);
		pictakerFrame.repaint();
		
		pictakerPanel.requestFocus();
	}
	
	/**
	 * 
	 * @return
	 * @throws HeadlessException
	 * @throws AWTException
	 */
	private final BufferedImage takeScreenshot() throws HeadlessException, AWTException {
		return new Robot()
			.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	}
	
	private static final String generateRandomImageName(int n) {
        final String AlphaNumericString = 
        	"ABCDEFGHIJKLMNOPQRSTUVWXYZ" + 
            "0123456789" + 
            "abcdefghijklmnopqrstuvxyz"; 
  
        final StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
            int index = (int)(AlphaNumericString.length() * Math.random()); 
            sb.append(AlphaNumericString.charAt(index)); 
        } 
  
        return sb.toString() + ".png"; 
    }

	@Override
	public void selectingAreaComplete() {
		final String fileName = generateRandomImageName(FILE_NAME_LENGTH);
		final BufferedImage croppedImage = pictakerPanel.getCroppedImage();
		
		try {
			ImageIO.write(croppedImage, "png", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(fileName);
		System.exit(0);		
	} 
	
	@Override
	public void selectingAreaCanceled() {
		System.out.println("Canceled");
		System.exit(0);	
	}
}
