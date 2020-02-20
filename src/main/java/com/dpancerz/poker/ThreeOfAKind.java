package com.dpancerz.poker;


import static com.dpancerz.poker.Hands.THREE_OF_A_KIND;
import static java.util.Objects.hash;

import com.dpancerz.cards.Rank;

class ThreeOfAKind extends PokerRank {
  private final Rank rank;

  public ThreeOfAKind(final Rank rank) {
    this.rank = rank;
  }

  static ThreeOfAKind of(final Rank rank) {
    return new ThreeOfAKind(rank);
  }

  @Override
  public int compareTo(final PokerRank other) {
    if ( !(other instanceof ThreeOfAKind)) {
      return super.compareTo(other);
    }
    return rank.compareTo(((ThreeOfAKind) other).rank);
  }

  @Override
  Hands rank() {
    return THREE_OF_A_KIND;
  }

  @Override
  public String toString() {
    return "ThreeOfAKind- " + rank;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final ThreeOfAKind that = (ThreeOfAKind) o;
    return rank == that.rank;
  }

  @Override
  public int hashCode() {
    return hash(rank);
  }
}
