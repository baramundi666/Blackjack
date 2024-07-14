# Blackjack Strategy Simulator
## Table of contents
1. [Ruleset](#rules)
2. [Simulation examples](#examples)
    1. [Basic Strategy](#1)
    2. [Basic Strategy with card counting and custom insurance pattern](#2)

## Ruleset: <a name="rules"></a>
- *ENHC* variation (European No Hole Card)
- S17 (Dealer Stands on Soft 17)
- *DOA* (Double on Anything)
- *DAS* (Double After Split)
- Split aces receive one card each, and cannot be resplit
- No Blackjack bonus after split
- Lose All to a Natural
- Surrender (A player cannot surrender against the dealer's ace)
- Blackjack pays 3:2
- Insurance pays 2:1

## Simulation examples: <a name="examples"></a>

### 1. Basic Strategy <a name="1"></a>
#### Simulation parameters:
- source: *Basic Strategy* based on ***Professional Blackjack*** by Stanford Wong
- decks: 6
- hands: 1 billion
- threads: 4
- time: 16min 10sec
#### Simulation results:
- Win: 38.3%
- Lose: 44.9%
- Push: 7.9%
- Surrender: 4.37%
- Blackjack: 4.52%
- Casino's edge: 0.2508%

### 2. Basic Strategy with card counting and custom insurance pattern <a name="2"></a>
#### Simulation parameters:
- source: *Basic Strategy* based on ***Professional Blackjack*** by Stanford Wong
- decks: 6
- hands: 100 million
- threads: 4
- time: 1min 44sec
#### Simulation results:
- Win: 38.44%
- Lose: 44.87%
- Push: 8.02%
- Surrender: 4.14%
- Blackjack: 4.53%
- Players's edge: 0.1193%
