package com.dpancerz.poker;

import static com.dpancerz.poker.Hands.ONE_PAIR;
import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

class OnePair extends PokerRank {
  private final com.dpancerz.cards.Rank rank;

  public OnePair(final com.dpancerz.cards.Rank rank) {
    this.rank = requireNonNull(rank);
  }

  static OnePair of(final com.dpancerz.cards.Rank rank) {
    return new OnePair(rank);
  }

  @Override
  public String toString() {
    return "One Pair of " + rank + 's';
  }

  @Override
  public Hands rank() {
    return ONE_PAIR;
  }

  @Override
  public int compareTo(final PokerRank other) {
    if ( !(other instanceof OnePair) ) {
      return super.compareTo(other);
    }
    return rank.compareTo(((OnePair) other).rank);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    return rank == ((OnePair) o).rank;
  }

  @Override
  public int hashCode() {
    return hash(rank);
  }
}
