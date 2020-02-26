package com.dpancerz.poker;

import static com.dpancerz.cards.Rank.ACE;
import static com.dpancerz.cards.Rank.FIVE;
import static com.dpancerz.cards.Rank.JACK;
import static com.dpancerz.cards.Rank.KING;
import static com.dpancerz.cards.Rank.QUEEN;
import static com.dpancerz.cards.Rank.SEVEN;
import static com.dpancerz.cards.Rank.TEN;
import static com.dpancerz.cards.Rank.THREE;
import static com.dpancerz.cards.Suit.CLUBS;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dpancerz.cards.Card;
import com.dpancerz.cards.TestCardMapper;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PokerHandTest {
  private Hand hand;

  @Test
  void should_identify_high_card_only() {
    // given
    hand = handOf("4❤️", "7♣️", "9❤️", "J❤️", "Q♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(HighCard.of(QUEEN), pokerRank);
  }

  @Test
  void should_identify_pair() {
    // given
    hand = handOf("4❤️", "7♣️", "9❤️", "J❤️", "J♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(OnePair.of(JACK), pokerRank);
  }

  @Test
  void should_identify_two_pairs() {
    // given
    hand = handOf("4❤️", "7♣️", "7❤️", "J❤️", "J♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(TwoPair.of(JACK, SEVEN), pokerRank);
  }

  @Test
  void should_identify_three_of_a_kind() {
    // given
    hand = handOf("7♦️", "7♣️", "7❤️", "J❤️", "Q♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(ThreeOfAKind.of(SEVEN), pokerRank);
  }

  @Test
  void should_identify_four_of_a_kind() {
    // given
    hand = handOf("A♦️", "A♣️", "A❤️", "A♠️", "Q♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(FourOfAKind.of(ACE), pokerRank);
  }

  @Test
  void should_identify_a_full_house() {
    // given
    hand = handOf("A♦️", "A♣️", "A❤️", "Q♠️", "Q♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(FullHouse.of(ACE, QUEEN), pokerRank);
  }

  @Test
  void should_identify_a_straight() {
    // given
    hand = handOf("A♦️", "K♣️", "Q♦️", "J❤️", "10♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(Straight.startingFrom(TEN), pokerRank);
  }

  @Test
  void should_identify_a_straight_that_contains_ace_as_lowest_card() {
    // given
    hand = handOf("A♦️", "2♣️", "3♦️", "4❤️", "5♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(Straight.startingFrom(ACE), pokerRank);
  }

  @Test
  void should_identify_a_flush() {
    // given
    hand = handOf("7♣️", "J♣️", "3♣️", "Q♣️", "5♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(Flush.of(CLUBS, SEVEN, JACK, THREE, QUEEN, FIVE), pokerRank);
  }

  @Test
  void should_identify_a_straight_flush() {
    // given
    hand = handOf("A♣️", "K♣️", "Q♣️", "J♣️", "10♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(StraightFlush.fromCardsInSuit(
        asList(ACE, KING, QUEEN, JACK, TEN),
        CLUBS), pokerRank);
  }

  private Hand handOf(String... cardIds) {
    final Set<Card> cards = stream(cardIds)
        .map(TestCardMapper::cardFor)
        .collect(toSet());
    return new Hand(cards);
  }

}
