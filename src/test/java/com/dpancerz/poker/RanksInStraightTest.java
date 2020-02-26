package com.dpancerz.poker;

import static com.dpancerz.poker.RanksInStraight.ACE;
import static com.dpancerz.poker.RanksInStraight.KING;
import static com.dpancerz.poker.RanksInStraight.TWO;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RanksInStraightTest {
  private static final int NUMBER_OF_RANKS = 13;


  @Nested
  class In_Regular_Straight {
    @Test
    void ace_is_higher_than_king_by_one() {
      assertThat(KING.higherBy(1))
          .isEqualTo(Optional.of(ACE));
    }

    @Test
    void ace_is_higher_than_two() {
      assertThat(TWO.higherBy(NUMBER_OF_RANKS - 1))
          .isEqualTo(Optional.of(ACE));
    }
  }


  @Nested
  class In_Straight_With_Ace_Acting_As_One {
    @Test
    void two_is_higher_than_ace_by_one() {
      assertThat(ACE.higherBy(1))
          .isEqualTo(Optional.of(TWO));
    }

    @Test
    void two_is_not_higher_than_king_even_though_ace_duality_would_suggest_otherwise() {
      assertThat(KING.higherBy(2))
          .isNotEqualTo(Optional.of(TWO))
          .isEmpty();
    }
  }
}
