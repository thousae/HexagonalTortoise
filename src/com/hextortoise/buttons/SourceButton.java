package com.hextortoise.buttons;

import com.hextortoise.frames.MainFrame;

public class SourceButton extends NumberedButton {
	public SourceButton(int i, MainFrame main) {
		super(i);
		final SourceButton that = this;
		this.addActionListener(e -> main.select(that));
	}
}
