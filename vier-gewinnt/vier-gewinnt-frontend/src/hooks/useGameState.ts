import {createContext, useEffect, useState} from "react";
import {GameObject, GameState, Player} from "../types/types.ts";
import {useNavigate, useParams} from "react-router";
import {get, post} from "../utils/useAxios.ts";
import {useInterval} from "./useInterval.ts";

export const GameStateContext = createContext<ReturnType<typeof useGameState>>({
    gameID: undefined,
    setPlayer: () => {
    },
    player: null,
    doTurn() {
    },
    setGameState() {
    },
    gameState: undefined,
    setGameID() {
    },
    createNewGame() {
    },
    async joinGame() {
    },
    restartGame() {
    },
})

export function useGameState() {
    const [gameState, setGameState] = useState<GameState>();
    const [player, setPlayer] = useState<Player>("O")
    const {gameID} = useParams()
    const navigate = useNavigate()


    useEffect(() => {
            if (gameID !== undefined) {
                updateGame()
            } else {
                setGameState(undefined)
            }
        },
        // eslint-disable-next-line react-hooks/exhaustive-deps
        [gameID]
    )

    useInterval(updateGame, 250, gameID !== undefined)

    function updateGame() {
        if (gameID === undefined) {
            return
        }
        get<GameState>(gameID).then(setGameState)
    }

    function createNewGame() {
        setPlayer("X")
        get<GameObject>("newGame").then((res) => setGameID(res.id))
    }

    async function joinGame(gameID: string) {
        setPlayer("O")
        const game = await get<GameState>(gameID)
        console.log(game)
        setGameID(gameID)
    }

    const doTurn = (colIndex: number) => {
        if (gameID !== undefined) {
            post<GameState>(gameID + "/doTurn/", "", {params: {column: colIndex}}).then(setGameState)
        }
    }

    function setGameID(gameID: string) {
        navigate(`/${gameID}`)
    }

    function restartGame() {
        get<GameState>(gameID + "/restart").then(setGameState)
    }

    return {gameID, gameState, setGameState, setGameID, doTurn, player, setPlayer, createNewGame, joinGame, restartGame}
}