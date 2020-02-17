package com.dpancerz.poker;

import static java.util.UUID.randomUUID;

import com.dpancerz.cards.Card;

class Player {
  private final Hand hand;
  private final String name;

  Player() {
    this(new Hand(), randomUUID().toString().substring(6));
  }

  Player(Hand hand, final String name) {
    this.hand = hand;
    this.name = name;
  }

  void drawCard(final Card card) {
    hand.add(card);
    //CardDrawn
  }


}
