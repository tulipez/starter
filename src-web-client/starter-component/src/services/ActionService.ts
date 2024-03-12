/* eslint-disable lines-between-class-members */
import { createContext } from '@lit/context';
import { HttpClient } from '../HttpClient.js';
import { Observable } from '../Observable.js';
import { IObserver } from '../IObserver.js';
import { Action } from '../model/Action.js';
import { WorkspaceService } from './WorkspaceService.js';

export const EVENT_ACTION_CREATED = "action created";

export class ActionService {
	
	private httpClient: HttpClient;
	private _workspaceService: WorkspaceService;
	
	observable = new Observable<Action>();
	
	constructor(httpClient: HttpClient, workspaceService: WorkspaceService) {
		this.httpClient = httpClient;
		this._workspaceService = workspaceService;
	}
	
	subscribe(observer: IObserver<Action>) {
        this.observable.subscribe(observer);
    }

    unsubscribe(observer: IObserver<Action>) {
        this.observable.unsubscribe(observer);
    }

    async createAction(specif: {name: string, series: string}) {
		return this.httpClient.post<Action>('/api/action', specif).then(action => {
			if(!action) return;
			const currentWorkspace = this._workspaceService?.currentWorkspace;
			currentWorkspace?.actions.push(action);
			this.observable.notify(EVENT_ACTION_CREATED, action);
	    });
	}

	async updateAction(action: Action) {
		if(!action) return undefined;
		return this.httpClient.put<Action>('/api/action', action);
	}
	
}
export const actionServiceContext = createContext<ActionService>('actionService');

