package com.dpancerz.game;

import static java.util.UUID.randomUUID;

import com.dpancerz.cards.Card;

public class Player {
  private final Hand hand;
  private final String name;

  public Player() {
    this(new Hand(), randomUUID().toString().substring(6));
  }

  protected Player(Hand hand, final String name) {
    this.hand = hand;
    this.name = name;
  }

  public void drawCard(final Card card) {
    hand.add(card);
  }
}
