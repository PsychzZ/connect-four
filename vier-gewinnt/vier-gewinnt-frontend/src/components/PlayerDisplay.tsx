import {Player} from "../types/types.ts";

import "./PlayerDisplay.css"

export type PlayerDisplayProps = {
    player: Player
}

export function PlayerDisplay({player}: PlayerDisplayProps) {
    if (player === "X"){
        return <span className={"redPlayer"}>Red</span>
    }

    if (player === "O"){
        return <span className={"yellowPlayer"}>Yellow</span>
    }

    return <>unknown</>
}
