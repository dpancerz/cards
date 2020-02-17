package com.dpancerz.poker;

import static com.dpancerz.poker.Hands.HIGH_CARD;
import static com.dpancerz.poker.Hands.ONE_PAIR;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HandsTest {
  @Test
  void should_high_card_be_lower_than_one_pair() {
    assertThat(HIGH_CARD).isLessThan(ONE_PAIR);
  }
}
