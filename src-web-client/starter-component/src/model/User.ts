/* eslint-disable lines-between-class-members */

export class User {
	
	private _id: number | undefined;
    public get id(): number | undefined { return this._id; }
    
    private _name: string = "";
    public get name(): string { return this._name; }
    
    private _given_name: string = "";
    public get given_name(): string { return this._given_name; }
    
    private _family_name: string = "";
    public get family_name(): string { return this._family_name; }
    
    private _picture: string = "";
    public get picture(): string { return this._picture; }
    
    private _email: string = "";
    public get email(): string { return this._email; }
    
    private _email_verified: boolean = false;
    public get email_verified(): boolean { return this._email_verified; }
    
	private _locale: string = "";
    public get locale(): string { return this._locale; }
	    
}