# Blackjack Strategy Simulator
## Rules:
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

## Simulation example:
### Simulation parameters:
- strategy: *Basic Strategy* based on ***Professional Blackjack*** by Stanford Wong
- hands: $10^9$
- threads: 4
- time: 16min 10sec
### Simulation results:
- Win: $\approx38.3$%
- Lose: $\approx44.9$%
- Push: $\approx7.9$%
- Surrender: $\approx4.37$%
- Blackjack: $\approx4.52$%
- Casino's edge: $\approx0.2508$%
