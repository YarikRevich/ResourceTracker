export namespace SentData {
	export type deleteProject = {
		projectName: string
	}
}

export namespace ReceivedData {
	export type Project = {
		name: string,
		description: string,
	}
}

export namespace Utils { }

export namespace StateComponenents {
	export interface App {
		initialized: boolean
	}

	export interface Home {
		projects: ReceivedData.Project[];
		selectedProjectStatus: boolean,
		selectedProjectStatusChecked: boolean,
	}

	export interface Edit{
		project: ReceivedData.Project,
		cloudProviders: string[],
		frequencyModifiers: string[]
	}
}

export interface State {
	app: StateComponenents.App
	home: StateComponenents.Home
	edit: StateComponenents.Edit
}

export namespace Components {
	export namespace App {
		export interface AppType {
			initialized: boolean
			initialize: () => void
		}
	}

	export namespace Home {
		export interface HomeType {
			selectedProjectStatus: boolean,
		}
	}

	export namespace Edit {
		export interface EditType {
			projectName: string,
		}
	}
}

export namespace Reducers {
	export namespace AppReducer {
		export interface IAppReducer {
			type: string
		}

		export const INITIALIZED_SUCCESS = "INITIALIZED_SUCCESS";
	}

	export namespace HomeReducer {
		export interface IHomeReducer {
			type: string
		}

		export const DELETE_PROJECT_SUCCESS = "DELETE_PROJECT_SUCCESS";
		export const DELETE_PROJECT_ERROR = "DELETE_PROJECT_ERROR";
		export const CHECK_PROJECT_STATUS_SUCCESS = "CHECK_PROJECT_STATUS_SUCCESS";
		export const CHECK_PROJECT_STATUS_ERROR = "CHECK_PROJECT_STATUS_ERROR";
		export const SET_PROJECT_STATUS_UNCHECKED = "SET_PROJECT_STATUS_UNCHECKED";
		export const GET_PROJECTS_SUCCESS = "GET_PROJECTS_SUCCESS";
		export const GET_PROJECTS_ERROR = "GET_PROJECTS_ERROR";
	}
	export namespace EditReducer {
		export interface IEditReducer {
			type: string
		}

		export const SAVE_CHANGES_SUCCESS = "SAVE_CHANGES_SUCCESS";
		export const SAVE_CHANGES_ERROR = "SAVE_CHANGES_ERROR";
		export const GET_CLOUD_PROVIDERS_SUCCESS = "GET_CLOUD_PROVIDERS_SUCCESS";
		export const GET_CLOUD_PROVIDERS_ERROR = "GET_CLOUD_PROVIDERS_ERROR";
		export const GET_FREQUENCY_MODIFIERS_SUCCESS = "GET_FREQUENCY_MODIFIERS_SUCCESS";
		export const GET_FREQUENCY_MODIFIERS_ERROR = "GET_FREQUENCY_MODIFIERS_ERROR";
	}
}

export namespace HOCs {
	export interface WithAuth {
		isAuthed: boolean
	}
}
