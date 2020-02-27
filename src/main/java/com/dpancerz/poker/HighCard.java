package com.dpancerz.poker;

import static com.dpancerz.poker.Hands.HIGH_CARD;
import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

import com.dpancerz.cards.Card;
import com.dpancerz.poker.Hand.Matcher;

class HighCard extends PokerRank {
  private final com.dpancerz.cards.Rank rank;

  HighCard(final com.dpancerz.cards.Rank rank) {
    this.rank = requireNonNull(rank);
  }

  static HighCard of(final com.dpancerz.cards.Rank rank) {
    return new HighCard(rank);
  }

  @Override
  public Hands rank() {
    return HIGH_CARD;
  }

  @Override
  public int compareTo(final PokerRank other) {
    if ( !(other instanceof HighCard) ) {
      return super.compareTo(other);
    }
    return rank.compareTo(((HighCard) other).rank);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final HighCard highCard = (HighCard) o;
    return rank == highCard.rank;
  }

  @Override
  public int hashCode() {
    return hash(rank);
  }

  @Override public String toString() {
    return "HighCard: " + rank;
  }

  static class Matcher implements Hand.Matcher {
    @Override
    public Hands handRank() {
      return HIGH_CARD;
    }

    @Override
    public PokerRank rank(final Hand hand) {
      final Card card = hand.highestCard()
          .orElseThrow(() -> new RuntimeException("Empty hand not allowed?"));
      return new HighCard(card.rank());
    }

    @Override
    public boolean matches(final Hand hand) {
      return true;
    }
  }
}
