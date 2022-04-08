import type { Dispatch } from "react"
import { Reducers } from "../types"

const initialState = {
    initialized: false as boolean
}

type initialStateType = typeof initialState

const appReducer = (state: initialStateType = initialState, action: Reducers.AppReducer.IAppReducer) => {
    switch (action.type) {
        case Reducers.AppReducer.INITIALIZED_SUCCESS:
            return { ...state, initialized: true }
    }

    return state
}

export const createInitialized = () => (dispatch: Dispatch<any>) => {
    dispatch(createInitializedSuccess())
}

const createInitializedSuccess = (): Reducers.AppReducer.IAppReducer => {
    return { type: Reducers.AppReducer.INITIALIZED_SUCCESS }
}

export default appReducer
