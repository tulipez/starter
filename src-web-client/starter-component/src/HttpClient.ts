import axios from 'axios';
// import { parseISO } from "date-fns";

export class HttpClient {

	async get<T> (url: string): Promise<void | T> {
		return axios.get<T>(url)
	      .then(res => res.data)
	      .catch(this.handleError);
	}
	
	async put<T> (url: string, object: T): Promise<void> {
		return axios.put<T>(url, object)
		.then(() => undefined)
	    .catch(this.handleError);
	}
	
	async post<T> (url: string, object: Object): Promise<void | T> {
		return axios.post<T>(url, object)
		.then(res => res.data)
	    .catch(this.handleError);
	}
	
	private handleError(error: any) {
		if (error.response) {
			// la requête a été faite et le code de réponse du serveur n’est pas dans
			// la plage 2xx
			console.log(error.response.data);
			console.log(error.response.status);
			console.log(error.response.headers);
		} else if (error.request) {
			// la requête a été faite mais aucune réponse n’a été reçue
			console.log(error.request);
		} else {
			// quelque chose s’est passé lors de la construction de la requête et cela
			// a provoqué une erreur
			console.log('Error', error.message);
		}
		console.log(error.config);
	}
	
}
