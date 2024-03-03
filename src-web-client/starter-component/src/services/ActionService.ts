/* eslint-disable lines-between-class-members */
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
	
	createAction(specif: {name: String}) {
		return new Promise<Action>((resolve, reject) => {
			fetch('/api/action', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify(specif)
			})
			.then(response => response.ok ? response.json() : undefined)
	        .then(data => {
				const action: Action = data;
				const currentWorkspace = this._workspaceService?.currentWorkspace;
				currentWorkspace?.actions.push(action);
				resolve(action);
				this.observable.notify(EVENT_ACTION_CREATED, action);
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            reject(new Error("ActionService.create failed"));
	        });
		});
	}
	
}
export const actionServiceContext = createContext<ActionService>('actionService');

