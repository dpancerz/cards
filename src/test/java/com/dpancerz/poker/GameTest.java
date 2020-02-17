package com.dpancerz.poker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GameTest {
  @Test
  void game_should_not_be_started_after_creation() {
    // when
    Game game = new Game();

    // then
    assertFalse(game.isStarted());
  }

  @Test
  void should_not_start_with_zero_players() {
    // given
    Game game = new Game();

    // expect
    assertThrows(NumberOfPlayersOutOfBounds.class,
        // when
        game::start);
  }

  @Test
  void should_not_start_with_one_player() {
    // given
    Game game = new Game();
    game.join(new Player());

    // expect
    assertThrows(NumberOfPlayersOutOfBounds.class,
        // when
        game::start);
  }

  @Test
  void should_start_with_two_players() {
    // given
    Game game = new Game();
    game.join(new PlayerSpy("Kuba"));
    game.join(new PlayerSpy("Jacek"));

    // when
    game.start();

    // then
    assertThat(game.isStarted()).isTrue();
  }

  @Test
  void should_deal_the_cards_to_players() {
    // given
    Game game = new Game();
    final HandSpy kubasHand = new HandSpy();
    final HandSpy jaceksHand = new HandSpy();
    final Player kuba = new PlayerSpy(kubasHand,"Kuba");
    final Player jacek = new PlayerSpy(jaceksHand, "Jacek");
    game.join(kuba);
    game.join(jacek);
    game.start();

    // when
    game.dealCards();

    // then
    assertEquals(5, kubasHand.size());
    assertEquals(5, jaceksHand.size());
    assertEquals(42, game.deckSize());
  }
}
