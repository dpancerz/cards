package com.dpancerz.poker;

import com.dpancerz.cards.Deck;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

class Game {
  public static final int MINIMUM_NUMBER_OF_PLAYERS = 2;
  private static final int MAXIMUM_NUMBER_OF_PLAYERS = 10;
  private static final int NUMBER_OF_CARDS_PER_PLAYER = 5;
  private final Set<Player> players;
  private final Deck deck;
  private boolean started;

  Game(final Deck deck) {
    this.started = false;
    this.players = new HashSet<>();
    this.deck = deck;
  }

  Game() {
    this(Deck.ofFiftyTwoCards());
  }

  void start() {
    if (players.size() < MINIMUM_NUMBER_OF_PLAYERS
        || players.size() > MAXIMUM_NUMBER_OF_PLAYERS) {
      throw new NumberOfPlayersOutOfBounds(players.size());
    }
    setStarted(true);
    //post(new GameStarted(players.*id));
  }

  private void setStarted(final boolean started) {
    this.started = started;
  }

  void join(final Player player) {
    players.add(player);
  }

  boolean isStarted() {
    return started;
  }

  void dealCards() {
    IntStream.rangeClosed(1, NUMBER_OF_CARDS_PER_PLAYER)
        .forEach(cardNumber -> players
            .forEach(player -> player
                .drawCard(deck.drawOne())));
  }

  int deckSize() {
    return deck.size();
  }
}
