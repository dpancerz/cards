package com.dpancerz.game;

import com.dpancerz.cards.Card;
import java.util.HashSet;
import java.util.Set;

public class Hand {
  private final Set<Card> cards;

  protected Hand(final Set<Card> cards) {
    this.cards = cards;
  }

  public Hand() {
    this(new HashSet<>());
  }

  protected int size() {
    return cards.size();
  }

  protected Set<Card> cards() {
    return cards;
  }
}
