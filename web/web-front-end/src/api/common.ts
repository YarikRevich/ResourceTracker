import axios from "axios"

const i = axios.create({
	baseURL: "http://127.0.0.1:8997",
})

export default i
