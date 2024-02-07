import axios, {AxiosRequestConfig} from "axios";

const SERVER_URL: string = `http://${document.location.hostname}:8080/viergewinnt/`

export async function get<T>(url: string) {
    const response = await axios.get<T>(SERVER_URL + url);
    return response.data
}


export async function post<T>(url: string, body: any, config: AxiosRequestConfig) {
    const response = await axios.post<T>(SERVER_URL + url, body, config);
    return response.data
}