package com.dcman58.Handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.lwjgl.input.Mouse;

public class MouseHandler implements MouseListener {

	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {

	}
	
	public void mouseHovered(int xmin, int ymin, int xmax, int ymax){
		if(Mouse.getX() > xmin && Mouse.getX() < xmax && Mouse.getY() > ymin && Mouse.getY() < ymax){
			
		}
	}


}
