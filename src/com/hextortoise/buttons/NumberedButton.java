package com.hextortoise.buttons;

import com.hextortoise.Util;

import javax.swing.*;

public class NumberedButton extends JButton {
	private int number;

	private NumberedButton(final String imageName) {
		super(new ImageIcon(Util.getResourcesPath(imageName)));
		this.setBorder(BorderFactory.createEmptyBorder());
	}

	public NumberedButton(final int i) {
		this("num" + i + ".png");
		this.setNumber(i);
	}

	public NumberedButton() {
		this("num.png");
		this.setNumber(0);
	}

	public void setNumber(final int i) {
		String imageName;
		if (i == 0) {
			imageName = "num.png";
		} else {
			imageName = "num" + i + ".png";
		}
		this.setIcon(new ImageIcon(
				Util.getResourcesPath(imageName)
		));
		this.number = i;
	}

	public int getNumber() {
		return number;
	}
}
