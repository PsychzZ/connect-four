import {useEffect} from "react";

export function useInterval(func: () => void, ms: number, active: boolean) {
    useEffect(() => {
        if (!active){
            return
        }
        const intervalID = setInterval(func, ms)

        return () => {
            clearInterval(intervalID)
        }
    },[func, ms, active])
}