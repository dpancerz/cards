package com.dpancerz.poker;

import static com.dpancerz.cards.Rank.JACK;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dpancerz.cards.Card;
import com.dpancerz.cards.TestCardMapper;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PokerHandTest {
  private Hand hand;

  @Test
  void should_identify_pair() {
    // given
    hand = handOf("4❤️", "7♣️", "9❤️", "J❤️", "J♣️");

    // when
    final Rank rank = hand.rank();

    // then
    assertEquals(OnePair.of(JACK), rank);
  }

  private Hand handOf(String... cardIds) {
    final Set<Card> cards = stream(cardIds)
        .map(TestCardMapper::cardFor)
        .collect(toSet());
    return new Hand(new RankMatcher(), cards);
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
