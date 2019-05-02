package controller;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import view.WindowUserCom;

public class Run {
	private static final int AREA_WIDTH = 1920;
	private static final int AREA_HIGHT = 1080;
	
	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException, TransformerException {
		run(new WindowUserCom(), AREA_WIDTH, AREA_HIGHT);
	}

	public static void run(final WindowUserCom frame, final int wigth, final int hight) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.main.setTitle(frame.getClass().getSimpleName());
				frame.main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.main.setSize(wigth, hight);
				frame.main.setVisible(true);
			}
		});
	}
}
