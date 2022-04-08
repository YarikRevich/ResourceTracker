import configuredAxios from "./common"
import { SentData } from "./../types"

import messagePublisher from "messagepublisher"

export const deleteProjectDELETE = async (d: SentData.deleteProject): Promise<boolean | void> => {
	try {
		const r = await configuredAxios.get("/deleteProject")
		return r.data.service.ok
	} catch (error: any) {
		messagePublisher.add(error.message)
	}
}

export const checkProjectStatusGET = async (): Promise<boolean | void> => {
	try {
		const r = await configuredAxios.get("/checkProjectStatus")
		return r.data.service.ok
	} catch (error: any) {
		messagePublisher.add(error.message)
	}
}

export const getProjectsGET = async (): Promise<boolean | void> => {
	try {
		const r = await configuredAxios.get("/getProjects")
		return r.data.service.ok
	} catch (error: any) {
		messagePublisher.add(error.message)
	}
}
