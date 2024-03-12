/* eslint-disable lines-between-class-members */
import axios from 'axios';
import { createContext } from '@lit/context';
import { Observable } from '../controllers/Observable.js';
import { IObserver } from '../controllers/IObserver.js';
import { Action } from '../model/Action.js';
import { WorkspaceService } from './WorkspaceService.js';

export const EVENT_ACTION_CREATED = "action created";

export class ActionService {
	
	private _workspaceService: WorkspaceService;
	
	observable = new Observable<Action>();
	
	constructor(workspaceService: WorkspaceService) {
		this._workspaceService = workspaceService;
	}
	
	subscribe(observer: IObserver<Action>) {
        this.observable.subscribe(observer);
    }

    unsubscribe(observer: IObserver<Action>) {
        this.observable.unsubscribe(observer);
    }
    
    createAction(specif: {name: string, series: string}) {
		return axios.post<Action>('/api/action', specif)
		.then(res => {
	    	const action: Action = res.data;
			const currentWorkspace = this._workspaceService?.currentWorkspace;
			currentWorkspace?.actions.push(action);
			this.observable.notify(EVENT_ACTION_CREATED, action);
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
	
	updateAction(action: Action) {
		return axios.put<Action>('/api/action', action)
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
export const actionServiceContext = createContext<ActionService>('actionService');

