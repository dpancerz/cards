package com.dpancerz.poker;

import com.dpancerz.poker.hands.Hand;
import com.dpancerz.poker.hands.HandSpy;
import java.util.HashSet;

public class PlayerSpy extends Player {
  public PlayerSpy(final Hand hand, final String name) {
    super(hand, name);
  }

  public PlayerSpy(final String name) {
    this(new HandSpy(new HashSet<>()), name);
  }
}
