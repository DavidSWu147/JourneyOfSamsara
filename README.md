# JourneyOfSamsara

Journey of Samsara is a simple, self-invented game, where the entire game state can be encoded in 12 bits, and every turn you have at most 2 options for moves (forwards or backwards).

The board has 32 spaces. 0 is the start space, 1~10 form the outer ring, 11~20 form the medial ring, 21~30 form the inner ring, and * (or 31) is the end space.

Basically, every turn you roll one 6-sided die and move that many spaces forwards or backwards, with the exception of 6, which keeps you on the same space. The catch is that you can only advance to the next ring by starting your move on a space that is a multiple of 10, and also you cannot go backwards over the border between 1 and 10 (or 11 and 20 or 21 and 30) unless you have already moved forwards over that border, or rolled 6. 

Finally, if both players end on a space in the same sector (same one's digit), a clash occurs. Each player rolls off and the loser must go all the way back to 0, ties going to the mover. 

Despite its simplicity, the game is not at all trivial to solve for the optimal strategy.
