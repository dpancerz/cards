package com.dpancerz.poker;

import static com.dpancerz.poker.Hands.STRAIGHT;

import com.dpancerz.cards.Rank;
import java.util.Objects;

class Straight extends PokerRank {
  private static final int DIFF_BETWEEN_LOWEST_AND_HIGHEST = 4;
  private final Rank lowestCard;
  private final Rank highestCard;

  private Straight(final Rank lowestCard, final Rank highestCard) {
    this.lowestCard = lowestCard;
    this.highestCard = highestCard;
  }

  static Straight startingFrom(final Rank lowestCard) {
    final Rank highestCard = RanksInStraight.of(lowestCard)
        .higherBy(DIFF_BETWEEN_LOWEST_AND_HIGHEST)
        .orElseThrow(RuntimeException::new)
        .rank();
    return new Straight(lowestCard, highestCard);
  }

  @Override
  Hands rank() {
    return STRAIGHT;
  }

  Rank from() {
    return lowestCard;
  }

  Rank to() {
    return highestCard;
  }

  @Override
  public int compareTo(final PokerRank other) {
    if (!(other instanceof Straight)) {
      return super.compareTo(other);
    }
    final Straight otherStraight = (Straight) other;
    return this.highestCard.compareTo(otherStraight.highestCard);
  }

  @Override
  public String toString() {
    return "Straight from " + lowestCard + " to " + highestCard;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Straight straight = (Straight) o;
    return lowestCard == straight.lowestCard &&
        highestCard == straight.highestCard;
  }

  @Override
  public int hashCode() {
    return Objects.hash(lowestCard, highestCard);
  }
}
