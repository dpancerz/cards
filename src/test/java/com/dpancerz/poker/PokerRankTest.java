package com.dpancerz.poker;

import static com.dpancerz.cards.Rank.EIGHT;
import static com.dpancerz.cards.Rank.FIVE;
import static com.dpancerz.cards.Rank.FOUR;
import static com.dpancerz.cards.Rank.JACK;
import static com.dpancerz.cards.Rank.KING;
import static com.dpancerz.cards.Rank.NINE;
import static com.dpancerz.cards.Rank.QUEEN;
import static com.dpancerz.cards.Rank.SEVEN;
import static com.dpancerz.cards.Rank.TWO;
import static com.dpancerz.cards.Suit.CLUBS;
import static com.dpancerz.cards.Suit.DIAMONDS;
import static com.dpancerz.cards.Suit.HEARTS;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PokerRankTest {

  @Nested
  class Pairs {
    @Test
    void pair_of_jacks_is_lower_than_pair_of_queens() {
      assertThat(OnePair.of(JACK)).isLessThan(OnePair.of(QUEEN));
    }
  }

  @Nested
  class TwoPairs {
    @Test
    void two_pairs_of_jacks_and_sevens_is_lower_than_pair_of_queens_and_eights() {
      assertThat(TwoPair.of(JACK, SEVEN)).isLessThan(TwoPair.of(QUEEN, EIGHT));
    }
    @Test
    void two_pairs_of_jacks_and_eights_is_lower_than_pair_of_queens_and_sevens() {
      assertThat(TwoPair.of(JACK, EIGHT)).isLessThan(TwoPair.of(QUEEN, SEVEN));
    }
    @Test
    void two_pairs_of_jacks_and_sevens_is_lower_than_pair_of_jacks_and_eights() {
      assertThat(TwoPair.of(JACK, SEVEN)).isLessThan(TwoPair.of(JACK, EIGHT));
    }
    @Test
    void two_pairs_of_same_strength_are_equal() {
      assertThat(TwoPair.of(JACK, SEVEN)).isEqualTo(TwoPair.of(JACK, SEVEN));
    }
  }

  @Nested
  class ThreesOfAKind {
    @Test
    void three_of_fives_is_lower_than_three_of_kings() {
      assertThat(ThreeOfAKind.of(FIVE)).isLessThan(ThreeOfAKind.of(KING));
    }
  }

  @Nested
  class FoursOfAKind {
    @Test
    void four_of_queens_is_lower_than_four_of_kings() {
      assertThat(FourOfAKind.of(QUEEN)).isLessThan(FourOfAKind.of(KING));
    }
  }

  @Nested
  class FullHouses {
    @Test
    void full_house_of_lower_three_cards_but_higher_two_is_less() {
      assertThat(FullHouse.of(QUEEN, FIVE)).isLessThan(FullHouse.of(KING, TWO));
    }
    @Test
    void full_house_of_same_threes_but_lower_two_is_less() {
      assertThat(FullHouse.of(QUEEN, FIVE)).isLessThan(FullHouse.of(QUEEN, JACK));
    }
    @Test
    void full_house_of_same_threes_and_same_two_is_same() {
      assertThat(FullHouse.of(QUEEN, JACK)).isEqualTo(FullHouse.of(QUEEN, JACK));
    }
  }

  @Nested
  class Flushes {

    @Test
    void orders_cards_in_descending_order() {
      final Flush flush = Flush.of(DIAMONDS, SEVEN, FIVE, QUEEN, JACK, FOUR);

      assertThat(flush.highestCard()).isEqualTo(QUEEN);
    }

    @Test
    void flush_with_higher_card_wins() {
      assertThat(Flush.of(CLUBS, QUEEN, JACK, SEVEN, FIVE, FOUR))
          .isLessThan(Flush.of(CLUBS, KING, NINE, SEVEN, FIVE, FOUR));
    }
    @Test
    void flush_with_higher_lower_card_wins_if_highest_card_is_same() {
      assertThat(Flush.of(HEARTS, QUEEN, JACK, SEVEN, FIVE, FOUR))
          .isGreaterThan(Flush.of(HEARTS, QUEEN, NINE, SEVEN, FIVE, FOUR));
    }
  }
}
