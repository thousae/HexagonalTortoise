package com.hextortoise.frames;

import com.hextortoise.Util;
import com.hextortoise.buttons.DestinationButton;
import com.hextortoise.buttons.SourceButton;
import com.hextortoise.labels.AnswerLabel;
import com.hextortoise.labels.BoardLabel;
import com.hextortoise.panels.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainFrame extends JFrame {
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 788;
	public static final int BUTTON_NUMS = 16;
	public static final int TORTOISE_NUMS = 4;
	public static final int RANDOM_NUMS = 6;

	public AnswerLabel correctLabel = null;
	public AnswerLabel wrongLabel = null;

	private SourceButton selected = null;

	private final SourceButton[] sources = new SourceButton[BUTTON_NUMS];
	private final DestinationButton[] destinations = new DestinationButton[BUTTON_NUMS];
	private final BoardLabel[] sumLabels = new BoardLabel[TORTOISE_NUMS];

	private final int sum;

	public MainFrame() {
		super(Util.GAME_NAME);

		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		final BackgroundPanel panel = new BackgroundPanel("board.png");
		this.setNumArea(panel);

		this.sum = new Random().nextInt(23) + 40;
		final BoardLabel sumLabel = new BoardLabel(
				this,
				"합: " + Integer.toString(this.sum),
				40.0f,
				400, -200
		);
		sumLabel.setForeground(Color.WHITE);
		panel.add(sumLabel);

		initializeDestinations();

		this.setAnswerLabel(panel);

		this.setContentPane(panel);

		this.setVisible(true);
	}

	private void initializeDestinations() {
		final int[] randomTortoise = Util.getRandomTortoise(RANDOM_NUMS, BUTTON_NUMS, this.sum);

		for (int i = 0; i < BUTTON_NUMS; i++) {
			if (randomTortoise[i] != 0) {
				this.destinations[i].setNumber(randomTortoise[i]);
				this.sources[randomTortoise[i] - 1].setVisible(false);
			}
		}

		this.calcSum();
	}

	private void setNumArea(final JPanel panel) {
		final int buttonRadius = 83;
		final int space = 25;
		final int x = 300;
		final int y = HEIGHT / 2 - 30;
		final Point[] numsPosition = Util.getButtonPositions(
				x, y, buttonRadius + space
		);
		for (int i = 0; i < BUTTON_NUMS; i++) {
			this.destinations[i] = new DestinationButton(this);
			this.destinations[i].setBounds(
					numsPosition[i].x - 41,
					numsPosition[i].y - 41,
					buttonRadius, buttonRadius
			);
		}

		for (final JButton button : this.destinations) {
			panel.add(button);
		}

		final Point LEFT_CORNER = new Point(650, 300);
		final int columns = BUTTON_NUMS / 4;
		final int buttonSpace = 90;
		for (int i = 0; i < BUTTON_NUMS; i++) {
			this.sources[i] = new SourceButton(i + 1, this);
			this.sources[i].setBounds(
					LEFT_CORNER.x + i % columns * buttonSpace,
					LEFT_CORNER.y + i / columns * buttonSpace,
					buttonRadius, buttonRadius
			);
		}

		for (final JButton button : this.sources) {
			panel.add(button);
		}

		final Point[] sumPositions = Util.getSumPositions(
				x, y, buttonRadius + space
		);

		for (int i = 0; i < sumPositions.length; i++) {
			this.sumLabels[i] = new BoardLabel(
					"0", 40.0f,
					sumPositions[i].x, sumPositions[i].y
			);
			this.sumLabels[i].setForeground(Color.WHITE);
			panel.add(this.sumLabels[i]);
		}
	}

	private void setAnswerLabel(final JPanel panel) {
		correctLabel = new AnswerLabel(this, "맞았습니다!");
		correctLabel.setForeground(Color.GREEN);
		correctLabel.setVisible(false);
		panel.add(correctLabel);
		panel.setComponentZOrder(correctLabel, 1);

		wrongLabel = new AnswerLabel(this, "틀렸습니다!");
		wrongLabel.setForeground(Color.RED);
		wrongLabel.setVisible(false);
		panel.add(wrongLabel);
		panel.setComponentZOrder(wrongLabel, 1);
	}

	public SourceButton getSelected() {
		return this.selected;
	}

	public void select(final SourceButton button) {
		this.selected = button;
	}

	public boolean isTortoiseValid() {
		final int[] nums = new int[BUTTON_NUMS];
		for (int i = 0; i < BUTTON_NUMS; i++) {
			nums[i] = this.destinations[i].getNumber();
		}

		return Util.isTortoiseValid(nums, this.sum);
	}

	public void calcSum() {
		for (int i = 0; i < Util.HEXAGONS.length; i++) {
			int sum = 0;
			for (final int index : Util.HEXAGONS[i]) {
				sum += this.destinations[index].getNumber();
			}
			this.sumLabels[i].setText(Integer.toString(sum));
			if (sum < this.sum) {
				this.sumLabels[i].setForeground(Color.RED);
			} else if (sum == this.sum) {
				this.sumLabels[i].setForeground(Color.GREEN);
			} else {
				this.sumLabels[i].setForeground(Color.BLUE);
			}
		}
	}
}
