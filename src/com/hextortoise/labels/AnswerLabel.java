package com.hextortoise.labels;

import javax.swing.*;
import java.awt.*;

class ChangeSizeThread extends Thread {
	private Thread thread = null;
	private final String threadName;
	private final JLabel target;

	private static final int SLEEP_MILLIS = 50;

	public ChangeSizeThread(final JLabel label) {
		this.target = label;
		this.threadName = "ChangeSizeThread" + this.target.getText();
	}

	@Override
	public void run() {
		final Color labelColor = this.target.getForeground();
		final float delta = 0.1f;
		final int max = 255;
		try {
			for (float i = 0; i <= Math.PI / 2; i += delta) {
				Thread.sleep(SLEEP_MILLIS);
				final Color newColor = new Color(
						labelColor.getRed(),
						labelColor.getGreen(),
						labelColor.getBlue(),
						(int)(max * Math.cos(i))
				);
				this.target.setForeground(newColor);
			}
		} catch (InterruptedException exception) {
			System.out.println(exception.getMessage());
		} finally {
			final Color newColor = new Color(
					labelColor.getRed(),
					labelColor.getGreen(),
					labelColor.getBlue(),
					0
			);
			this.target.setForeground(newColor);
		}
	}

	@Override
	public synchronized void start() {
		if (this.thread == null) {
			this.thread = new Thread(this, this.threadName);
			this.thread.start();
		}
	}
}

public class AnswerLabel extends BoardLabel {
	public AnswerLabel(final JFrame frame, final String text) {
		super(frame, text, 60.0f, 0, 0);
	}

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		if (aFlag) {
			final Thread thread = new ChangeSizeThread(this);
			thread.start();
		}
	}
}
