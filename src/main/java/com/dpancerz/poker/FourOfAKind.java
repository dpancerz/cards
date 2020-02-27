package com.dpancerz.poker;

import static com.dpancerz.poker.Hands.FOUR_OF_A_KIND;
import static java.util.Objects.hash;

import com.dpancerz.cards.Rank;
import com.dpancerz.poker.Hand.Matcher;

class FourOfAKind extends PokerRank {
  private final Rank rank;

  public FourOfAKind(final Rank rank) {
    this.rank = rank;
  }

  static FourOfAKind of(final Rank rank) {
    return new FourOfAKind(rank);
  }

  @Override
  public int compareTo(final PokerRank other) {
    if ( !(other instanceof FourOfAKind)) {
      return super.compareTo(other);
    }
    return this.rank.compareTo(((FourOfAKind) other).rank);
  }

  @Override
  Hands rank() {
    return FOUR_OF_A_KIND;
  }

  @Override
  public String toString() {
    return "FourOfAKind " + rank;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final FourOfAKind that = (FourOfAKind) o;
    return rank == that.rank;
  }

  @Override
  public int hashCode() {
    return hash(rank);
  }

  static class Matcher implements Hand.Matcher {
    @Override
    public Hands handRank() {
      return FOUR_OF_A_KIND;
    }

    @Override
    public PokerRank rank(final Hand cards) {
      final Rank rank = cards.findNumberOfAKind(4).stream().findFirst()
          .orElseThrow(() -> new RuntimeException(
              "does not contain Four-of-a-kind even though it should"));

      return new FourOfAKind(rank);
    }

    @Override
    public boolean matches(final Hand hand) {
      return hand.containsFourOfAKind();
    }
  }
}
