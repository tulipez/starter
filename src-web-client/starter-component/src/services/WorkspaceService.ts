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

		return new Promise<void>((resolve, reject) => {
			fetch('/api/workspace', {
				method: 'GET',
				headers: { 'Content-Type': 'application/json' }
			})
	        .then(response => response.ok ? response.json() : undefined)
	        .then(data => {
	            this._currentWorkspace = data;
	            console.log(JSON.stringify(this._currentWorkspace));
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

