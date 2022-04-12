import { createStore, combineReducers, Reducer, applyMiddleware } from "redux";
import thunk from "redux-thunk"
import appReducer from "./app-reducer"
import homeReducer from "./home-reducer"
import editReducer from "./edit-reducer"
import configureStore from 'redux-mock-store'

let reducers = combineReducers<Reducer>({
	app: appReducer,
	home: homeReducer,
	edit: editReducer,
})


const initialStore = {
	app: { initialized: true },
	home: {
		projects: [{
			name: "example_1",
			description: "test",
		}],
		deleteProjectComplete: false,
		selectedProjectStatus: false,
		selectedProjectStatusChecked: false,
	},
	edit: {
		project: {
			name: "example_1",
			description: "test",
		},
		cloudProviders: ["aws", "az", "gcp"],
		frequencyModifiers: ["d", "m", "s"],
	}
}



export const store = createStore(reducers, applyMiddleware(thunk));
export const testStore = configureStore([thunk])(initialStore)
