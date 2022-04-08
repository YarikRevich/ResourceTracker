import React, { Dispatch } from "react"
import { Edit } from "./Edit"
import { compose } from "redux"
import { connect } from "react-redux"
import type { State } from "../../types"
import {createSaveChanges, createGetCloudProviders, createGetFrequencyModifiers} from "../../redux/edit-reducer";

const mapStateToProps = (state: State) => {
	return ({
		project: state.edit.project,
		cloudProviders: state.edit.cloudProviders,
		frequencyModifiers: state.edit.frequencyModifiers,
    })
}

const mapDispatchToProps = (dispatch: Dispatch<any>) => {
    return ({
		saveChanges: () => {
			dispatch(createSaveChanges())
		},
		getCloudProviders: () => {
			dispatch(createGetCloudProviders())
		},
		getFrequencyModifiers: () => {
			dispatch(createGetFrequencyModifiers())
		}
    })
}

export default compose(connect(mapStateToProps, mapDispatchToProps))(Edit)
