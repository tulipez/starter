/* eslint-disable lines-between-class-members */
export class Action {
	
	private _id: number | undefined;
    public get id(): number | undefined { return this._id; }
    public set id(value: number | undefined) { this._id = value; }
    
	private _name: string = "";
    public get name(): string { return this._name; }
    public set name(value: string) { this._name = value; }
    
}