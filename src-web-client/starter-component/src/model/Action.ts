/* eslint-disable lines-between-class-members */
export class Action {
	
	private _id: number | undefined;
    public get id(): number | undefined { return this._id; }
    
	private _name: string = "";
    public get name(): string { return this._name; }
    public set name(value: string) { this._name = value; }
    
	private _ratingValue: number | undefined;
    public get ratingValue(): number | undefined { return this._ratingValue; }
    public set ratingValue(value: number | undefined) { this._ratingValue = value; }
    
	private _date: number | undefined;
    public get date(): number | undefined { return this._date; }
    public set date(value: number | undefined) { this._date = value; }
}