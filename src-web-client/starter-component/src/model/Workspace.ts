/* eslint-disable lines-between-class-members */
import { User } from './User.js';
import { Action } from './Action.js';

export class Workspace {
	
	private _id: number | undefined;
    public get id(): number | undefined { return this._id; }
    
	private _user: User | undefined;
    public get user(): User | undefined { return this._user; }
	
	private _darkMode: boolean = false;
    public get darkMode(): boolean { return this._darkMode; }
    public set darkMode(value: boolean) { this._darkMode = value; }
    
    private _actions: Action[] = [];
    public get actions(): Action[] { return this._actions; }
    public set actions(value: Action[]) { this._actions = value; }
    
}