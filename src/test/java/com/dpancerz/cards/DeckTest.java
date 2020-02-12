package com.dpancerz.cards;


import static com.dpancerz.cards.Rank.EIGHT;
import static com.dpancerz.cards.Rank.TWO;
import static com.dpancerz.cards.Suit.CLUBS;
import static com.dpancerz.cards.Suit.HEARTS;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DeckTest {
  private Deck deck;

  @Test
  void shuffle_should_not_break_deck_size() {
    // given
    deck = Deck.ofTwentyFourCards();

    // when
    deck.shuffle();

    //then
    //System.out.println(deck.toString());
    assertEquals(24, deck.size());
  }

  @Test
  void draw_one_should_remove_top_card_from_deck() {
    // given
    deck = Deck.ofFiftyTwoCards();

    // when
    Card drawnCard = deck.drawOne();

    // then
    // System.out.println(drawnCard);
    assertEquals(51, deck.size());
    assertTrue(deck.doesNotContain(drawnCard));
  }

  @Test
  void should_not_draw_when_deck_is_empty() {
   // given
    deck = new Deck(new ArrayList<>());

    // expect
    assertThrows(DrawFromEmptyDeck.class,
        // when
        deck::drawOne);
  }

  @Test
  void smallest_deck_should_not_contain_low_rank_cards() {
    // given
    deck = Deck.ofTwentyFourCards();
    Card eightOfClubs = new Card(EIGHT, CLUBS);
    Card twoOfHearts = new Card(TWO, HEARTS);
    final List<Card> outsideOfTheDeck = asList(eightOfClubs, twoOfHearts);

    // when
    boolean result = deck.doesNotContainAnyOf(outsideOfTheDeck);

    //then
    assertTrue(result);
  }
}
