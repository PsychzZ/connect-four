import {useContext} from "react";
import {GameField} from "../components/GameField.tsx";
import {GameStateContext} from "../hooks/useGameState.ts";
import {PlayerDisplay} from "../components/PlayerDisplay.tsx";

export function Game() {
    const {gameID, player, gameState, restartGame} = useContext(GameStateContext)
    if (gameState === undefined){
        return <></>
    }
    return (
        <div className="connect-four">
            <div className="game">
                {gameID === null || gameID === undefined
                    ? <h2>Welcome to Connect four</h2>
                    : <h2>You are currently in game <br></br>
                        The Game ID is: {gameID}
                    </h2>
                }
            </div>
            <GameField/>
            <div>
                <div>
                    {gameState.winner === null ? <>
                            <h2>You Play as <PlayerDisplay player={player}/></h2>
                            <br/>
                            <h2>its <PlayerDisplay player={gameState.currentPlayer}/> turn</h2>
                            <br/>
                        </>
                        :
                        <div>
                            <h2>You Play as <PlayerDisplay player={player}/></h2>
                            <br/>
                            <h2>The Winner is <PlayerDisplay player={gameState.winner}/></h2>
                            <br/>
                            <button onClick={restartGame}>Restart Game</button>
                        </div>
                    }
                </div>
            </div>
        </div>
    );
}

