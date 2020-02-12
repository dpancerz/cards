package com.dpancerz.poker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dpancerz.game.HandSpy;
import com.dpancerz.game.NumberOfPlayersOutOfBounds;
import com.dpancerz.game.Player;
import com.dpancerz.game.PlayerSpy;
import org.junit.jupiter.api.Test;

class PokerGameTest {
  @Test
  void game_should_not_be_started_after_creation() {
    // when
    PokerGame game = new PokerGame();

    // then
    assertFalse(game.isStarted());
  }

  @Test
  void should_not_start_with_zero_players() {
    // given
    PokerGame game = new PokerGame();

    // expect
    assertThrows(NumberOfPlayersOutOfBounds.class,
        // when
        game::start);
  }

  @Test
  void should_not_start_with_one_player() {
    // given
    PokerGame game = new PokerGame();
    game.join(new Player());

    // expect
    assertThrows(NumberOfPlayersOutOfBounds.class,
        // when
        game::start);
  }

  @Test
  void should_start_with_two_players() {
    // given
    PokerGame game = new PokerGame();
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
    PokerGame game = new PokerGame();
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
    game.deck();
  }
}
