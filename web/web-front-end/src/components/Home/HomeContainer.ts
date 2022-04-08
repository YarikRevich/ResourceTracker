import React, { Dispatch } from "react"
import { Home } from "./Home"
import { compose } from "redux"
import { connect } from "react-redux"
import type { State } from "../../types"

import { createGetProjects, createCheckSelectedProjectStatus, createSetSelectedProjectStatusUnchecked } from "../../redux/home-reducer"

const mapStateToProps = (state: State) => {
    return ({
		projects: state.home.projects,
		selectedProjectStatus: state.home.selectedProjectStatus,
		selectedProjectStatusChecked: state.home.selectedProjectStatusChecked,
    })
}

const mapDispatchToProps = (dispatch: Dispatch<any>) => {
    return ({
		getProjects: () => {
			dispatch(createGetProjects())
		},
		deleteProject: () => {
			console.log("deleteProject")
		},
		checkSelectedProjectStatus: (projectName: string) => {
			dispatch(createCheckSelectedProjectStatus());
		},
		setSelectedProjectStatusUnchecked: () => {
			dispatch(createSetSelectedProjectStatusUnchecked());
		}
    })
}

export default compose(connect(mapStateToProps, mapDispatchToProps))(Home)
