import configuredAxios from "./common"
import { SentData } from "./../types"

import messagePublisher from "messagepublisher"

export const saveChangesPUT = async (d: SentData.deleteProject): Promise<boolean | void> => {
	try {
		const r = await configuredAxios.put("/saveChanges")
		return r.data.service.ok
	} catch (error: any) {
		messagePublisher.add(error.message)
	}
}

export const getCloudProvidersGET = async (d: SentData.deleteProject): Promise<boolean | void> => {
	try {
		const r = await configuredAxios.get("/getCloudProviders")
		return r.data.service.ok
	} catch (error: any) {
		messagePublisher.add(error.message)
	}
}

export const getFrequencyModifiersGET = async (d: SentData.deleteProject): Promise<boolean | void> => {
	try {
		const r = await configuredAxios.get("/getFrequencyModifiers")
		return r.data.service.ok
	} catch (error: any) {
		messagePublisher.add(error.message)
	}
}
