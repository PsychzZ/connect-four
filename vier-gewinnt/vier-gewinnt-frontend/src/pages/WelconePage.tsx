import {GameStateContext} from "../hooks/useGameState.ts";
import {useContext, useState, KeyboardEvent} from "react";
import "./WelcomePage.css"

export function WelcomePage() {
    const {joinGame, createNewGame} = useContext(GameStateContext)
    const [gameID, setGameID] = useState("")

    const handleKeyDown = (event :  KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter'){
            joinGame(gameID)
        }
    }

    return (
        <div>
            <h1 className="dark-theme-title">Welcome to Connect Four</h1>
            <div className="dark-theme-input-container">
                <input className="dark-theme-input" onKeyDown={handleKeyDown}
                       onChange={(e) => setGameID(e.currentTarget.value)}/>

                <button className="dark-theme-button" onClick={() => joinGame(gameID)}>Join Game</button>
            </div>
            <button className="dark-theme-button" onClick={createNewGame}>
                Create New Game
            </button>
        </div>
    )
}