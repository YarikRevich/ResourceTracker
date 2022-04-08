import { createStore, combineReducers, Reducer, applyMiddleware } from "redux";
import thunk from "redux-thunk"
import appReducer from "./app-reducer"
import homeReducer from "./home-reducer"
import editReducer from "./edit-reducer"

let reducers = combineReducers<Reducer>({
    app: appReducer,
	home: homeReducer,
	edit: editReducer,
})

export const store = createStore(reducers, applyMiddleware(thunk));
export const testStore = createStore(reducers, applyMiddleware(thunk));
