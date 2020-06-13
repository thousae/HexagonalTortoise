package com.hextortoise;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class Util {
	public static final String ROOT_PATH = new File(".").getAbsolutePath();
	public static final String RESOURCES_PATH
			= ROOT_PATH + File.separator + "src" + File.separator + "resources" + File.separator;

	public static final String GAME_NAME = "지수귀문도";

	public static String getResourcesPath(final String name) {
		return RESOURCES_PATH + name;
	}

	public static final int[][] HEXAGONS = {
			{  0,  1,  2,  3,  4,  6 },
			{  3,  5,  6,  8,  9, 11 },
			{  4,  6,  7,  9, 10, 12 },
			{  9, 11, 12, 13, 14, 15 }
	};

	private static boolean isCorrect(final int[] nums, final int sum) {
		for (final int[] hexagon : HEXAGONS) {
			int _sum = 0;
			for (final int index : hexagon) {
				if (nums[index] == 0) {
					_sum = sum;
					break;
				}
				_sum += nums[index];
			}
			if (_sum != sum) {
				return false;
			}
		}
		return true;
	}

	private static boolean isTortoiseValid(
			final int[] nums,
			final boolean[] used,
			final Deque<Integer> order,
			final int sum
	) {
		if (order.isEmpty()) {
			return isCorrect(nums, sum);
		}

		for (int i = 1; i < used.length; i++) {
			if (!used[i]) {
				final int next = order.pop();
				nums[next] = i;
				used[i] = true;
				if (isCorrect(nums, sum) && isTortoiseValid(nums, used, order, sum)) {
					return true;
				}
				used[i] = false;
				nums[next] = 0;
				order.push(next);
			}
		}
		return false;
	}

	public static boolean isTortoiseValid(final int[] tortoise, final int sum) {
		final Deque<Integer> order = new ArrayDeque<>();
		final boolean[] used = new boolean[tortoise.length + 1];
		for (int i = 0; i < tortoise.length; i++) {
			if (tortoise[i] == 0) {
				order.add(i);
			} else {
				used[tortoise[i]] = true;
			}
		}

		return isTortoiseValid(tortoise, used, order, sum);
	}

	public static int[] getRandomIntArray(final int size, final int lowerBound, final int upperBound) {
		final Random rand = new Random();

		final List<Integer> randoms = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			final int randomValue = rand.nextInt(upperBound - lowerBound) + lowerBound;
			if (randoms.contains(randomValue)) {
				i--;
				continue;
			}
			randoms.add(randomValue);
		}

		final int[] result = new int[size];
		for (int i = 0; i < size; i++) {
			result[i] = randoms.get(i);
		}
		return result;
	}

	public static int[] getRandomIntArray(final int size, final int bound) {
		return getRandomIntArray(size, 0, bound);
	}

	public static int[] getRandomTortoise(final int num, final int total, final int sum) {
		final int[] nums = getRandomIntArray(total, 1, total + 1);
		final int[] randoms = getRandomIntArray(num, total);
		final int[] tortoise = new int[total];
		for (final int index : randoms) {
			tortoise[index] = nums[index];
		}

		if (isTortoiseValid(tortoise.clone(), sum)) {
			return tortoise;
		} else {
			return getRandomTortoise(num, total, sum);
		}
	}

	public static Point[] getButtonPositions(final int x, final int y, final int space) {
		final double sqrt3 = Math.sqrt(3.0);
		final Point[] result = new Point[] {
				new Point(0, (int)(-5.0 / 2 * space)),
				new Point((int)(-sqrt3 / 2 * space), -2 * space),
				new Point((int)(sqrt3 / 2 * space), -2 * space),
				new Point((int)(-sqrt3 / 2 * space), -space),
				new Point((int)(sqrt3 / 2 * space), -space),
				new Point((int)(-sqrt3 * space), (int)(-1.0 / 2 * space)),
				new Point(0, (int)(-1.0 / 2 * space)),
				new Point((int)(sqrt3 * space), (int)(-1.0 / 2 * space)),
				new Point((int)(-sqrt3 * space), (int)(1.0 / 2 * space)),
				new Point(0, (int)(1.0 / 2 * space)),
				new Point((int)(sqrt3 * space), (int)(1.0 / 2 * space)),
				new Point((int)(-sqrt3 / 2 * space), space),
				new Point((int)(sqrt3 / 2 * space), space),
				new Point((int)(-sqrt3 / 2 * space), 2 * space),
				new Point((int)(sqrt3 / 2 * space), 2 * space),
				new Point(0, (int)(5.0 / 2 * space))
		};
		for (int i = 0; i < result.length; i++) {
			result[i].x += x;
			result[i].y += y;
		}
		return result;
	}

	public static Point[] getSumPositions(final int x, final int y, final int space) {
		final double sqrt3 = Math.sqrt(3.0);
		return new Point[] {
				new Point(x, y - (int)(3.0 / 2 * space)),
				new Point(x - (int)(sqrt3 / 2 * space), y),
				new Point(x + (int)(sqrt3 / 2 * space), y),
				new Point(x, y + (int)(3.0 / 2 * space))
		};
	}
}
