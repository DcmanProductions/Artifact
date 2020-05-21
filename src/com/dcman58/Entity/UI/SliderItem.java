package com.dcman58.Entity.UI;

import java.awt.Color;
import java.awt.Graphics2D;

public class SliderItem {

	private float min, max, current;
	private Graphics2D graphics;
	private int segments = 0, posX, posY, width, height;

	public SliderItem(int posX, int posY, int width, int height, float min, float max, float defaultValue, int segments, Graphics2D graphics) {
		this.graphics = graphics;
		this.min = min;
		this.max = max;
		this.current = defaultValue;
		this.segments = segments;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}

	public void draw() {
		// Bar
		graphics.setColor(Color.red);
		graphics.fillRect(posX, posY, width, height);
		// Head
		graphics.setColor(Color.white);
//		graphics.fillRect(posX + getOffset(), posY - 5, 10, height + 10);
		for (int i = 0; i < segments; i++) {
//			graphics.fillRect(posX + (max/), posY - 5, 10, height + 10);

		}
	}

	private int getOffset() {
		if (current == min)
			return 0;
		else if (current == max)
			return width;
		else {

			return 0;
		}

	}

	public float Increment() {
		if (current != max || !((current + (max / segments)) > max))
			current += max / segments;
		if ((current + (max / segments)) > max)
			current = max;
		return getCurrent();
	}

	public float Decrement() {
		if (current != max || !((current + (max / segments)) < min))
			current -= max / segments;
		if ((current - (max / segments)) < min)
			current = min;
		return getCurrent();
	}

	public Graphics2D getGraphics() {
		return graphics;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	public float getCurrent() {
		return current;
	}

}
