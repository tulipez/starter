/* eslint-disable lines-between-class-members */
import { createContext } from '@lit/context';
import { Workspace } from '../model/Workspace.js';
import { UserService } from './UserService.js';

export class WorkspaceService {
	
	private _userService: UserService;
	
	get currentWorkspace() : Workspace | undefined { return this._userService?.currentUser?.workspace; }
	
	constructor(userService: UserService) {
		this._userService = userService;
	}
	
	saveCurrentWorkspace() {
		return new Promise<void>((resolve, reject) => {
			fetch('/api/workspace', {
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify(this.currentWorkspace)
			})
	        .then(response => {
				if(!response.ok) reject(new Error("saveCurrentWorkspace failed"));
				else resolve();
			})
	        .catch(error => {
	            console.error('Error:', error);
	            reject(new Error("saveCurrentWorkspace failed"));
	        });
		});
	}
	
}
export const workspaceServiceContext = createContext<WorkspaceService>('workspaceService');

