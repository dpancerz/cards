package com.dpancerz.maths;

import java.util.List;

public class ConsecutiveCounter {
  public static boolean containsConsecutive(final List<Integer> toCheck, final int howMany) {
    if (howMany < 1) {
      throw new IllegalArgumentException("should check for at least 1 consecutive number");
    }
    if (toCheck.isEmpty() || toCheck.size() < howMany) {
      return false;
    }
    if (howMany == 1) {
      return true;
    }
    int current = 0, previous = Integer.MIN_VALUE, consecutive = 1, iter;
    for (iter = 0; iter < toCheck.size(); iter++) {
      current = toCheck.get(iter);
      if (current == previous + 1) {
        consecutive++;
        if (consecutive == howMany) {
          return true;
        }
      } else {
        consecutive = 1;
      }
      previous = current;
    }
    return false;
  }
}
