/* eslint-disable lines-between-class-members */
import { createContext } from '@lit/context';
import { Workspace } from '../model/Workspace.js';

export class WorkspaceService {
	
	private _currentWorkspace: Workspace | undefined;
	get currentWorkspace() : Workspace | undefined { return this._currentWorkspace; }
	
	updateCurrentWorkspace() {
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
	
	loadCurrentWorkspace() {
		return new Promise<void>((resolve, reject) => {
			fetch('/api/workspace', {
				method: 'GET',
				headers: { 'Content-Type': 'application/json' }
			})
	        .then(response => response.ok ? response.json() : undefined)
	        .then(data => {
	            this._currentWorkspace = data;
	            resolve();
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            reject(new Error("login failed"));
	        });
		});
	}
	
}
export const workspaceServiceContext = createContext<WorkspaceService>('workspaceService');

