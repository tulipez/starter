/* eslint-disable lines-between-class-members */
import { createContext } from '@lit/context';
import { Action } from '../model/Action.js';
import { WorkspaceService } from './WorkspaceService.js';

export class ActionService {
	
	private _workspaceService: WorkspaceService;
	
	constructor(workspaceService: WorkspaceService) {
		this._workspaceService = workspaceService;
	}
	
	create(specif: {name: String}) {
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
				
				console.log(action);
				console.log(currentWorkspace?.actions);
				
				currentWorkspace?.actions.push(action);
	            resolve(action);
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            reject(new Error("ActionService.create failed"));
	        });
		});
	}
	
}
export const actionServiceContext = createContext<ActionService>('actionService');

