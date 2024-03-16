/* eslint-disable lines-between-class-members */
import { createContext } from '@lit/context';
import { HttpClient } from '../HttpClient.js';
import { Workspace } from '../model/Workspace.js';

export class WorkspaceService {
	
	private httpClient: HttpClient;
	
	private _currentWorkspace: Workspace | undefined;
	get currentWorkspace() : Workspace | undefined { return this._currentWorkspace; }
	
	constructor(httpClient: HttpClient) {
		this.httpClient = httpClient;
	}
	
	async updateCurrentWorkspace() {
		if(!this._currentWorkspace) return undefined;
		return this.httpClient.put<Workspace>('/api/workspace', this._currentWorkspace);
	}
	
	// TODO ne pas charger tout le contenu, principalement la liste des actions.
	// faire du lazy load
	async loadCurrentWorkspace() {
		return this.httpClient.get<Workspace>('/api/workspace').then(workspace => {
	        this._currentWorkspace = workspace || undefined;
        });
	}
	
}
export const workspaceServiceContext = createContext<WorkspaceService>('workspaceService');

