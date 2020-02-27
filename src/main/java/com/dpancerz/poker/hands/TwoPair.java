package com.dpancerz.poker.hands;

import static com.dpancerz.cards.Rank.inDescendingOrder;
import static com.dpancerz.poker.hands.Hands.TWO_PAIR;
import static java.lang.String.format;
import static java.util.Objects.hash;
import static java.util.stream.Collectors.toList;

import com.dpancerz.cards.Rank;
import java.util.List;

class TwoPair extends PokerRank {
  private final Rank greater;
  private final Rank lower;

  TwoPair(final Rank greater, final Rank lower) {
    if (greater.isLowerOrEqualTo(lower)) {
      throw new RuntimeException(format(
          "'%s' should be after '%s'- Pairs should be ordered in descending order",
          greater, lower));
    }
    this.greater = greater;
    this.lower = lower;
  }

  static TwoPair of(final Rank greater, final Rank lower) {
    return new TwoPair(greater, lower);
  }

  @Override
  public int compareTo(final PokerRank other) {
    if ( !(other instanceof TwoPair)) {
      return super.compareTo(other);
    }
    final TwoPair otherTwoPairs = (TwoPair) other;
    if (this.greater.isGreaterThan(otherTwoPairs.greater)) {
      return 1;
    } else if (this.greater.isLowerThan(otherTwoPairs.greater)) {
      return -1;
    }
    if (this.lower.isGreaterThan(otherTwoPairs.lower)) {
      return 1;
    } else if (this.lower.isLowerThan(otherTwoPairs.lower)) {
      return -1;
    }
    return 0;
  }

  @Override
  Hands rank() {
    return TWO_PAIR;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final TwoPair twoPair = (TwoPair) o;
    return greater == twoPair.greater &&
        lower == twoPair.lower;
  }

  @Override
  public int hashCode() {
    return hash(greater, lower);
  }

  @Override
  public String toString() {
    return "TwoPair: " + greater + ", " + lower;
  }

  public static class Matcher implements com.dpancerz.poker.hands.Matcher {
    @Override
    public Hands handRank() {
      return TWO_PAIR;
    }

    @Override
    public PokerRank rank(final Cards cards) {
      final List<Rank> rank = cards.findPairs()
          .stream()
          .sorted(inDescendingOrder())
          .collect(toList());

      return new TwoPair(rank.get(0), rank.get(1));
    }

    @Override
    public boolean matches(final Cards hand) {
      return hand.containsTwoPair();
    }
  }
}
