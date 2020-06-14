package com.hextortoise.labels;

import com.hextortoise.Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class BoardLabel extends JLabel {
	protected static Font aquaFont;

	private final int x, y;

	static {
		try {
			aquaFont = Font.createFont(
					Font.TRUETYPE_FONT,
					new File(Util.getResourcesPath("a아쿠아리움.ttf"))
			);
		} catch (Exception exception) {
			aquaFont = new JLabel().getFont();
		}
	}

	public BoardLabel(final JFrame frame, final String text, final float fontSize, final int x, final int y) {
		super(text);

		this.x = x;
		this.y = y;

		this.setFont(aquaFont.deriveFont(fontSize));

		final Dimension preferredSize = this.getPreferredSize();
		final int space = 20;
		final Dimension size = new Dimension(
				preferredSize.width + space,
				preferredSize.height + space
		);
		this.setBounds(
				frame.getWidth() / 2 - size.width / 2 + x,
				frame.getHeight() / 2 - size.height / 2 + y,
				size.width, size.height
		);
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
	}

	public BoardLabel(final String text, final float fontSize, final int x, final int y) {
		super(text);

		this.x = x;
		this.y = y;

		this.setFont(aquaFont.deriveFont(fontSize));

		final Dimension preferredSize = this.getPreferredSize();
		final int space = 20;
		final Dimension size = new Dimension(
				preferredSize.width + space,
				preferredSize.height + space
		);
		this.setBounds(x - size.width / 2, y - size.height / 2, size.width, size.height);
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
	}

	@Override
	public void setText(final String text) {
		super.setText(text);
		final Dimension preferredSize = this.getPreferredSize();
		final int space = 20;
		final Dimension size = new Dimension(
				preferredSize.width + space,
				preferredSize.height + space
		);
		this.setBounds(x - size.width / 2, y - size.height / 2, size.width, size.height);
	}
}
