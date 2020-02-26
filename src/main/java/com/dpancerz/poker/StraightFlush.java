package com.dpancerz.poker;

import static com.dpancerz.poker.Hands.STRAIGHT_FLUSH;

import com.dpancerz.cards.Rank;
import com.dpancerz.cards.Suit;
import java.util.List;
import java.util.Objects;

class StraightFlush extends PokerRank {
  private final Straight straight;
  private final Flush flush;

  StraightFlush(final Straight straight, final Flush flush) {
    this.straight = straight;
    this.flush = flush;
  }

  static StraightFlush fromCardsInSuit(final List<Rank> cards, final Suit suit) {
    final Flush flush = new Flush(suit, cards);
    final Rank lowestCard = flush.highestCard(5);

    return new StraightFlush(Straight.startingFrom(lowestCard), flush);
  }

  @Override
  Hands rank() {
    return STRAIGHT_FLUSH;
  }

  Rank from() {
    return straight.from();
  }

  Rank to() {
    return straight.to();
  }

  @Override
  public int compareTo(final PokerRank other) {
    if (!(other instanceof StraightFlush)) {
      return 1;
    }
    final StraightFlush otherStraightFlush = (StraightFlush) other;
    return this.straight.compareTo(otherStraightFlush.straight);
  }

  @Override
  public String toString() {
    return "Straight Flush from " + from() + " to " + to() + " in " + flush.suit();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final StraightFlush other = (StraightFlush) o;
    return this.flush.equals(other.flush)
        && this.straight.equals(other.straight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flush, straight);
  }
}
