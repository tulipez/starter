/* eslint-disable lines-between-class-members */
import axios from 'axios';
import { createContext } from '@lit/context';
import { Workspace } from '../model/Workspace.js';

export class WorkspaceService {
	
	// TODO faire un axiosService (init axios + gestion d'erreur + autre...)
	// ou juste un errorHandler generic
	
	private _currentWorkspace: Workspace | undefined;
	get currentWorkspace() : Workspace | undefined { return this._currentWorkspace; }
	
	updateCurrentWorkspace() {
		return axios.put<Workspace>('/api/workspace', this._currentWorkspace)
	    .catch(error => {
        	if (error.response) {
		      // la requête a été faite et le code de réponse du serveur n’est pas dans
		      // la plage 2xx
		      console.log(error.response.data);
		      console.log(error.response.status);
		      console.log(error.response.headers);
		    } else if (error.request) {
		      // la requête a été faite mais aucune réponse n’a été reçue
		      // `error.request` est une instance de XMLHttpRequest dans le navigateur
		      // et une instance de http.ClientRequest avec node.js
		      console.log(error.request);
		    } else {
		      // quelque chose s’est passé lors de la construction de la requête et cela
		      // a provoqué une erreur
		      console.log('Error', error.message);
		    }
		    console.log(error.config);
        });
	}
	
	loadCurrentWorkspace() {
		
		/*
		return new Promise<void>((resolve) => {
			const jsonWorkspace : any = {
				"id":1,
				"user":{
					"id":1,
					"sub":"117969078089563355670",
					"name":"zulut zulut",
					"given_name":"zulut",
					"family_name":"zulut",
					"picture":"https://lh3.googleusercontent.com/a/ACg8ocLiEkAFyacc4bNzuUxCTa09hCVLWHLIqb_IHryPfhWD=s96-c",
					"email":"carlos.potatovaldes75@gmail.com",
					"email_verified":true,
					"locale":"fr"
				},
				"actions":[
					{"id":1,"name":"Marcher"},
					{"id":2,"name":"Pas trop fumer"},
					{"id":2,"name":"Manger léger"}
				],
				"dark_mode":false};
				
			this._currentWorkspace = jsonWorkspace;
			resolve();
		});
		*/
		
		return axios.get<Workspace>('/api/workspace')
	      .then(res => {
	        this._currentWorkspace = res.data;
	      })
	      .catch(error => {
            if (error.response) {
		      // la requête a été faite et le code de réponse du serveur n’est pas dans
		      // la plage 2xx
		      console.log(error.response.data);
		      console.log(error.response.status);
		      console.log(error.response.headers);
		    } else if (error.request) {
		      // la requête a été faite mais aucune réponse n’a été reçue
		      // `error.request` est une instance de XMLHttpRequest dans le navigateur
		      // et une instance de http.ClientRequest avec node.js
		      console.log(error.request);
		    } else {
		      // quelque chose s’est passé lors de la construction de la requête et cela
		      // a provoqué une erreur
		      console.log('Error', error.message);
		    }
		    console.log(error.config);
        });
		
	}
	
}
export const workspaceServiceContext = createContext<WorkspaceService>('workspaceService');

