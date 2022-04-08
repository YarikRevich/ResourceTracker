import type { Dispatch } from "react"
import { Reducers, ReceivedData } from "../types"
import {saveChangesPUT, getCloudProvidersGET, getFrequencyModifiersGET} from "../api/edit";

const initialState = {
	project: [] as ReceivedData.Project[],
	cloudProviders: [] as string[],
}

type initialStateType = typeof initialState

const editReducer = (state: initialStateType = initialState, action: Reducers.EditReducer.IEditReducer) => {
	switch (action.type) {
		case Reducers.EditReducer.SAVE_CHANGES_SUCCESS:
		case Reducers.EditReducer.SAVE_CHANGES_ERROR:
		case Reducers.EditReducer.GET_CLOUD_PROVIDERS_SUCCESS:
		case Reducers.EditReducer.GET_CLOUD_PROVIDERS_ERROR:
		case Reducers.EditReducer.GET_FREQUENCY_MODIFIERS_SUCCESS:
		case Reducers.EditReducer.GET_FREQUENCY_MODIFIERS_ERROR:
	}

	return state
}

export const createSaveChanges = () => async (dispatch: Dispatch<any>) => {
	const ok = await saveChangesPUT()
	if (ok) {
		dispatch(createSaveChangesSuccess())
	} else {
		dispatch(createSaveChangesError())
	}
}

const createSaveChangesSuccess = (): Reducers.EditReducer.IEditReducer => {
	return { type: Reducers.EditReducer.SAVE_CHANGES_SUCCESS }
}

const createSaveChangesError = (): Reducers.AppReducer.IAppReducer => {
	return { type: Reducers.EditReducer.SAVE_CHANGES_ERROR }
}

export const createGetCloudProviders = () => async (dispatch: Dispatch<any>) => {
	const ok = await getCloudProvidersGET()
	if (ok) {
		dispatch(createGetCloudProvidersSuccess())
	} else {
		dispatch(createGetCloudProvidersError())
	}
}

const createGetCloudProvidersSuccess = (): Reducers.EditReducer.IEditReducer => {
	return { type: Reducers.EditReducer.GET_CLOUD_PROVIDERS_SUCCESS }
}

const createGetCloudProvidersError = (): Reducers.EditReducer.IEditReducer => {
	return { type: Reducers.EditReducer.GET_CLOUD_PROVIDERS_ERROR }
}

export const createGetFrequencyModifiers = () => async (dispatch: Dispatch<any>) => {
	const ok = await getFrequencyModifiersGET()
	if (ok) {
		dispatch(createGetFrequencyModifiersSuccess())
	} else {
		dispatch(createGetFrequencyModifiersError())
	}
}

const createGetFrequencyModifiersSuccess = (): Reducers.EditReducer.IEditReducer => {
	return { type: Reducers.EditReducer.GET_FREQUENCY_MODIFIERS_SUCCESS }
}

const createGetFrequencyModifiersError = (): Reducers.EditReducer.IEditReducer => {
	return { type: Reducers.EditReducer.GET_FREQUENCY_MODIFIERS_ERROR }
}

export default editReducer
