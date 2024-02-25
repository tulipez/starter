/* eslint-disable lines-between-class-members */
import { Action } from './Action.js';

export class Workspace {
	
	private _dark_mode: boolean = false;
    public get dark_mode(): boolean { return this._dark_mode; }
    public set dark_mode(value: boolean) { this._dark_mode = value; }
    
    private _actions: Action[] = [];
    public get actions(): Action[] { return this._actions; }
    public set actions(value: Action[]) { this._actions = value; }
    
}