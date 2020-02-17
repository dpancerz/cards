package com.dpancerz.poker;

import com.dpancerz.cards.Card;
import java.util.HashSet;
import java.util.Set;

class Hand {
  private final RankMatcher matcher;
  private final Set<Card> cards;

  Hand(final RankMatcher matcher, final Set<Card> cards) {
    this.matcher = matcher;
    this.cards = cards;
  }

  Hand() {
    this(new RankMatcher(), new HashSet<>());
  }

  int size() {
    return cards.size();
  }

  Set<Card> cards() {
    return cards;
  }

  void add(final Card card) {
    cards.add(card);
    //CardAdded
  }

  Rank rank() {
    return matcher.match(cards());
  }
}
