package com.dpancerz.poker.hands;

import com.dpancerz.cards.Card;
import java.util.HashSet;
import java.util.Set;

public class Hand {
  private final Cards cards;
  private final RankFinder rankFinder;

  public Hand() {
    this(new HashSet<>());
  }

  Hand(final Set<Card> cards) {
    this(cards, new RankFinder(new Matchers()));
  }

  private Hand(final Set<Card> cards, final RankFinder rankFinder) {
    this.cards = new Cards(cards);
    this.rankFinder = rankFinder;
  }

  public void add(final Card card) {
    cards.add(card);
    //CardAdded
  }

  int size() {
    return cards.size();
  }

  PokerRank rank() {
    return rankFinder.findRank(cards);
  }
}
