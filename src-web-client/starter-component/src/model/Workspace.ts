/* eslint-disable lines-between-class-members */
import { User } from './User.js';
import { Action } from './Action.js';

export class Workspace {
	
	private _id: number | undefined;
    public get id(): number | undefined { return this._id; }
    
	private _user: User | undefined;
    public get user(): User | undefined { return this._user; }
	
	private _dark_mode: boolean = false;
    public get dark_mode(): boolean { return this._dark_mode; }
    public set dark_mode(value: boolean) { this._dark_mode = value; }
    
    private _actions: Action[] = [];
    public get actions(): Action[] { return this._actions; }
    public set actions(value: Action[]) { this._actions = value; }
    
}