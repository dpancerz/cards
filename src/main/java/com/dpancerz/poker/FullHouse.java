package com.dpancerz.poker;

import static com.dpancerz.poker.Hands.FULL_HOUSE;

import com.dpancerz.cards.Rank;
import java.util.Objects;

class FullHouse extends PokerRank {
  private final Rank threeOfAKind;
  private final Rank pair;

  FullHouse(final Rank threeOfAKind, final Rank pair) {
    this.threeOfAKind = threeOfAKind;
    this.pair = pair;
  }

  static FullHouse of(final Rank threeOfAKind, final Rank pair) {
    return new FullHouse(threeOfAKind, pair);
  }

  @Override
  Hands rank() {
    return FULL_HOUSE;
  }

  @Override
  public String toString() {
    return "FullHouse- " + threeOfAKind + "s on " + pair + 's';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final FullHouse fullHouse = (FullHouse) o;
    return threeOfAKind == fullHouse.threeOfAKind &&
        pair == fullHouse.pair;
  }

  @Override public int hashCode() {
    return Objects.hash(threeOfAKind, pair);
  }
}
