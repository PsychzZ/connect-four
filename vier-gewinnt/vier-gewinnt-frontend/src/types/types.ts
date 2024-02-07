export interface GameObject{
    id: string;
    gameState : GameState;
}

export interface GameState{
    currentPlayer: Player;
    draw: boolean;
    field: Player[][];
    winner: Player;
}

export type Player = "O" | "X" | null