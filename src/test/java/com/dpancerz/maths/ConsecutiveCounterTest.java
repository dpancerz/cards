package com.dpancerz.maths;

import static com.dpancerz.maths.ConsecutiveCounter.containsConsecutive;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConsecutiveCounterTest {

  @Nested
  class Regular_cases {
    @Test
    void discovers_consecutive_when_containing_only_them() {
      assertTrue(containsConsecutive(asList(1, 2, 3, 4), 4));
    }

    @Test
    void rejects_not_enough() {
      assertFalse(containsConsecutive(asList(1, 2, 3, 4), 5));
    }

    @Test
    void discovers_consecutive_when_contains_different_values() {
      assertTrue(containsConsecutive(asList(1, 2, 3, 10, 11, 12, 13, 14), 5));
    }

    @Test
    void discovers_one_consecutive() {
      assertTrue(containsConsecutive(asList(1,3,8), 1));
    }
  }

  @Nested
  class Edge_Cases {
    @Test
    void throws_error_on_zero_or_less_consecutive() {
      assertThrows(IllegalArgumentException.class,
          () -> containsConsecutive(asList(1,3,8), 0));
    }

    @Test
    void returns_false_on_empty_input() {
      assertFalse(containsConsecutive(new ArrayList<>(), 1));
    }

    @Test
    void allows_negative_integers() {
      assertTrue(containsConsecutive(asList(-3, -2 ,-1), 3));
    }

  }
}
