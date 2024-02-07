import {useContext, useState} from "react";
import * as classNames from "classnames";
import {GameStateContext} from "../hooks/useGameState.ts";
import "./GameField.css"

export function GameField() {
    const [hoveredColumn, setHoveredColumn] = useState<number | null>(null);

    const {
        gameState,
        doTurn,
        player,

    } = useContext(GameStateContext)

    if (!gameState?.field){
        return <></>
    }

    return (<div className="board">
            {gameState.field.map((row, rowIndex) => (
                <div key={rowIndex} className="row">
                    {row.map((cell, colIndex) => (
                        <div
                            key={colIndex}
                            className={classNames("cell", cell?.toLowerCase(), {hover: hoveredColumn === colIndex})}
                            onClick={() => {
                                if (gameState.currentPlayer === player) doTurn(colIndex)
                            }}
                            onMouseOver={() => setHoveredColumn(colIndex)}
                            onMouseOut={() => setHoveredColumn(null)}
                        >
                        </div>
                    ))}
                </div>
            ))}
        </div>
    )
}