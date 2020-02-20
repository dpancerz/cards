package com.dpancerz.poker;

import static com.dpancerz.cards.Rank.JACK;
import static com.dpancerz.cards.Rank.QUEEN;
import static com.dpancerz.cards.Rank.SEVEN;
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
  void should_identify_high_card_only() {
    // given
    hand = handOf("4❤️", "7♣️", "9❤️", "J❤️", "Q♣️");

    // when
    final PokerRank pokerRank = hand.rank();

    // then
    assertEquals(HighCard.of(QUEEN), pokerRank);
  }

  private Hand handOf(String... cardIds) {
    final Set<Card> cards = stream(cardIds)
        .map(TestCardMapper::cardFor)
        .collect(toSet());
    return new Hand(cards);
  }


//"❤️"
//"♦️"
//"♣️"
//"♠️"
//"A"
//"K"
//"Q"
//"J"
//"10"
//"9"
//"8"
//"7"
//"6"
//"5"
//"4"
//"3"
//"2"
}
