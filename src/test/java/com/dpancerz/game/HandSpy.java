package com.dpancerz.game;

import com.dpancerz.cards.Card;
import java.util.HashSet;
import java.util.Set;

public class HandSpy extends Hand {
  public HandSpy() {
    this(new HashSet<>());
  }
  public HandSpy(final Set<Card> cards) {
    super(cards);
  }

  public int size() {
    return super.size();
  }

  public Set<Card> cards() {
    return super.cards();
  }
}
