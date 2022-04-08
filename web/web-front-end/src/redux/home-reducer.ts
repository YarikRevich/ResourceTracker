import type { Dispatch } from "react"
import { Reducers, ReceivedData} from "../types"

import { deleteProjectDELETE, getProjectsGET } from "../api/home";

const initialState = {
	projects: [] as ReceivedData.Project[],
	deleteProjectComplete: false as boolean,
	selectedProjectStatus: false as boolean,
	selectedProjectStatusChecked: false as boolean,
}

type initialStateType = typeof initialState

const homeReducer = (state: initialStateType = initialState, action: Reducers.AppReducer.IAppReducer) => {
	switch (action.type) {
		case Reducers.HomeReducer.DELETE_PROJECT_SUCCESS:
			return { ...state, deleteProjectComplete: true, selectedProjectStatusChecked: true }
		case Reducers.HomeReducer.DELETE_PROJECT_ERROR:
			return { ...state, deleteProjectComplete: false, selectedProjectStatusChecked: true }
		case Reducers.HomeReducer.CHECK_PROJECT_STATUS_SUCCESS:
			return { ...state, selectedProjectStatus: true, selectedProjectStatusChecked: true }
		case Reducers.HomeReducer.CHECK_PROJECT_STATUS_ERROR:
			return { ...state, selectedProjectStatus: false, selectedProjectStatusChecked: true }
		case Reducers.HomeReducer.SET_PROJECT_STATUS_UNCHECKED:
			return { ...state, selectedProjectStatusChecked: false }
		case Reducers.HomeReducer.GET_PROJECTS_SUCCESS:
		case Reducers.HomeReducer.GET_PROJECTS_ERROR:
	}

	return state
}

export const createDeleteProject = () => async (dispatch: Dispatch<any>) => {
	// const ok = await deleteProjectDELETE()
	// if (ok) {
		dispatch(createDeleteProjectSuccess())
	// } else {
		// dispatch(createDeleteProjectError())
	// }
}

const createDeleteProjectSuccess = (): Reducers.AppReducer.IAppReducer => {
	return { type: Reducers.HomeReducer.DELETE_PROJECT_SUCCESS }
}

const createDeleteProjectError = (): Reducers.AppReducer.IAppReducer => {
	return { type: Reducers.HomeReducer.DELETE_PROJECT_ERROR }
}

export const createCheckSelectedProjectStatus = () => async (dispatch: Dispatch<any>) => {
	// const ok = await deleteProjectDELETE()
	// if (ok) {
	dispatch(createCheckSelectedProjectStatusSuccess())
	// } else {
	// dispatch(checkSelectedProjectStatusError())
	// }
}

const createCheckSelectedProjectStatusSuccess = (): Reducers.HomeReducer.IHomeReducer => {
	return { type: Reducers.HomeReducer.CHECK_PROJECT_STATUS_SUCCESS }
}

const createCheckSelectedProjectStatusError = (): Reducers.AppReducer.IAppReducer => {
	return { type: Reducers.HomeReducer.CHECK_PROJECT_STATUS_ERROR }
}

export const createSetSelectedProjectStatusUnchecked = () => {
	return { type: Reducers.HomeReducer.SET_PROJECT_STATUS_UNCHECKED }
}

export const createGetProjects = () => async (dispatch: Dispatch<any>) => {
	const ok = await getProjectsGET()
	if (ok) {
		dispatch(createGetProjectsSuccess())
	} else {
		dispatch(createGetProjectsError())
	}
}

const createGetProjectsSuccess = (): Reducers.AppReducer.IAppReducer => {
	return { type: Reducers.HomeReducer.GET_PROJECTS_SUCCESS }
}

const createGetProjectsError = (): Reducers.AppReducer.IAppReducer => {
	return { type: Reducers.HomeReducer.GET_PROJECTS_ERROR }
}

export default homeReducer
