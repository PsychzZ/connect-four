import './App.css'
import {GameStateContext, useGameState} from "./hooks/useGameState.ts";
import {WelcomePage} from "./pages/WelconePage.tsx";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import ErrorPage from "./Error-page.tsx";
import {Game} from "./pages/Game.tsx";
import {ReactNode} from "react";

function Provider({children}: {children: ReactNode}) {
    const gameState = useGameState()
    return <GameStateContext.Provider value={gameState} children={children} />
}

const router = createBrowserRouter([
    {
        path: "/",
        element: <Provider><WelcomePage/></Provider>,
        errorElement: <ErrorPage/>,
    },
    {
        path: "/:gameID",
        element: <Provider><Game/></Provider>,
        errorElement: <ErrorPage/>,
    }
])

export function App() {
    return (
        <RouterProvider router={router} />
    );
}
