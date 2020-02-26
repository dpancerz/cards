package com.dpancerz.poker;

import static com.dpancerz.poker.Hands.FLUSH;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;

import com.dpancerz.cards.Rank;
import com.dpancerz.cards.Suit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Flush extends PokerRank {
  public static final int POKER_HAND_SIZE = 5;
  private final List<Rank> ranks;
  private final Suit suit;

  public Flush(final Suit suit, final List<Rank> ranks) {
    if (ranks.size() != POKER_HAND_SIZE) {
      throw new IllegalArgumentException(
          "wrong number of cards. Expected 5, got: " + ranks.size());
    }
    final ArrayList<Rank> ranksList = new ArrayList<>(ranks);
    ranksList.sort(comparingInt(Rank::ordinal).reversed());
    this.ranks = ranksList;
    this.suit = suit;
  }

  static Flush of(final Suit suit, final Rank... ranks) {
    return new Flush(suit, asList(ranks));
  }

  Rank highestCard() {
    return ranks.get(0);
  }

  Rank highestCard(int number) {
    if (number > POKER_HAND_SIZE || number < 1) {
      throw new IllegalArgumentException("allowed values: 1 to 5.");
    }
    return ranks.get(number - 1);
  }

  @Override
  Hands rank() {
    return FLUSH;
  }

  @Override
  public int compareTo(final PokerRank other) {
    if ( !(other instanceof Flush) ) {
      return super.compareTo(other);
    }
    final Flush otherFlush = (Flush) other;
    for (int i = 1; i <= POKER_HAND_SIZE; i ++) {
      final int comparison = this.highestCard(i)
          .compareTo(otherFlush.highestCard(i));
      if (comparison != 0) return comparison;
    }
    return 0;
  }

  @Override
  public String toString() {
    return "Flush of " + ranks + " in " + suit;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Flush flush = (Flush) o;
    return Objects.equals(ranks, flush.ranks) &&
        suit == flush.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ranks, suit);
  }

  Suit suit() {
    return suit;
  }
}
